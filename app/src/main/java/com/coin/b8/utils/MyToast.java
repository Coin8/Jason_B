package com.coin.b8.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.coin.b8.app.B8Application;

/**
 * Created by zhangyi on 2018/5/29.
 */
public class MyToast {

    private static Toast mToast = null;

    private static void showShortToast(Context context, String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public static void showShortToast(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(B8Application.getIntstance(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

}
