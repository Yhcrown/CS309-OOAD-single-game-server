package com.pacman.network.message;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;

/**
 * @Author 王广帅
 * @Date 2021/3/7 13:49
 */
@GameMessageMeta(messageId = 1,messageType = 2)
public class ChannelHandShakeResponse implements IGameMessage {
    private String pubKey;

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }
}
