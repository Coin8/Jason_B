package com.coin.b8.ui.presenter;

import com.coin.b8.http.B8Api;
import com.coin.b8.model.DeleteYuJingResponse;
import com.coin.b8.model.ModifyYuJingResponse;
import com.coin.b8.model.YujingListResponse;
import com.coin.b8.ui.iView.IYuJingRecordView;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by zhangyi on 2018/7/10.
 */
public class YuJingRecordPresenter extends BasePresenterImpl<IYuJingRecordView>{
    public YuJingRecordPresenter(IYuJingRecordView iYuJingRecordView) {
        mView = iYuJingRecordView;
    }

    public void getYuJingList(String uid){
        DisposableObserver<YujingListResponse> disposableObserver = new DisposableObserver<YujingListResponse>() {
            @Override
            public void onNext(YujingListResponse yujingListResponse) {
                if(mView != null){
                    mView.onYuJingListSuccess(yujingListResponse);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onYuJingListError();
                }
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.getYuJingList(disposableObserver,uid);
        mCompositeDisposable.add(disposableObserver);
    }

    public void deleteYuJing(String uid,long id){
        DisposableObserver<DeleteYuJingResponse> disposableObserver = new DisposableObserver<DeleteYuJingResponse>() {
            @Override
            public void onNext(DeleteYuJingResponse response) {
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
        B8Api.deleteYuJing(disposableObserver,uid,id);
        mCompositeDisposable.add(disposableObserver);
    }

    public void modifyYuJing(String uid,long id ,int status){
        DisposableObserver<ModifyYuJingResponse> disposableObserver = new DisposableObserver<ModifyYuJingResponse>() {
            @Override
            public void onNext(ModifyYuJingResponse response) {
                if(mView != null){
                    mView.onModifySuccess(response);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onModifyError();
                }
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.modifyYuJing(disposableObserver,uid,id,status);
        mCompositeDisposable.add(disposableObserver);
    }


}
