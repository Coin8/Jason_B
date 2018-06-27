package com.coin.b8.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.coin.b8.app.B8Application;

/**
 * Created by zhangyi on 2018/5/29.
 */
public class AppUtil {

    public static String getVersionName() {
        String versionName = "";
        try {
            PackageManager pm = B8Application.getIntstance().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(B8Application.getIntstance().getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {
            Log.e("AppUtil",e.getMessage());
        }
        return versionName;
    }

    public static int getVersionCode() {
        int versionCode = -1;
        try {
            PackageManager pm = B8Application.getIntstance().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(B8Application.getIntstance().getPackageName(), 0);
            versionCode = pi.versionCode;
        } catch (Exception e) {
            Log.e("AppUtil", e.getMessage());
        }
        return versionCode;
    }

    public static String getChannelNo(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo != null) {
                return appInfo.metaData.getString("CHANNEL_NO");
            }
        } catch (Exception e) {
            Log.e("CommonUtils", e.toString());
        }
        return null;
    }


}
