package com.xinyue.game.utils.encrypt;

/**
 * @Author 王广帅
 * @Date 2021/3/7 14:07
 */
public class RsaKeyPair {
    private byte[] publicKey;
    private byte[] privateKey;

    public RsaKeyPair(byte[] publicKey, byte[] privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public byte[] getPrivateKey() {
        return privateKey;
    }
}
