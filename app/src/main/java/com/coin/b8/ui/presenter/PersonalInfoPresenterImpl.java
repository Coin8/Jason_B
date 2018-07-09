package com.coin.b8.ui.presenter;

import com.coin.b8.http.B8Api;
import com.coin.b8.model.ModifyUserHeadResponse;
import com.coin.b8.model.ModifyUserParameter;
import com.coin.b8.model.ModifyUserResponse;
import com.coin.b8.model.UserInfoResponse;
import com.coin.b8.ui.iView.IPersonalInfoView;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by zhangyi on 2018/7/5.
 */
public class PersonalInfoPresenterImpl extends BasePresenterImpl<IPersonalInfoView>{

    public PersonalInfoPresenterImpl(IPersonalInfoView iPersonalInfoView) {
        mView = iPersonalInfoView;
    }

    public void getUserInfo(String uid){
        DisposableObserver<UserInfoResponse> disposableObserver = new DisposableObserver<UserInfoResponse>() {
            @Override
            public void onNext(UserInfoResponse userInfoResponse) {
                if(mView != null){
                    mView.onUserInfo(userInfoResponse);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.getUserInfo(disposableObserver,uid);
        mCompositeDisposable.add(disposableObserver);
    }


    public void modifyUserInfo(int sex,String name ,String uid){
        DisposableObserver<ModifyUserResponse> disposableObserver = new DisposableObserver<ModifyUserResponse>() {
            @Override
            public void onNext(ModifyUserResponse modifyUserResponse) {
                if(mView != null){
                    mView.onModifyUserSuccess(modifyUserResponse);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onModifyUserFail();
                }
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.modifyUserInfo(disposableObserver,sex,name,uid);
        mCompositeDisposable.add(disposableObserver);
    }

    public void modifyUserHead(String filePath){
        DisposableObserver<ModifyUserHeadResponse> disposableObserver = new DisposableObserver<ModifyUserHeadResponse>() {
            @Override
            public void onNext(ModifyUserHeadResponse modifyUserHeadResponse) {
                if(mView != null){
                    mView.onModifyUserHeadSuccess(modifyUserHeadResponse);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onModifyUserHeadFail();
                }
            }

            @Override
            public void onComplete() {

            }
        };

        B8Api.modifyUserHead(disposableObserver,filePath);
        mCompositeDisposable.add(disposableObserver);
    }

}
