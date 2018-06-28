package com.coin.b8.app;

import android.util.Log;

/**
 * Created by Jason on 2018/6/27.
 */
public class AppLogger {

    public static final String APP_TAG = "B8";

    // Open(true) or Close(false)
    private static boolean LOG_ONOFF = false;

    private AppLogger() {
    }

    public static void setOnOff(boolean onOff) {
        LOG_ONOFF = onOff;
        d("Set log on-off:" + onOff);
    }

    public static boolean getLogStatus() {
        return LOG_ONOFF;
    }

    /**
     * Generate title and content for log
     *
     * @param content log content
     * @return
     */
    private static String generateLog(String content) {
        // This code may cause a ANR. So Close record log when app running.
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String logFormat = "[%s.%s]-%s";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        return String.format(logFormat, callerClazzName, caller.getMethodName(), content);
    }

    public static void d(String content) {
        if (!LOG_ONOFF) {
            return;
        }
        Log.d(APP_TAG, generateLog(content));
    }

    public static void d(String content, Throwable tr) {
        if (!LOG_ONOFF) {
            return;
        }
        Log.d(APP_TAG, generateLog(content), tr);
    }

    public static void i(String content) {
        if (!LOG_ONOFF) {
            return;
        }
        Log.i(APP_TAG, generateLog(content));
    }

    public static void i(String content, Throwable tr) {
        if (!LOG_ONOFF) {
            return;
        }
        Log.i(APP_TAG, generateLog(content), tr);
    }

    public static void v(String content) {
        if (!LOG_ONOFF) {
            return;
        }
        Log.v(APP_TAG, generateLog(content));
    }

    public static void v(String content, Throwable tr) {
        if (!LOG_ONOFF) {
            return;
        }
        Log.v(APP_TAG, generateLog(content), tr);
    }

    public static void w(String content) {
        if (!LOG_ONOFF) {
            return;
        }
        Log.w(APP_TAG, generateLog(content));
    }

    public static void w(String content, Throwable tr) {
        if (!LOG_ONOFF) {
            return;
        }
        Log.w(APP_TAG, generateLog(content), tr);
    }

    public static void w(Throwable tr) {
        if (!LOG_ONOFF) {
            return;
        }
        Log.w(APP_TAG, tr);
    }

    public static void e(String content) {
        if (!LOG_ONOFF) {
            return;
        }
        Log.e(APP_TAG, generateLog(content));
    }

    public static void e(String content, Throwable tr) {
        if (!LOG_ONOFF) {
            return;
        }
        Log.e(APP_TAG, generateLog(content), tr);
    }

}
