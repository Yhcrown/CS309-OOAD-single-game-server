package com.pacman.game.server.messages.hero;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-12-05-22:43
 */
@Data
@GameMessageMeta(messageId = 10200 ,messageType = 1)
public class CreateHeroRequest implements IGameMessage {
    Integer typeId;
}
