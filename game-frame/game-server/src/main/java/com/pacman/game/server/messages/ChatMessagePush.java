package com.pacman.game.server.messages;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author 王广帅
 * @date 2021年02月03日 2:46 下午
 */
@GameMessageMeta(messageId = 10009,messageType = 3)
@Data
public class ChatMessagePush implements IGameMessage {
    private Long playerId;
    private String name;
    private String message;
    private String time;
    private Integer head;
    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
