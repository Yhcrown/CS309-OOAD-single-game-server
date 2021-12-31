package com.pacman.game.server;

import com.pacman.game.server.system.GameServerBoot;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableScheduling
@SpringBootApplication
public class ServerBootMain {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ServerBootMain.class);
        app.setBannerMode(Mode.LOG);
        app.setWebApplicationType(WebApplicationType.NONE);
        ApplicationContext context = app.run(args);
        //启动服务
        GameServerBoot gameServerBoot = context.getBean(GameServerBoot.class);
        gameServerBoot.start();
    }
}

