package com.pacman.game.server.pojo.database;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-12-05-14:43
 */
@TableName("item")
@Data
public class Item extends BaseTable {
    private Long accountId;
    private Integer typeId;
    private String name;
    private Integer num;

    @TableField(exist = false)
    private String prop;
}
