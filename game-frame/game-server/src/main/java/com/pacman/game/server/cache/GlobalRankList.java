package com.pacman.game.server.cache;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pacman.game.server.beanconfig.StaticConfig;
import com.pacman.game.server.mapper.PlayerMapper;
import com.pacman.game.server.pojo.cache.PlayerInfo;
import com.pacman.game.server.pojo.database.Player;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

/**
 * @author yhc
 * @create 2021-12-25-21:01
 */
@Component
@Data
public class GlobalRankList {
    private List<Player> rankList;

    @Autowired
    PlayerMapper playerMapper;
    @Autowired
    StaticConfig staticConfig;

    @Autowired
    PlayerCache playerCache;

    public void update() {
        Integer intValue;
        try {
            intValue = staticConfig.getIntValue(StaticConfig.PLAYER_LVUP_EXP);
            QueryWrapper<Player> playerQueryWrapper = new QueryWrapper<>();
            setRankList(playerMapper.selectList(playerQueryWrapper));
//            for (int i = 0; i < rankList.size(); i++) {
//                PlayerInfo playerInfo = playerCache.getPlayerInfo(rankList.get(i).getAccountId(), false);
//                if (playerInfo != null)
//                    rankList.set(i, playerInfo.getBaseInfo());
//            }
            rankList.sort(new Comparator<Player>() {
                @Override
                public int compare(Player o1, Player o2) {
                    return o2.getLevel() * intValue + o2.getExp() - (o1.getLevel() * intValue + o1.getExp());
                }
            });
        } catch (NumberFormatException e) {

        }

    }
}
