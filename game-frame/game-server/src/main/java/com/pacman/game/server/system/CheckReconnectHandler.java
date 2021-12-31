package com.pacman.game.server.system;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author yhc
 * @create 2021-11-11-16:12
 */
public class CheckReconnectHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        Channel channel = ctx.channel();
//        Attribute<Long> attribute = channel.attr(GameChannelContext.CHANNEL_PLAYER_ID_ATTR);
//        if (attribute != null) {
//            Long id = attribute.get();
//            if (id == null)
//        } else {
//
//
//        }
    }
}
