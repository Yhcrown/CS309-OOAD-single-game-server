package com.pacman.game.server.messages.global;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-12-26-21:57
 */
@GameMessageMeta(messageId = 777, messageType = 1)
@Data
public class AnnouncementRequest implements IGameMessage {
    String password;
    String announcement;
}
