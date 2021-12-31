package com.pacman.game.server.gamehandler;

import com.pacman.game.server.messages.tavern.*;
import com.pacman.game.server.service.TavernService;
import com.pacman.network.codec.IGameMessage;
import com.pacman.network.hamdlermapping.GameChannelContext;
import com.pacman.network.hamdlermapping.GameHandlerComponent;
import com.pacman.network.hamdlermapping.GameMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yhc
 * @create 2021-12-11-21:21
 */
@GameHandlerComponent
public class TavernHandler extends AbstractGameHandler{
    @Autowired
    TavernService tavernService;
    @GameMapping(OneCoinLotteryRequest.class)
    public void oneCoinLottery(GameChannelContext ctx, OneCoinLotteryRequest request, int seqid) {
        Long playerId = ctx.getPlayerId();
        IGameMessage res = tavernService.oneCoinLottery(playerId);
        ctx.sendGameMessage(res,seqid);
    }
    @GameMapping(TenCoinLotteryRequest.class)
    public void tenCoinLottery(GameChannelContext ctx, TenCoinLotteryRequest request, int seqid) {
        Long playerId = ctx.getPlayerId();
        IGameMessage res = tavernService.tenCoinLottery(playerId);
        ctx.sendGameMessage(res,seqid);
    }
    @GameMapping(OneDiamondLotteryRequest.class)
    public void oneDiamondLottery(GameChannelContext ctx, OneDiamondLotteryRequest request, int seqid) {
        Long playerId = ctx.getPlayerId();
        IGameMessage res = tavernService.oneDiamondLottery(playerId);
        ctx.sendGameMessage(res,seqid);
    }
    @GameMapping(TenDiamondLotteryRequest.class)
    public void tenDiamondLottery(GameChannelContext ctx, TenDiamondLotteryRequest request, int seqid) {
        Long playerId = ctx.getPlayerId();
        IGameMessage res = tavernService.tenDiamondLottery(playerId);
        ctx.sendGameMessage(res,seqid);
    }
    @GameMapping(GetTavernInfoRequest.class)
    public void getInfo(GameChannelContext ctx, GetTavernInfoRequest request, int seqid) {
        Long playerId = ctx.getPlayerId();
        IGameMessage res = tavernService.getTavernInfo(playerId);
        ctx.sendGameMessage(res,seqid);
    }
    @GameMapping(OneFreeDiamondLotteryRequest.class)
    public void getInfo(GameChannelContext ctx, OneFreeDiamondLotteryRequest request, int seqid) {
        Long playerId = ctx.getPlayerId();
        IGameMessage res = tavernService.freeDimondLottery(playerId);
        ctx.sendGameMessage(res,seqid);
    }
    @GameMapping(OneTicketCoinLotteryRequest.class)
    public void getInfo(GameChannelContext ctx, OneTicketCoinLotteryRequest request, int seqid) {
        Long playerId = ctx.getPlayerId();
        IGameMessage res = tavernService.oneTicketCoinLottery(playerId);
        ctx.sendGameMessage(res,seqid);
    }
    @GameMapping(OneTicketDiamondLotteryRequest.class)
    public void getInfo(GameChannelContext ctx, OneTicketDiamondLotteryRequest request, int seqid) {
        Long playerId = ctx.getPlayerId();
        IGameMessage res = tavernService.oneTicketDiamondLottery(playerId);
        ctx.sendGameMessage(res,seqid);
    }
}
