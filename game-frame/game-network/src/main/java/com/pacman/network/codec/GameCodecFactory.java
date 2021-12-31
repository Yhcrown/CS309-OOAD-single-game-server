package com.pacman.network.codec;

import com.alibaba.fastjson.JSON;
//import com.xinyue.game.utils.encrypt.AESUtils;
import com.pacman.network.hamdlermapping.MessageClassKey;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 王广帅
 * @Date 2021/1/24 17:40
 */
public class GameCodecFactory {

    private static Logger logger = LoggerFactory.getLogger(GameCodecFactory.class);
    //如果包体的长度大于这个值，将执行压缩操作
    private static final int COMPRESS_LENGTH = 2;

    private static Map<MessageClassKey, Class<? extends IGameMessage>> messageClassMap = new HashMap<>();

    public static MessageClassKey addMessageClass(Class<? extends IGameMessage> clazz) {
        GameMessageMeta gameMessageMeta = clazz.getAnnotation(GameMessageMeta.class);
        MessageClassKey messageClassKey = new MessageClassKey(gameMessageMeta.messageId(), gameMessageMeta.messageType());
        messageClassMap.put(messageClassKey, clazz);
        return messageClassKey;
    }

    public static ByteBuf writeMessage(GameMessageContext gameMessageContext) throws Exception {
        IGameMessage gameMessage = gameMessageContext.getGameMessage();
        int totalSize = 4;//消息长度所占字节数量
        byte[] bodyBytes = null;
        int isCompress = 0;
        int isEncrypt = 0;
        totalSize += 2;//isComporess isEncrypt 所占字节长度
        if (gameMessage != null) {
            String toJson = JSON.toJSONString(gameMessage);
//            System.out.println("1.Json: "+toJson);
            bodyBytes = toJson.getBytes(StandardCharsets.UTF_8);
//            System.out.println("2.bytes "+ Arrays.toString(bodyBytes));
//            GameMessageMeta gameMessageMeta = gameMessage.getClass().getAnnotation(GameMessageMeta.class);
//            if (bodyBytes != null) {
//                if (bodyBytes.length > COMPRESS_LENGTH) {
//                    isCompress = 1;
//                    bodyBytes = GzipUtil.compress(bodyBytes);
//                    System.out.println("3.Compress "+Arrays.toString(bodyBytes));
//                }
//                if (gameMessageContext.getEncryptFactors() != null && gameMessageMeta.isEncrypt()) {
//                    isEncrypt = 1;
//                    if (logger.isDebugEnabled()) {
//                        logger.debug("发送加密消息，密钥：{}", Base64Utils.encode(gameMessageContext.getEncryptFactors()));
//                    }
//                    bodyBytes = encryptBody(bodyBytes, gameMessageContext.getEncryptFactors());
//                    System.out.println("4.encrypt"+Arrays.toString(bodyBytes));
//                }
//            }
            totalSize += bodyBytes.length;
        }
        GameMessageHeader header = gameMessageContext.getHeader();
        totalSize += GameMessageHeader.HEADER_LENGTH;

        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.directBuffer(totalSize);
        byteBuf.writeInt(totalSize);
        //写入包头信息
        header.write(byteBuf);
        byteBuf.writeByte(isEncrypt);
        byteBuf.writeByte(isCompress);
        //写入包体信息
        if (bodyBytes != null && bodyBytes.length > 0) {
            byteBuf.writeBytes(bodyBytes);
        }
        return byteBuf;
    }

    /**
     * 消息体进行加密操作
     *
     * @param body
     * @return
     */
    private static byte[] encryptBody(byte[] body, byte[] encryptFactors) throws Exception {
//        body = AESUtils.encode(encryptFactors, body);
        return body;
    }

    public static GameMessageContext readMessage(ByteBuf byteBuf, byte[] decryptFactors) {

        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.duplicate().readBytes(bytes);
//        System.out.println("message "+new String(bytes));
//        System.out.println();
        int messageSize = byteBuf.readInt();

        try {
            GameMessageHeader header = new GameMessageHeader();
            header.read(byteBuf);
            boolean isEncrypt = byteBuf.readByte() == 1 ? true : false;
            boolean isUncompress = byteBuf.readByte() == 1 ? true : false;
            IGameMessage gameMessage = null;
            if (byteBuf.isReadable()) {
                byte[] body = new byte[byteBuf.readableBytes()];
                byteBuf.readBytes(body);
//                if (isEncrypt) {
//                    if (logger.isDebugEnabled()) {
//                        logger.debug("消息需要解密，密钥：{}", Base64Utils.encode(decryptFactors));
//                    }
//                    //此消息经过加密了，读取的时候，需要解密
//                    body = AESUtils.decode(decryptFactors, body);
//                }
//                if (isUncompress) {
//                    //说明此消息压缩了，需要解压
//                    body = GzipUtil.uncompress(body);
//                }
                int messageId = header.getMessageId();
//                System.out.println("type:"+header.getMessageType());
                MessageClassKey classKey = new MessageClassKey(messageId, header.getMessageType());
                Class<? extends IGameMessage> clazz = messageClassMap.get(classKey);
                if (clazz == null) {
                    throw new IllegalArgumentException("不存在的消息:" + messageId);
                }
                String toJson = new String(body, StandardCharsets.UTF_8);
                gameMessage = JSON.parseObject(toJson, clazz);
            }
            GameMessageContext gameMessageContext = new GameMessageContext(header, null);
            gameMessageContext.setGameMessage(gameMessage);
            return gameMessageContext;
        } catch (Throwable e) {
            logger.error("读取消息异常，消息长度：{}", messageSize, e);
        }
        return null;
    }

    public static ByteBuf writePushMessage(IGameMessage gameMessage, byte[] encryptFactors) throws Exception {
        GameMessageMeta gameMessageMeta = gameMessage.getClass().getAnnotation(GameMessageMeta.class);
        if (gameMessageMeta.messageType() != 3) {
            throw new IllegalArgumentException("不是push类型消息，不能调用此方法，" + gameMessage.getClass().getName());
        }
        GameMessageHeader header = new GameMessageHeader();
        header.setMessageType(gameMessageMeta.messageType());
        header.setMessageId(gameMessageMeta.messageId());
        long nowTime = System.currentTimeMillis();
        header.setSendTime(nowTime);
        GameMessageContext gameMessageContext = new GameMessageContext(header, encryptFactors);
        gameMessageContext.setGameMessage(gameMessage);
        return GameCodecFactory.writeMessage(gameMessageContext);
    }
}
