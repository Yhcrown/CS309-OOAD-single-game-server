package com.pacman.game.server.messages.player;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-11-03-13:39
 */
@GameMessageMeta(messageType = 2, messageId = 10002,isEncrypt = true)
@Data
public class RegisterResponse implements IGameMessage {
}
