package com.pacman.game.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.pacman.game.server.beanconfig.HeroConfig;
import com.pacman.game.server.beanconfig.StaticConfig;
import com.pacman.game.server.cache.PlayerCache;
import com.pacman.game.server.messages.FailMessage;
import com.pacman.game.server.messages.hero.HeroLevelUpResponse;
import com.pacman.game.server.messages.hero.HeroStarUpResponse;
import com.pacman.game.server.messages.status.ErrorStatus;
import com.pacman.game.server.mapper.HeroMapper;
import com.pacman.game.server.pojo.cache.PlayerInfo;
import com.pacman.game.server.pojo.database.Hero;
import com.pacman.game.server.pojo.database.mould.HeroMould;
import com.pacman.network.codec.IGameMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author yhc
 * @create 2021-12-04-23:14
 */
@Service
@Slf4j
public class HeroService {


    @Autowired
    StaticConfig staticConfig;

    @Autowired
    HeroMapper heroMapper;

    @Autowired
    HeroConfig heroConfig;

    @Autowired
    PlayerCache playerCache;

    @Autowired
    ItemService itemService;

    @Autowired
    PlayerService playerService;

    public Hero createHero(Long playerId, Integer typeId) {
        HeroMould heroMould = heroConfig.getHeroMould(typeId);
        if (heroMould == null) {
            log.error("HeroMouldIdErrorException");
            return null;
//            throw new HeroMouldIdErrorException();
        }

        Hero hero = new Hero();
        hero.setAccountId(playerId);
        hero.setName(heroMould.getName());
        hero.setLv(1);
        hero.setSkill(heroMould.getInitSkill());
        hero.setSpeed(heroMould.getInitSpeed());
        hero.setCoin(heroMould.getInitCoin());
        hero.setExp(heroMould.getInitExp());
        hero.setTypeId(typeId);

        // 先保存数据到数据库
        try {
            heroMapper.insert(hero);
        } catch (Exception e) {
            log.error("createHero error: playerId=" + playerId + " typeId=" + typeId);
//            throw new HeroCreateErrorException();
            return null;
        }

        // 增加数据到内存
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        playerInfo.getHeroes().add(hero);
        return hero;
    }


    public void initCache(PlayerInfo playerInfo) {
        // 访问数据库，获取用户英雄信息
        QueryWrapper<Hero> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", playerInfo.getBaseInfo().getAccountId());
        List<Hero> heroes = heroMapper.selectList(wrapper);
//        log.debug("get heroes info {}", heroes);
        // 将英雄数据放入缓存中
        playerInfo.setHeroes(heroes);
    }

    public List<Hero> getHeroList(Long playerId) {
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        List<Hero> heroes = playerInfo.getHeroes();
        heroes.sort(new Comparator<Hero>() {
            @Override
            public int compare(Hero o1, Hero o2) {
                if (!Objects.equals(o1.getStar(), o2.getStar())) return o2.getStar() - o1.getStar();
                else return o2.getLv() - o1.getLv();
            }
        });
        return heroes;
    }

    public IGameMessage lvUp(Long playerId, Long heroId) {
        Hero hero = getHero(playerId, heroId);
        if (hero == null)
            return new FailMessage(ErrorStatus.HERO_ID_ERROR.getCode(), ErrorStatus.HERO_ID_ERROR.getMsg()); // 已满级

        // 每星提升20级等级上限
        Integer maxLv = staticConfig.getIntValue(StaticConfig.HERO_MAX_LV_STAR) * hero.getStar();
        if (hero.getLv() >= maxLv) {
            return new FailMessage(ErrorStatus.HERO_LV_MAX_ERROR.getCode(), ErrorStatus.HERO_LV_MAX_ERROR.getMsg()); // 已满级
        }
        int cost = staticConfig.getIntValue(StaticConfig.HERO_LVUP_COST) * hero.getLv();
        if (cost > playerCache.getPlayerInfo(playerId).getBaseInfo().getCoin())
            return new FailMessage(ErrorStatus.COIN_NOT_ENOUGH.getCode(), ErrorStatus.COIN_NOT_ENOUGH.getMsg()); //钱不够

        playerService.changeCoin(playerId, -cost);
        HeroMould heroMould = heroConfig.getHeroMould(hero.getTypeId());

        hero.setLv(hero.getLv() + 1);
        hero.setExp(hero.getExp() + heroMould.getLvUpExp());
        hero.setSpeed(hero.getSpeed() + heroMould.getLvUpSpeed());
        hero.setSkill(hero.getSkill() + heroMould.getLvUpSkill());
        hero.setCoin(hero.getCoin() + heroMould.getLvUpCoin());
        HeroLevelUpResponse response = new HeroLevelUpResponse();
        response.setHero(hero);
        log.debug("hero {} lvup", hero);
        return response; //成功
    }


    public Hero getHero(Long playerId, Long heroId) {
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        List<Hero> heroes = playerInfo.getHeroes();
        for (Hero hero : heroes) {
            if (hero.getId().equals(heroId)) {
                return hero;
            }
        }
//        throw new HeroIdErrorException();
        log.error("get Hero error: playerId=" + playerId + " heroId=" + heroId);
        return null;
    }

    public IGameMessage starUp(Long playerId, Long heroId) {
        Hero mainHero = getHero(playerId, heroId);
        if (mainHero == null)
            return new FailMessage(ErrorStatus.HERO_ID_ERROR.getCode(), ErrorStatus.HERO_ID_ERROR.getMsg());

        Integer star = mainHero.getStar();
        Integer type = mainHero.getTypeId();
        Integer maxLv = staticConfig.getIntValue(StaticConfig.HERO_MAX_LV_STAR) * star;
        if (star == 5)
            return new FailMessage(ErrorStatus.Hero_STAR_MAX_ERROR.getCode(), ErrorStatus.Hero_STAR_MAX_ERROR.getMsg());
        if (!mainHero.getLv().equals(maxLv))
            return new FailMessage(ErrorStatus.HERO_STAR_UP_LEVEL_ERROR.getCode(), ErrorStatus.HERO_STAR_UP_LEVEL_ERROR.getMsg());
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        List<Hero> heroes = playerInfo.getHeroes();
        List<Hero> deleteHeroes = new ArrayList<>();
        for (Hero hero : heroes) {
            if (hero.getStar().equals(star) && hero.getTypeId().equals(type) && !hero.getId().equals(heroId))
                deleteHeroes.add(hero);
        }
        deleteHeroes.sort(new Comparator<Hero>() {
            @Override
            public int compare(Hero o1, Hero o2) {
                return o1.getLv() - o2.getLv();
            }
        });
        if (deleteHeroes.size() < 2)
            return new FailMessage(ErrorStatus.HERO_NOT_ENOUGH_HERO.getCode(), ErrorStatus.HERO_NOT_ENOUGH_HERO.getMsg());
        Hero id1 = deleteHeroes.remove(0);
        Hero id2 = deleteHeroes.remove(0);
        heroes.remove(id1);
        heroes.remove(id2);
        heroMapper.deleteById(id1.getId());
        heroMapper.deleteById(id2.getId());
        mainHero.setStar(mainHero.getStar() + 1);
        HeroStarUpResponse heroStarUpResponse = new HeroStarUpResponse();
        heroStarUpResponse.setHero(mainHero);
        log.debug("hero {} starup, {} and {} be delete", mainHero, id1, id2);
        return heroStarUpResponse;

    }


    public int removeHero(Long playerId, Long heroID) {
        Hero hero = getHero(playerId, heroID);
        if (hero == null) return 1;
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        if (hero.getId()==playerInfo.getChoose().getId())
            return 2;
        List<Hero> heroes = playerInfo.getHeroes();
        if (heroes.size() == 1) return 2;
        playerService.changeCoin(playerId, 1000 * hero.getStar() + 200 * hero.getLv());

        try {
            heroMapper.deleteById(heroID);
            heroes.remove(hero);
        } catch (Exception e) {
            return 1;
        }
        return 0;
    }
}
