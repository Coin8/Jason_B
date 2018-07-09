package com.coin.b8.http;

import android.util.Log;

import com.coin.b8.model.FeedBackParameter;
import com.coin.b8.model.FeedBackResult;
import com.coin.b8.model.LoginParameter;
import com.coin.b8.model.LoginResponseInfo;
import com.coin.b8.model.ModifyUserHeadResponse;
import com.coin.b8.model.ModifyUserParameter;
import com.coin.b8.model.ModifyUserResponse;
import com.coin.b8.model.PhoneParameter;
import com.coin.b8.model.RegisterParameter;
import com.coin.b8.model.RegisterResponseInfo;
import com.coin.b8.model.ResetPasswordParameter;
import com.coin.b8.model.ResetPasswordResponseInfo;
import com.coin.b8.model.TestModel;
import com.coin.b8.model.B8UpdateInfo;
import com.coin.b8.model.UnLoginUidInfo;
import com.coin.b8.model.UserInfoResponse;
import com.coin.b8.model.VerifycodeResponseModel;
import com.google.gson.Gson;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
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

    public static void getUnLoginUidInfo(Observer<UnLoginUidInfo> observer, String imei){
        PhoneParameter phoneParameter = new PhoneParameter();
        phoneParameter.setPhoneType("android");
        phoneParameter.setPhoneStr(imei);
        Gson gson = new Gson();
        String data = gson.toJson(phoneParameter);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), data);
        BuildApi.getAPIService().getUnLoginUid(requestBody)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     *
     * @param observer
     * @param email
     * @param verifyType 1注册，2重置密码
     */
    public static void sendVerifyCode(Observer<VerifycodeResponseModel> observer,
                                      String email,
                                      int verifyType){
        BuildApi.getAPIService().sendVerifyCode(email,verifyType)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    public static void getRegisterInfo(Observer<RegisterResponseInfo> observer,
                                       String email,
                                       String password,
                                       String verifyCode){
        RegisterParameter registerParameter = new RegisterParameter();
        registerParameter.setEmail(email);
        registerParameter.setPassword(password);
        registerParameter.setVerifyCode(verifyCode);
        Gson gson = new Gson();
        String data = gson.toJson(registerParameter);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), data);
        BuildApi.getAPIService().register(requestBody)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void getLoginInfo(Observer<LoginResponseInfo> observer,
                                       String email,
                                       String password){
        LoginParameter loginParameter = new LoginParameter();
        loginParameter.setEmail(email);
        loginParameter.setPassword(password);
        Gson gson = new Gson();
        String data = gson.toJson(loginParameter);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), data);
        BuildApi.getAPIService().login(requestBody)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void resetPassword(Observer<ResetPasswordResponseInfo> observer,
                                     String email,
                                     String password,
                                     String verifyCode ){
        ResetPasswordParameter resetPasswordParameter = new ResetPasswordParameter();
        resetPasswordParameter.setEmail(email);
        resetPasswordParameter.setPassword(password);
        resetPasswordParameter.setVerifyCode(verifyCode);
        Gson gson = new Gson();
        String data = gson.toJson(resetPasswordParameter);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), data);
        BuildApi.getAPIService().resetPassword(requestBody)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void getUserInfo(Observer<UserInfoResponse> observer, String uid){
        BuildApi.getAPIService().getUserInfo(uid)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void modifyUserInfo(Observer<ModifyUserResponse> observer,
                                     int sex,
                                     String name,
                                     String uid ){
        ModifyUserParameter modifyUserParameter = new ModifyUserParameter();
        modifyUserParameter.setGender(sex);
        modifyUserParameter.setName(name);
        modifyUserParameter.setId(Long.parseLong(uid));
        Gson gson = new Gson();
        String data = gson.toJson(modifyUserParameter);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), data);
        BuildApi.getAPIService().modifyUserInfo(uid,requestBody)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void modifyUserHead(Observer<ModifyUserHeadResponse> observer, String filePath){
        File file = new File(filePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("img",file.getName(),requestBody);

        BuildApi.getAPIService().modifyUserHead(1,body)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
