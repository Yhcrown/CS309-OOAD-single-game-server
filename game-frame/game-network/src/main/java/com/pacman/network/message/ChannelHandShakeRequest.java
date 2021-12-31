package com.pacman.network.message;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;

/**
 * @Author 王广帅
 * @Date 2021/3/7 13:48
 */
@GameMessageMeta(messageId = 1,messageType = 1)
public class ChannelHandShakeRequest implements IGameMessage {
    private String aesKey;

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String desKey) {
        this.aesKey = desKey;
    }
}
