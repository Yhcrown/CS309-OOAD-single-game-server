package com.pacman.game.server.messages;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;

/**
 * @author yhc
 * @create 2021-11-28-21:58
 */
@GameMessageMeta(messageId = 8888, messageType = 3)
public class NotifyReloginPush implements IGameMessage {
}
