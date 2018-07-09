package com.coin.b8.ui.iView;

import com.coin.b8.model.LoginResponseInfo;

/**
 * Created by zhangyi on 2018/7/4.
 */
public interface ILoginView {
    void onLoginSuccess(LoginResponseInfo loginResponseInfo);
    void onLoginFail();
}
