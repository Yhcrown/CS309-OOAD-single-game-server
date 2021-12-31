package com.pacman.game.server.gamehandler;

import com.pacman.game.server.messages.FailMessage;
import com.pacman.game.server.messages.SuccessMessage;
import com.pacman.game.server.messages.item.*;
import com.pacman.game.server.messages.status.ErrorStatus;

import com.pacman.game.server.pojo.database.Item;
import com.pacman.game.server.service.ItemService;
import com.pacman.network.hamdlermapping.GameChannelContext;
import com.pacman.network.hamdlermapping.GameHandlerComponent;
import com.pacman.network.hamdlermapping.GameMapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author yhc
 * @create 2021-12-11-15:21
 */
@GameHandlerComponent
public class ItemHandler extends AbstractGameHandler{
    @Autowired
    ItemService itemService;

    @GameMapping(GetItemListRequest.class)
    public void getItemList(GameChannelContext ctx, GetItemListRequest request, int seqId) {
        Long playerId = ctx.getPlayerId();
        List<Item> itemList = itemService.getItemList(playerId);
        ItemListResponse itemListResponse = new ItemListResponse();
        itemListResponse.setItems(itemList);
        logger.debug("player id {} get Item list {}",playerId,itemList);
        ctx.sendGameMessage(itemListResponse,seqId);
    }

    @GameMapping(AddItemRequest.class)
    public void addItemList(GameChannelContext ctx , AddItemRequest request , int seqId){
        Long playerId = ctx.getPlayerId();
        Integer typeId = request.getTypeId();
        Integer num = request.getNum();
        itemService.addItem(playerId,typeId,num);
        ctx.sendGameMessage(new SuccessMessage(),seqId);
    }

    @GameMapping(UseItemRequest.class)
    public void useItem(GameChannelContext ctx , UseItemRequest request ,int seqId){
        Long playerId = ctx.getPlayerId();
        Long itemId = request.getItemId();
        Integer num = request.getNum();
        int code = itemService.useItem(playerId, itemId, num);
        if (code==0)
            ctx.sendGameMessage(new SuccessMessage(),seqId);
        else if (code==1)
            ctx.sendGameMessage(new FailMessage(ErrorStatus.ITEM_NUMBER_ERROR.getCode(), ErrorStatus.ITEM_NUMBER_ERROR.getMsg()),seqId);
        else
            ctx.sendGameMessage(new FailMessage(ErrorStatus.ITEM_CANT_ERROR.getCode(), ErrorStatus.ITEM_CANT_ERROR.getMsg()),seqId);
    }

    @GameMapping(OpenTreasureBoxRequest.class)
    public void openBox(GameChannelContext ctx, OpenTreasureBoxRequest request,int seqId){
        Long playerId = ctx.getPlayerId();
        Long itemId = request.getItemId();
        Integer num = request.getNum();
        List<Item> items = itemService.openTreasureBox(playerId, itemId, num);
        logger.debug("player {} open box {} ",playerId,items);
        if (items==null)
            ctx.sendGameMessage(new FailMessage(ErrorStatus.ITEM_NUMBER_ERROR.getCode(), ErrorStatus.ITEM_NUMBER_ERROR.getMsg()),seqId);
        else {
            TreasureResponse treasureResponse=new TreasureResponse();
            treasureResponse.setItemList(items);
            ctx.sendGameMessage(treasureResponse,seqId);
        }
    }
}
