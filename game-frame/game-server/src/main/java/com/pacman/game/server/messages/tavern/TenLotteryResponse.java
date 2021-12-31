package com.pacman.game.server.messages.tavern;

import com.pacman.game.server.pojo.LotteryRes;
import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

import java.util.List;

/**
 * @author yhc
 * @create 2021-12-11-20:20
 */
@GameMessageMeta(messageId = 10401,messageType = 2)
@Data
public class TenLotteryResponse implements IGameMessage {
    List<LotteryRes> lotteryRes;
}
