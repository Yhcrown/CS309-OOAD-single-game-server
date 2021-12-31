package com.pacman.game.server.messages.game;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-12-18-22:15
 */


@GameMessageMeta(messageId = 10600,messageType = 2)
@Data
public class Reward implements IGameMessage {
    Integer exp;
    Integer coin;
    Integer box;
    Integer lvUp = 0;
}
