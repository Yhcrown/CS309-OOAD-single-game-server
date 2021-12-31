package com.pacman.game.server.cache;

import com.pacman.game.server.messages.NotifyReloginPush;
import com.pacman.game.server.mapper.HeroMapper;
import com.pacman.game.server.mapper.ItemMapper;
import com.pacman.game.server.mapper.PlayerMapper;
import com.pacman.game.server.pojo.cache.PlayerInfo;
import com.pacman.game.server.pojo.database.Hero;
import com.pacman.game.server.pojo.database.Item;
import com.pacman.game.server.system.PlayerChannelService;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yhc
 * @create 2021-11-06-14:23
 */
@Component
public class PlayerCache {
    private ConcurrentHashMap<Long, PlayerInfo> playerInfos = new ConcurrentHashMap<>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    HeroMapper heroMapper;

    @Autowired
    PlayerMapper playerMapper;

    @Autowired
    PlayerChannelService playerChannelService;

    @Autowired
    ItemMapper itemMapper;

    public PlayerInfo getPlayerInfo(Long playerId) {
        return getPlayerInfo(playerId, true);
    }

    public PlayerInfo getPlayerInfo(Long playerId, boolean throwException) {
        PlayerInfo playerInfo = playerInfos.get(playerId);
        if (playerInfo == null && throwException) {
            logger.debug("can't find the player {}", playerId);
            Channel channel = playerChannelService.channelMap.remove(playerId);
            if (channel != null) {
                channel.writeAndFlush(new NotifyReloginPush());
                channel.close();
            }
            return null;
        }
        return playerInfo;
    }

    public ConcurrentHashMap<Long, PlayerInfo> getPlayerInfos() {
        return playerInfos;
    }

    public void addPlayerInfo(PlayerInfo playerInfo) {
        playerInfos.put(playerInfo.getBaseInfo().getAccountId(), playerInfo);
    }

    public void save(PlayerInfo playerInfo) {
        playerMapper.updateById(playerInfo.getBaseInfo());
        List<Hero> heroes = playerInfo.getHeroes();
        for (Hero hero : heroes) {
            heroMapper.updateById(hero);
        }
        List<Item> items = playerInfo.getItems();
        for (Item item : items) {
            itemMapper.updateById(item);
        }
    }

    public void removeOne(Long playerId) {
        playerInfos.remove(playerId);

    }

    public void saveAll() {
        for (PlayerInfo playerInfo : playerInfos.values()) {
            save(playerInfo);
            Channel channel = playerChannelService.channelMap.remove(playerInfo.getBaseInfo().getAccountId());
            if (channel != null) {
                channel.writeAndFlush(new NotifyReloginPush());
                channel.close();
            }
        }
    }
}
