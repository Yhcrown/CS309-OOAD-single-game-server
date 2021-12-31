package com.pacman.game.server.messages.room;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-11-26-20:09
 */
@Data
@GameMessageMeta(messageId = 10104 ,messageType = 2)
public class CreateRoomResponse implements IGameMessage {
    Long id;
}
