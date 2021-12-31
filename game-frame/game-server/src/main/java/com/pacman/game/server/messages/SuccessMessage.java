package com.pacman.game.server.messages;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;

/**
 * @author yhc
 * @create 2021-11-03-13:48
 */
@GameMessageMeta(messageType = 2, messageId = 200,isEncrypt = false)
public class SuccessMessage implements IGameMessage {

}
