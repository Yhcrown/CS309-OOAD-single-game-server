package com.pacman.network.codec;

/**
 * @Author 王广帅
 * @Date 2021/1/24 18:02
 */
public class GameMessageContext {
    private GameMessageHeader header;
    private IGameMessage gameMessage;
    private byte[] encryptFactors;//消息加密因子

    public GameMessageContext(GameMessageHeader header,byte[] encryptFactors) {
        this.header = header;
        this.encryptFactors = encryptFactors;
    }

    public void setGameMessage(IGameMessage gameMessage) {
        this.gameMessage = gameMessage;
        if (gameMessage != null) {
            GameMessageMeta gameMessageMeta = gameMessage.getClass().getAnnotation(GameMessageMeta.class);
            header.setMessageId(gameMessageMeta.messageId());
            header.setMessageType(gameMessageMeta.messageType());
        }
    }

    public GameMessageHeader getHeader() {
        return header;
    }

    public <T extends IGameMessage> T  getGameMessage() {
        return (T)gameMessage;
    }

    public byte[] getEncryptFactors() {
        return encryptFactors;
    }
}
