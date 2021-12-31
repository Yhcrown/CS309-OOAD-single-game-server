package com.pacman.game.server.messages.room;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;

/**
 * @author yhc
 * @create 2021-12-02-23:22
 */
@GameMessageMeta(messageId = 10107,messageType = 3)
public class ExitRoomPush implements IGameMessage {
}
