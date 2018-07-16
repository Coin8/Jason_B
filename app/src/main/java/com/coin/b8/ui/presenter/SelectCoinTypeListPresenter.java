package com.coin.b8.ui.presenter;

import com.coin.b8.http.B8Api;
import com.coin.b8.model.SelectCoinTypeListResponse;
import com.coin.b8.ui.iView.ISelectCoinTypeListView;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by zhangyi on 2018/7/10.
 */
public class SelectCoinTypeListPresenter extends BasePresenterImpl<ISelectCoinTypeListView>{
    public SelectCoinTypeListPresenter(ISelectCoinTypeListView iSelectCoinTypeListView) {
        mView = iSelectCoinTypeListView;
    }

    public void getCoinTypeList(int id){
        DisposableObserver<SelectCoinTypeListResponse> disposableObserver = new DisposableObserver<SelectCoinTypeListResponse>() {
            @Override
            public void onNext(SelectCoinTypeListResponse selectCoinTypeListResponse) {
                if(mView != null){
                    mView.onCoinListSuccess(selectCoinTypeListResponse);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onCoinListError();
                }
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.getSelectCoinTypeList(disposableObserver,id);
        mCompositeDisposable.add(disposableObserver);
    }

}
