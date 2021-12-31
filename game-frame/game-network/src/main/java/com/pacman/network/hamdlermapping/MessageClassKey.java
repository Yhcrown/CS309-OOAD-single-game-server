package com.pacman.network.hamdlermapping;

import java.util.Objects;

/**
 * @author 王广帅
 * @date 2021年01月26日 4:42 下午
 */
public class MessageClassKey {
    private int messageId;
    private int messageType;

    public MessageClassKey(int messageId, int messageType) {
        this.messageId = messageId;
        this.messageType = messageType;
    }

    public int getMessageId() {
        return messageId;
    }

    public int getMessageType() {
        return messageType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageClassKey that = (MessageClassKey) o;
        return messageId == that.messageId &&
                messageType == that.messageType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, messageType);
    }
}
