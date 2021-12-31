package com.pacman.game.server.pojo.database;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-11-03-19:02
 */
@TableName("player")
@Data
public class Player extends BaseTable {

    private Long accountId;
    private String nickname;
    private Integer level;
    private Long chooseHero;
    private Integer coin;
    private Integer exp;
    private Integer total;
    private Integer win;
    private Integer diamond;
    private Integer killNum;
    private Integer death;
    private Integer item;
    private Integer doubleCoinLeft;
    private Integer doubleExpLeft;
    private String lastFreeLotteryTime;
    private Integer head;

//    @TableField(exist = false)

}
