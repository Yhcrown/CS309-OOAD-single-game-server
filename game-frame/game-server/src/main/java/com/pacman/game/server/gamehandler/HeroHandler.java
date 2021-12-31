package com.pacman.game.server.gamehandler;

import com.pacman.game.server.cache.PlayerCache;
import com.pacman.game.server.messages.FailMessage;
import com.pacman.game.server.messages.SuccessMessage;
import com.pacman.game.server.messages.hero.*;
import com.pacman.game.server.messages.status.ErrorStatus;

import com.pacman.game.server.pojo.cache.PlayerInfo;
import com.pacman.game.server.pojo.database.Hero;
import com.pacman.game.server.service.HeroService;
import com.pacman.network.codec.IGameMessage;
import com.pacman.network.hamdlermapping.GameChannelContext;
import com.pacman.network.hamdlermapping.GameHandlerComponent;
import com.pacman.network.hamdlermapping.GameMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author yhc
 * @create 2021-12-05-23:24
 */
@GameHandlerComponent
@Slf4j
public class HeroHandler extends AbstractGameHandler {

    @Autowired
    HeroService heroService;
    @Autowired
    PlayerCache playerCache;

    @GameMapping(CreateHeroRequest.class)
    public void createHero(GameChannelContext ctx, CreateHeroRequest request, int seqId) {
        Long playerId = ctx.getPlayerId();
        Integer typeId = request.getTypeId();
        Hero hero = heroService.createHero(playerId, typeId);
        if (hero == null)
            ctx.sendGameMessage(new FailMessage(ErrorStatus.HERO_ID_ERROR.getCode(), ErrorStatus.HERO_ID_ERROR.getMsg()), seqId);
        else {
            CreateHeroResponse createHeroResponse = new CreateHeroResponse();
            createHeroResponse.setHero(hero);
            logger.debug("player {} create hero {}", playerId, hero);
            ctx.sendGameMessage(createHeroResponse, seqId);
        }
    }

    //
    @GameMapping(GetHeroListRequest.class)
    public void getHeroList(GameChannelContext ctx, GetHeroListRequest request, int seqId) {
        Long playerId = ctx.getPlayerId();
        List<Hero> heroList = heroService.getHeroList(playerId);
        HeroListResponse heroListResponse = new HeroListResponse();
        heroListResponse.setHeroList(heroList);
        ctx.sendGameMessage(heroListResponse, seqId);
    }


    @GameMapping(HeroLevelUpRequest.class)
    public void heroLevelUp(GameChannelContext ctx, HeroLevelUpRequest request, int seqId) {
        Long heroId = request.getHeroId();
        Long playerId = ctx.getPlayerId();
        IGameMessage iGameMessage = heroService.lvUp(playerId, heroId);
        ctx.sendGameMessage(iGameMessage, seqId);


    }

    @GameMapping(HeroStarUpRequest.class)
    public void heroStarUp(GameChannelContext ctx, HeroStarUpRequest request, int seqId) {
        Long heroId = request.getHeroId();
        Long playerId = ctx.getPlayerId();
        ctx.sendGameMessage(heroService.starUp(playerId, heroId), seqId);
    }

    @GameMapping(ChooseHeroRequest.class)
    public void chooseHero(GameChannelContext ctx, ChooseHeroRequest request, int seqId) {
        Long playerId = ctx.getPlayerId();
        Long heroId = request.getHeroId();
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        Hero hero = heroService.getHero(playerId, heroId);
        if (hero != null) {
            playerInfo.setChoose(hero);
            playerInfo.getBaseInfo().setChooseHero(heroId);
            ctx.sendGameMessage(new SuccessMessage(), seqId);
        } else
            ctx.sendGameMessage(new FailMessage(ErrorStatus.HERO_ID_ERROR.getCode(), ErrorStatus.HERO_ID_ERROR.getMsg()), seqId);
    }

    @GameMapping(DeleteHeroRequest.class)
    public void deleteHero(GameChannelContext ctx, DeleteHeroRequest request, int seqId) {
        int i = heroService.removeHero(ctx.getPlayerId(), request.getHeroId());

        if (i == 0) ctx.sendGameMessage(new SuccessMessage(), seqId);
        else if (i == 1)
            ctx.sendGameMessage(new FailMessage(ErrorStatus.HERO_ID_ERROR.getCode(), ErrorStatus.HERO_ID_ERROR.getMsg()), seqId);
        else if (i == 2)
            ctx.sendGameMessage(new FailMessage(ErrorStatus.HERO_CHOOSE_SELL_ERROR.getCode(), ErrorStatus.HERO_CHOOSE_SELL_ERROR.getMsg()), seqId);
        else
            ctx.sendGameMessage(new FailMessage(ErrorStatus.Hero_CANT_SELL_ERROR.getCode(), ErrorStatus.Hero_CANT_SELL_ERROR.getMsg()), seqId);
    }
}
