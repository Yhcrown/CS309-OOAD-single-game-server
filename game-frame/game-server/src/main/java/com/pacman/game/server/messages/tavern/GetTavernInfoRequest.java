package com.pacman.game.server.messages.tavern;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;

/**
 * @author yhc
 * @create 2021-12-11-22:08
 */
@GameMessageMeta(messageId = 10404,messageType = 1)
public class GetTavernInfoRequest implements IGameMessage {
}
