package com.pacman.network.codec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @Author 王广帅
 * @Date 2021/4/11 13:38
 */
public class GzipUtil {
    private static Logger logger = LoggerFactory.getLogger(GzipUtil.class);

    /**
     * 压缩数据
     * @param data
     * @return
     */
    public static byte[] compress(byte[] data){
        ByteArrayOutputStream out = new ByteArrayOutputStream(data.length);
        GZIPOutputStream gzip = null;
        try{
            gzip =  new GZIPOutputStream(out,data.length);
            gzip.write(data);
            gzip.close();//必须先关闭它
            return out.toByteArray();
        }catch (Exception e){
            logger.error("压缩数据失败",e);
        } finally {
            if(gzip != null){
                try {
                    gzip.close();
                } catch (IOException e) {
                   logger.error("关闭压缩数据流失败",e);
                }
            }
        }
        //如果压缩失败了，就原样返回数据
        return data;
    }

    /**
     * 解压数据
     * @param data
     * @return
     */
    public static byte[] uncompress(byte[] data){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        GZIPInputStream gin;
        try{
            gin = new GZIPInputStream(in,data.length);
            byte[] buffer = new byte[1024];
            int n ;
            while((n = gin.read(buffer))>= 0){
                out.write(buffer,0,n);
            }
            return out.toByteArray();
        }catch ( Exception e){
            logger.error("解压数据失败",e);
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
               logger.error("关闭解压输入流失败",e);
            }

        }
        //解压失败就返回原来的数据
        return data;
    }
}
