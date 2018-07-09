package com.coin.b8.ui.presenter;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by zhangyi on 2018/7/4.
 */
public class BasePresenterImpl <T>{
    protected T mView;
    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public void onDetach(){
        mView = null;
        mCompositeDisposable.clear();
    }
}
