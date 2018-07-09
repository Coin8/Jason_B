package com.coin.b8.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.coin.b8.app.AppLogger;
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
            AppLogger.e(e.getMessage(), e);
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
            AppLogger.e(e.getMessage(), e);
        }
        return versionCode;
    }

    public static String getChannelNo(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo != null) {
                String channel = appInfo.metaData.getString("CHANNEL_NO");
                AppLogger.d("App channel: " + channel);
                return channel;
            }
        } catch (Exception e) {
            AppLogger.e(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Get APP mode. True is debug, false is online.
     *
     * @param context
     * @return
     */
    public static boolean getMode(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo != null) {
                boolean mode = appInfo.metaData.getBoolean("MODE");
                AppLogger.d("App mode:" + mode);
                return mode;
            }
        } catch (Exception e) {
            AppLogger.e(e.getMessage(), e);
        }
        return false;
    }

    /**
     * Get log on-off. True is open, false is close.
     *
     * @param context
     * @return
     */
    public static boolean getLogOnOff(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo != null) {
                boolean onOff = appInfo.metaData.getBoolean("LOG");
                AppLogger.d("Log on-off:" + onOff);
                return onOff;
            }
        } catch (Exception e) {
            AppLogger.e(e.getMessage(), e);
        }
        return false;
    }

}
