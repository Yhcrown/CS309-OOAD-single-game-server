package com.pacman.game.server.messages.room;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;

/**
 * @author yhc
 * @create 2021-11-27-17:55
 */
@GameMessageMeta(messageId = 10106, messageType = 3)
public class DestoryRoomMessage implements IGameMessage {
}
