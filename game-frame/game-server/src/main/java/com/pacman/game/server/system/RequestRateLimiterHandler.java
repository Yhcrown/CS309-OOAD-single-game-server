package com.pacman.game.server.system;

import com.google.common.util.concurrent.RateLimiter;
import com.pacman.game.server.common.GameServerUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * @Author 王广帅
 * @Date 2021/4/11 15:08
 */
public class RequestRateLimiterHandler extends ChannelInboundHandlerAdapter {
    private RateLimiter rateLimiter;
    private static Logger logger = LoggerFactory.getLogger(RequestRateLimiterHandler.class);
    private PlayerChannelService playerChannelService;
    public RequestRateLimiterHandler(int qps, ApplicationContext context){
        rateLimiter = RateLimiter.create(qps);
        this.playerChannelService = context.getBean(PlayerChannelService.class);
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       boolean flag = rateLimiter.tryAcquire();
       if(flag){
           ctx.fireChannelRead(msg);
       } else {
           long playerId = playerChannelService.getPlayerId(ctx.channel());
           logger.error("请求太频繁，断开连接,channelId:{},playerId:{}", GameServerUtil.getChannelId(ctx.channel()),playerId);
       }
    }
}
