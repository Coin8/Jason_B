package com.coin.b8.app;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.coin.b8.constant.Constants;
import com.coin.b8.help.DemoHelper;
import com.coin.b8.update.UpdateManager;
import com.coin.b8.utils.AppUtil;
import com.coin.b8.utils.CommonUtils;
import com.umeng.commonsdk.UMConfigure;

/**
 * Created by zhangyi on 2018/5/29.
 */
public class B8Application extends Application {

    private static B8Application mB8Application;
    private static Handler mHandler;

    private NetWorkStateReceiver mNetWorkReceiver;

    private String CHANNEL = "999999999";

    private static final String UMENG_KEY = "5b29bc71b27b0a2f4e000014";

    @Override
    public void onCreate() {
        super.onCreate();
        this.initAppEnv();
        mB8Application = this;
        mHandler = new Handler();
        DemoHelper.getInstance().init(mB8Application);
        UpdateManager.setDebuggable(true);
        UpdateManager.setWifiOnly(false);
        UpdateManager.setUrl("b8", CHANNEL);
        this.registerNetWorkReceiver();
        this.initUMeng();
    }

    /**
     * Init APP environment. Such as, log, host......
     */
    private void initAppEnv() {
        AppLogger.setOnOff(AppUtil.getLogOnOff(this));
        CHANNEL = AppUtil.getChannelNo(this);
        if (AppUtil.getMode(this)) {
            Constants.DEBUG = true;
            Constants.BASEURL = Constants.DEBUG_URL;
        } else {
            Constants.DEBUG = false;
            Constants.BASEURL = Constants.ONLINE_URL;
        }
    }

    private void initUMeng() {
        UMConfigure.init(this, UMENG_KEY, CHANNEL, UMConfigure.DEVICE_TYPE_PHONE, null);
        UMConfigure.setLogEnabled(AppLogger.getLogStatus());
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

