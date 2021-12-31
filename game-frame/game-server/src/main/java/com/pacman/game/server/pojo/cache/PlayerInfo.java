package com.pacman.game.server.pojo.cache;

import com.pacman.game.server.pojo.database.Hero;
import com.pacman.game.server.pojo.database.Item;
import com.pacman.game.server.pojo.database.Player;
import lombok.Data;

import java.util.List;

/**
 * @author yhc
 * @create 2021-11-06-14:24
 */
@Data
public class PlayerInfo {
    Player baseInfo;
    long lastHeartBeatTime;
    private List<Hero> heroes;
    private List<Item> items;
    Boolean inRoom = Boolean.FALSE;
    Long roomId;
    Hero choose;

}
