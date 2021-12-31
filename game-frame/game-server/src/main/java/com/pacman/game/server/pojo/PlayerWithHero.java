package com.pacman.game.server.pojo;

import com.pacman.game.server.pojo.database.Hero;
import com.pacman.game.server.pojo.database.Player;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-12-15-20:52
 */
@Data
@AllArgsConstructor
public class PlayerWithHero {
    Player player;
    Hero hero;
    Boolean isReady;
}
