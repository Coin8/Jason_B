package com.coin.b8.ui.presenter;

import com.coin.b8.http.B8Api;
import com.coin.b8.model.AddMarketSelfResponse;
import com.coin.b8.model.CommonResponse;
import com.coin.b8.model.DeleteMarketSelfResponse;
import com.coin.b8.model.ExchangeListResponse;
import com.coin.b8.model.HotCoinResponse;
import com.coin.b8.model.MarketListSearchResponse;
import com.coin.b8.ui.iView.ISearchView;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by zhangyi on 2018/7/17.
 */
public class SearchPresenterImpl extends BasePresenterImpl<ISearchView>{

    public SearchPresenterImpl(ISearchView iSearchView) {
        mView = iSearchView;
    }

    public void getHotCoinList(){
        DisposableObserver<HotCoinResponse> disposableObserver = new DisposableObserver<HotCoinResponse>() {
            @Override
            public void onNext(HotCoinResponse hotCoinResponse) {
                if(mView != null){
                    mView.onHotCoinSuccess(hotCoinResponse);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onHotCoinError();
                }
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.getHotCoin(disposableObserver);
        mCompositeDisposable.add(disposableObserver);
    }

    public void getExchangeList(){
        DisposableObserver<ExchangeListResponse> disposableObserver = new DisposableObserver<ExchangeListResponse>() {
            @Override
            public void onNext(ExchangeListResponse exchangeListResponse) {
                if(mView != null){
                    mView.onExchangeSuccess(exchangeListResponse);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onExchangeError();
                }
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.getExchangeList(disposableObserver);
        mCompositeDisposable.add(disposableObserver);
    }

    public void search(String content, int start, int limit,String exchange){
        DisposableObserver<MarketListSearchResponse> disposableObserver = new DisposableObserver<MarketListSearchResponse>() {
            @Override
            public void onNext(MarketListSearchResponse response) {
                if(mView != null){
                    mView.onSearchSuccess(response);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onSearchError();
                }
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.getMarketListSearch(disposableObserver,content,-1,start,limit,1,exchange);
        mCompositeDisposable.add(disposableObserver);
    }


    public void searchMore(String content, int start, int limit,String exchange){
        DisposableObserver<MarketListSearchResponse> disposableObserver = new DisposableObserver<MarketListSearchResponse>() {
            @Override
            public void onNext(MarketListSearchResponse response) {
                if(mView != null){
                    mView.onSearchMoreSuccess(response);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onSearchMoreError();
                }
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.getMarketListSearch(disposableObserver,content,-1,start,limit,1,exchange);
        mCompositeDisposable.add(disposableObserver);
    }


    public void addSelf(long id){
        DisposableObserver<AddMarketSelfResponse> disposableObserver = new DisposableObserver<AddMarketSelfResponse>() {
            @Override
            public void onNext(AddMarketSelfResponse response) {
                if(mView != null){
                    mView.onAddSelf(response);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onAddSelf(null);
                }
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.addMarketSelfList(disposableObserver,id);
        mCompositeDisposable.add(disposableObserver);
    }


    public void deleteSelf(long ucrid){
        DisposableObserver<CommonResponse> disposableObserver = new DisposableObserver<CommonResponse>() {
            @Override
            public void onNext(CommonResponse response) {
                if(mView != null){
                    mView.onDeleteSelf(response);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onDeleteSelf(null);
                }
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.deleteMarketSelfList(disposableObserver,ucrid);
        mCompositeDisposable.add(disposableObserver);
    }




}
