package com.pacman.game.server.messages.room;

import com.pacman.game.server.pojo.PlayerWithHero;
import com.pacman.game.server.pojo.RoomInfo;
import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

import java.util.List;

/**
 * @author yhc
 * @create 2021-11-25-23:12
 */
@Data
@GameMessageMeta(messageId = 10103, messageType = 2)
public class RoomPlayerInfo implements IGameMessage {
    List<PlayerWithHero> playerList;
    Long manager;
    RoomInfo roomInfo;

}
