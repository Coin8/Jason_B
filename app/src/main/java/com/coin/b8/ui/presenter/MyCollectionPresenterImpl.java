package com.coin.b8.ui.presenter;

import com.coin.b8.http.B8Api;
import com.coin.b8.model.CancelCollectionResponse;
import com.coin.b8.model.CollectionListInfoResponse;
import com.coin.b8.ui.iView.IMyCollectionView;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by zhangyi on 2018/7/9.
 */
public class MyCollectionPresenterImpl extends BasePresenterImpl<IMyCollectionView>{

    public MyCollectionPresenterImpl(IMyCollectionView iMyCollectionView) {
        mView = iMyCollectionView;
    }

    public void getCollectionList(String uid ,long start, long limit){
        DisposableObserver<CollectionListInfoResponse> disposableObserver = new DisposableObserver<CollectionListInfoResponse>() {
            @Override
            public void onNext(CollectionListInfoResponse collectionListInfoResponse) {
                if(mView != null){
                    mView.onCollectionList(collectionListInfoResponse);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onCollectionError();
                }
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.getCollectionList(disposableObserver,uid,start,limit);
        mCompositeDisposable.add(disposableObserver);
    }

    public void getCollectionListMore(String uid ,long start,long limit){
        DisposableObserver<CollectionListInfoResponse> disposableObserver = new DisposableObserver<CollectionListInfoResponse>() {
            @Override
            public void onNext(CollectionListInfoResponse collectionListInfoResponse) {
                if(mView != null){
                    mView.onCollectionMore(collectionListInfoResponse);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onCollectionMoreError();
                }
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.getCollectionList(disposableObserver,uid,start,limit);
        mCompositeDisposable.add(disposableObserver);
    }


    public void deleteCollection(long id ,int targetType){
        DisposableObserver<CancelCollectionResponse> disposableObserver = new DisposableObserver<CancelCollectionResponse>() {
            @Override
            public void onNext(CancelCollectionResponse cancelCollectionResponse) {
                if(mView != null){
                    mView.onDeleteCollectionSuccess(cancelCollectionResponse);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onDeleteCollectionError();
                }
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.cancelCollection(disposableObserver,id,targetType);
        mCompositeDisposable.add(disposableObserver);
    }

}
