package com.pacman.game.server.pojo;

import lombok.Data;

import java.util.LinkedHashMap;

/**
 * @author yhc
 * @create 2021-11-17-21:41
 */

@Data
public class Room extends RoomInfo{
    private RoomInfo info;
    private Boolean start;
    private GameResource resource;
    private LinkedHashMap<Long, PlayerWithHero> playerMap =new LinkedHashMap<>(8);
    private Long managerId;

}
