package com.pacman.game.server.messages.hero;

import com.pacman.game.server.pojo.database.Hero;
import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-12-08-20:40
 */
@Data
@GameMessageMeta(messageId = 10202,messageType = 2)
public class HeroLevelUpResponse implements IGameMessage {
    Hero hero;
}
