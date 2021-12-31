package com.pacman.game.server.messages.room;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-11-25-23:33
 */
@Data
@GameMessageMeta(messageId = 10103, messageType = 1)
public class EnterRoomRequest implements IGameMessage {
    Long roomId;
}
