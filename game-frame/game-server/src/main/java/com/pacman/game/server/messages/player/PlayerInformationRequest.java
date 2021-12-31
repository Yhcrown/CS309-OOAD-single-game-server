package com.pacman.game.server.messages.player;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-11-05-21:32
 */
@Data
@GameMessageMeta(messageId = 10003,messageType = 1)
public class PlayerInformationRequest implements IGameMessage {
    Long playerId;
}
