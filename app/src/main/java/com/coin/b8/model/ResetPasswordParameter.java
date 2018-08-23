package com.coin.b8.model;

/**
 * Created by zhangyi on 2018/7/5.
 */
public class ResetPasswordParameter {

    /**
     * email : liao@litsoft.com.cn
     * password : 123456
     * verifyCode : 383504
     */

    private String email;
    private String password;
    private String verifyCode;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
