package com.pacman.game.server.messages.player;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;

/**
 * @author yhc
 * @create 2021-11-06-15:12
 */
@GameMessageMeta(messageId = 9999,messageType = 1)
public class HeartBeat implements IGameMessage {
}
