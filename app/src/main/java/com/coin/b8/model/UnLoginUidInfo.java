package com.coin.b8.model;

/**
 * Created by zhangyi on 2018/7/4.
 */
public class UnLoginUidInfo {


    /**
     * uid : 633
     * code : 200
     * message : 成功
     */

    private int uid;
    private int code;
    private String message;
    private String password;
    private String easename;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEasename() {
        return easename;
    }

    public void setEasename(String easename) {
        this.easename = easename;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
