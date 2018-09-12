package com.coin.b8.ui.iView;

import com.coin.b8.model.LoginResponseInfo;
import com.coin.b8.model.RegisterResponseInfo;
import com.coin.b8.model.VerifycodeResponseModel;

/**
 * Created by zhangyi on 2018/7/4.
 */
public interface IRegisterView {
    void onCheckVerifyCodeSuccess();
    void onCheckVerifyCodeFail(String message);
    void onVerifyCodeSuccess();
    void onVerifyCodeFail(String message);
    void onRegisterSuccess(RegisterResponseInfo registerResponseInfo);
    void onRegisterFail();
    void onLoginSuccess(LoginResponseInfo loginResponseInfo);
    void onLoinFailed();
}
