package com.pacman.game.server.system;

import com.pacman.game.server.common.GameServerConfig;
import com.pacman.game.server.common.GameServerUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.TimeUnit;


public class GameNetworkServer {
    private Logger logger = LoggerFactory.getLogger(GameNetworkServer.class);
    private ApplicationContext context;
    private EventLoopGroup bossGroup = null;
    private EventLoopGroup workerGroup = null;
    private ChannelFuture channelFuture;

    public GameNetworkServer(ApplicationContext context) {
        this.context = context;
    }

    public void start() {
        //从Spring的上下文bean中获取服务器的配置信息
        GameServerConfig serverConfig = context.getBean(GameServerConfig.class);
        boolean useEpoll = useEpoll();
        bossGroup = useEpoll? new EpollEventLoopGroup(1) : new NioEventLoopGroup(1);// 它主要用来处理连接的管理
        //设置工作线程，工作线程负责处理Channel中的消息
        workerGroup = useEpoll? new EpollEventLoopGroup(serverConfig.getWorkThreads()) :  new NioEventLoopGroup(serverConfig.getWorkThreads());
        int port = serverConfig.getPort();
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            //创建连接channel的初始化器
            GameChannelInitializer channelInitializer = new GameChannelInitializer(context);
            bootstrap.group(bossGroup, workerGroup)
                    .channel(useEpoll? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128).option(ChannelOption.SO_REUSEADDR, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_SNDBUF, serverConfig.getSendBuffSize())
                    .childOption(ChannelOption.SO_RCVBUF, serverConfig.getReceiveBuffSize())
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childHandler(channelInitializer);
            logger.info("----开始启动Netty服务，port:{}", port);
            channelFuture = bootstrap.bind(port);
            channelFuture.sync();
            logger.info("----游戏服务器启动成功，port:{}---", port);
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            logger.error("服务器启动失败,自动退出", e);
            System.exit(0);
        }
    }

    private boolean useEpoll() {
        boolean flag = GameServerUtil.isLinuxPlatform() && Epoll.isAvailable();
        logger.info("启用epoll: {}",flag);
        return flag;
    }

    public void stop() {

        if (channelFuture != null) {
            channelFuture.channel().close();
        }
        int quietPeriod = 5;
        int timeout = 30;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        if (workerGroup != null) {
            workerGroup.shutdownGracefully(quietPeriod, timeout, timeUnit);
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully(quietPeriod, timeout, timeUnit);
        }
    }
}
