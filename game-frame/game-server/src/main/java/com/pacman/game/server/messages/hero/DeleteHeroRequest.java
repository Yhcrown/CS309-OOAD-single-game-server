package com.pacman.game.server.messages.hero;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-12-12-13:40
 */
@GameMessageMeta(messageId = 10205,messageType = 1)
@Data
public class  DeleteHeroRequest implements IGameMessage {
    Long heroId;
}
