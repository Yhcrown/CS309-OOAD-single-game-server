package com.pacman.game.server.messages.player;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-12-24-17:15
 */
@GameMessageMeta(messageId = 10012,messageType = 1)
@Data
public class ChangeHeadRequest implements IGameMessage {
    Integer head;
}
