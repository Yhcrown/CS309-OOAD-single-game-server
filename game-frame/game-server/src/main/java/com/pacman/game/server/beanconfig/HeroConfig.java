package com.pacman.game.server.beanconfig;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pacman.game.server.mapper.HeroMouldMapper;
import com.pacman.game.server.pojo.database.mould.HeroMould;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yhc
 * @create 2021-12-04-18:06
 */

@Component
public class HeroConfig implements CommandLineRunner {
    private List<HeroMould> heroMouldList = new ArrayList<>();
    @Autowired
    HeroMouldMapper heroMouldMapper;

    // 英雄总权重
    private Integer allWeight = 0;

    public Integer getAllWeight(){
        return allWeight;
    }

    public List<HeroMould> getHeroMouldList(){
        return heroMouldList;
    }

    public HeroMould getHeroMould(Integer typeId){
        for (int i=0; i<heroMouldList.size(); i++){
            HeroMould heroMould = heroMouldList.get(i);
            if (heroMould.getId().equals(typeId)){
                return heroMould;
            }
        }
        return null;
    }

    @Override
    public void run(String... args) throws Exception {
        // 加载配置
//        根据需求增加配置到heroMouldList
        QueryWrapper<HeroMould> wrapper = new QueryWrapper<>();
        List<HeroMould> heroMoulds = heroMouldMapper.selectList(wrapper);
        this.heroMouldList.addAll(heroMoulds);

        // 计算英雄总权重
//        for (int i=0; i<heroMouldList.size(); i++){
//            Integer weight = heroMouldList.get(i).getLotteryWeight();
//            allWeight += weight;
//        }
    }
}
