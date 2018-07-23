package com.coin.b8.ui.presenter;

import com.coin.b8.http.B8Api;
import com.coin.b8.model.BannerResponse;
import com.coin.b8.model.SelectCoinListResponse;
import com.coin.b8.ui.iView.ISelectCoinView;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by zhangyi on 2018/7/9.
 */
public class SelectCoinPresenterImpl extends BasePresenterImpl<ISelectCoinView>{

    public SelectCoinPresenterImpl(ISelectCoinView iSelectCoinView) {
        mView = iSelectCoinView;
    }

    public void getSelectCoinList(){
        DisposableObserver<SelectCoinListResponse> disposableObserver = new DisposableObserver<SelectCoinListResponse>() {
            @Override
            public void onNext(SelectCoinListResponse selectCoinListResponse) {
                if(mView != null){
                    mView.onSelectCoinSuccess(selectCoinListResponse);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onSelectCoinFail();
                }
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.getSelectCoinList(disposableObserver);
        mCompositeDisposable.add(disposableObserver);
    }

    public void getBanner(){
        DisposableObserver<BannerResponse> disposableObserver = new DisposableObserver<BannerResponse>() {
            @Override
            public void onNext(BannerResponse bannerResponse) {
                if(mView != null){
                    mView.onBannerSuccess(bannerResponse);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onBannerFail();
                }
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.getBanner(disposableObserver,1);
        mCompositeDisposable.add(disposableObserver);
    }



}
