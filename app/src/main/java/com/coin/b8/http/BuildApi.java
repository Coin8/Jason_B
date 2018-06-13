package com.coin.b8.http;


import com.coin.b8.constant.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by yizhang210244 on 2017/4/27.
 */

public class BuildApi {

    private static Retrofit retrofit;

    public static OkHttpClient defaultOkHttpClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.writeTimeout(30 * 1000, TimeUnit.MILLISECONDS);
        client.readTimeout(20 * 1000, TimeUnit.MILLISECONDS);
        client.connectTimeout(15 * 1000, TimeUnit.MILLISECONDS);
        return client.build();
    }

    public static APIService getAPIService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASEURL) //设置Base的访问路径
                    .addConverterFactory(GsonConverterFactory.create()) //设置默认的解析库：Gson
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(defaultOkHttpClient())
                    .build();
        }
        return retrofit.create(APIService.class);
    }


}
