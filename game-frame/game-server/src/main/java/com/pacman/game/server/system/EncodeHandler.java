package com.pacman.game.server.system;

import com.pacman.network.codec.GameCodecFactory;
import com.pacman.network.codec.GameMessageContext;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;

@ChannelHandler.Sharable
public class EncodeHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof GameMessageContext) {
            GameMessageContext gameMessageContext = (GameMessageContext) msg;
            ByteBuf byteBuf = GameCodecFactory.writeMessage(gameMessageContext);
            ctx.writeAndFlush(byteBuf, promise);
        } else {
            ctx.writeAndFlush(msg, promise);
        }
    }
}
