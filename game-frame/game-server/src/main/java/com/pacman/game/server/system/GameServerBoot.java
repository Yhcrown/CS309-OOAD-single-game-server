package com.pacman.game.server.system;

import com.pacman.game.server.cache.PlayerCache;
import com.pacman.network.hamdlermapping.GameHandlerMappingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PreDestroy;

/**
 * @author 王广帅
 * @date 2021年01月26日 7:55 下午
 */

public class GameServerBoot {
    private Logger logger = LoggerFactory.getLogger(GameServerBoot.class);
    private ApplicationContext context;
    private GameNetworkServer networkServer;
    private GameHandlerMappingService gameHandlerMappingService;

    @Autowired
    PlayerCache playerCache;
    public GameServerBoot(ApplicationContext context) {
        this.context = context;
    }

    public void init() {
        gameHandlerMappingService = new GameHandlerMappingService();
        gameHandlerMappingService.scanGameHandler(context);
    }

    public void start() {
        //启动Netty服务端监听
        networkServer = new GameNetworkServer(context);
        networkServer.start();
    }

    public ApplicationContext getContext() {
        return context;
    }

    public GameNetworkServer getNetworkServer() {
        return networkServer;
    }

    public GameHandlerMappingService getGameHandlerMappingService() {
        return gameHandlerMappingService;
    }

    @PreDestroy
    public void stop() {
        logger.info("--->服务器开始关闭");
        playerCache.saveAll();
        networkServer.stop();
        logger.info("--->服务器关闭成功");
    }
}
