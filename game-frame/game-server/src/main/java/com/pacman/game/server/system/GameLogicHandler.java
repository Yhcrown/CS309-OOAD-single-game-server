package com.pacman.game.server.system;

import com.pacman.game.server.cache.PlayerCache;
import com.pacman.game.server.messages.global.AnnouncementRequest;
import com.pacman.game.server.messages.global.CloseServerRequest;
import com.pacman.game.server.messages.global.OpenServerRequest;
import com.pacman.game.server.messages.player.RegisterRequest;
import com.pacman.game.server.messages.player.LoginRequest;
import com.pacman.network.codec.GameMessageContext;
import com.pacman.network.codec.GameMessageHeader;
import com.pacman.network.codec.IGameMessage;
import com.pacman.network.hamdlermapping.GameChannelContext;
import com.pacman.network.hamdlermapping.GameHandlerMappingService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class GameLogicHandler extends ChannelInboundHandlerAdapter {
    private ApplicationContext appContext;
    private Logger logger = LoggerFactory.getLogger(GameLogicHandler.class);
    private GameServerBoot gameServerBoot;

    @Autowired
    PlayerCache playerCache;
    @Autowired
    PlayerChannelService playerChannelService;

    public GameLogicHandler(ApplicationContext appContext) {
        this.appContext = appContext;
        gameServerBoot = appContext.getBean(GameServerBoot.class);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        GameMessageContext gameMessageContext = (GameMessageContext) msg;
        IGameMessage gameMessage = gameMessageContext.getGameMessage();
        GameHandlerMappingService gameHandlerMappingService = gameServerBoot.getGameHandlerMappingService();
        GameMessageHeader header = gameMessageContext.getHeader();
        int messageId = header.getMessageId();
        int messageType = header.getMessageType();
        GameChannelContext gtx = new GameChannelContext(ctx, gameMessageContext);
        Channel channel = ctx.channel();
        Attribute<Long> attribute = channel.attr(GameChannelContext.CHANNEL_PLAYER_ID_ATTR);
        if (gameMessage instanceof CloseServerRequest || gameMessage instanceof OpenServerRequest || gameMessage instanceof AnnouncementRequest) {
            gameHandlerMappingService.callMethod(gtx, header, gameMessageContext.getGameMessage());
            return;
        }
        if ((!(gameMessage instanceof LoginRequest) && !(gameMessage instanceof RegisterRequest)) && attribute == null) {
//            ctx.writeAndFlush(new NotifyReloginPush());
            logger.error("player info lose!");
            ctx.channel().close();
        } else {
            if ((!(gameMessage instanceof LoginRequest) && !(gameMessage instanceof RegisterRequest)) && attribute.get() == null) {
//                ctx.writeAndFlush(new NotifyReloginPush());
                logger.error("player info lose!");
                ctx.channel().close();
            } else
                gameHandlerMappingService.callMethod(gtx, header, gameMessageContext.getGameMessage());
        }
//        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //接收channel内部的一些事件
        if (evt instanceof IdleStateEvent) {
            logger.debug("channel idle and to be close,{}", ctx.channel().id().asShortText());
            ctx.channel().close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("服务器内部异常", cause);
    }
}
