package com.pacman.game.server.messages.hero;

import com.pacman.game.server.pojo.database.Hero;
import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-12-08-20:50
 */
@GameMessageMeta(messageId = 10200 ,messageType = 2)
@Data
public class CreateHeroResponse implements IGameMessage {
    Hero hero;
}
