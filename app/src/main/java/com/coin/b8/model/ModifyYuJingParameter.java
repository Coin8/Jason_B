package com.coin.b8.model;

/**
 * Created by zhangyi on 2018/7/10.
 */
public class ModifyYuJingParameter {

    /**
     * openStatus : 0
     * id : 335
     * uid : 440
     */

    private int openStatus;  //1.打开0.关闭
    private long id;
    private String uid;

    public int getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(int openStatus) {
        this.openStatus = openStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
