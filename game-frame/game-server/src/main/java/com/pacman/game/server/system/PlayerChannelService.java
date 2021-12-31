package com.pacman.game.server.system;

import com.pacman.game.server.cache.PlayerCache;
import com.pacman.game.server.pojo.cache.PlayerInfo;
import com.pacman.game.server.service.RoomService;
import com.pacman.network.codec.GameCodecFactory;
import com.pacman.network.codec.IGameMessage;
import com.pacman.network.hamdlermapping.GameChannelContext;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelId;
import io.netty.util.Attribute;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理玩家Id与连接的对应关系，可以通过玩家Id查询到他对应的连接channel，方便给这个玩家发送信息
 * <p>
 * 这里有一点需要注意，即玩家连接与添加可能不在同一个线程之后，比如在网络不好的情况下，玩家的连接A突然断开了
 * 这个时候检测到连接断开，会直接再重新建立一个连接。连接断开时会从channelMap中移除，极限情况下可能新的连接刚加进来，
 * 被旧的断开连接事件给移除掉，导致新连接断开。所以在添加和移除的方法中添加了锁。
 * </p>
 *
 * @Author 王广帅
 * @Date 2021/1/31 23:20
 */
@Service
public class PlayerChannelService {
    private Logger logger = LoggerFactory.getLogger(PlayerChannelService.class);
    public Map<Long, Channel> channelMap = new ConcurrentHashMap<>();
    private Map<Long, String> accountMap = new ConcurrentHashMap<>();
    //移除连接的监听事件，当连接断开时，会执行这块代码
    @Autowired
    PlayerCache playerCache;
    @Autowired
    RoomService roomService;
    private final ChannelFutureListener remover = future -> {
        Channel channel = future.channel();
        Attribute<Long> attribute = channel.attr(GameChannelContext.CHANNEL_PLAYER_ID_ATTR);
        if (attribute != null) {
            long playerId = attribute.get();
            PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId, false);
            if (playerInfo != null) {
                if (playerInfo.getInRoom() == Boolean.TRUE)
                    roomService.exitRoom(playerInfo.getRoomId(), playerId);
                playerCache.save(playerCache.getPlayerInfo(playerId));
                playerCache.removeOne(playerId);
            } else {
                logger.debug("玩家信息已丢失，无法保存");
            }
            logger.debug("连接断开，移除连接:{}->{}", playerId, channel.id());
            remove(playerId, channel.id());
        }
    };

    public long getPlayerId(Channel channel) {
        Attribute<Long> attribute = channel.attr(GameChannelContext.CHANNEL_PLAYER_ID_ATTR);
        long playerId = 0;
        if (attribute != null) {
            playerId = attribute.get();
        }
        return playerId;
    }

    /**
     * 移除某个玩家id对应的channel
     *
     * @param playerId
     * @param channelId
     */
    void remove(Long playerId, ChannelId channelId) {
        if (playerId == null || channelId == null) {
            return;
        }
        String key = playerId.toString().intern();
        synchronized (key) {
            Channel channel = this.channelMap.get(playerId);
            //这里需要判断一下，如果channelId不相同，表示不是想要移除的连接了，就不移除了
            if (channel != null && channel.id() == channelId) {
                Channel removeChannel = this.channelMap.remove(playerId);
                if (removeChannel != null) {
                    removeChannel.closeFuture().removeListener(remover);
                }
            }
        }
    }


    public Channel findChannel(Long playerId) {
        return this.channelMap.get(playerId);
    }

    public void addChannel(Long playerId, Channel channel) {
        String key = playerId.toString().intern();
        synchronized (key) {
            Channel oldChannel = this.channelMap.put(playerId, channel);
            if (oldChannel == null) {//等于null说明新加入的连接是新连接，需要回加上channel断开的监听
                Attribute<Long> attr = channel.attr(GameChannelContext.CHANNEL_PLAYER_ID_ATTR);
                attr.set(playerId);
                channel.closeFuture().addListener(remover);
            } else {
                logger.debug("playerId {} login repeat!", playerId);
                oldChannel.close();
                Attribute<Long> attr = channel.attr(GameChannelContext.CHANNEL_PLAYER_ID_ATTR);
                attr.set(playerId);
                channel.closeFuture().addListener(remover);
            }
        }
    }

    public void broadcast(IGameMessage gameMessage) {
        if (gameMessage != null) {
            try {
                //广播消息不需要加密
                ByteBuf byteBuf = GameCodecFactory.writePushMessage(gameMessage, null);
                ByteBuf sendByteBuf;
                for (Channel channel : this.channelMap.values()) {
                    sendByteBuf = byteBuf.retainedDuplicate();
                    channel.writeAndFlush(sendByteBuf);
                }
            } catch (Exception e) {
                logger.error("消息广播失败", e);
            }
        } else {
            throw new IllegalArgumentException("发送的消息不可以为null");
        }
    }

    /**
     * 向多个连接广播同一个消息。此消息必须是push类型的
     *
     * @param gameMessage
     * @param receivePlayerIds
     */
    public void broadcast(IGameMessage gameMessage, List<Long> receivePlayerIds) {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (gameMessage != null) {
            try {
                //广播消息不需要加密
                ByteBuf byteBuf = GameCodecFactory.writePushMessage(gameMessage, null);
                ByteBuf sendByteBuf;
                Channel channel;
                for (Long playerId : receivePlayerIds) {
                    sendByteBuf = byteBuf.retainedDuplicate();
                    channel = findChannel(playerId);
                    channel.writeAndFlush(sendByteBuf);
                }
                ReferenceCountUtil.release(byteBuf);
            } catch (Exception e) {
                logger.error("消息广播失败", e);
            }
        } else {
            throw new IllegalArgumentException("发送的消息不能为null");
        }
    }

    /**
     * 给某个玩家主动push一条消息。此消息必须是push类型的
     *
     * @param gameMessage
     * @param playerId
     */
    public void pushMessage(IGameMessage gameMessage, Long playerId) {
        if (gameMessage != null) {
            Channel channel = findChannel(playerId);
            if (channel != null && channel.isActive()) {
                byte[] encryptFactors = null;
                if (channel.hasAttr(GameChannelContext.CHANNEL_AES_ENCRYPT_KEY)) {
                    Attribute<byte[]> attr = channel.attr(GameChannelContext.CHANNEL_AES_ENCRYPT_KEY);
                    encryptFactors = attr.get();
                }
                try {
                    ByteBuf byteBuf = GameCodecFactory.writePushMessage(gameMessage, encryptFactors);
                    channel.writeAndFlush(byteBuf);
                } catch (Exception e) {
                    logger.error("消息发送失败", e);
                }
            } else {
                logger.trace("{} 的连接不存在或已关闭", playerId);
            }
        } else {
            throw new IllegalArgumentException("发送的消息不能为null");
        }
    }


}
