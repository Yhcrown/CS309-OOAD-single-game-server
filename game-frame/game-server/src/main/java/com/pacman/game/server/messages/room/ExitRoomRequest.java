package com.pacman.game.server.messages.room;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-11-27-18:13
 */
@Data
@GameMessageMeta(messageId = 10107, messageType = 1)
public class ExitRoomRequest implements IGameMessage {
    Long roomId;
}
