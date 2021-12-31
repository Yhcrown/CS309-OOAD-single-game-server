package com.pacman.game.server.messages.global;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-12-26-13:18
 */
@GameMessageMeta(messageId = 999, messageType = 1)
@Data
public class CloseServerRequest implements IGameMessage {
    String password;
    String reason;
}
