package com.pacman.game.server.messages.tavern;

import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;

/**
 * @author yhc
 * @create 2021-12-11-22:28
 */
@GameMessageMeta(messageId = 10407, messageType = 1)
public class OneFreeDiamondLotteryRequest implements IGameMessage {
}
