package com.coin.b8.ui.presenter;

import com.coin.b8.http.B8Api;
import com.coin.b8.model.QuickNewsResponse;
import com.coin.b8.ui.iView.IQuickNewsView;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by zhangyi on 2018/7/10.
 */
public class DynamicQuickNewsPresenter extends BasePresenterImpl<IQuickNewsView>{

    public DynamicQuickNewsPresenter(IQuickNewsView iQuickNewsView) {
        mView = iQuickNewsView;
    }

    public void getQuickNews(){
        DisposableObserver<QuickNewsResponse> disposableObserver = new DisposableObserver<QuickNewsResponse>() {
            @Override
            public void onNext(QuickNewsResponse quickNewsResponse) {
                if(mView != null){
                    mView.onNewsSuccess(quickNewsResponse);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onNewsError();
                }
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.getQuickNews(disposableObserver,1);
        mCompositeDisposable.add(disposableObserver);
    }

    public void getQuickNewsMore(int page){
        DisposableObserver<QuickNewsResponse> disposableObserver = new DisposableObserver<QuickNewsResponse>() {
            @Override
            public void onNext(QuickNewsResponse quickNewsResponse) {
                if(mView != null){
                    mView.onNewsMoreSuccess(quickNewsResponse);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onNewsMoreError();
                }
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.getQuickNews(disposableObserver,page);
        mCompositeDisposable.add(disposableObserver);
    }

}
