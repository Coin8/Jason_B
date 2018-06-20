package com.coin.b8.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coin.b8.R;
import com.coin.b8.ui.iView.ISettingView;
import com.coin.b8.ui.presenter.SetttingPresenterImpl;
import com.coin.b8.ui.view.SettingItemView;
import com.coin.b8.ui.view.SwitchButton;
import com.coin.b8.utils.DialogUtil;
import com.coin.b8.utils.MyToast;

/**
 * Created by zhangyi on 2018/6/7.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener, ISettingView {

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private ImageView mViewBack;
    private SettingItemView mAboutUs;
    private SettingItemView mCleanCache;
    private SetttingPresenterImpl mSetttingPresenter;
    private SwitchButton mPushMangerSwitchButton;

    private MyToast mToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mToast = new MyToast(this);
        initView();
        initData();
        initToolBar();
        mSetttingPresenter.initCache();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                finish();
                //  return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSetttingPresenter.onDetach();
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mToolbarTitle = mToolbar.findViewById(R.id.toolbar_title);
        mViewBack = findViewById(R.id.toolbar_back);
        mAboutUs = findViewById(R.id.item_about_us);
        mCleanCache = findViewById(R.id.item_clean_cache);
        mPushMangerSwitchButton = findViewById(R.id.switch_button);
        mAboutUs.setOnClickListener(this);
        mCleanCache.setOnClickListener(this);
    }

    private void initData() {
        mSetttingPresenter = new SetttingPresenterImpl(this, this);
        mPushMangerSwitchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

            }
        });
    }

    private void initToolBar() {
        super.initToolBar(mToolbar, "");
        mToolbarTitle.setText("系统设置");
        TextPaint tp = mToolbarTitle.getPaint();
        tp.setFakeBoldText(true);
        mViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.item_clean_cache:
                cleanCache();
                break;
            case R.id.item_about_us:
                startActivity(new Intent(this, AboutUsActivity.class));
                break;
            default:
                break;
        }

    }


    private void cleanCache() {

        mSetttingPresenter.cleanCache();
//        DialogUtil.showMyDialog(this, "缓存清理", "确定要清除图片的缓存吗？", "确定", "取消", new DialogUtil.OnDialogClickListener() {
//
//            @Override
//            public void onConfirm() {
//                mSetttingPresenter.cleanCache();
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//        });

    }


    @Override
    public void setCacheSize(String cacheSize) {
        mCleanCache.setRightText(cacheSize);
    }

    @Override
    public void cleanCacheSuccess() {
        mToast.showToast("清理缓存成功");
    }
}
