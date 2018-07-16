package com.coin.b8.http;

import com.coin.b8.model.AddMarketSelfResponse;
import com.coin.b8.model.CancelCollectionResponse;
import com.coin.b8.model.CollectionListInfoResponse;
import com.coin.b8.model.CommonResponse;
import com.coin.b8.model.DeleteMarketSelfResponse;
import com.coin.b8.model.DeleteYuJingResponse;
import com.coin.b8.model.DynamicImportNewsResponse;
import com.coin.b8.model.FeedBackResult;
import com.coin.b8.model.ImportantNewsBannerResponse;
import com.coin.b8.model.LoginResponseInfo;
import com.coin.b8.model.MarketListSearchResponse;
import com.coin.b8.model.MarketSelfListResponse;
import com.coin.b8.model.ModifyUserHeadResponse;
import com.coin.b8.model.ModifyUserResponse;
import com.coin.b8.model.ModifyYuJingResponse;
import com.coin.b8.model.QuickNewsResponse;
import com.coin.b8.model.RegisterResponseInfo;
import com.coin.b8.model.ResetPasswordResponseInfo;
import com.coin.b8.model.SelectCoinListResponse;
import com.coin.b8.model.SelectCoinTypeListResponse;
import com.coin.b8.model.TestModel;
import com.coin.b8.model.B8UpdateInfo;
import com.coin.b8.model.UnLoginUidInfo;
import com.coin.b8.model.UserInfoResponse;
import com.coin.b8.model.VerifycodeResponseModel;
import com.coin.b8.model.YujingListResponse;

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
    Observable<CancelCollectionResponse> deleteCollection(
            @Path("id")  long id,
            @Query("targetType") int type);

    /**
     *  获取选币列表
     * @return
     */
    @GET("b/a/coin/type/list")
    Observable<SelectCoinListResponse> getSelectCoinList();

    /**
     *  获取选币类型下的币列表
     * @return
     */
    @GET("b/a/coin/type/{id}")
    Observable<SelectCoinTypeListResponse> getSelectCoinTypeList(@Path("id")  int id);

    /**
     *  获取要闻列表
     * @return
     */
    @GET("b/a/news/search")
    Observable<DynamicImportNewsResponse> getDynamicImportantNews(@Query("page") int page);

    /**
     *  获取要闻banner
     * @return
     */
    @GET("b/a/v2/banner/list/0")
    Observable<ImportantNewsBannerResponse> getDynamicImportantNewsBanner();


    /**
     *  获取快讯
     * @return
     */
    @GET("b/a/article/search")
    Observable<QuickNewsResponse> getQuickNews(@Query("page") int page);

    /**
     *  获取预警列表
     * @return
     */
    @GET("b/a/uu/warning/list/{uid}")
    Observable<YujingListResponse> getYuJingList(@Path("uid") String uid);

    /**
     *  删除预警
     * @return
     */
    @DELETE("b/a/uu/warning/{uid}/{id}")
    Observable<DeleteYuJingResponse> deleteYuJing(
            @Path("uid") String uid,
            @Path("id") long id);

    /**
     *  修改预警，打开关闭状态
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @PUT("b/a/uu/warning")
    Observable<ModifyYuJingResponse> modifyYuJing(@Body RequestBody parameter);


    /**
     * 行情列表搜索
     * @param content  （用户搜索的内容用这个字段传）可以搜索币种、交易所、全网；
     都是根据简称进行搜索。
    全网简称: coinmarketcap
    火币交易所简称：火币Pro
     * @param sort
     * 排序，1为升序，-1为降序默认为-1
     * @param start 默认为1
     * @param limit 默认为20
     * @param sortType
     * 1是交易额，2是交易量 ，3是当前价格，4是涨幅5市值
     * @param exchange
     * （app操作中限制的条件用这个字段传，比如用户点了只在火币网内搜，就在这个字段传火币Pro,未限制就不传）
     * @return
     */
    @GET("/b/a/coin/search")
    Observable<MarketListSearchResponse> getMarketListSearch(
            @Query("content") String content,
            @Query("sort") int sort,
            @Query("start") int start,
            @Query("limit") int limit,
            @Query("sortType") int sortType,
            @Query("exchange") String exchange);


    /**
     *  自选列表
     * @param uid
     * @param sort 排序，1为升序，-1为降序默认为-1
     * @param start 默认为1
     * @param limit 默认20
     * @param sortType  默认值为1；1是交易额，2是交易量 ，3是当前价格，4是涨幅5市值
     * @return
     */
    @GET("b/a/u/coin/list/{uid}")
    Observable<MarketSelfListResponse> getMarketSelfList(
            @Path("uid") String uid,
            @Query("sort") int sort,
            @Query("start") int start,
            @Query("limit") int limit,
            @Query("sortType") int sortType);

    /**
     *  添加自选
     * @param parameter
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("b/a/u/coin/{uid}")
    Observable<AddMarketSelfResponse> addMarketSelf(
            @Path("uid") String uid,
            @Body RequestBody parameter);

    /**
     * 删除自选
     * @param ucrid
     * @return
     */
    @DELETE("b/a/u/coin/{ucrid}")
    Observable<CommonResponse> deleteMarketSelf(
            @Path("ucrid") long ucrid);


    @GET("b/a/u/coin/top/{ucrid}")
    Observable<CommonResponse> topMarketSelfCoin(
            @Path("ucrid") long ucrid);
}
