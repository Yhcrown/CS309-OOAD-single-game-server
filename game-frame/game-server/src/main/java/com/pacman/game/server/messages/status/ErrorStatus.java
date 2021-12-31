package com.pacman.game.server.messages.status;

import lombok.Getter;

/**
 * @author yhc
 * @create 2021-11-03-14:27
 */
@Getter
public enum ErrorStatus {

    REG_ACCOUNT_FORM_ERROR(1001, "acoount format error"),
    REG_PASSWORD_FORM_ERROR(1002, "password format error"),
    REG_DUPLICATE_ACCOUNT_ERROR(1003, "exist account"),
    LOGIN_FIND_ACCOUNT_ERROR(1004, "can't find the account"),
    LOGIN_PASSWORD_ERROR(1005, "wrong password"),
    NOT_LOGIN_ERROR(1006, "please login again"),
    PLAYER_CREATE_ERROR(1101, "can't create player"),
    PLAYER_FIND_ERROR(1102, "can't find player"),
    CHANGE_NAME_ERROR(1103,"The max length of name is 18!"),
    ROOM_ENTER_ERROR1(1201, "Room is not exist!"),
    ROOM_ENTER_ERROR2(1202, "Room is Full!"),
    ROOM_ENTER_ERROR3(1203, "You can't enter two room!"),
    ROOM_ENTER_ERROR4(1204,"The room is in game!"),
    HERO_MOULD_ID_ERROR(1301, "wrong hero type id "),
    HERO_CREATE_ERROR(1202, "fail to create hero"),
    HERO_ID_ERROR(1203, "wrong hero id"),
    HERO_LV_MAX_ERROR(1204, "you have reached max level"),
    HERO_NOT_ENOUGH_HERO(1205, "you don't have 3 heroes have same star"),
    HERO_STAR_UP_LEVEL_ERROR(1206, "you can star up when you reach max level"),
    Hero_STAR_MAX_ERROR(1207, "you have reached max star"),
    Hero_CANT_SELL_ERROR(1208, "You should have at least one hero!"),
    HERO_CHOOSE_SELL_ERROR(1209,"You can't sell the hero you have choosed"),
    COIN_NOT_ENOUGH(1406, "coin not enough!"),
    DIAMOND_NOT_ENOUGH(1407, "Diamond not enough!"),
    ITEM_NUMBER_ERROR(1401, "Item number error!"),
    ITEM_CANT_ERROR(1402, "Item can't use"),
    FREE_LOTTERY_TIME_ERROR(1501, "The free lottery hasn't arrived yet"),
    HERO_TOO_MUCH_ERROR(1502, "You can't have more than 50 heroes, please exile some heroes"),
    GAME_NOT_READY_ERROR(1601, "There are some player not ready!"),
    ADMIN_PASSWORD_WRONG_ERROR(1701,"Wrong admin password!");
    private Integer code;
    private String msg;

    ErrorStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
