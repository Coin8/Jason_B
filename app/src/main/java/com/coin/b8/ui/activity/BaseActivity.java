package com.coin.b8.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coin.b8.R;
import com.coin.b8.constant.Constants;
import com.jaeger.library.StatusBarUtil;
import com.umeng.analytics.MobclickAgent;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Setting;

import java.util.List;

/**
 * Created by zhangyi on 2018/6/7.
 */
public class BaseActivity extends AppCompatActivity {
    protected Toolbar mToolbar;
    protected TextView mToolbarTitle;
    protected ImageView mViewBack;
    protected TextView mToolbarRightTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initSystemBar();
    }

    @Override
    protected void onResume(){
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause(){
        super.onPause();
        MobclickAgent.onPause(this);
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

    private void initToolBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);// 标题的文字需在setSupportActionBar之前，不然会无效
//        toolbar.setNavigationIcon(R.drawable.icon_back);
        setSupportActionBar(toolbar);
        toolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }

    public void setInitToolBar(String title) {
        mToolbar = findViewById(R.id.toolbar);
        mToolbarTitle = mToolbar.findViewById(R.id.toolbar_title);
        mViewBack = findViewById(R.id.toolbar_back);
        mToolbarRightTitle = mToolbar.findViewById(R.id.right_title);
        initToolBar(mToolbar, "");
        mToolbarTitle.setText(title);
        TextPaint tp = mToolbarTitle.getPaint();
        tp.setFakeBoldText(true);
        mViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void showSettingDialog(Context context, final List<String> permissions) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.message_permission_always_failed, TextUtils.join("\n", permissionNames));

        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(R.string.title_dialog)
                .setMessage(message)
                .setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setPermission();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    /**
     * Set permissions.
     */
    public void setPermission() {
        AndPermission.with(this)
                .runtime()
                .setting()
                .onComeback(new Setting.Action() {
                    @Override
                    public void onAction() {
                        Toast.makeText(BaseActivity.this, R.string.message_setting_comeback, Toast.LENGTH_SHORT).show();
                    }
                })
                .start();
    }


}
