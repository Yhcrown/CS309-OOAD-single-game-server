package com.pacman.game.server.gamehandler;

import com.pacman.game.server.messages.FailMessage;
import com.pacman.game.server.messages.SuccessMessage;
import com.pacman.game.server.messages.game.GameRes;
import com.pacman.game.server.messages.game.Reward;
import com.pacman.game.server.messages.game.StartGamePush;
import com.pacman.game.server.messages.game.StartGameRequest;
import com.pacman.game.server.messages.room.RefreshRoomInnerMessage;
import com.pacman.game.server.messages.status.ErrorStatus;
import com.pacman.game.server.service.GameService;
import com.pacman.game.server.pojo.Room;
import com.pacman.game.server.service.RoomService;
import com.pacman.network.hamdlermapping.GameChannelContext;
import com.pacman.network.hamdlermapping.GameHandlerComponent;
import com.pacman.network.hamdlermapping.GameMapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * @author yhc
 * @create 2021-12-19-0:13
 */

@GameHandlerComponent
public class GameHandler extends AbstractGameHandler {

    @Autowired
    GameService gameService;
    @Autowired
    RoomService roomService;


    @GameMapping(GameRes.class)
    public void GameResult(GameChannelContext ctx, GameRes message, int seqId) {
        Reward reward = gameService.gameRes(ctx.getPlayerId(), message);
        ctx.sendGameMessage(reward, seqId);
    }

    @GameMapping(StartGameRequest.class)
    public void startGame(GameChannelContext ctx, StartGameRequest message, int seqId) {

        Long playerId = ctx.getPlayerId();

        Room room = roomService.getRoom(message.getRoomId());
        room.setStart(Boolean.TRUE);
        logger.debug("room {} start game", room);
        Long managerId = room.getManagerId();
        ctx.sendGameMessage(new SuccessMessage(), seqId);
        if (playerId.equals(managerId) && gameService.checkStart(message.getRoomId())) {
            StartGamePush startGamePush = new StartGamePush();

            startGamePush.setAllocation(gameService.gameInfo(message.getRoomId()));
            RefreshRoomInnerMessage refreshRoomInnerMessage = new RefreshRoomInnerMessage();
            refreshRoomInnerMessage.setRoomInfo(room.getInfo());
            refreshRoomInnerMessage.setManager(room.getManagerId());
            refreshRoomInnerMessage.setPlayerList(new ArrayList<>(room.getPlayerMap().values()));
            roomService.sendPushMessage(message.getRoomId(), refreshRoomInnerMessage);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            roomService.sendPushMessage(message.getRoomId(), startGamePush);
        } else
            ctx.sendGameMessage(new FailMessage(ErrorStatus.GAME_NOT_READY_ERROR.getCode(), ErrorStatus.GAME_NOT_READY_ERROR.getMsg()), seqId);
    }
}
