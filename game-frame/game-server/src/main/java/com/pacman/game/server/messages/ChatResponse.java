package com.pacman.game.server.messages;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;

/**
 * @author 王广帅
 * @date 2021年02月03日 2:40 下午
 */

@GameMessageMeta(messageType = 2,messageId = 1)
public class ChatResponse implements IGameMessage {

}
