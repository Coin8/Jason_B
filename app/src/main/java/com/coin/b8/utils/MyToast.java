package com.coin.b8.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.coin.b8.R;

/**
 * By Jason
 */
public class MyToast {

    private Activity mActivity;

    private LayoutInflater mLayoutInflater;

    public MyToast(Activity activity) {
        this.mActivity = activity;
    }

    public MyToast(LayoutInflater layoutInflater) {
        this.mLayoutInflater = layoutInflater;
    }

    public void showToast(String text) {
        View view;
        Context context;
        if (mActivity != null) {
            LayoutInflater inflater = mActivity.getLayoutInflater();
            view = inflater.inflate(R.layout.toast_layout, null);
            context = mActivity.getApplication();
        } else if (mLayoutInflater != null) {
            view = mLayoutInflater.inflate(R.layout.toast_layout, null);
            context = mLayoutInflater.getContext();
        } else {
            return;
        }
        TextView title = view.findViewById(R.id.toast_text);
        title.setText(text);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);//setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

}
