package com.pacman.game.server.messages.room;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-12-24-15:57
 */
@GameMessageMeta(messageId = 10108,messageType = 1)
@Data
public class ReadyRequest implements IGameMessage {
    Long roomId;
}
