package com.pacman.game.server.dao.entity;

//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.index.Indexed;
//import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author 王广帅
 * @date 2021年02月03日 2:50 下午
 */
//@Document("GamePlayer")
public class GamePlayer {
//    @Id
//    @Indexed
    private long playerId;
    private String  nickname;
    private int exp;
    private int level;


    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
