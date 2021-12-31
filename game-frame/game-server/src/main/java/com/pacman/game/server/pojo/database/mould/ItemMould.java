package com.pacman.game.server.pojo.database.mould;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-12-05-14:43
 */
@Data
@TableName("item_config")
public class ItemMould {
    private Integer id;
    private String name;
    private Integer type;
    private String prop;
    private String des;
}
