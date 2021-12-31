package com.pacman.game.server.messages.hero;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;

/**
 * @author yhc
 * @create 2021-12-05-22:49
 */
@GameMessageMeta(messageId = 10201,messageType = 1)
public class GetHeroListRequest implements IGameMessage {
}
