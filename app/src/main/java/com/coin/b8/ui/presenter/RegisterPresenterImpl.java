package com.coin.b8.ui.presenter;

import com.coin.b8.http.B8Api;
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

    public void register(String email,String password,String code){
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
        B8Api.getRegisterInfo(disposableObserver,email,password,code);
        mCompositeDisposable.add(disposableObserver);
    }

    public void onDetach(){
        mView = null;
        mCompositeDisposable.dispose();
    }

}
