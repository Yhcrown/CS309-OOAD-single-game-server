package com.pacman.game.server.messages.game;

import com.pacman.game.server.pojo.PlayerWithHero;
import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

import java.util.List;

/**
 * @author yhc
 * @create 2021-12-19-16:32
 */
@Data
@GameMessageMeta(messageId = 10601,messageType = 3)
public class StartGamePush implements IGameMessage {
    List<PlayerWithHero> allocation;
}
