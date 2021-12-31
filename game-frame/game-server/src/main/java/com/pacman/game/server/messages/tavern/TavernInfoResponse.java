package com.pacman.game.server.messages.tavern;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-12-11-22:09
 */
@Data
@GameMessageMeta(messageId = 10404,messageType = 2)
public class TavernInfoResponse implements IGameMessage {
    Integer coinNum;
    Integer diamondNum;
    Integer timeLeft;
}
