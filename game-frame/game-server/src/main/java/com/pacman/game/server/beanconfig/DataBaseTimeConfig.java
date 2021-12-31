package com.pacman.game.server.beanconfig;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yhc
 * @create 2021-11-03-12:13
 */
@Configuration

public class DataBaseTimeConfig implements MetaObjectHandler {
    private String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    @Override
    public void insertFill(MetaObject metaObject) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        String date = sdf.format(new Date());
        setFieldValByName("createTime",date,metaObject);
        setFieldValByName("updateTime",date,metaObject);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        String date = sdf.format(new Date());
//        setFieldValByName("createTime",date,metaObject);
        setFieldValByName("updateTime",date,metaObject);
    }
}
