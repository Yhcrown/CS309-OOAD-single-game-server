package com.pacman.game.server.messages.room;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-12-24-16:46
 */
@GameMessageMeta(messageId = 10110,messageType =1)
@Data
public class CancelReadyRequst implements IGameMessage {
    Long roomId;
}
