package com.pacman.game.server.messages.player;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;

/**
 * @author yhc
 * @create 2021-12-25-20:53
 */
@GameMessageMeta(messageId = 10015,messageType = 1)
public class GetRankListRequest implements IGameMessage {
    
}
