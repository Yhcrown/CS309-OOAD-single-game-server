package com.pacman.game.server.messages.item;

import com.pacman.game.server.pojo.database.Item;
import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

import java.util.List;

/**
 * @author yhc
 * @create 2021-12-05-23:17
 */
@GameMessageMeta(messageId = 10301, messageType = 2)
@Data
public class ItemListResponse implements IGameMessage {
    List<Item> items;
}
