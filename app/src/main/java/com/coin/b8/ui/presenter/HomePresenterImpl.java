package com.coin.b8.ui.presenter;

import com.coin.b8.http.B8Api;
import com.coin.b8.model.B8UpdateInfo;
import com.coin.b8.model.UnLoginUidInfo;
import com.coin.b8.ui.iView.IHomeMine;
import com.coin.b8.ui.iView.IHomeView;
import com.coin.b8.utils.AppUtil;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by zhangyi on 2018/7/4.
 */
public class HomePresenterImpl {
    private IHomeView mView;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public HomePresenterImpl(IHomeView iHomeView) {
        mView = iHomeView;
    }
    public void getUpdateInfo(boolean auto){
        DisposableObserver<B8UpdateInfo> disposableObserver = new DisposableObserver<B8UpdateInfo>() {
            @Override
            public void onNext(B8UpdateInfo updateInfo) {
                if(mView != null){
                    mView.onUpdateInfo(updateInfo, auto);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onUpdateInfoError(e);
                }
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.getUpdateInfo(disposableObserver, String.valueOf(AppUtil.getVersionCode()));
        mCompositeDisposable.add(disposableObserver);
    }

    public void getUnLoginUid(String imei){
        DisposableObserver<UnLoginUidInfo> disposableObserver = new DisposableObserver<UnLoginUidInfo>() {
            @Override
            public void onNext(UnLoginUidInfo unLoginUidInfo) {
                if(mView != null){
                    mView.onUnLoginUidInfo(unLoginUidInfo);
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.getUnLoginUidInfo(disposableObserver,imei);
        mCompositeDisposable.add(disposableObserver);
    }

    public void onDetach(){
        mView = null;
        mCompositeDisposable.dispose();
    }

}
