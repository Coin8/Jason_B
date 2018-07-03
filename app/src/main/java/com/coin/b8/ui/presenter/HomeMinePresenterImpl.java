package com.coin.b8.ui.presenter;

import com.coin.b8.http.B8Api;
import com.coin.b8.model.B8UpdateInfo;
import com.coin.b8.ui.iView.IHomeMine;
import com.coin.b8.utils.AppUtil;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by zhangyi on 2018/6/29.
 */
public class HomeMinePresenterImpl {
    private IHomeMine mView;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public HomeMinePresenterImpl(IHomeMine iHomeMine) {
        mView = iHomeMine;
    }
    public void getUpdateInfo(boolean auto){
        DisposableObserver<B8UpdateInfo> disposableObserver = new DisposableObserver<B8UpdateInfo>() {
            @Override
            public void onNext(B8UpdateInfo updateInfo) {
                if(updateInfo != null){
                    mView.onUpdateInfo(updateInfo, auto);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(e != null){
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


    public void onDetach(){
        mView = null;
        mCompositeDisposable.dispose();
    }


}
