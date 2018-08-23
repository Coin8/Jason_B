package com.coin.b8.ui.iView;

import com.coin.b8.model.ResetPasswordResponseInfo;

/**
 * Created by zhangyi on 2018/7/4.
 */
public interface IForgetPasswordView {
    void onCheckVerifyCodeSuccess();
    void onCheckVerifyCodeFail(String message);
    void onVerifyCodeSuccess();
    void onVerifyCodeFail(String message);
    void onResetPasswordSuccess(ResetPasswordResponseInfo resetPasswordResponseInfo);
    void onResetPasswordFail();
}
