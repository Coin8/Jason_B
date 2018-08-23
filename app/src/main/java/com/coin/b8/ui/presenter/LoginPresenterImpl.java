package com.coin.b8.ui.presenter;

import com.coin.b8.http.B8Api;
import com.coin.b8.model.LoginResponseInfo;
import com.coin.b8.model.VerifycodeResponseModel;
import com.coin.b8.ui.iView.ILoginView;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by zhangyi on 2018/7/4.
 */
public class LoginPresenterImpl extends BasePresenterImpl<ILoginView>{

    public LoginPresenterImpl(ILoginView iLoginView) {
        mView = iLoginView;
    }

    public void getLoginInfo(String email,String password,String contact,String type){
        DisposableObserver<LoginResponseInfo> disposableObserver = new DisposableObserver<LoginResponseInfo>() {
            @Override
            public void onNext(LoginResponseInfo loginResponseInfo) {
                if(mView != null){
                    mView.onLoginSuccess(loginResponseInfo);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onLoginFail();
                }
            }

            @Override
            public void onComplete() {

            }
        };

        B8Api.getLoginInfo(disposableObserver,email,password,contact,type);
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


}
