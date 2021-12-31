package com.pacman.game.server.messages.player;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-11-28-15:26
 */
@GameMessageMeta(messageId = 10007,messageType = 1)
@Data
public class ChangeNameRequest implements IGameMessage {
    String name;
}
