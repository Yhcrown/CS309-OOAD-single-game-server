package com.pacman.game.server.system;

import com.pacman.game.server.common.GameServerConfig;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * Netty的channel初始化器，负责向channel中添加各种消息处理器
 *
 * @Author 王广帅
 * @Date 2021/1/24 17:20
 */
public class GameChannelInitializer extends ChannelInitializer<Channel> {
    private static Logger logger = LoggerFactory.getLogger(GameChannelInitializer.class);

    private ApplicationContext context;
    private DecodeHandler decodeHandler;
    private EncodeHandler encodeHandler;

    public GameChannelInitializer(ApplicationContext context ) {
        this.context = context;
        decodeHandler = new DecodeHandler();
        encodeHandler = new EncodeHandler();
    }

    @Override
    protected void initChannel(Channel ch) {
        GameServerConfig gameServerConfig = context.getBean(GameServerConfig.class);
        ChannelPipeline p = ch.pipeline();
        int allIdleTimeSeconds = gameServerConfig.getMaxIdleTime();
        p.addLast(new IdleStateHandler(0, 0, allIdleTimeSeconds));
//        p.addLast(new ShowMessageHandler());
        p.addLast(new LengthFieldBasedFrameDecoder(1024 * 8, 0, 4, -4, 0));// 添加拆包
        p.addLast(new RequestRateLimiterHandler(gameServerConfig.getRequestQps(), context));
//        p.addLast(new HttpServerCodec());
        p.addLast("DecodeHandler", decodeHandler);// 添加解码
        p.addLast("EncodeHandler", encodeHandler);// 添加编码Handler
//        p.addLast("handshakeHandler",new ChannelHandShakeHandler());
        p.addLast("GameLogicHandler", new GameLogicHandler(context));
    }
}
