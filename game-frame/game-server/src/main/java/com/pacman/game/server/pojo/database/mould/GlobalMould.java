package com.pacman.game.server.pojo.database.mould;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-12-04-18:25
 */
@Data
@TableName("global_config")
public class GlobalMould {
    private Integer id;
    private String name;
    private String val;
    private String des;
}

