package com.coin.b8.ui.presenter;

import com.coin.b8.http.B8Api;
import com.coin.b8.model.MarketListSearchResponse;
import com.coin.b8.ui.iView.IHomeMarketNormalView;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by zhangyi on 2018/7/11.
 */
public class HomeMarketNormalPresenter extends BasePresenterImpl<IHomeMarketNormalView>{

    public HomeMarketNormalPresenter(IHomeMarketNormalView iHomeMarketNormalView) {
        mView = iHomeMarketNormalView;
    }

    public void getRefreshList(String content,
                               int sort,
                               int start,
                               int limit,
                               int sortType,
                               String exchange){
        DisposableObserver<MarketListSearchResponse> disposableObserver = new DisposableObserver<MarketListSearchResponse>() {
            @Override
            public void onNext(MarketListSearchResponse response) {
                if(mView != null){
                    mView.onListSuccess(0,response);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onListError(0);
                }
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.getMarketListSearch(disposableObserver,content,sort,start,limit,sortType,exchange);
        mCompositeDisposable.add(disposableObserver);
    }


    public void getLoadMoreList(String content,
                               int sort,
                               int start,
                               int limit,
                               int sortType,
                               String exchange){
        DisposableObserver<MarketListSearchResponse> disposableObserver = new DisposableObserver<MarketListSearchResponse>() {
            @Override
            public void onNext(MarketListSearchResponse response) {
                if(mView != null){
                    mView.onListSuccess(1,response);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onListError(1);
                }
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.getMarketListSearch(disposableObserver,content,sort,start,limit,sortType,exchange);
        mCompositeDisposable.add(disposableObserver);
    }



}
