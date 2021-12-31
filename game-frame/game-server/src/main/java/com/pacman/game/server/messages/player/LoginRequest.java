package com.pacman.game.server.messages.player;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;

/**
 * @author 王广帅
 * @date 2021年01月26日 8:31 下午
 */
@GameMessageMeta(messageId = 10001, messageType = 1,isEncrypt = true)
public class LoginRequest implements IGameMessage {
    private String account;
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
