package com.pacman.game.server.messages.global;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;

/**
 * @author yhc
 * @create 2021-12-29-17:37
 */
@GameMessageMeta(messageId = 555,messageType = 1)
public class BeforeAnnouncement implements IGameMessage {

}
