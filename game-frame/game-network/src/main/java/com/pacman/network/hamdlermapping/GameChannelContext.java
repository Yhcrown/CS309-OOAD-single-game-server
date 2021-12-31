package com.pacman.network.hamdlermapping;

import com.pacman.network.codec.GameMessageContext;
import com.pacman.network.codec.GameMessageHeader;
import com.pacman.network.codec.IGameMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * @author 王广帅
 * @date 2021年01月26日 4:26 下午
 */
public class GameChannelContext {
    //channel上面绑定的Player ID 属性
    public static final AttributeKey<Long> CHANNEL_PLAYER_ID_ATTR = AttributeKey.newInstance("channelPlayerIDdAttr");
    // channel上面绑定的加密信息的密钥
    public static final AttributeKey<byte[]> CHANNEL_AES_ENCRYPT_KEY = AttributeKey.newInstance("channelAesEncryptKeyAttr");

//    public static final Attribute<String> CHANNEL_PLAYER_
    private ChannelHandlerContext ctx;
    private GameMessageContext gameMessageContext;


    public GameChannelContext(ChannelHandlerContext ctx,GameMessageContext gameChannelContext) {
        this.ctx = ctx;
        this.gameMessageContext = gameChannelContext;
    }

    public Long getPlayerId(){
        Long playerId = ctx.channel().attr(CHANNEL_PLAYER_ID_ATTR).get();
        return playerId;
    }

    public void sendGameMessage(IGameMessage gameMessage,int seqId) {
        GameMessageContext newContext = newMessageContext(gameMessage,seqId);
        ctx.writeAndFlush(newContext);
    }
    private GameMessageContext newMessageContext(IGameMessage gameMessage,int seqId){
        GameMessageHeader header = gameMessageContext.getHeader();
        header.setSeqId(seqId);
        byte[] encryptFactors = null;
        if(ctx.channel().hasAttr(CHANNEL_AES_ENCRYPT_KEY)) {
            Attribute<byte[]> attr = ctx.channel().attr(CHANNEL_AES_ENCRYPT_KEY);
            encryptFactors = attr.get();
        }
        GameMessageContext newContext = new GameMessageContext(header,encryptFactors);
        newContext.setGameMessage(gameMessage);
        header.setSendTime(System.currentTimeMillis());
        return newContext;
    }

    public void sendError(IGameError error) {
        GameMessageHeader header = gameMessageContext.getHeader();
        header.setErrorCode(error.getErrorCode());
        GameMessageContext newContext = new GameMessageContext(header,null);
        ctx.writeAndFlush(newContext);
    }
    public Channel getChannel(){
        return ctx.channel();
    }
}
