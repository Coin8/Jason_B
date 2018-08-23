package com.coin.b8.ui.presenter;

import com.coin.b8.http.B8Api;
import com.coin.b8.model.CoinStoreListResponse;
import com.coin.b8.model.CommonResponse;
import com.coin.b8.ui.iView.IStoreView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by zhangyi on 2018/8/21.
 */
public class StoreDialogPresenterImpl extends BasePresenterImpl<IStoreView>{

    public StoreDialogPresenterImpl(IStoreView view) {
        mView = view;
    }

    public void createCoinStore(String name){
        DisposableObserver<CommonResponse> disposableObserver = new DisposableObserver<CommonResponse>() {
            @Override
            public void onNext(CommonResponse response) {
                if(mView == null){
                    return;
                }
                if(response != null){
                    if(response.isResult()){
                        mView.onCreateStoreSuccess();
                    }else {
                        mView.onCreateStoreError(response.getMessage());
                    }
                }else {
                    mView.onCreateStoreError("创建仓库失败");
                }


            }

            @Override
            public void onError(Throwable e) {
                mView.onCreateStoreError("创建仓库失败");
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.createCoinStore(disposableObserver,name);
        mCompositeDisposable.add(disposableObserver);
    }


    public void deleteCoinStore(long id){
        DisposableObserver<CommonResponse> disposableObserver = new DisposableObserver<CommonResponse>() {
            @Override
            public void onNext(CommonResponse response) {
                if(mView == null){
                    return;
                }
                if(response != null){
                    if(response.isResult()){
                        mView.onDeleteStoreSuccess();
                    }else {
                        mView.onDeleteStoreError(response.getMessage());
                    }
                }else {
                    mView.onDeleteStoreError("删除仓库失败");
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.onDeleteStoreError("删除仓库失败");
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.deleteCoinStore(disposableObserver,id);
        mCompositeDisposable.add(disposableObserver);
    }

    public void renameCoinStore(long id,String name){
        DisposableObserver<CommonResponse> disposableObserver = new DisposableObserver<CommonResponse>() {
            @Override
            public void onNext(CommonResponse response) {
                if(mView == null){
                    return;
                }
                if(response != null){
                    if(response.isResult()){
                        mView.onRenameStoreSuccess();
                    }else {
                        mView.onRenameStoreError(response.getMessage());
                    }
                }else {
                    mView.onRenameStoreError("重命名仓库失败");
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.onRenameStoreError("重命名仓库失败");
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.renameCoinStore(disposableObserver,id,name);
        mCompositeDisposable.add(disposableObserver);
    }

    public void switchCoinStore(long id){
        DisposableObserver<CommonResponse> disposableObserver = new DisposableObserver<CommonResponse>() {
            @Override
            public void onNext(CommonResponse response) {
                if(mView == null){
                    return;
                }
                if(response != null){
                    if(response.isResult()){
                        mView.onSwitchStoreSuccess();
                    }else {
                        mView.onSwitchStoreError(response.getMessage());
                    }
                }else {
                    mView.onSwitchStoreError("切换仓库失败");
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.onSwitchStoreError("切换仓库失败");
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.switchCoinStore(disposableObserver,id);
        mCompositeDisposable.add(disposableObserver);
    }



    public void getCoinStoreList(){
        DisposableObserver<CoinStoreListResponse> disposableObserver = new DisposableObserver<CoinStoreListResponse>() {
            @Override
            public void onNext(CoinStoreListResponse coinStoreListResponse) {
                if(mView != null){
                    mView.onStoreListSuccess(coinStoreListResponse);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onStoreListError();
                }
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.getCoinStoreList(disposableObserver);
        mCompositeDisposable.add(disposableObserver);
    }


}
