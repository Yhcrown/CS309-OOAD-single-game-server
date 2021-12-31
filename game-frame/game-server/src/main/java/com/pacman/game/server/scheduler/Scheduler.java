package com.pacman.game.server.scheduler;

import com.pacman.game.server.beanconfig.StaticConfig;
import com.pacman.game.server.cache.GlobalRankList;
import com.pacman.game.server.cache.PlayerCache;
import com.pacman.game.server.pojo.cache.PlayerInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yhc
 * @create 2021-11-17-19:51
 */
@Component
@Slf4j
public class Scheduler {
    private final Integer LOGOUT_TIME = 2 * 60 * 1000;

    @Autowired
    PlayerCache playerCache;

    @Autowired
    GlobalRankList rankList;

    @Autowired
    StaticConfig staticConfig;

    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void checkHeartBeat() {
        long now = System.currentTimeMillis();
        ConcurrentHashMap<Long, PlayerInfo> playerInfos = playerCache.getPlayerInfos();
        for (PlayerInfo value : playerInfos.values()) {
            if (value.getLastHeartBeatTime() + LOGOUT_TIME < now) {
                //TODO: save data
//                log.debug("fuck!!!!!");
                playerCache.save(value);
                playerInfos.remove(value.getBaseInfo().getId());
            }
        }
    }

    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void updateGlobalRankList() {
        log.debug("Ranklist update");
        rankList.update();
    }
}
