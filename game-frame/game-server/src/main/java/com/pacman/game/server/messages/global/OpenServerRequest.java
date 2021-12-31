package com.pacman.game.server.messages.global;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-12-26-13:19
 */
@GameMessageMeta(messageId = 888, messageType = 1)
@Data
public class OpenServerRequest implements IGameMessage {
    String password;
}
