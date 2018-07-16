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
import com.coin.b8.help.PreferenceHelper;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

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

}
