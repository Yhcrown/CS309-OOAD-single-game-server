package com.pacman.game.server.pojo.database.mould;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/**
 * @author yhc
 * @create 2021-12-04-17:38
 */
@Data
@TableName("hero_config")
public class HeroMould {
    Integer id;
    String name;
    Integer initSpeed;
    Integer initSkill;
    Integer initExp;
    Integer initCoin;
    Integer lvUpSpeed;
    Integer lvUpSkill;
    Integer lvUpExp;
    Integer lvUpCoin;
}
