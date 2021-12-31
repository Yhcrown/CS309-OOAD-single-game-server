package com.pacman.game.server.messages.player;

import com.pacman.game.server.pojo.database.Player;
import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

import java.util.List;

/**
 * @author yhc
 * @create 2021-12-25-21:22
 */
@GameMessageMeta(messageId = 10015,messageType = 2)
@Data
public class RankListResponse implements IGameMessage {
    List<Player> rankList;
}
