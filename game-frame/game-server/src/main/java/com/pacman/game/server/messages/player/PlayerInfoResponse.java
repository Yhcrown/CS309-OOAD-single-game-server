package com.pacman.game.server.messages.player;

import com.pacman.game.server.pojo.cache.PlayerInfo;
import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-11-05-22:33
 */
@GameMessageMeta(messageId = 10003,messageType =2 ,isEncrypt = true)
@Data
public class PlayerInfoResponse implements IGameMessage {
//    private String nickname;
//    private Integer level;
//    private Integer head;
//    private Integer coin;
//    private Integer exp;
    PlayerInfo playerInfo;
}
