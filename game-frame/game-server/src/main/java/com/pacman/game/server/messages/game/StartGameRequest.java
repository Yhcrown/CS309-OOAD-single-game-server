package com.pacman.game.server.messages.game;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-12-19-16:32
 */
@GameMessageMeta(messageId = 10601,messageType = 1)
@Data
public class StartGameRequest implements IGameMessage {
    Long roomId;

}
