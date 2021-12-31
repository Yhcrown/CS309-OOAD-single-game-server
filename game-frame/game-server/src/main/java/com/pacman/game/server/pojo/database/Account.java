package com.pacman.game.server.pojo.database;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-11-03-12:05
 */
@Data
// 注意1：指定实体类对象与数据库表的关联关系
@TableName("account")
public class Account extends BaseTable {
    // 账号
    private String account;
    // 密码
    private String password;

}