package com.coin.b8.model;

import java.util.List;

/**
 * Created by zhangyi on 2018/7/9.
 */
public class SelectCoinItemModel {
    private SelectCoinListResponse.DataBean.ItemsBean mItemsBean;
    private List<BannerResponse.DataBean> mBannerDatas;
    private String title;
    private int type;

    public List<BannerResponse.DataBean> getBannerDatas() {
        return mBannerDatas;
    }

    public void setBannerDatas(List<BannerResponse.DataBean> bannerDatas) {
        mBannerDatas = bannerDatas;
    }

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
