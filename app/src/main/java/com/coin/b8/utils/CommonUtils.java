package com.coin.b8.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.coin.b8.app.AppLogger;
import com.coin.b8.app.B8Application;
import com.coin.b8.help.DemoHelper;
import com.coin.b8.help.PreferenceHelper;
import com.coin.b8.http.B8Api;
import com.coin.b8.model.UnLoginUidInfo;
import com.umeng.analytics.MobclickAgent;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by zhangyi on 2018/6/20.
        */
public class CommonUtils {

    public static Map<String, String> getHeaders(Context context) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", PreferenceHelper.getUid(context));
        map.put("imei", PreferenceHelper.getIMEI(context));
        map.put("channel", encode(Build.MANUFACTURER));
        map.put("os","android");
        map.put("channelCode", AppUtil.getChannelNo(context));
        map.put("token",PreferenceHelper.getToken(context));
        return map;
    }

    public static String encode(String s) {
        if(TextUtils.isEmpty(s)){
            return "";
        }
        try {
            s = java.net.URLEncoder.encode(s, "UTF-8");
        } catch (Exception e) {
            AppLogger.e(e.getMessage(), e);
        }
        return s;
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String millis2String(final long millis, @NonNull final DateFormat format) {
        return format.format(new Date(millis));
    }

    public static String getCachePath(Context context) {
        String cachePath = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {

            cachePath = context.getExternalCacheDir().getPath();

        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }


    public static String createRandomUid(){
        String uid = UUID.randomUUID().toString();
        return uid;
    }

    public static boolean isEmail(final CharSequence input) {
        return isMatch("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", input);
    }
    public static boolean isMatch(final String regex, final CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }

    public static String getWeek(long time) {
        String Week = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new Date(time));
        } catch (Exception e) {
            e.printStackTrace();
        }

        int wek=c.get(Calendar.DAY_OF_WEEK);

        if (wek == 1) {
            Week += "星期日";
        }
        if (wek == 2) {
            Week += "星期一";
        }
        if (wek == 3) {
            Week += "星期二";
        }
        if (wek == 4) {
            Week += "星期三";
        }
        if (wek == 5) {
            Week += "星期四";
        }
        if (wek == 6) {
            Week += "星期五";
        }
        if (wek == 7) {
            Week += "星期六";
        }
        return Week;
    }


    public static void getUnLoginUid(){

        if(PreferenceHelper.getIsLogin(B8Application.getIntstance())){
            return;
        }

        String imei = PreferenceHelper.getIMEI(B8Application.getIntstance());
        if(TextUtils.isEmpty(imei)){
            return;
        }
        DisposableObserver<UnLoginUidInfo> disposableObserver = new DisposableObserver<UnLoginUidInfo>() {
            @Override
            public void onNext(UnLoginUidInfo unLoginUidInfo) {
                if(unLoginUidInfo != null){
                    if(!PreferenceHelper.getIsLogin(B8Application.getIntstance())){
                        PreferenceHelper.setUid(B8Application.getIntstance(),String.valueOf(unLoginUidInfo.getUid()));

                        if(!TextUtils.isEmpty(unLoginUidInfo.getEasename())
                                && !TextUtils.isEmpty(unLoginUidInfo.getPassword())){
                            PreferenceHelper.setEaseName(B8Application.getIntstance(),unLoginUidInfo.getEasename());
                            PreferenceHelper.setEasePassword(B8Application.getIntstance(),unLoginUidInfo.getPassword());
                            if(DemoHelper.getInstance().isLoggedIn()){
                                DemoHelper.getInstance().logout();
                            }
                            DemoHelper.getInstance().login(unLoginUidInfo.getEasename(),
                                    unLoginUidInfo.getPassword());
                        }


                    }
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        };
        B8Api.getUnLoginUidInfo(disposableObserver,imei);
    }

    public static HashMap<String,String> getCommonPara(){
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("uuid",PreferenceHelper.getIMEI(B8Application.getIntstance()));
        map.put("ip_address","");
        map.put("system_version",Build.VERSION.RELEASE);
        map.put("device_version",Build.DEVICE);
        map.put("uid",PreferenceHelper.getUid(B8Application.getIntstance()));
        map.put("user_mail",PreferenceHelper.getEaseName(B8Application.getIntstance()));
        return map;
    }

    public static void umengReport(Context context,String id){
        if(context == null){
            return;
        }
        HashMap<String,String> map = getCommonPara();
        MobclickAgent.onEvent(context, id, map);
    }


}
