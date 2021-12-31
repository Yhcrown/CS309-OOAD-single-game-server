package com.pacman.game.server.messages;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

import java.util.List;

/**
 * @author yhc
 * @create 2021-12-01-20:28
 */
@GameMessageMeta(messageId = 10010,messageType = 2)
@Data
public class ChatListResponse implements IGameMessage {
    List<ChatMessagePush > chatMessagePushes ;
}
