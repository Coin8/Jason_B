package com.coin.b8.model;

/**
 * Created by zhangyi on 2018/7/4.
 */
public class RegisterParameter {

    /**
     * email : 123@qq.com
     * verifyCode : 11111111111
     * password : android
     */

    private String email;
    private String verifyCode;
    private String password;
    private String contact;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
