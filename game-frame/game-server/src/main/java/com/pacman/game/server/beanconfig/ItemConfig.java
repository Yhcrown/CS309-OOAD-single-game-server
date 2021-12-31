package com.pacman.game.server.beanconfig;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pacman.game.server.mapper.ItemMouldMapper;
import com.pacman.game.server.pojo.database.mould.ItemMould;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yhc
 * @create 2021-12-05-17:37
 */
@Component
@Slf4j
public class ItemConfig implements CommandLineRunner {
    // 普通
    public final static int ITEM_TYPE_NORMAL = 1;
    // 英雄碎片
    public final static int ITEM_TYPE_HERO_FRAGMENT = 2;
    // 金币双倍卡
    public final static int ITEM_TYPE_COIN_CARD = 3;
    // 经验双倍卡
    public final static int ITEM_TYPE_EXP_CARD = 4;
    //加金币的卡，测试用
    public final static int ITEM_COIN = 5;
    //加钻石
    public final static int ITEM_DIAMOND = 6;

    private List<ItemMould> itemMouldList = new ArrayList<>();

    @Autowired
    ItemMouldMapper itemMouldMapper;

    // 通过道具模板id获取模板信息
    public ItemMould getItemMould(Integer typeId) {
        for (int i = 0; i < itemMouldList.size(); i++) {
            ItemMould itemMould = itemMouldList.get(i);
            if (itemMould.getType().equals(typeId)) {
                return itemMould;
            }
        }

        log.debug("未找到具模板id为" + typeId + "的道具");
        return null;
    }


    @Override
    public void run(String... args) throws Exception {
        // 加载配置
//        根据需求增加配置到itemMouldList
        QueryWrapper<ItemMould> wrapper = new QueryWrapper<>();
        List<ItemMould> itemMoulds = itemMouldMapper.selectList(wrapper);
        this.itemMouldList.addAll(itemMoulds);
    }
}
