package com.pacman.game.server.messages.room;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-11-26-20:06
 */
@Data
@GameMessageMeta(messageId = 10104,messageType = 1)
public class CreateRoomRequest implements IGameMessage {
    String name;
    Integer map;
    Integer mode;
    Integer capacity;

}
