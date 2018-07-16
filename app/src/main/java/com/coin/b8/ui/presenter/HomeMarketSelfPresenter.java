package com.coin.b8.ui.presenter;

import com.coin.b8.http.B8Api;
import com.coin.b8.model.CommonResponse;
import com.coin.b8.model.MarketSelfListResponse;
import com.coin.b8.ui.iView.IHomeMarketSelfView;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by zhangyi on 2018/7/13.
 */
public class HomeMarketSelfPresenter extends BasePresenterImpl<IHomeMarketSelfView>{

    public HomeMarketSelfPresenter(IHomeMarketSelfView iHomeMarketSelfView) {
        mView = iHomeMarketSelfView;
    }

    public void getRefreshList( String uid,
                                int sort,
                                int start,
                                int limit,
                                int sortType){
        DisposableObserver<MarketSelfListResponse> disposableObserver = new DisposableObserver<MarketSelfListResponse>() {
            @Override
            public void onNext(MarketSelfListResponse response) {
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
        B8Api.getMarketSelfList(disposableObserver,uid,sort,start,limit,sortType);
        mCompositeDisposable.add(disposableObserver);
    }

    public void getLoadMoreList( String uid,
                                 int sort,
                                 int start,
                                 int limit,
                                 int sortType){
        DisposableObserver<MarketSelfListResponse> disposableObserver = new DisposableObserver<MarketSelfListResponse>() {
            @Override
            public void onNext(MarketSelfListResponse response) {
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
        B8Api.getMarketSelfList(disposableObserver,uid,sort,start,limit,sortType);
        mCompositeDisposable.add(disposableObserver);
    }



    public void toTop( long ucid){
        DisposableObserver<CommonResponse> disposableObserver = new DisposableObserver<CommonResponse>() {
            @Override
            public void onNext(CommonResponse response) {
                if(mView != null){
                    mView.onTopSuccess(response);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onTopError();
                }
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.getTopMarketSelfList(disposableObserver,ucid);
        mCompositeDisposable.add(disposableObserver);
    }

    public void toDelete( long ucid){
        DisposableObserver<CommonResponse> disposableObserver = new DisposableObserver<CommonResponse>() {
            @Override
            public void onNext(CommonResponse response) {
                if(mView != null){
                    mView.onDeleteSuccess(response);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onDeleteError();
                }
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.deleteMarketSelfList(disposableObserver,ucid);
        mCompositeDisposable.add(disposableObserver);
    }




}
