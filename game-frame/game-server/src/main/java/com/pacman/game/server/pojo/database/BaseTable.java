package com.pacman.game.server.pojo.database;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @author yhc
 * @create 2021-11-03-12:06
 */
@Data
public class BaseTable {
    @TableId(type= IdType.AUTO)
    protected Long id;
    // 逻辑删除
    @TableLogic
    protected Integer del;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    protected String createTime;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected String updateTime;
}