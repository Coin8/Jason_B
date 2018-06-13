package com.coin.b8.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
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
        StatusBarUtil.setColor(this, getResources().getColor(R.color.black), 60);
    }

    public void printScreen(){
        if(Constants.DEBUG){
            WindowManager manager = this.getWindowManager();
            DisplayMetrics dm = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(dm);
            String s = "width = " + dm.widthPixels + ", height = " + dm.heightPixels
                    + ", d = " + dm.density + ", dpi = " + dm.densityDpi
                    + ", scale = " + dm.scaledDensity;
            Log.e("zy",s);
        }

    }

    public void initToolBar(Toolbar toolbar, String title, int icon) {
        toolbar.setTitle(title);// 标题的文字需在setSupportActionBar之前，不然会无效
        toolbar.setNavigationIcon(icon);
        setSupportActionBar(toolbar);
        toolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }


}
