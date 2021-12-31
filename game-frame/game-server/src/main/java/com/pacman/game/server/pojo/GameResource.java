package com.pacman.game.server.pojo;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yhc
 * @create 2021-11-17-22:22
 */
public class GameResource {
    private ConcurrentHashMap<String,Pacman> pacmans = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String,Ghost> ghosts = new ConcurrentHashMap<>();
}
