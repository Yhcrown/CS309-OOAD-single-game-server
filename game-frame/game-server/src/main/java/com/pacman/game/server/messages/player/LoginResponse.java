package com.pacman.game.server.messages.player;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;

/**
 * @author 王广帅
 * @date 2021年01月28日 9:01 下午
 */
@GameMessageMeta(messageType = 2, messageId = 10001,isEncrypt = true)
public class LoginResponse implements IGameMessage {
    private long playId;

    public long getPlayId() {
        return playId;
    }

    public void setPlayId(long playId) {
        this.playId = playId;
    }
}
