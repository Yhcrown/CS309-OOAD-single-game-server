package com.pacman.game.server.system;

import com.pacman.game.server.dao.entity.GamePlayer;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author 王广帅
 * @Date 2021/4/11 15:27
 */
@Service
public class PlayerCacheService {

    private Map<Long, GamePlayer> gamePlayerMap = new ConcurrentHashMap<>();
}
