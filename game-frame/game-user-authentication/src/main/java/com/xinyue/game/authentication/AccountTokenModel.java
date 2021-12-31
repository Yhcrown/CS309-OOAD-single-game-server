package com.xinyue.game.authentication;

/**
 * @Author 王广帅
 * @Date 2021/5/10 23:50
 */
public class AccountTokenModel {
    private String id; //账号id
    private String password; //账号密码，一般是加密之后的值
    private long createTime;//token创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
