package com.pacman.game.server.gamehandler;

import com.pacman.game.server.messages.FailMessage;
import com.pacman.game.server.messages.SuccessMessage;
import com.pacman.game.server.messages.room.*;
import com.pacman.game.server.messages.status.ErrorStatus;

import com.pacman.game.server.pojo.PlayerWithHero;
import com.pacman.game.server.pojo.Room;
import com.pacman.game.server.pojo.RoomInfo;
import com.pacman.game.server.service.RoomService;
import com.pacman.network.hamdlermapping.GameChannelContext;
import com.pacman.network.hamdlermapping.GameHandlerComponent;
import com.pacman.network.hamdlermapping.GameMapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author yhc
 * @create 2021-11-25-22:54
 */
@GameHandlerComponent
public class RoomHandler extends AbstractGameHandler {
    @Autowired
    RoomService roomService;

    @GameMapping(GetRoomListRequest.class)
    public void getRoomInfo(GameChannelContext ctx, GetRoomListRequest request, int seqid) {
        List<RoomInfo> roomInfos = roomService.getRoomInfos();
        RoomListResponse roomListResponse = new RoomListResponse();
        roomListResponse.setInfo(roomInfos);
//        logger.debug("get room list");
        ctx.sendGameMessage(roomListResponse, seqid);
    }

    //TODO:yichangchuli
    @GameMapping(InnerRoomRequest.class)
    public void roomInner(GameChannelContext ctx, InnerRoomRequest request, int seqid) {
        Room room = roomService.getRoom(request.getRoomId());
        List<PlayerWithHero> players = roomService.innerInfo(room.getRoomId());
        RoomPlayerInfo roomPlayerInfo = new RoomPlayerInfo();
        roomPlayerInfo.setPlayerList(players);
        roomPlayerInfo.setRoomInfo(room.getInfo());
        roomPlayerInfo.setManager(room.getManagerId());
        ctx.sendGameMessage(roomPlayerInfo, seqid);
    }

    @GameMapping(EnterRoomRequest.class)
    public void enterRoom(GameChannelContext ctx, EnterRoomRequest request, int seqid) {
        Long roomId = request.getRoomId();
        if (roomId == null) {
            ctx.sendGameMessage(new FailMessage(ErrorStatus.ROOM_ENTER_ERROR3.getCode(), ErrorStatus.ROOM_ENTER_ERROR3.getMsg()), seqid);
            return;
        }
        int code = roomService.enterRoom(request.getRoomId(), ctx.getPlayerId());
        if (code == 1)
            ctx.sendGameMessage(new FailMessage(ErrorStatus.ROOM_ENTER_ERROR1.getCode(), ErrorStatus.ROOM_ENTER_ERROR1.getMsg()), seqid);
        if (code == 2)
            ctx.sendGameMessage(new FailMessage(ErrorStatus.ROOM_ENTER_ERROR2.getCode(), ErrorStatus.ROOM_ENTER_ERROR2.getMsg()), seqid);
        if (code == 3)
            ctx.sendGameMessage(new FailMessage(ErrorStatus.ROOM_ENTER_ERROR3.getCode(), ErrorStatus.ROOM_ENTER_ERROR3.getMsg()), seqid);
        if (code == 5)
            ctx.sendGameMessage(new FailMessage(ErrorStatus.ROOM_ENTER_ERROR4.getCode(), ErrorStatus.ROOM_ENTER_ERROR4.getMsg()), seqid);
        if (code != 4)
            return;
        List<PlayerWithHero> players = roomService.innerInfo(request.getRoomId());
        logger.debug("player {} enter room {}, info:{}", ctx.getPlayerId(), request.getRoomId(), players);

        RefreshRoomInnerMessage refreshRoomInner = new RefreshRoomInnerMessage();
        Room room = roomService.getRoom(roomId);
        refreshRoomInner.setRoomInfo(room.getInfo());
        refreshRoomInner.setManager(room.getManagerId());
        refreshRoomInner.setPlayerList(players);
        roomService.sendPushMessage(roomId, refreshRoomInner);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ctx.sendGameMessage(new SuccessMessage(), seqid);
//        RoomPlayerInfo roomPlayerInfo = new RoomPlayerInfo();
//        roomPlayerInfo.setPlayerList(players);
//        ctx.sendGameMessage(roomPlayerInfo, seqid);
    }

    @GameMapping(CreateRoomRequest.class)
    public void createRoom(GameChannelContext ctx, CreateRoomRequest request, int seqid) {
        Long id = roomService.createRoom(request.getName(), request.getMode(), request.getMap(), request.getCapacity(), ctx.getPlayerId());
        logger.debug("create room , id {}", id);
        CreateRoomResponse response = new CreateRoomResponse();
        response.setId(id);
        ctx.sendGameMessage(response, seqid);
    }


    @GameMapping(ExitRoomRequest.class)
    public void exitRoom(GameChannelContext ctx, ExitRoomRequest request, int seqid) {
        Long id = request.getRoomId();
        roomService.exitRoom(id, ctx.getPlayerId());
        logger.debug("player{} exit room {}", ctx.getPlayerId(), id);
        ctx.sendGameMessage(new SuccessMessage(), seqid);
    }

    @GameMapping(ReadyRequest.class)
    public void ready(GameChannelContext ctx, ReadyRequest request, int seqid) {
        roomService.ready(request.getRoomId(), ctx.getPlayerId());
        ctx.sendGameMessage(new SuccessMessage(), seqid);
    }

    @GameMapping(CancelReadyRequst.class)
    public void cancelready(GameChannelContext ctx, CancelReadyRequst request, int seqid) {
        roomService.cancelReady(request.getRoomId(), ctx.getPlayerId());
        ctx.sendGameMessage(new SuccessMessage(), seqid);
    }
}