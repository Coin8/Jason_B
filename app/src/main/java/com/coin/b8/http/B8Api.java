package com.coin.b8.http;

import android.util.Log;

import com.coin.b8.model.FeedBackParameter;
import com.coin.b8.model.FeedBackResult;
import com.coin.b8.model.TestModel;
import com.coin.b8.model.B8UpdateInfo;
import com.google.gson.Gson;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by zhangyi on 2018/6/11.
 */
public class B8Api {

    public static void getTest(){
        Observer<TestModel> observer = new DisposableObserver<TestModel>() {
            @Override
            public void onNext(TestModel testModel) {
                if(testModel != null){
                    Log.e("B8Api","author_intro = " + testModel.getAuthor_intro());
                }

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        BuildApi.getAPIService().getTestData()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void getUpdateInfo(Observer<B8UpdateInfo> observer, String versionCode){
        BuildApi.getAPIService().getUpdateInfo(versionCode)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void postFeedBackQuestion(Observer<FeedBackResult> observer,int type,String content,String contact){
        FeedBackParameter feedBackParameter = new FeedBackParameter();
        feedBackParameter.setType(type);
        feedBackParameter.setContact(contact);
        feedBackParameter.setContent(content);
        Gson gson = new Gson();
        String data = gson.toJson(feedBackParameter);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), data);
        BuildApi.getAPIService().userFeedBackQuestion(requestBody)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
