package com.pacman.game.server.messages.hero;

import com.pacman.game.server.pojo.database.Hero;
import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

import java.util.List;

/**
 * @author yhc
 * @create 2021-12-05-22:50
 */
@Data
@GameMessageMeta(messageId = 10201,messageType = 2)
public class HeroListResponse implements IGameMessage {
    List<Hero> heroList;
}
