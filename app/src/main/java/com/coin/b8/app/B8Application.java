package com.coin.b8.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;

import com.coin.b8.help.DemoHelper;

/**
 * Created by zhangyi on 2018/5/29.
 */
public class B8Application extends Application{
    private static B8Application mB8Application;
    private static Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        mB8Application = this;
        mHandler = new Handler();
        DemoHelper.getInstance().init(mB8Application);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        return mHandler;
    }

    public static B8Application getIntstance() {
        return mB8Application;
    }
}

