package com.coin.b8.app;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.coin.b8.help.DemoHelper;

/**
 * Created by zhangyi on 2018/5/29.
 */
public class B8Application extends Application {

    private static B8Application mB8Application;
    private static Handler mHandler;

    private NetWorkStateReceiver mNetWorkReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        mB8Application = this;
        mHandler = new Handler();
        DemoHelper.getInstance().init(mB8Application);
        this.registerNetWorkReceiver();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mNetWorkReceiver != null) {
            unregisterReceiver(mNetWorkReceiver);
            mNetWorkReceiver = null;
        }
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

    private void registerNetWorkReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
//        filter.addAction("android.net.wifi.STATE_CHANGE");
        if (mNetWorkReceiver != null) {
            Log.w("B8", "NetWork Receiver is not null!");
            return;
        }
        mNetWorkReceiver = new NetWorkStateReceiver();
        registerReceiver(mNetWorkReceiver, filter);
    }

    public static B8Application getIntstance() {
        return mB8Application;
    }
}

