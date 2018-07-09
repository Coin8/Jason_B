package com.coin.b8.ui.presenter;

import com.coin.b8.http.B8Api;
import com.coin.b8.model.ResetPasswordResponseInfo;
import com.coin.b8.model.VerifycodeResponseModel;
import com.coin.b8.ui.iView.IForgetPasswordView;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by zhangyi on 2018/7/4.
 */
public class ForgetPasswordPresenterImpl extends BasePresenterImpl<IForgetPasswordView>{

    public ForgetPasswordPresenterImpl(IForgetPasswordView iForgetPasswordView) {
        mView = iForgetPasswordView;
    }

    public void sendVerifyCode(String email,int type){
        DisposableObserver<VerifycodeResponseModel> disposableObserver = new DisposableObserver<VerifycodeResponseModel>() {
            @Override
            public void onNext(VerifycodeResponseModel verifycodeResponseModel) {
                if(mView != null){
                    if(verifycodeResponseModel != null && verifycodeResponseModel.isResult() == true){
                        mView.onVerifyCodeSuccess();
                    }else {
                        mView.onVerifyCodeFail();
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.onVerifyCodeFail();
            }

            @Override
            public void onComplete() {

            }
        };

        B8Api.sendVerifyCode(disposableObserver,email,type);
        mCompositeDisposable.add(disposableObserver);
    }

    public void resetPassword(String email,String password,String verifyCode){
        DisposableObserver<ResetPasswordResponseInfo> disposableObserver = new DisposableObserver<ResetPasswordResponseInfo>() {
            @Override
            public void onNext(ResetPasswordResponseInfo resetPasswordResponseInfo) {
                if(mView != null){
                    mView.onResetPasswordSuccess(resetPasswordResponseInfo);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onResetPasswordFail();
                }
            }

            @Override
            public void onComplete() {

            }
        };

        B8Api.resetPassword(disposableObserver,email,password,verifyCode);
        mCompositeDisposable.add(disposableObserver);
    }


}
