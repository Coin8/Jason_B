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

    public void checkPhoneCode(String contact,String verifyCode, int verifyType){
        DisposableObserver<VerifycodeResponseModel> disposableObserver = new DisposableObserver<VerifycodeResponseModel>() {
            @Override
            public void onNext(VerifycodeResponseModel verifycodeResponseModel) {
                if(mView != null){
                    if(verifycodeResponseModel == null ) {
                        mView.onCheckVerifyCodeFail("校验失败");
                        return;
                    }
                    if(verifycodeResponseModel.isResult() == true){
                        mView.onCheckVerifyCodeSuccess();
                    }else {
                        mView.onCheckVerifyCodeFail(verifycodeResponseModel.getMessage());
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.onCheckVerifyCodeFail("校验失败");
            }

            @Override
            public void onComplete() {

            }
        };

        B8Api.checkVerifyCode(disposableObserver,contact,verifyCode,verifyType);
        mCompositeDisposable.add(disposableObserver);
    }


    public void sendVerifyCode(String email,String contact,int type){
        DisposableObserver<VerifycodeResponseModel> disposableObserver = new DisposableObserver<VerifycodeResponseModel>() {
            @Override
            public void onNext(VerifycodeResponseModel verifycodeResponseModel) {
                if(mView != null){
                    if(verifycodeResponseModel == null){
                        mView.onVerifyCodeFail("发送失败");
                        return;
                    }
                    if(verifycodeResponseModel.isResult() == true){
                        mView.onVerifyCodeSuccess();
                    }else {
                        mView.onVerifyCodeFail(verifycodeResponseModel.getMessage());
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.onVerifyCodeFail("发送失败");
            }

            @Override
            public void onComplete() {

            }
        };

        B8Api.sendVerifyCode(disposableObserver,email,contact,type);
        mCompositeDisposable.add(disposableObserver);
    }

    public void resetPassword(String email,String contact,String password,String verifyCode){
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

        B8Api.resetPassword(disposableObserver,email,contact,password,verifyCode);
        mCompositeDisposable.add(disposableObserver);
    }


}
