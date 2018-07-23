package com.coin.b8.ui.presenter;

import com.coin.b8.http.B8Api;
import com.coin.b8.model.DynamicImportNewsResponse;
import com.coin.b8.model.BannerResponse;
import com.coin.b8.ui.iView.IDynamicImportView;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by zhangyi on 2018/7/9.
 */
public class DynamicImportPresenter extends BasePresenterImpl<IDynamicImportView>{

    public DynamicImportPresenter(IDynamicImportView iDynamicImportView) {
        mView = iDynamicImportView;
    }

    public void getImportNews(){
        DisposableObserver<DynamicImportNewsResponse> disposableObserver = new DisposableObserver<DynamicImportNewsResponse>() {
            @Override
            public void onNext(DynamicImportNewsResponse dynamicImportNewsResponse) {
                if(mView != null){
                    mView.onNewsSuccess(dynamicImportNewsResponse);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onNewError();
                }
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.getDynamicImportantNews(disposableObserver,1);
        mCompositeDisposable.add(disposableObserver);
    }

    public void getImportNewsLoadMore(int page){
        DisposableObserver<DynamicImportNewsResponse> disposableObserver = new DisposableObserver<DynamicImportNewsResponse>() {
            @Override
            public void onNext(DynamicImportNewsResponse dynamicImportNewsResponse) {
                if(mView != null){
                    mView.onNewsLoadMoreSuccess(dynamicImportNewsResponse);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onNewsLoadMoreError();
                }
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.getDynamicImportantNews(disposableObserver,page);
        mCompositeDisposable.add(disposableObserver);
    }


    public void getBanner(){
        DisposableObserver<BannerResponse> disposableObserver = new DisposableObserver<BannerResponse>() {
            @Override
            public void onNext(BannerResponse importantNewsBannerResponse) {
                if(mView != null){
                    mView.onBannerSuccess(importantNewsBannerResponse);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onBannerError();
                }
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.getBanner(disposableObserver,0);
        mCompositeDisposable.add(disposableObserver);
    }


}
