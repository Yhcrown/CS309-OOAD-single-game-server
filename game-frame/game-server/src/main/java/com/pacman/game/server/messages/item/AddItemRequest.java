package com.pacman.game.server.messages.item;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-12-05-22:56
 */
@Data
@GameMessageMeta(messageId = 10300,messageType = 1)
public class AddItemRequest implements IGameMessage {
    Integer typeId;
    Integer num;
}
