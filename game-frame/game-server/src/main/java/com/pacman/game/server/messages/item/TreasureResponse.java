package com.pacman.game.server.messages.item;

import com.pacman.game.server.pojo.database.Item;
import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

import java.util.List;

/**
 * @author yhc
 * @create 2021-12-18-16:38
 */
@Data
@GameMessageMeta(messageId = 10303,messageType = 2)
public class TreasureResponse implements IGameMessage {
    List<Item> itemList;
}
