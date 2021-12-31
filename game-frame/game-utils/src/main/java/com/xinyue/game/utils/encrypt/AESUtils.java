package com.xinyue.game.utils.encrypt;

import org.apache.commons.lang3.RandomStringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AESUtils {
    private static final String AES = "AES";

    public static String createSecret() {
        return RandomStringUtils.randomAscii(16);
    }

    private static SecretKey generateKey(byte[] secret) throws NoSuchAlgorithmException {
        KeyGenerator keygen = KeyGenerator.getInstance(AES);
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(secret);
        keygen.init(128, secureRandom);
        SecretKey original_key = keygen.generateKey();
        byte[] raw = original_key.getEncoded();
        SecretKey key = new SecretKeySpec(raw, AES);
        return key;
    }

    public static byte[] encode(byte[] secret, byte[] content) throws Exception {

        SecretKey key = generateKey(secret);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = cipher.doFinal(content);
        return result;

    }

    public static byte[] decode(byte[] secret, byte[] content) throws Exception {

        SecretKey key = generateKey(secret);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] result = cipher.doFinal(content);
        return result;

    }

}
