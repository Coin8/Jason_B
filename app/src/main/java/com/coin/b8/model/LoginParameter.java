package com.coin.b8.model;

/**
 * Created by zhangyi on 2018/7/4.
 */
public class LoginParameter {

    /**
     * email : liao@litsoft.com.cn
     * password : 123456
     */

    private String email;
    private String password;
    private String contact;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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
}
