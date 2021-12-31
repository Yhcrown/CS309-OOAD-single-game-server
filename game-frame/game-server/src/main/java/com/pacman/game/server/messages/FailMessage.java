package com.pacman.game.server.messages;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-11-03-13:51
 */
@GameMessageMeta(messageType = 2, messageId = 400,isEncrypt = false)
@Data
public class FailMessage implements IGameMessage {
    private int code;
    private String msg;

    public FailMessage(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
