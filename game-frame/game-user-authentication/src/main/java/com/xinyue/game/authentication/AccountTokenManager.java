package com.xinyue.game.authentication;

import com.alibaba.fastjson.JSON;
import com.xinyue.game.utils.encrypt.AESUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @Author 王广帅
 * @Date 2021/5/10 23:55
 */
public class AccountTokenManager {

    private byte[] aesFactorBytes;

    public AccountTokenManager(byte[] aesFactorBytes) {
        this.aesFactorBytes = aesFactorBytes;
    }

    public String createToken(AccountTokenModel accountTokenModel) throws Exception {
        String value = JSON.toJSONString(accountTokenModel);
        byte[] tokenBytes = value.getBytes(StandardCharsets.UTF_8);
        byte[] encryptValue = AESUtils.encode(aesFactorBytes, tokenBytes);
        String token = Base64.getEncoder().encodeToString(encryptValue);
        return token;
    }

    public AccountTokenModel decodeToken(String token) throws Exception {
        byte[] data = Base64.getDecoder().decode(token);
        byte[] unEncryptValue = AESUtils.decode(aesFactorBytes, data);
        String json = new String(unEncryptValue, StandardCharsets.UTF_8);
        AccountTokenModel accountTokenModel = JSON.parseObject(json, AccountTokenModel.class);
        return accountTokenModel;
    }
}
