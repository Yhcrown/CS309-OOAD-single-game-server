package com.pacman.network.codec;


import org.testng.Assert;

import java.nio.charset.StandardCharsets;


/**
 * @Author 王广帅
 * @Date 2021/4/11 14:06
 */
public class GzipUtilTest {

    @org.testng.annotations.Test
    public void testCompress() {
        String str =  "aaaa";
        byte[] data = str.getBytes(StandardCharsets.UTF_8);
        byte[] compressData = GzipUtil.compress(data);
        byte[] uncompressData = GzipUtil.uncompress(compressData);
        String value = new String(uncompressData,StandardCharsets.UTF_8);
        Assert.assertEquals(value,str);
    }
}