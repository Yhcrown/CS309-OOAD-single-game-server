package com.pacman.game.server.gamehandler;

import com.pacman.game.server.cache.PlayerCache;
import com.pacman.game.server.messages.SuccessMessage;
import com.pacman.game.server.messages.player.HeartBeat;
import com.pacman.game.server.pojo.cache.PlayerInfo;
import com.pacman.network.hamdlermapping.GameChannelContext;
import com.pacman.network.hamdlermapping.GameHandlerComponent;
import com.pacman.network.hamdlermapping.GameMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yhc
 * @create 2021-11-21-21:45
 */
@GameHandlerComponent
public class HeartBeatHandler extends AbstractGameHandler {
    @Autowired
    PlayerCache playerCache;

    @GameMapping(HeartBeat.class)
    public void HeartBeat(GameChannelContext ctx, HeartBeat request, int seq) {
        Long playerId = ctx.getPlayerId();
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId,true);
        if (playerInfo != null) {
            playerInfo.setLastHeartBeatTime(System.currentTimeMillis());
            ctx.sendGameMessage(new SuccessMessage(), seq);
        }
//        logger.debug("channel {} heart beat",ctx.getChannel());
    }
}
