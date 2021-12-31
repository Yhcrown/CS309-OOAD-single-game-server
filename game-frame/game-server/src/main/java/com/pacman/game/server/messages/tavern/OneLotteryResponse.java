package com.pacman.game.server.messages.tavern;

import com.pacman.game.server.pojo.LotteryRes;
import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-12-11-20:19
 */
@Data
@GameMessageMeta(messageId = 10400, messageType = 2)
public class OneLotteryResponse implements IGameMessage {
    LotteryRes lotteryRes;
}
