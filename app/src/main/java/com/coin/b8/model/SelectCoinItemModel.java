package com.coin.b8.model;

/**
 * Created by zhangyi on 2018/7/9.
 */
public class SelectCoinItemModel {
    private SelectCoinListResponse.DataBean.ItemsBean mItemsBean;
    private String title;
    private int type;

    public SelectCoinListResponse.DataBean.ItemsBean getItemsBean() {
        return mItemsBean;
    }

    public void setItemsBean(SelectCoinListResponse.DataBean.ItemsBean itemsBean) {
        mItemsBean = itemsBean;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
