package com.pacman.game.server.service;

import com.pacman.game.server.beanconfig.StaticConfig;
import com.pacman.game.server.cache.PlayerCache;
import com.pacman.game.server.messages.FailMessage;
import com.pacman.game.server.messages.status.ErrorStatus;
import com.pacman.game.server.messages.tavern.OneLotteryResponse;
import com.pacman.game.server.messages.tavern.TavernInfoResponse;
import com.pacman.game.server.messages.tavern.TenLotteryResponse;
import com.pacman.game.server.pojo.LotteryRes;
import com.pacman.game.server.pojo.cache.PlayerInfo;
import com.pacman.game.server.pojo.database.Hero;
import com.pacman.game.server.pojo.database.Item;
import com.pacman.game.server.pojo.database.Player;
import com.pacman.network.codec.IGameMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author yhc
 * @create 2021-12-11-18:04
 */
@Service
@Slf4j
public class TavernService {

    @Autowired
    StaticConfig staticConfig;
    @Autowired
    PlayerService playerService;
    @Autowired
    HeroService heroService;
    @Autowired
    PlayerCache playerCache;
    @Autowired
    ItemService itemService;

    public IGameMessage getTavernInfo(Long playerId){

        Integer coinLotteryItemTypeId = staticConfig.getIntValue(StaticConfig.COIN_LOTTERY_ITEM_TYPE_ID);
        Item item = itemService.getItemByTypeId(playerId, coinLotteryItemTypeId);
        Integer coinLotteryItemNum = 0;
        if (item!=null){
            coinLotteryItemNum = item.getNum();
        }

        Integer diamondLotteryItemTypeId = staticConfig.getIntValue(StaticConfig.DIAMOND_LOTTERY_ITEM_TYPE_ID);
        Item item2 = itemService.getItemByTypeId(playerId,diamondLotteryItemTypeId);
        Integer diamondItemNum = 0;
        if (item2!=null){
            diamondItemNum = item2.getNum();
        }
        Integer lastFreeTime = getLastFreeLotteryTime(playerId);
        if (lastFreeTime<0) {
            lastFreeTime = 0;
        }
        TavernInfoResponse tavernInfoResponse = new TavernInfoResponse();
        tavernInfoResponse.setCoinNum(coinLotteryItemNum);
        tavernInfoResponse.setDiamondNum(diamondItemNum);
        tavernInfoResponse.setTimeLeft(lastFreeTime);
        return tavernInfoResponse;
    }

    public IGameMessage oneTicketCoinLottery(Long playerId) {
        // 判断用户道具并扣除
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        if (playerInfo.getHeroes().size()>=50){
            return new FailMessage(ErrorStatus.HERO_TOO_MUCH_ERROR.getCode(), ErrorStatus.HERO_TOO_MUCH_ERROR.getMsg());
        }
        Integer itemTypeId = staticConfig.getIntValue(StaticConfig.COIN_LOTTERY_ITEM_TYPE_ID);
        Item item = itemService.getItemByTypeId(playerId, itemTypeId);
        boolean b = itemService.delItem(item, 1);
        if (!b) {
            return new FailMessage(ErrorStatus.ITEM_NUMBER_ERROR.getCode(), ErrorStatus.ITEM_NUMBER_ERROR.getMsg());
        }
        Random random = new Random();

        // 根据配置的权重随机获取英雄typeId
        Integer heroTypeId = random.nextInt(6) + 1;
        // 创建英雄
        Hero hero = heroService.createHero(playerId, heroTypeId);
        double rate = random.nextDouble();
        int star;
        if (rate < (double) staticConfig.getIntValue(StaticConfig.COIN_ONE_RATE) / 100)
            star = 1;
        else if (rate < (double) staticConfig.getIntValue(StaticConfig.COIN_TWO_RATE) / 100)
            star = 2;
        else
            star = 3;
        hero.setStar(star);
        OneLotteryResponse oneLotteryResponse = new OneLotteryResponse();
        LotteryRes lotteryRes = new LotteryRes();
        lotteryRes.setStar(star);
        lotteryRes.setTypeId(hero.getTypeId());
        oneLotteryResponse.setLotteryRes(lotteryRes);

        return oneLotteryResponse;
    }

    // 元宝单抽
    public IGameMessage oneCoinLottery(Long playerId) {
        // 判断用户元宝并扣除
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        if (playerInfo.getHeroes().size()>=50){
            return new FailMessage(ErrorStatus.HERO_TOO_MUCH_ERROR.getCode(), ErrorStatus.HERO_TOO_MUCH_ERROR.getMsg());
        }
        Integer oneLotteryCoin = staticConfig.getIntValue(StaticConfig.ONE_LOTTERY_COIN);
        boolean b = playerService.changeCoin(playerId, -oneLotteryCoin);
        if (!b) {
            return new FailMessage(ErrorStatus.COIN_NOT_ENOUGH.getCode(), ErrorStatus.COIN_NOT_ENOUGH.getMsg());
        }
        Random random = new Random();

        // 根据配置的权重随机获取英雄typeId
        Integer heroTypeId = random.nextInt(6) + 1;
        // 创建英雄
        Hero hero = heroService.createHero(playerId, heroTypeId);
        double rate = random.nextDouble();
        int star;
        if (rate < (double) staticConfig.getIntValue(StaticConfig.COIN_ONE_RATE) / 100)
            star = 1;
        else if (rate < (double) staticConfig.getIntValue(StaticConfig.COIN_TWO_RATE) / 100)
            star = 2;
        else
            star = 3;
        hero.setStar(star);
        OneLotteryResponse oneLotteryResponse = new OneLotteryResponse();
        LotteryRes lotteryRes = new LotteryRes();
        lotteryRes.setStar(star);
        lotteryRes.setTypeId(hero.getTypeId());
        oneLotteryResponse.setLotteryRes(lotteryRes);
        return oneLotteryResponse;
    }

    // 元宝十连抽
    public IGameMessage tenCoinLottery(Long playerId) {
        // 判断用户元宝并扣除
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        if (playerInfo.getHeroes().size()>=41){
            return new FailMessage(ErrorStatus.HERO_TOO_MUCH_ERROR.getCode(), ErrorStatus.HERO_TOO_MUCH_ERROR.getMsg());
        }
        Integer oneLotteryCoin = staticConfig.getIntValue(StaticConfig.TEN_LOTTERY_COIN);
        boolean b = playerService.changeCoin(playerId, -oneLotteryCoin);
        if (!b) {
            return new FailMessage(ErrorStatus.ITEM_NUMBER_ERROR.getCode(), ErrorStatus.ITEM_NUMBER_ERROR.getMsg());
        }
        Random random = new Random();
        double rate;
        int star;
        Integer heroTypeId;
        List<LotteryRes> lotteryResList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            heroTypeId = random.nextInt(6) + 1;
            // 创建英雄
            rate = random.nextDouble();
            if (rate < (double) staticConfig.getIntValue(StaticConfig.COIN_ONE_RATE) / 100)
                star = 1;
            else if (rate < (double) staticConfig.getIntValue(StaticConfig.COIN_TWO_RATE) / 100)
                star = 2;
            else
                star = 3;
            Hero hero = heroService.createHero(playerId, heroTypeId);
            hero.setStar(star);
            LotteryRes lotteryRes = new LotteryRes();
            lotteryRes.setTypeId(heroTypeId);
            lotteryRes.setStar(star);
            lotteryResList.add(lotteryRes);
        }
        heroTypeId = random.nextInt(6) + 1;

        rate = random.nextDouble();
        if (rate < (double) staticConfig.getIntValue(StaticConfig.COIN_TWO_RATE) / 100)
            star = 2;
        else
            star = 3;
        Hero hero = heroService.createHero(playerId, heroTypeId);
        hero.setStar(star);
        LotteryRes lotteryRes = new LotteryRes();
        lotteryRes.setTypeId(heroTypeId);
        lotteryRes.setStar(star);
        lotteryResList.add(lotteryRes);
        TenLotteryResponse tenLotteryResponse = new TenLotteryResponse();
        tenLotteryResponse.setLotteryRes(lotteryResList);
        return tenLotteryResponse;
    }

    public IGameMessage oneTicketDiamondLottery(Long playerId) {
        // 判断用户元宝并扣除
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        if (playerInfo.getHeroes().size()>=50){
            return new FailMessage(ErrorStatus.HERO_TOO_MUCH_ERROR.getCode(), ErrorStatus.HERO_TOO_MUCH_ERROR.getMsg());
        }
        Integer itemTypeId = staticConfig.getIntValue(StaticConfig.DIAMOND_LOTTERY_ITEM_TYPE_ID);
        Item item = itemService.getItemByTypeId(playerId, itemTypeId);
        boolean b = itemService.delItem(item, 1);
        if (!b) {
            return new FailMessage(ErrorStatus.DIAMOND_NOT_ENOUGH.getCode(), ErrorStatus.DIAMOND_NOT_ENOUGH.getMsg());
        }
        Random random = new Random();

        // 根据配置的权重随机获取英雄typeId
        Integer heroTypeId = random.nextInt(6) + 1;
        // 创建英雄
        Hero hero = heroService.createHero(playerId, heroTypeId);
        double rate = random.nextDouble();
        int star;
        if (rate < (double) staticConfig.getIntValue(StaticConfig.COIN_ONE_RATE) / 100)
            star = 2;
        else if (rate < (double) staticConfig.getIntValue(StaticConfig.COIN_TWO_RATE) / 100)
            star = 3;
        else
            star = 4;
        hero.setStar(star);
        OneLotteryResponse oneLotteryResponse = new OneLotteryResponse();
        LotteryRes lotteryRes = new LotteryRes();
        lotteryRes.setStar(star);
        lotteryRes.setTypeId(hero.getTypeId());
        oneLotteryResponse.setLotteryRes(lotteryRes);

        return oneLotteryResponse;
    }

    // 元宝单抽
    public IGameMessage oneDiamondLottery(Long playerId) {
        // 判断用户元宝并扣除
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        if (playerInfo.getHeroes().size()>=50){
            return new FailMessage(ErrorStatus.HERO_TOO_MUCH_ERROR.getCode(), ErrorStatus.HERO_TOO_MUCH_ERROR.getMsg());
        }
        Integer oneLotteryCoin = staticConfig.getIntValue(StaticConfig.ONE_LOTTERY_DIAMOND);
        boolean b = playerService.changeDiamond(playerId, -oneLotteryCoin);
        if (!b) {
            return new FailMessage(ErrorStatus.DIAMOND_NOT_ENOUGH.getCode(), ErrorStatus.DIAMOND_NOT_ENOUGH.getMsg());
        }
        Random random = new Random();

        // 根据配置的权重随机获取英雄typeId
        Integer heroTypeId = random.nextInt(6) + 1;
        // 创建英雄
        Hero hero = heroService.createHero(playerId, heroTypeId);
        double rate = random.nextDouble();
        int star;
        if (rate < (double) staticConfig.getIntValue(StaticConfig.COIN_ONE_RATE) / 100)
            star = 2;
        else if (rate < (double) staticConfig.getIntValue(StaticConfig.COIN_TWO_RATE) / 100)
            star = 3;
        else
            star = 4;
        hero.setStar(star);
        OneLotteryResponse oneLotteryResponse = new OneLotteryResponse();
        LotteryRes lotteryRes = new LotteryRes();
        lotteryRes.setStar(star);
        lotteryRes.setTypeId(hero.getTypeId());
        oneLotteryResponse.setLotteryRes(lotteryRes);

        return oneLotteryResponse;
    }

    // 元宝十连抽
    public IGameMessage tenDiamondLottery(Long playerId) {
        // 判断用户元宝并扣除
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        if (playerInfo.getHeroes().size()>=41){
            return new FailMessage(ErrorStatus.HERO_TOO_MUCH_ERROR.getCode(), ErrorStatus.HERO_TOO_MUCH_ERROR.getMsg());
        }
        Integer tenLotteryCoin = staticConfig.getIntValue(StaticConfig.TEN_LOTTERY_DIAMOND);
        boolean b = playerService.changeDiamond(playerId, -tenLotteryCoin);
        if (!b) {
            return new FailMessage(ErrorStatus.DIAMOND_NOT_ENOUGH.getCode(), ErrorStatus.DIAMOND_NOT_ENOUGH.getMsg());
        }
        Random random = new Random();
        double rate;
        int star;
        Integer heroTypeId;
        List<LotteryRes> lotteryResList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            heroTypeId = random.nextInt(6) + 1;
            // 创建英雄
            rate = random.nextDouble();
            if (rate < (double) staticConfig.getIntValue(StaticConfig.COIN_ONE_RATE) / 100)
                star = 2;
            else if (rate < (double) staticConfig.getIntValue(StaticConfig.COIN_TWO_RATE) / 100)
                star = 3;
            else
                star = 4;
            Hero hero = heroService.createHero(playerId, heroTypeId);
            hero.setStar(star);
            LotteryRes lotteryRes = new LotteryRes();
            lotteryRes.setTypeId(heroTypeId);
            lotteryRes.setStar(star);
            lotteryResList.add(lotteryRes);
        }
        heroTypeId = random.nextInt(6) + 1;

        rate = random.nextDouble();
        if (rate < (double) staticConfig.getIntValue(StaticConfig.COIN_TWO_RATE) / 100)
            star = 3;
        else
            star = 4;
        Hero hero = heroService.createHero(playerId, heroTypeId);
        hero.setStar(star);
        LotteryRes lotteryRes = new LotteryRes();
        lotteryRes.setTypeId(heroTypeId);
        lotteryRes.setStar(star);
        lotteryResList.add(lotteryRes);
        TenLotteryResponse tenLotteryResponse = new TenLotteryResponse();
        tenLotteryResponse.setLotteryRes(lotteryResList);
        return tenLotteryResponse;
    }


    // 获取免费单抽的倒计时
    private Integer getLastFreeLotteryTime(Long playerId) {
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        Integer freeDisLotteryTime = staticConfig.getIntValue(StaticConfig.FREE_LOTTERY_TIME);
        String lastFreeLotteryTime = playerInfo.getBaseInfo().getLastFreeLotteryTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lastFreeLotteryUnixTime = 0;
        try {
            lastFreeLotteryUnixTime = df.parse(lastFreeLotteryTime).getTime() / 1000;
        } catch (Exception e) {
            log.error("getTavernInfo error: playerId=" + playerId + " lastFreeLotteryTime=" + lastFreeLotteryTime);
            return 1000000;
        }

        Date date = new Date();
        long curTime = date.getTime() / 1000;

        long lastFreeTime = lastFreeLotteryUnixTime + freeDisLotteryTime - curTime;
        return (int) lastFreeTime;
    }


    public IGameMessage freeDimondLottery(Long playerId) {
        // 判断用户元宝并扣除
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        if (playerInfo.getHeroes().size()>=50){
            return new FailMessage(ErrorStatus.HERO_TOO_MUCH_ERROR.getCode(), ErrorStatus.HERO_TOO_MUCH_ERROR.getMsg());
        }
        Integer lastFreeTime = getLastFreeLotteryTime(playerId);
        if (lastFreeTime>0) {
            // 时间还没有到
            return  new FailMessage(ErrorStatus.FREE_LOTTERY_TIME_ERROR.getCode(), ErrorStatus.FREE_LOTTERY_TIME_ERROR.getMsg());
        }


        Player baseProp = playerInfo.getBaseInfo();

        // 设置免费抽奖时间为当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String curTimeStr = df.format(new Date());
        baseProp.setLastFreeLotteryTime(curTimeStr);

        Random random = new Random();

        // 根据配置的权重随机获取英雄typeId
        Integer heroTypeId = random.nextInt(6) + 1;
        // 创建英雄
        Hero hero = heroService.createHero(playerId, heroTypeId);
        double rate = random.nextDouble();
        int star;
        if (rate < (double) staticConfig.getIntValue(StaticConfig.COIN_ONE_RATE) / 100)
            star = 2;
        else if (rate < (double) staticConfig.getIntValue(StaticConfig.COIN_TWO_RATE) / 100)
            star = 3;
        else
            star = 4;
        hero.setStar(star);
        OneLotteryResponse oneLotteryResponse = new OneLotteryResponse();
        LotteryRes lotteryRes = new LotteryRes();
        lotteryRes.setStar(star);
        lotteryRes.setTypeId(hero.getTypeId());
        oneLotteryResponse.setLotteryRes(lotteryRes);

        return oneLotteryResponse;
    }
}
