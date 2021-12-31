package com.pacman.game.server.messages.hero;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-12-05-22:51
 */
@GameMessageMeta(messageId = 10202,messageType = 1)
@Data
public class HeroLevelUpRequest implements IGameMessage {
    Long heroId;
}
