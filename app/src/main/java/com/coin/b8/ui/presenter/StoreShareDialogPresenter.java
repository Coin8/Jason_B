package com.coin.b8.ui.presenter;

import com.coin.b8.http.B8Api;
import com.coin.b8.model.ShareCommentResponse;
import com.coin.b8.ui.iView.IStoreShareView;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by zhangyi on 2018/8/22.
 */
public class StoreShareDialogPresenter extends BasePresenterImpl<IStoreShareView>{

    public StoreShareDialogPresenter(IStoreShareView view) {
        mView = view;
    }

    public void getShareComment(){
        DisposableObserver<ShareCommentResponse> disposableObserver = new DisposableObserver<ShareCommentResponse>() {
            @Override
            public void onNext(ShareCommentResponse response) {
                if(mView != null){
                    mView.onComment(response);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.getShareComment(disposableObserver);
        mCompositeDisposable.add(disposableObserver);
    }


}
