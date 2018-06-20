package com.coin.b8.ui.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.coin.b8.http.B8Api;
import com.coin.b8.model.B8UpdateInfo;
import com.coin.b8.ui.iView.IMainView;
import com.coin.b8.utils.AppUtil;
import com.coin.b8.utils.CommonUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;

import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangyi on 2018/5/31.
 */
public class MainPresenterImpl {
    private static final int THUMB_SIZE = 150;
    private IMainView mView;
    private Context mContext;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();


    public MainPresenterImpl(Context context,IMainView mainView) {
        this.mView = mainView;
        this.mContext = context;
    }

    public void getUpdateInfo(boolean auto){

        DisposableObserver<B8UpdateInfo> disposableObserver = new DisposableObserver<B8UpdateInfo>() {
            @Override
            public void onNext(B8UpdateInfo updateInfo) {
                if(updateInfo != null){
                    mView.onUpdateInfo(updateInfo, auto);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(e != null){
                    mView.onUpdateInfoError(e);
                }
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.getUpdateInfo(disposableObserver, String.valueOf(AppUtil.getVersionCode()));
        mCompositeDisposable.add(disposableObserver);
    }


    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public void getShareNetworkImage(int type,String url){
        Observable observable = Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(ObservableEmitter<Bitmap> emitter) throws Exception {
                try {
                    Bitmap bmp = BitmapFactory.decodeStream(new URL(url).openStream());
                    emitter.onNext(bmp);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });

        DisposableObserver disposableObserver = new DisposableObserver <Bitmap>() {

            @Override
            public void onNext(Bitmap bitmap) {
                if(mView != null){
                    mView.onGetShareNetImageBitmap(type,bitmap);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mView != null){
                    mView.onGetShareNetImageBitmapError();
                }
            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);

        mCompositeDisposable.add(disposableObserver);
    }


    public void onDetach(){
        mView = null;
        mCompositeDisposable.dispose();
    }



}
