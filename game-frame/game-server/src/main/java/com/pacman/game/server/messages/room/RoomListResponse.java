package com.pacman.game.server.messages.room;

import com.pacman.game.server.pojo.RoomInfo;
import com.pacman.network.codec.GameMessageMeta;
import com.pacman.network.codec.IGameMessage;
import lombok.Data;

import java.util.List;

/**
 * @author yhc
 * @create 2021-11-25-22:01
 */
@Data
@GameMessageMeta(messageId = 10100 , messageType = 2)
public class RoomListResponse implements IGameMessage {
    List<RoomInfo> info;
}
