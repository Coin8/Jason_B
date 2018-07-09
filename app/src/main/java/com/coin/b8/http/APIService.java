package com.coin.b8.http;

import com.coin.b8.model.CancelCollectionResponse;
import com.coin.b8.model.CollectionListInfoResponse;
import com.coin.b8.model.DynamicImportNewsResponse;
import com.coin.b8.model.FeedBackResult;
import com.coin.b8.model.LoginResponseInfo;
import com.coin.b8.model.ModifyUserHeadResponse;
import com.coin.b8.model.ModifyUserResponse;
import com.coin.b8.model.RegisterResponseInfo;
import com.coin.b8.model.ResetPasswordResponseInfo;
import com.coin.b8.model.SelectCoinListResponse;
import com.coin.b8.model.TestModel;
import com.coin.b8.model.B8UpdateInfo;
import com.coin.b8.model.UnLoginUidInfo;
import com.coin.b8.model.UserInfoResponse;
import com.coin.b8.model.VerifycodeResponseModel;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
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

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("b/a/user")
    Observable<UnLoginUidInfo> getUnLoginUid(@Body RequestBody parameter);

    @GET("b/a/verifycode")
    Observable<VerifycodeResponseModel> sendVerifyCode(@Query("email")  String email,
                                                       @Query("verifyType")  int verifyType);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("b/a/regist")
    Observable<RegisterResponseInfo> register(@Body RequestBody parameter);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("b/a/login")
    Observable<LoginResponseInfo> login(@Body RequestBody parameter);

    /**
     * 重置密码
     * @param parameter
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @PUT("b/a/password")
    Observable<ResetPasswordResponseInfo> resetPassword(@Body RequestBody parameter);

    /**
     * 获取用户信息
     * @param uid
     * @return
     */
    @GET("b/a/u/info/{uid}")
    Observable<UserInfoResponse> getUserInfo(@Path("uid")  String uid);

    /**
     * 修改用户信息
     * @param uid
     * @return
     */
    @POST("b/a/u/info/{uid}")
    Observable<ModifyUserResponse> modifyUserInfo(@Path("uid")  String uid, @Body RequestBody parameter);


    /**
     * 修改用户头像
     * @param type
     * @param file
     * @return
     */
    @Multipart
    @POST("b/a/u/info/img")
    Observable<ModifyUserHeadResponse> modifyUserHead(@Query("type")  int type,
                                                      @Part MultipartBody.Part file);


    /**
     *  获取收藏列表
     * @param uid
     * @param start
     * @return
     */
    @GET("b/a/u/article/{uid}")
    Observable<CollectionListInfoResponse> getCollectionList(@Path("uid")  String uid, @Query("start")  long start);


    /**
     * 删除收藏
     * @param id
     * @param type
     * @return
     */
    @DELETE("b/a/u/article/{id}")
    Observable<CancelCollectionResponse> deleteCollection(@Path("id")  long id, @Query("targetType")  int type);

    /**
     *  获取选币列表
     * @return
     */
    @GET("b/a/coin/type/list")
    Observable<SelectCoinListResponse> getSelectCoinList();

    /**
     *  获取要闻列表
     * @return
     */
    @GET("b/a/news/search")
    Observable<DynamicImportNewsResponse> getDynamicImportantNews(@Query("page") int page);

}
