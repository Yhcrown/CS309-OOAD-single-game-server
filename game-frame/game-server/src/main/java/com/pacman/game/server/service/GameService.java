package com.pacman.game.server.service;

import com.pacman.game.server.cache.PlayerCache;
import com.pacman.game.server.messages.game.GameRes;
import com.pacman.game.server.messages.game.Reward;
import com.pacman.game.server.pojo.PlayerWithHero;
import com.pacman.game.server.pojo.Room;
import com.pacman.game.server.pojo.cache.PlayerInfo;
import com.pacman.game.server.pojo.database.Hero;
import com.pacman.game.server.pojo.database.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author yhc
 * @create 2021-12-18-22:21
 */
@Service
public class GameService {

    @Autowired
    PlayerService playerService;

    @Autowired
    PlayerCache playerCache;

    @Autowired
    ItemService itemService;

    @Autowired
    RoomService roomService;

    public Reward gameRes(Long playerId, GameRes gameRes) {
        Integer mode = gameRes.getMode();
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);

        if (mode == 1 || mode == 2 || playerInfo.getInRoom()) {
            Long roomId = gameRes.getRoomId();
            Room room = roomService.getRoom(roomId);
            LinkedHashMap<Long, PlayerWithHero> playerMap = room.getPlayerMap();
            for (PlayerWithHero value : playerMap.values()) {
                value.setIsReady(false);
            }
            room.setStart(Boolean.FALSE);
        }
        Integer map = gameRes.getMap();
        Integer killNum = gameRes.getKill();
        Integer death = gameRes.getDeath();
        Integer rank = gameRes.getRank();

        Boolean win = gameRes.getWin();
        Reward reward = new Reward();
        int coin = 0;
        int exp = 0;
        int box = 0;
//        if (map == 1) {
        if (win) {
            if (rank == 1) {
                coin = 5000;
                exp = 80;
                box = 1;
            } else {
                coin = 3000;
                exp = 60;
                box = 0;
            }
        } else {
            coin = 1000;
            exp = 30;
        }
//        }


        Hero choose = playerInfo.getChoose();
        Player baseInfo = playerInfo.getBaseInfo();


        baseInfo.setDeath(baseInfo.getDeath() + death);
        baseInfo.setKillNum(baseInfo.getKillNum() + killNum);

        baseInfo.setTotal(baseInfo.getTotal() + 1);
        if (win)
            baseInfo.setWin(baseInfo.getWin() + 1);
        if (baseInfo.getDoubleCoinLeft() > 0) {
            coin *= 2;
            baseInfo.setDoubleCoinLeft(baseInfo.getDoubleCoinLeft() - 1);
        }
        if (baseInfo.getDoubleExpLeft() > 0) {
            exp *= 2;
            baseInfo.setDoubleExpLeft(baseInfo.getDoubleExpLeft() - 1);
        }
        coin *= (choose.getCoin() + 100) / 100.0;
        exp *= (choose.getExp() + 100) / 100.0;
        playerService.changeCoin(playerId, coin);
        playerService.addExp(playerId, exp);
        itemService.addItem(playerId, 9, box);
        reward.setCoin(coin);
        reward.setExp(exp);
        reward.setBox(box);
        reward.setLvUp(playerService.lvUp(playerId));
        return reward;
    }


    public boolean checkStart(Long roomId) {
        Room room = roomService.getRoom(roomId);
        for (PlayerWithHero value : room.getPlayerMap().values()) {
            if (!value.getIsReady() && !Objects.equals(value.getPlayer().getAccountId(), room.getManagerId())) {
                return false;
            }
        }
        return true;
    }

    public List<PlayerWithHero> gameInfo(Long roomId) {
        Room room = roomService.getRoom(roomId);
        LinkedHashMap<Long, PlayerWithHero> playerMap = room.getPlayerMap();
        ArrayList<PlayerWithHero> playerWithHeroes = new ArrayList<>(playerMap.values());
        for (PlayerWithHero playerWithHero : playerWithHeroes) {
            playerWithHero.setIsReady(false);
        }
        Collections.shuffle(playerWithHeroes);
        return playerWithHeroes;
    }
}
