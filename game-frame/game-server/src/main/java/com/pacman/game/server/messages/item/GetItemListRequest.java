package com.pacman.game.server.messages.item;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-12-05-23:04
 */
@Data
@GameMessageMeta(messageId = 10301,messageType = 1)
public class GetItemListRequest implements IGameMessage {
}
