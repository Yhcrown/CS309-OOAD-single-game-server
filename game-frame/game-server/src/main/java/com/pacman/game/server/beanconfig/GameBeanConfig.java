package com.pacman.game.server.beanconfig;

import com.pacman.game.server.system.GameServerBoot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 王广帅
 * @date 2021年01月26日 8:09 下午
 */
@Configuration
public class GameBeanConfig {
    @Autowired
    private ApplicationContext context;

    @Bean
    public GameServerBoot gameServerBoot() {
        GameServerBoot gameServerBoot = new GameServerBoot(context);
        gameServerBoot.init();
        return gameServerBoot;
    }
}
