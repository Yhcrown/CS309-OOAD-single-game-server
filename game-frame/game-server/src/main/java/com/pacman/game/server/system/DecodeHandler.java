package com.pacman.game.server.system;

import com.pacman.network.codec.GameCodecFactory;
import com.pacman.network.codec.GameMessageContext;
import com.pacman.network.message.ChannelHandShakeRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.TooLongFrameException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class DecodeHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(GameChannelInitializer.class);

    public DecodeHandler() {
        //添加系统消息
        GameCodecFactory.addMessageClass(ChannelHandShakeRequest.class);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        try {
//            Attribute<byte[]> attr = ctx.channel().attr(GameChannelContext.CHANNEL_AES_ENCRYPT_KEY);
            GameMessageContext gameMessageContext = GameCodecFactory.readMessage(byteBuf, new byte[]{0});
            ctx.fireChannelRead(gameMessageContext);
        }finally {
            byteBuf.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof TooLongFrameException){
            logger.debug("too long message");
        }else
            ctx.fireExceptionCaught(cause);
    }
}
