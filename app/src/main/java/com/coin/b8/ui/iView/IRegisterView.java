package com.coin.b8.ui.iView;

import com.coin.b8.model.RegisterResponseInfo;

/**
 * Created by zhangyi on 2018/7/4.
 */
public interface IRegisterView {
    void onVerifyCodeSuccess();
    void onVerifyCodeFail();
    void onRegisterSuccess(RegisterResponseInfo registerResponseInfo);
    void onRegisterFail();
}
