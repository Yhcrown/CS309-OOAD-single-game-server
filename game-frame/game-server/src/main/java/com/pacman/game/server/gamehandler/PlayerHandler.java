package com.pacman.game.server.gamehandler;

import com.pacman.game.server.cache.GlobalRankList;
import com.pacman.game.server.cache.PlayerCache;
import com.pacman.game.server.messages.FailMessage;
import com.pacman.game.server.messages.SuccessMessage;
import com.pacman.game.server.messages.player.*;
import com.pacman.game.server.messages.status.ErrorStatus;
import com.pacman.game.server.pojo.cache.PlayerInfo;
import com.pacman.game.server.pojo.database.Player;
import com.pacman.game.server.service.PlayerService;
import com.pacman.network.hamdlermapping.GameChannelContext;
import com.pacman.network.hamdlermapping.GameHandlerComponent;
import com.pacman.network.hamdlermapping.GameMapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author yhc
 * @create 2021-11-05-21:30
 */
@GameHandlerComponent
public class PlayerHandler extends AbstractGameHandler {
    @Autowired
    PlayerService playerService;
    @Autowired
    PlayerCache playerCache;

    @Autowired
    GlobalRankList globalRankList;


    @GameMapping(PlayerInformationRequest.class)
    public void getPlayerInfo(GameChannelContext ctx, PlayerInformationRequest request, int seqid) {
        Long playerId = request.getPlayerId();
        if (playerId == null || playerId == 0)
            playerId = ctx.getPlayerId();
//        logger.debug("player id :{}", playerId);
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId, false);
        PlayerInfoResponse response = new PlayerInfoResponse();
        if (playerInfo == null) {
            Player player = playerService.getInfo(playerId);
            logger.debug("player {} login", player.getAccountId());
            if (player == null) {
                logger.debug("找不到玩家信息 {}，请重新登录", playerId);
                ctx.getChannel().writeAndFlush(new FailMessage(ErrorStatus.PLAYER_FIND_ERROR.getCode(), ErrorStatus.PLAYER_FIND_ERROR.getMsg()));
                return;
            }
//            logger.debug("玩家{} 信息获取成功，{}", playerId, player);

            response.setPlayerInfo(playerCache.getPlayerInfo(playerId, false));
        } else {
            response.setPlayerInfo(playerInfo);
//            logger.debug("玩家{} 信息获取成功，{}", playerId, playerInfo);
        }
        ctx.sendGameMessage(response, seqid);
    }


    @GameMapping(ChangeNameRequest.class)
    public void changeName(GameChannelContext ctx, ChangeNameRequest request, int seqid) {
        Long playerId = ctx.getPlayerId();
//        System.out.println("changename : "+request.getName());
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        Player baseInfo = playerInfo.getBaseInfo();
        if (request.getName().length() > 18) {
            ctx.sendGameMessage(new FailMessage(ErrorStatus.CHANGE_NAME_ERROR.getCode(), ErrorStatus.CHANGE_NAME_ERROR.getMsg()), seqid);
            return;
        }
        baseInfo.setNickname(request.getName());
        logger.debug("player {} new name {}", playerId, playerCache.getPlayerInfo(playerId).getBaseInfo().getNickname());
        ctx.sendGameMessage(new SuccessMessage(), seqid);
    }

    @GameMapping(ChangeHeadRequest.class)
    public void changeName(GameChannelContext ctx, ChangeHeadRequest request, int seqid) {
        Long playerId = ctx.getPlayerId();
//        System.out.println("changename : "+request.getName());
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        Player baseInfo = playerInfo.getBaseInfo();
        baseInfo.setHead(request.getHead());

        ctx.sendGameMessage(new SuccessMessage(), seqid);

    }

    @GameMapping(GetRankListRequest.class)
    public void getRankList(GameChannelContext ctx, GetRankListRequest message, int seqId) {
        List<Player> rankList = globalRankList.getRankList();
        List<Player> players = null;
        if (rankList.size() >= 100) {
            players = rankList.subList(0, 100);
        } else {
            players = rankList;
        }

        RankListResponse rankListResponse = new RankListResponse();
        rankListResponse.setRankList(players);
        ctx.sendGameMessage(rankListResponse, seqId);

    }
}
