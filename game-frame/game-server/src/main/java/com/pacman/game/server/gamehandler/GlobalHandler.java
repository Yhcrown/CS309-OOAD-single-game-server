package com.pacman.game.server.gamehandler;

import com.pacman.game.server.cache.PlayerCache;
import com.pacman.game.server.messages.ChatMessagePush;
import com.pacman.game.server.messages.FailMessage;
import com.pacman.game.server.messages.SuccessMessage;
import com.pacman.game.server.messages.global.*;
import com.pacman.game.server.messages.status.ErrorStatus;
import com.pacman.game.server.service.GlobalService;
import com.pacman.game.server.system.PlayerChannelService;
import com.pacman.network.hamdlermapping.GameChannelContext;
import com.pacman.network.hamdlermapping.GameHandlerComponent;
import com.pacman.network.hamdlermapping.GameMapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author yhc
 * @create 2021-12-26-13:17
 */
@GameHandlerComponent
public class GlobalHandler extends AbstractGameHandler {

    private String MM_DD_HH_MM_SS = "MM-dd HH:mm:ss";

    @Autowired
    GlobalService globalService;
    @Autowired
    PlayerCache playerCache;
    @Autowired
    ChatHandler chatHandler;
    @Autowired
    PlayerChannelService playerChannelService;

    @GameMapping(CloseServerRequest.class)
    public void closeServer(GameChannelContext ctx, CloseServerRequest request, int seqid) {
        String password = request.getPassword();
        if (password.equals(globalService.getCLOSE_SERVER_PASSWORD())) {
            globalService.setClose(true);
            globalService.setReason(request.getReason());
            playerCache.saveAll();
            ctx.sendGameMessage(new SuccessMessage(), seqid);
        } else
            ctx.sendGameMessage(new FailMessage(ErrorStatus.ADMIN_PASSWORD_WRONG_ERROR.getCode(), ErrorStatus.ADMIN_PASSWORD_WRONG_ERROR.getMsg()), seqid);

    }

    @GameMapping(OpenServerRequest.class)
    public void openServer(GameChannelContext ctx, OpenServerRequest request, int seqid) {
        String password = request.getPassword();
        System.out.println(password.length());
        System.out.println(globalService.getCLOSE_SERVER_PASSWORD().length());
        if (globalService.getCLOSE_SERVER_PASSWORD().trim().equals(password.trim())) {
            globalService.setClose(false);
//            globalService.setReason(request.getReason());
            ctx.sendGameMessage(new SuccessMessage(), seqid);
        } else
            ctx.sendGameMessage(new FailMessage(ErrorStatus.ADMIN_PASSWORD_WRONG_ERROR.getCode(), ErrorStatus.ADMIN_PASSWORD_WRONG_ERROR.getMsg()), seqid);

    }

    @GameMapping(AnnouncementRequest.class)
    public void announcement(GameChannelContext ctx, AnnouncementRequest request, int seqid) {
        String password = request.getPassword();
        if (password.equals(globalService.getCLOSE_SERVER_PASSWORD())) {
            CopyOnWriteArrayList<ChatMessagePush> chatMessagePushes = chatHandler.getChatMessagePushes();
            ChatMessagePush chatMessagePush = new ChatMessagePush();
            chatMessagePush.setHead(-1);
            chatMessagePush.setPlayerId(-1);
            //暂时自定义一下，后面加上用户数据库存储之后再修改。
            chatMessagePush.setName("Announcement");
            chatMessagePush.setMessage(request.getAnnouncement());
            SimpleDateFormat sdf = new SimpleDateFormat(MM_DD_HH_MM_SS);
            String date = sdf.format(new Date());
            chatMessagePush.setTime(date);
            chatMessagePushes.add(chatMessagePush);
            playerChannelService.broadcast(chatMessagePush);
            ctx.sendGameMessage(new SuccessMessage(), seqid);
        } else
            ctx.sendGameMessage(new FailMessage(ErrorStatus.ADMIN_PASSWORD_WRONG_ERROR.getCode(), ErrorStatus.ADMIN_PASSWORD_WRONG_ERROR.getMsg()), seqid);

    }

    @GameMapping(BeforeAnnouncement.class)
    public void announcement(GameChannelContext ctx, BeforeAnnouncement request, int seqid) {
        AnnouncementResponse announcementResponse = new AnnouncementResponse();
        announcementResponse.setContent(globalService.getContent());
        ctx.sendGameMessage(announcementResponse, seqid);
    }
}