package com.pacman.game.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pacman.game.server.beanconfig.ItemConfig;
import com.pacman.game.server.beanconfig.StaticConfig;
import com.pacman.game.server.cache.PlayerCache;
import com.pacman.game.server.mapper.ItemMapper;
import com.pacman.game.server.pojo.cache.PlayerInfo;
import com.pacman.game.server.pojo.database.Item;
import com.pacman.game.server.pojo.database.mould.ItemMould;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author yhc
 * @create 2021-12-05-16:55
 */
@Service
@Slf4j
public class ItemService {


    @Autowired
    ItemMapper itemMapper;

    @Autowired
    PlayerCache playerCache;

    @Autowired
    ItemConfig itemConfig;

    @Autowired
    StaticConfig staticConfig;

    @Autowired
    HeroService heroService;

    @Autowired
    PlayerService playerService;

    // 从数据库中加载数据到内存
    public void initCache(PlayerInfo playerInfo) {
        // 访问数据库，获取用户英雄信息
        QueryWrapper<Item> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", playerInfo.getBaseInfo().getAccountId());
        List<Item> items = itemMapper.selectList(wrapper);
        for (Item item : items) {
            ItemMould itemMould = itemConfig.getItemMould(item.getTypeId());
            item.setProp(itemMould.getProp());
        }
        // 将英雄数据放入缓存中
        playerInfo.setItems(items);
    }


    public List<Item> getItemList(Long playerId) {
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        List<Item> items = playerInfo.getItems();
        return items;
    }

    public Item getItem(Long playerId, Long itemId) {
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        List<Item> items = playerInfo.getItems();
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (item.getId().equals(itemId)) {
                return item;
            }
        }

        log.error("player {} item {} error", playerId, itemId);
        return null;
    }

    // 根据道具typeId获取道具信息
    public Item getItemByTypeId(Long playerId, Integer itemTypeId) {
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        List<Item> items = playerInfo.getItems();
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (item.getTypeId().equals(itemTypeId)) {
                return item;
            }
        }
        return null;
    }

    public int useItem(Long playerId, Long itemId, Integer num) {
        if (num <= 0)
            return 1;//数量异常
        Item item = getItem(playerId, itemId);
        ItemMould itemMould = itemConfig.getItemMould(item.getTypeId());
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        switch (itemMould.getType()) {
            case ItemConfig.ITEM_TYPE_NORMAL:
                return 2;//无法使用
            case ItemConfig.ITEM_TYPE_HERO_FRAGMENT: {
                Integer heroTypeId = Integer.valueOf(itemMould.getProp());
                Integer fragmentNum = staticConfig.getIntValue(StaticConfig.ONE_HERO_FRAGMENT_NUM);
                if (num % fragmentNum != 0) {
                    return 1;//数量异常
                }
                if (delItem(item, num)) {
                    heroService.createHero(playerId, heroTypeId);
                    return 0;
                } else {
                    return 1;
                }
            }
            case ItemConfig.ITEM_TYPE_COIN_CARD: {
                Integer doubleCoinLeft = playerInfo.getBaseInfo().getDoubleCoinLeft();
                Integer itemLeft = Integer.valueOf(itemMould.getProp());
                if (delItem(item, num)) {
                    playerInfo.getBaseInfo().setDoubleCoinLeft(doubleCoinLeft + num * itemLeft);
                    return 0;
                } else
                    return 1;
            }
            case ItemConfig.ITEM_TYPE_EXP_CARD: {
                Integer doubleExpLeft = playerInfo.getBaseInfo().getDoubleExpLeft();
                Integer itemLeft = Integer.valueOf(itemMould.getProp());
                if (delItem(item, num)) {
                    playerInfo.getBaseInfo().setDoubleExpLeft(doubleExpLeft + num * itemLeft);
                    return 0;
                } else
                    return 1;
            }
            case ItemConfig.ITEM_COIN: {
                Integer coin = Integer.valueOf(itemMould.getProp());
                if (delItem(item, num)) {
                    playerService.changeCoin(playerId, coin);
                    return 0;
                } else
                    return 1;
            }
            case ItemConfig.ITEM_DIAMOND: {
                Integer diamond = Integer.valueOf(itemMould.getProp());
                if (delItem(item, num)) {
                    playerService.changeDiamond(playerId, diamond);
                    return 0;
                } else
                    return 1;
            }
        }
        return 3;//无对应type

    }


    public void addItem(Long playerId, Integer itemTypeId, Integer num) {
        Item item = getItemByTypeId(playerId, itemTypeId);
        if (item == null) {
            // 用户身上没有该typeId的道具
            Item newItem = addItemInDB(playerId, itemTypeId, num);

            // 再去内存中增加
            PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
            playerInfo.getItems().add(newItem);
        } else {
            // 用户身上有该typeId的道具
            Integer newNum = item.getNum() + num;
            item.setNum(newNum);
        }
    }

    // 在数据库中增加道具
    public Item addItemInDB(Long playerId, Integer itemTypeId, Integer num) {
        ItemMould itemMould = itemConfig.getItemMould(itemTypeId);
        Item newItem = new Item();
        newItem.setAccountId(playerId);
        newItem.setTypeId(itemTypeId);
        newItem.setNum(num);
        newItem.setName(itemMould.getName());

        // 先去数据库里增加
        try {
            itemMapper.insert(newItem);
        } catch (Exception e) {
            log.error("createItem error: playerId=" + playerId + " itemTypeId=" + itemTypeId);
//            throw new ItemCreateErrorException();
            return null;
        }


        return newItem;
    }


    public boolean delItem(Item item, Integer delNum) {
        if (item.getNum() < delNum) {
            return false;
        } else {
            int newNum = item.getNum() - delNum;
            item.setNum(newNum);
        }
        return true;
    }

    public List<Item> openTreasureBox(Long playerId, Long itemId, Integer num) {
        Item item = getItem(playerId, itemId);
        int[] candidates = {3, 4, 5, 6, 11, 12};
        Random random = new Random();
        List<Item> items = new ArrayList<>();
        if (delItem(item, num)) {
            for (int i = 0; i < num; i++) {
                int r = random.nextInt(candidates.length) ;
                int iId = candidates[r];
                int number = random.nextInt(2) + 1;
                Item itemRef = getItemByTypeId(playerId, iId);
                if (itemRef != null) {
                    Item item1 = new Item();
                    item1.setAccountId(itemRef.getAccountId());
                    item1.setName(itemRef.getName());
                    item1.setNum(number);
                    item1.setTypeId(itemRef.getTypeId());
                    item1.setProp(itemRef.getProp());
                    addItem(playerId, iId, number);
                    items.add(item1);
                } else {
                    Item item1 = addItemInDB(playerId, iId, number);
                    PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
                    playerInfo.getItems().add(item1);
                    items.add(item1);
                }
            }
        } else {
            return null;
        }
        return items;
    }
}
