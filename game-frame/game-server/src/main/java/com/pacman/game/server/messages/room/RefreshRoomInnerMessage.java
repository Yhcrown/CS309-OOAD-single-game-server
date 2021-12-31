package com.pacman.game.server.messages.room;

import com.pacman.game.server.pojo.PlayerWithHero;
import com.pacman.game.server.pojo.RoomInfo;
import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

import java.util.List;

/**
 * @author yhc
 * @create 2021-11-27-17:16
 */
@GameMessageMeta(messageId = 10105,messageType = 3)
@Data
public class RefreshRoomInnerMessage implements IGameMessage {
    List<PlayerWithHero> playerList;
    Long manager;
    RoomInfo roomInfo;
}
