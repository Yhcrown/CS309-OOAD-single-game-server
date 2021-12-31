package com.pacman.game.server.messages.room;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;

/**
 * @author yhc
 * @create 2021-11-25-22:01
 */
@GameMessageMeta(messageId = 10100,messageType = 1)
public class GetRoomListRequest implements IGameMessage {

}
