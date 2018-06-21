package com.coin.b8.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;

import com.coin.b8.R;
import com.coin.b8.utils.MyToast;

/**
 * Created by Jason on 2018/6/21.
 */
public class NetWorkStateReceiver extends BroadcastReceiver {

    private MyToast mToast;

    public NetWorkStateReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w("B8", "Network Change!");
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        mToast = new MyToast(layoutInflater);
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //检测API是不是小于23，因为到了API23之后getNetworkInfo(int networkType)方法被弃用
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            //获取WIFI连接的信息
            NetworkInfo wifiNetworkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            //获取移动数据连接的信息
            NetworkInfo dataNetworkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                //Toast.makeText(context, "WIFI已连接,移动数据已连接", Toast.LENGTH_SHORT).show();
            } else if (wifiNetworkInfo.isConnected() && !dataNetworkInfo.isConnected()) {
                // Toast.makeText(context, "WIFI已连接,移动数据已断开", Toast.LENGTH_SHORT).show();
            } else if (!wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                // Toast.makeText(context, "WIFI已断开,移动数据已连接", Toast.LENGTH_SHORT).show();
            } else {
                // Toast.makeText(context, "WIFI已断开,移动数据已断开", Toast.LENGTH_SHORT).show();
                mToast.showToast(context.getString(R.string.network_disconnect));
            }
        } else {//API大于23时使用下面的方式进行网络监听
            //获取所有网络连接的信息
            Network[] networks = manager.getAllNetworks();
            //用于存放网络连接信息
            StringBuilder sb = new StringBuilder();
            //通过循环将网络信息逐个取出来
            for (int i = 0; i < networks.length; i++) {
                //获取ConnectivityManager对象对应的NetworkInfo对象
                NetworkInfo networkInfo = manager.getNetworkInfo(networks[i]);
                sb.append(networkInfo.getTypeName() + " connect is " + networkInfo.isConnected() + ";");
            }
            if (networks.length < 1) {
                mToast.showToast(context.getString(R.string.network_disconnect));
            }
            // mToast.showToast(sb.toString());
        }
    }

}
