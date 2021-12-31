package com.pacman.game.server.messages.item;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-12-18-16:33
 */
@GameMessageMeta(messageId = 10303,messageType = 1)
@Data
public class OpenTreasureBoxRequest implements IGameMessage {
    Integer num;
    Long itemId;
}
