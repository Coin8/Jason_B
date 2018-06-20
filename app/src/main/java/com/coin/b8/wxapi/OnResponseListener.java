package com.coin.b8.wxapi;

/**
 * Created by zhangyi on 2018/6/19.
 */
public interface OnResponseListener {
    void onSuccess();

    void onCancel();

    void onFail(String message);
}
