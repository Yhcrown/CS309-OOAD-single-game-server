package com.pacman.game.server.pojo.database;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-12-04-23:13
 */
@Data
@TableName("hero")
public class Hero extends BaseTable{
    private Long accountId;
    private Integer typeId;
    private String name;
    private Integer speed;
    private Integer skill;
    private Integer coin;
    private Integer exp;
    private Integer lv;
    private Integer star;
}
