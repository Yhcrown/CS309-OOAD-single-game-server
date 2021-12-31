package com.pacman.game.server.messages.game;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-12-18-22:11
 */
@Data
@GameMessageMeta(messageId = 10600, messageType = 1)
public class GameRes implements IGameMessage {
    Integer mode;
    Integer map;
    Integer kill;
    Integer death;
    Integer rank;
    Boolean win;
    Long roomId;
}
