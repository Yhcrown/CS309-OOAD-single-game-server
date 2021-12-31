package com.pacman.network.codec;

import io.netty.buffer.ByteBuf;

public class GameMessageHeader {

    public static int HEADER_LENGTH = 21;
    private int messageId;
    private int seqId;
    private long sendTime;
    private int errorCode;
    //消息类型：1 request, 2 response , 3 push
    private int messageType;


    public void write(ByteBuf byteBuf) {
        //共21个字节
        byteBuf.writeInt(seqId);
        byteBuf.writeInt(messageId);
        byteBuf.writeByte(messageType);
        byteBuf.writeLong(sendTime);
        byteBuf.writeInt(errorCode);
    }

    public void read(ByteBuf byteBuf) {
        this.seqId = byteBuf.readInt();
        this.messageId = byteBuf.readInt();
        this.messageType = byteBuf.readByte();
        this.sendTime = byteBuf.readLong();
        this.errorCode = byteBuf.readInt();
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getSeqId() {
        return seqId;
    }

    public void setSeqId(int seqId) {
        this.seqId = seqId;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
}
