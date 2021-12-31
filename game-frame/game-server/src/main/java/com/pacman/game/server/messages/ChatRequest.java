package com.pacman.game.server.messages;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;

/**
 * @author 王广帅
 * @date 2021年02月03日 2:38 下午
 */
@GameMessageMeta(messageId = 10009,messageType = 1)
public class ChatRequest implements IGameMessage {
    private int chatType;// 聊天类型：1， 世界聊天
    private String message;//聊天内容
//    private String name;
    public int getChatType() {
        return chatType;
    }

    public void setChatType(int chatType) {
        this.chatType = chatType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
