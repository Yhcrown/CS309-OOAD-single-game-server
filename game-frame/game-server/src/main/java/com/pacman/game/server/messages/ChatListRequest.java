package com.pacman.game.server.messages;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-12-01-20:25
 */
@GameMessageMeta(messageId = 10010 ,messageType = 1)
@Data
public class ChatListRequest implements IGameMessage {
}
