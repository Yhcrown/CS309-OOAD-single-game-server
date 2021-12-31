package com.pacman.game.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pacman.game.server.beanconfig.StaticConfig;
import com.pacman.game.server.cache.PlayerCache;
import com.pacman.game.server.messages.player.PlayerInfoResponse;
import com.pacman.game.server.mapper.PlayerMapper;
import com.pacman.game.server.pojo.cache.PlayerInfo;
import com.pacman.game.server.pojo.database.Hero;
import com.pacman.game.server.pojo.database.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author yhc
 * @create 2021-11-05-22:02
 */
@Service
@Slf4j
public class PlayerService {
    @Autowired
    PlayerMapper playerMapper;

    @Autowired
    PlayerCache playerCache;

    @Autowired
    HeroService heroService;

    @Autowired
    ItemService itemService;

    @Autowired
    StaticConfig staticConfig;

    public Player createPlayer(Long playerId, String name) {
        Player player = new Player();
        player.setNickname(name);
        player.setCoin(0);
        player.setExp(0);
        player.setLevel(1);
        player.setDeath(0);
        player.setDiamond(0);
        player.setTotal(0);
        player.setWin(0);
        player.setItem(0);
        player.setKillNum(0);
        player.setAccountId(playerId);
        player.setChooseHero(0L);

        try {
            playerMapper.insert(player);
        } catch (Exception e) {
//            return new FailMessage(ErrorStatus.PLAYER_CREATE_ERROR.getCode(), ErrorStatus.PLAYER_CREATE_ERROR.getMsg());
        }
        PlayerInfoResponse response = new PlayerInfoResponse();
//        response.setPlayer(player);
//        response.setNickname(name);
//        response.setHead(1);
//        response.setCoin(0);
//        response.setExp(0);
//        response.setLevel(1);
        return player;
    }

    public Player getInfo(Long playerId) {
        QueryWrapper<Player> wrapper = new QueryWrapper<Player>();
        wrapper.eq("account_id", playerId);
        Player player = playerMapper.selectOne(wrapper);
        if (player == null) {
            player = createPlayer(playerId, "pacman");
            initCache(player);
        } else
            initCache(player);
        if (player.getLastFreeLotteryTime() == null || player.getLastFreeLotteryTime().equals("0")) {
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            date = calendar.getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String initTimeStr = df.format(date);
            player.setLastFreeLotteryTime(initTimeStr);
        }
        return player;

    }

    public void initCache(Player player) {
        PlayerInfo playerInfo = playerCache.getPlayerInfo(player.getAccountId(), false);
        if (playerInfo != null) {
            return;
        }
        playerInfo = new PlayerInfo();
        playerInfo.setBaseInfo(player);
        playerInfo.setLastHeartBeatTime(System.currentTimeMillis());
        playerCache.addPlayerInfo(playerInfo);
        heroService.initCache(playerInfo);
        // 加载道具信息
        itemService.initCache(playerInfo);

        Hero choose = heroService.getHero(player.getAccountId(), player.getChooseHero());
//        log.debug("choose {}", choose);
        if (choose == null) {
            List<Hero> heroList = heroService.getHeroList(player.getAccountId());
            if (heroList.size() == 0)
                heroService.createHero(player.getAccountId(), 1);
            Hero temp = heroList.get(0);
            playerInfo.setChoose(temp);

            player.setChooseHero(temp.getId());
        } else {
            playerInfo.setChoose(choose);

        }
//        System.out.println("add " + playerInfo + " to cache");

    }

    public void heartBeat(Long playerId) {
        playerCache.getPlayerInfo(playerId).setLastHeartBeatTime(System.currentTimeMillis());
    }

    public void changeName(String newName) {

    }


    public boolean changeCoin(Long playerId, Integer coin) {
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        Player baseInfo = playerInfo.getBaseInfo();
        Integer curCoin = baseInfo.getCoin();

        if (coin < 0) {
            if (curCoin < -coin) {
                return false;
            }
        } else if (coin == 0) {
            return true;
        }

        Integer newCoin = curCoin + coin;
        baseInfo.setCoin(newCoin);

        return true;
    }

    public Integer getCoin(Long playerId) {
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        return playerInfo.getBaseInfo().getCoin();
    }


    public boolean changeDiamond(Long playerId, Integer diamond) {
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        Player baseInfo = playerInfo.getBaseInfo();
        Integer curDiamond = baseInfo.getDiamond();

        if (diamond < 0) {
            if (curDiamond < -diamond) {
                return false;
            }
        } else if (diamond == 0) {
            return true;
        }

        Integer newDiamond = curDiamond + diamond;
        baseInfo.setDiamond(newDiamond);

        return true;
    }

    public Integer getDimond(Long playerId) {
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        return playerInfo.getBaseInfo().getDiamond();
    }

    public void addExp(Long playerId, Integer exp) {
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        Player baseInfo = playerInfo.getBaseInfo();
        baseInfo.setExp(baseInfo.getExp() + exp);
    }

    public Integer lvUp(Long playerId) {
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        Integer intValue = staticConfig.getIntValue(StaticConfig.PLAYER_LVUP_EXP);
        Integer exp = playerInfo.getBaseInfo().getExp();
        Integer level = playerInfo.getBaseInfo().getLevel();
        int count = 0;

        while (exp >= intValue) {
            exp -= intValue;
            playerInfo.getBaseInfo().setExp(exp);
            playerInfo.getBaseInfo().setLevel(level + 1);
            changeCoin(playerId, 5000 * (level / 10 + 1));
            changeDiamond(playerId, 50 * (level / 10 + 1));
            itemService.addItem(playerId, 9, (level / 10 + 1));
            count += 1;
//            return true;
        }
        return count;
    }
}
