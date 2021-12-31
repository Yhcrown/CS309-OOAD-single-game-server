package com.pacman.game.server.messages.player;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-11-03-13:38
 */
@GameMessageMeta(messageId = 10002, messageType = 1,isEncrypt = true)
@Data
public class RegisterRequest implements IGameMessage {
    private String account;
    private String password;
}
