package com.pacman.game.server.messages.hero;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-12-09-16:02
 */
@Data
@GameMessageMeta(messageId = 10203,messageType = 1)
public class HeroStarUpRequest implements IGameMessage {
    Long heroId;
}
