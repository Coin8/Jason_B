package com.coin.b8.http;

import com.coin.b8.model.FeedBackResult;
import com.coin.b8.model.TestModel;
import com.coin.b8.model.B8UpdateInfo;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhangyi on 2018/6/11.
 */
public interface APIService {

    @GET("https://api.douban.com/v2/book/1003078")
    Observable<TestModel> getTestData();

    @GET("b/a/version")
    Observable<B8UpdateInfo> getUpdateInfo(@Query("versionCode")  String versionCode);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("b/a/suggest")
    Observable<FeedBackResult> userFeedBackQuestion(@Body RequestBody parameter);
}
