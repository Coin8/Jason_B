package com.coin.b8.model;

/**
 * Created by zhangyi on 2018/6/12.
 */
public class FeedBackParameter {
    /**
     * 1 、建议 2、吐槽
     */
    private int type;
    /**
     * 内容
     */
    private String content;
    /**
     * 联系方式
     */
    private String contact;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
