package com.coin.b8.ui.presenter;

import com.coin.b8.http.B8Api;
import com.coin.b8.model.LoginResponseInfo;
import com.coin.b8.model.RegisterResponseInfo;
import com.coin.b8.model.VerifycodeResponseModel;
import com.coin.b8.ui.iView.IRegisterView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by zhangyi on 2018/7/4.
 */
public class RegisterPresenterImpl {
    private IRegisterView mView;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    public RegisterPresenterImpl(IRegisterView view) {
        mView = view;
    }

    public void sendVerifyCode(String email,String contact,int type){
        DisposableObserver<VerifycodeResponseModel> disposableObserver = new DisposableObserver<VerifycodeResponseModel>() {
            @Override
            public void onNext(VerifycodeResponseModel verifycodeResponseModel) {
                if(mView != null){
                    if(verifycodeResponseModel == null ){
                        mView.onVerifyCodeFail("发送验证码失败");
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
                mView.onVerifyCodeFail("发送验证码失败");
            }

            @Override
            public void onComplete() {

            }
        };

        B8Api.sendVerifyCode(disposableObserver,email,contact,type);
        mCompositeDisposable.add(disposableObserver);
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

    public void register(String email,String contact,String password,String code){
        DisposableObserver<RegisterResponseInfo> disposableObserver = new DisposableObserver<RegisterResponseInfo>() {
            @Override
            public void onNext(RegisterResponseInfo registerResponseInfo) {
                if(mView != null){
                    mView.onRegisterSuccess(registerResponseInfo);
                }

            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onRegisterFail();
                }
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.getRegisterInfo(disposableObserver,email,contact,password,code);
        mCompositeDisposable.add(disposableObserver);
    }


    public void getLoginInfo(String contact,String password){
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
                    mView.onLoinFailed();
                }
            }

            @Override
            public void onComplete() {

            }
        };

        B8Api.getLoginInfo(disposableObserver,"",password,contact,"1");
        mCompositeDisposable.add(disposableObserver);
    }


    public void onDetach(){
        mView = null;
        mCompositeDisposable.dispose();
    }

}
