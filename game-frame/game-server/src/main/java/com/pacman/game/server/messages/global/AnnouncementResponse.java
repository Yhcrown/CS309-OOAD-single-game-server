package com.pacman.game.server.messages.global;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-12-29-17:38
 */
@Data
@GameMessageMeta(messageId = 555, messageType = 2)
public class AnnouncementResponse implements IGameMessage {
    String content;
}
