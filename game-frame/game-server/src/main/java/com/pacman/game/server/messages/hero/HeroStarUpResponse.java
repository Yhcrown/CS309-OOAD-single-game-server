package com.pacman.game.server.messages.hero;

import com.pacman.game.server.pojo.database.Hero;
import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-12-09-16:03
 */
@Data
@GameMessageMeta(messageId = 10203,messageType = 2)
public class HeroStarUpResponse implements IGameMessage {
    Hero hero;
}
