package com.coin.b8.utils;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.coin.b8.R;

/**
 * Created by zhangyi on 2018/5/29.
 */
public class MyToast {

    //private static Toast mToast = null;

    private Activity mActivity;

    public MyToast(Activity activity) {
        this.mActivity = activity;
    }

//    public static void showShortToast(String text) {
//        if (TextUtils.isEmpty(text)) {
//            return;
//        }
//        if (mToast == null) {
//            mToast = Toast.makeText(B8Application.getIntstance(), text, Toast.LENGTH_SHORT);
//        } else {
//            mToast.setText(text);
//            mToast.setDuration(Toast.LENGTH_SHORT);
//        }
//        mToast.show();
//    }

    public void showToast(String text) {
        LayoutInflater inflater = mActivity.getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_layout, null);
        TextView title = view.findViewById(R.id.toast_text);
        title.setText(text);
        Toast toast = new Toast(mActivity.getApplication());
        toast.setGravity(Gravity.CENTER, 0, 0);//setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

}
