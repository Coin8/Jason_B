package com.coin.b8.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.coin.b8.R;
import com.coin.b8.constant.Constants;
import com.jaeger.library.StatusBarUtil;

/**
 * Created by zhangyi on 2018/6/7.
 */
public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initSystemBar();
    }

    private void initSystemBar() {
        int color = R.color.black;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            color = R.color.white;
        }
        StatusBarUtil.setColor(this, getResources().getColor(color), 0);

    }

    public void printScreen() {
        if (Constants.DEBUG) {
            WindowManager manager = this.getWindowManager();
            DisplayMetrics dm = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(dm);
            String s = "width = " + dm.widthPixels + ", height = " + dm.heightPixels
                    + ", d = " + dm.density + ", dpi = " + dm.densityDpi
                    + ", scale = " + dm.scaledDensity;
            Log.e("zy", s);
        }

    }

    public void initToolBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);// 标题的文字需在setSupportActionBar之前，不然会无效
        toolbar.setNavigationIcon(R.drawable.icon_back);
        setSupportActionBar(toolbar);
        toolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }


}
