package com.pacman.game.server.service;

import lombok.Data;
import org.springframework.stereotype.Service;

/**
 * @author yhc
 * @create 2021-12-26-13:16
 */
@Service
@Data
public class GlobalService {
    // 服务器是否是关闭状态
    public boolean isClose = false;

    public String reason = " ";

    private final String CLOSE_SERVER_PASSWORD = "YuanHengChen";

    private final String content = "This Game is a SUSTech OOAD project, and this game is only test for show\n" +
            "and doesn't mean that it is the final quality !\n" +
            "\n" +
            "Now this game can only contain 20 perple playing multiple mode \n" +
            "at the same time, so if you enter multiple mode but find you can't \n" +
            "enter, don't be nervous.\n";
}

