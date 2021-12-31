package com.pacman.game.server.messages.room;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-11-25-23:13
 */
@GameMessageMeta(messageId = 10102, messageType = 1)
@Data
public class InnerRoomRequest implements IGameMessage {
    Long roomId;
}
