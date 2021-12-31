package com.pacman.game.server.gamehandler;

import com.pacman.game.server.cache.PlayerCache;
import com.pacman.game.server.messages.*;
import com.pacman.game.server.system.PlayerChannelService;

import com.pacman.network.hamdlermapping.GameChannelContext;
import com.pacman.network.hamdlermapping.GameHandlerComponent;
import com.pacman.network.hamdlermapping.GameMapping;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author 王广帅
 * @date 2021年02月03日 2:47 下午
 */
@GameHandlerComponent
@Data
public class ChatHandler extends AbstractGameHandler {
    @Autowired
    private PlayerChannelService playerChannelService;

    @Autowired
    private PlayerCache playerCache;
    private String MM_DD_HH_MM_SS = "MM-dd HH:mm:ss";

    private CopyOnWriteArrayList<ChatMessagePush> chatMessagePushes = new CopyOnWriteArrayList<>();

    @GameMapping(ChatRequest.class)
    public void receiveChatRequest(GameChannelContext ctx, ChatRequest request, int seq) {
        logger.debug("{} say:{}", ctx.getPlayerId(), request.getMessage());
        ChatResponse response = new ChatResponse();
        ctx.sendGameMessage(response, seq);
        if (request.getChatType() == 1) {
            //世界聊天，给所有人发送聊天消息
            ChatMessagePush chatMessagePush = new ChatMessagePush();
            chatMessagePush.setPlayerId(ctx.getPlayerId());
            //暂时自定义一下，后面加上用户数据库存储之后再修改。
            chatMessagePush.setName(playerCache.getPlayerInfo(ctx.getPlayerId()).getBaseInfo().getNickname());
            chatMessagePush.setMessage(request.getMessage());
            SimpleDateFormat sdf = new SimpleDateFormat(MM_DD_HH_MM_SS);
            String date = sdf.format(new Date());
            chatMessagePush.setTime(date);
            chatMessagePush.setHead(playerCache.getPlayerInfo(ctx.getPlayerId()).getBaseInfo().getHead());
            chatMessagePushes.add(chatMessagePush);
            playerChannelService.broadcast(chatMessagePush);
        }
    }

    @GameMapping(ChatListRequest.class)
    public void getChatList(GameChannelContext ctx, ChatListRequest request, int seq) {
        ChatListResponse response = new ChatListResponse();
        response.setChatMessagePushes(chatMessagePushes);
        logger.debug("{} get chat list", ctx.getPlayerId());
        if (chatMessagePushes.size() > 1000)
            response.setChatMessagePushes(chatMessagePushes.subList(0, 1000));
        ctx.sendGameMessage(response, seq);
    }

}
