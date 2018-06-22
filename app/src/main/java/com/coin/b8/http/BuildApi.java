package com.coin.b8.http;


import android.text.TextUtils;

import com.coin.b8.app.B8Application;
import com.coin.b8.constant.Constants;
import com.coin.b8.utils.CommonUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
        client.addInterceptor(new VerifyInterceptor());
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

    public  static class VerifyInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Headers headers = chain.request().headers();
            Map<String, String> map = CommonUtils.getHeaders(B8Application.getIntstance());
            if(headers != null){
                Set<String> strings = headers.names();
                if(strings != null && strings.size() > 0){
                    for (String str : strings) {
                        if(!TextUtils.isEmpty(str)){
                            map.put(str,headers.get(str));
                        }
                    }
                }
            }
            Request request = chain.request().newBuilder()
                    .headers(Headers.of(map)).build();
            return chain.proceed(request);
        }
    }



}
