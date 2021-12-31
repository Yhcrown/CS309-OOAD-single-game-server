package com.pacman.game.server.messages.hero;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-12-09-21:11
 */
@GameMessageMeta(messageId = 10204,messageType = 1)
@Data
public class ChooseHeroRequest implements IGameMessage {
    Long heroId;
}
