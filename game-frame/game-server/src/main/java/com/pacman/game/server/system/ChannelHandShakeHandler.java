//package com.xinyue.game.server.system;
//
//import com.xinyue.game.server.common.GameServerUtil;
//import com.xinyue.game.utils.encrypt.RSAUtils;
//import com.xinyue.game.utils.encrypt.RsaKeyPair;
//import com.xinyue.network.codec.GameMessageContext;
//import com.xinyue.network.hamdlermapping.GameChannelContext;
//import com.xinyue.network.message.ChannelHandShakeRequest;
//import com.xinyue.network.message.ChannelHandShakeResponse;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInboundHandlerAdapter;
//import io.netty.util.Attribute;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.Base64Utils;
//
///**
// * 连接握手处理，主要的功能就是管理客户端与服务器的加密密钥的交换。此过程借鉴HTTPS的握手流程：<br/>
// * <li>
// *     <ul>1. 客户端连接建立成功之后，向服务器发送请求，获得服务器的非对称加密的公钥</ul>
// *     <ul>2. 客户端随机产生一个对称加密的密钥，并使用服务器非对称加密的公钥，将对称加密密钥传给服务器</ul>
// *     <ul>3. 交换对称加密密钥成功之后，后面的请求和数据返回，都采用此对称密钥加密</ul>
// * </li>
// *
// * @Author 王广帅
// * @Date 2021/3/7 13:38
// */
//public class ChannelHandShakeHandler extends ChannelInboundHandlerAdapter {
//    private static Logger logger = LoggerFactory.getLogger(ChannelHandShakeHandler.class);
//    //对称加密公钥
//    private byte[] aesKey;
//    //rsa公钥
//    private byte[] rsaPublicKey;
//    //rsa私钥
//    private byte[] rsaPrivateKey;
//
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        if (msg instanceof GameMessageContext) {
//            GameMessageContext gameMessageContext = (GameMessageContext) msg;
//            int messageId = gameMessageContext.getHeader().getMessageId();
//            if (messageId == 1) {
//                ChannelHandShakeRequest request = gameMessageContext.getGameMessage();
//                //如果为null，表示第一次握手，获取服务器的Rsa公钥
//                if (request.getAesKey() == null) {
//                    RsaKeyPair rsaKeyPair = RSAUtils.genKeyPair();
//                    this.rsaPublicKey = rsaKeyPair.getPublicKey();
//                    this.rsaPrivateKey = rsaKeyPair.getPrivateKey();
//                    ChannelHandShakeResponse response = new ChannelHandShakeResponse();
//                    String base64Str = Base64Utils.encodeToString(rsaPublicKey);
//                    response.setPubKey(base64Str);
//                    GameMessageContext responseContext = new GameMessageContext(gameMessageContext.getHeader(), null);
//                    responseContext.setGameMessage(response);
//                    gameMessageContext.getHeader().setSendTime(System.currentTimeMillis());
//                    ctx.writeAndFlush(responseContext);
//
//                } else {
//                    logger.debug("收到客户端加密密钥：{}", request.getAesKey());
//                    //如果不为null，表示第二次握手，同步对称加密的密钥
//                    byte[] desEncryptKey = Base64Utils.decodeFromString(request.getAesKey());
//                    try {
//                        this.aesKey = RSAUtils.decryptByPrivateKey(desEncryptKey, this.rsaPrivateKey);
//                        //绑定到channel上面
//                        Attribute<byte[]> attr = ctx.channel().attr(GameChannelContext.CHANNEL_AES_ENCRYPT_KEY);
//                        attr.set(aesKey);
//                        ChannelHandShakeResponse response = new ChannelHandShakeResponse();
//                        GameMessageContext responseContext = new GameMessageContext(gameMessageContext.getHeader(), null);
//                        responseContext.setGameMessage(response);
//                        gameMessageContext.getHeader().setSendTime(System.currentTimeMillis());
//                        ctx.writeAndFlush(responseContext);
//
//                    } catch (Exception e) {
//                        logger.error("对称密钥同步失败，连接关闭:{}", GameServerUtil.getChannelId(ctx.channel()), e);
//                        ctx.close();
//                    }
//
//                }
//                return;
//            } else {
//                //TODO: 添加握手
////                if (rsaPublicKey == null) {
////                    ctx.close();
////                    logger.error("没有握手，断开连接");
////                }
//            }
//        }
//        ctx.fireChannelRead(msg);
//    }
//}
