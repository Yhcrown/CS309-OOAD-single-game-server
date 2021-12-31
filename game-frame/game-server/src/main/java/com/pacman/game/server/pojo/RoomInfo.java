package com.pacman.game.server.pojo;

import lombok.Data;

/**
 * @author yhc
 * @create 2021-11-25-21:09
 */
@Data
public class RoomInfo {
    private Long roomId;
    private Integer type;
    private String name;
    private Integer mapId;
    private Integer peopleNum;
    private Integer capacity;
    private Integer pacmanNum;

}
