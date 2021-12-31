package com.pacman.game.server.beanconfig;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pacman.game.server.cache.GlobalRankList;
import com.pacman.game.server.mapper.GlobalMouldMapper;
import com.pacman.game.server.mapper.PlayerMapper;
import com.pacman.game.server.pojo.database.Player;
import com.pacman.game.server.pojo.database.mould.GlobalMould;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @author yhc
 * @create 2021-12-04-18:01
 */

@Component
public class StaticConfig implements CommandLineRunner {


    @Autowired
    GlobalRankList globalRankList;
    @Autowired
    PlayerMapper playerMapper;

    private HashMap<String, String> globalConfigMap = new HashMap<>();
    public static final String HERO_MAX_LV_STAR = "hero_max_lv_star";
    public static final String HERO_LVUP_COST = "hero_lv_up_cost";
    public static final String PLAYER_LVUP_EXP = "player_lv_up_exp";
    public static final String ONE_HERO_FRAGMENT_NUM = "one_hero_fragment_num";
    //    public static final String STAR_LV = "star_lv";
//    public static final String MAX_STAR = "max_star";
    public static final String ONE_LOTTERY_COIN = "one_lottery_coin";
    public static final String TEN_LOTTERY_COIN = "ten_lottery_coin";
    public static final String ONE_LOTTERY_DIAMOND = "one_lottery_diamond";
    public static final String TEN_LOTTERY_DIAMOND = "ten_lottery_diamond";
    public static final String COIN_ONE_RATE = "coin_one_rate";
    public static final String COIN_TWO_RATE = "coin_two_rate";

    public static final String FREE_LOTTERY_TIME = "free_lottery_time";

    //    public static final String EXP_ITEM_TYPE_ID = "exp_item_type_id";
//    public static final String STAR_STONE_ITEM_TYPE_ID = "star_stone_item_type_id";
    public static final String COIN_LOTTERY_ITEM_TYPE_ID = "coin_lottery_item_type_id";
    public static final String DIAMOND_LOTTERY_ITEM_TYPE_ID = "diamond_lottery_item_type_id";
    public static final String TREASURE_BOX_ITEM_TYPE_ID = "treasure_box_item_type_id";
//
//    public static final String PLAYER_INIT_ITEMS = "player_init_items";
//    public static final String PLAYER_INIT_HEAD = "player_init_head";
//    public static final String PLAYER_INIT_LV = "player_init_lv";
//    public static final String PLAYER_INIT_COIN = "player_init_coin";
//    public static final String PLAYER_INIT_GUANQIA = "player_init_guanqia";

    @Autowired
    GlobalMouldMapper globalMouldMapper;

    public String getStringValue(String name) {
        return globalConfigMap.get(name);
    }

    public Integer getIntValue(String name) {
        return Integer.valueOf(globalConfigMap.get(name));
    }

    @Override
    public void run(String... args) throws Exception {
        QueryWrapper<Player> playerQueryWrapper = new QueryWrapper<>();
        globalRankList.setRankList(playerMapper.selectList(playerQueryWrapper));

        QueryWrapper<GlobalMould> wrapper = new QueryWrapper<>();
        List<GlobalMould> globalMoulds = globalMouldMapper.selectList(wrapper);
        for (int i = 0; i < globalMoulds.size(); i++) {
            GlobalMould globalMould = globalMoulds.get(i);
            globalConfigMap.put(globalMould.getName(), globalMould.getVal());
        }
        globalRankList.update();
    }
}
