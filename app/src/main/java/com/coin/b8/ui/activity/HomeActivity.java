package com.coin.b8.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.coin.b8.R;
import com.coin.b8.constant.Constants;
import com.coin.b8.help.BottomNavigationViewHelper;
import com.coin.b8.help.DemoHelper;
import com.coin.b8.help.PreferenceHelper;
import com.coin.b8.model.B8UpdateInfo;
import com.coin.b8.model.UnLoginUidInfo;
import com.coin.b8.permission.RuntimeRationale;
import com.coin.b8.ui.fragment.BaseFragment;
import com.coin.b8.ui.fragment.HomeDynamicFragment;
import com.coin.b8.ui.fragment.HomeMarketFragment;
import com.coin.b8.ui.fragment.HomeMineFragment;
import com.coin.b8.ui.fragment.HomeSelectCoinFragment;
import com.coin.b8.ui.iView.IHomeView;
import com.coin.b8.ui.presenter.HomePresenterImpl;
import com.coin.b8.update.ICheckAgent;
import com.coin.b8.update.IUpdateChecker;
import com.coin.b8.update.IUpdateParser;
import com.coin.b8.update.UpdateInfo;
import com.coin.b8.update.UpdateManager;
import com.coin.b8.utils.MyToast;
import com.coin.b8.utils.PhoneUtils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyi on 2018/6/27.
 */
public class HomeActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener,IHomeView{
    private static long EXIT_TIME_LAST = 0;
    private static final long EXIT_TIME = 2000;
    private ArrayList<BaseFragment> mFragments;
    BottomNavigationView mBottomNavigationView;
    private HomeMarketFragment mHomeMarketFragment;
    private HomeDynamicFragment mHomeDynamicFragment;
    private HomeSelectCoinFragment mHomeSelectCoinFragment;
    private HomeMineFragment mHomeMineFragment;
    private int mLastFgIndex;
    private MyToast mToast;
    private HomePresenterImpl mHomePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mHomePresenter = new HomePresenterImpl(this);
        mFragments = new ArrayList<>();
        initFragment();
        initBottomNavigationView();
        mToast = new MyToast(this);
        requestAppPermission();
        mHomePresenter.getUpdateInfo(false);
        initData();
    }

    private void initData(){

    }

    private void initBottomNavigationView(){
        mBottomNavigationView = findViewById(R.id.bottom_navigation_view);
        mBottomNavigationView.setItemIconTintList(null);
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private void initFragment(){
        mHomeMarketFragment = HomeMarketFragment.getInstance();
        mHomeDynamicFragment = HomeDynamicFragment.getInstance();
        mHomeSelectCoinFragment = HomeSelectCoinFragment.getInstance();
        mHomeMineFragment = HomeMineFragment.getInstance();
        mFragments.add(mHomeMarketFragment);
        mFragments.add(mHomeDynamicFragment);
        mFragments.add(mHomeSelectCoinFragment);
        mFragments.add(mHomeMineFragment);
        switchFragment(0);
    }

    private void switchFragment(int position) {
        if (position >= mFragments.size()) {
            return;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment targetFg = mFragments.get(position);
        Fragment lastFg = mFragments.get(mLastFgIndex);
        mLastFgIndex = position;
        ft.hide(lastFg);
        if (!targetFg.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(targetFg).commit();
            ft.add(R.id.contentLayout, targetFg);
        }
        ft.show(targetFg);
        ft.commitAllowingStateLoss();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.tab_market:
                switchFragment(0);
                break;
            case R.id.tab_dynamic:
                switchFragment(1);
                break;
            case R.id.tab_select_coin:
                switchFragment(2);
                break;
            case R.id.tab_mine:
                switchFragment(3);
                break;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            /**
             * 退出判断
             */
            if (!exit()) {
                return true;
            }
        }


        return super.onKeyDown(keyCode, event);
    }

    private boolean exit() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - EXIT_TIME_LAST > EXIT_TIME) {
            EXIT_TIME_LAST = currentTime;
            mToast.showToast(getString(R.string.main_back_again));
            return false;
        } else {
            EXIT_TIME_LAST = 0;
            finish();
            System.exit(0);
            return true;
        }
    }

    private void requestAppPermission(){
        AndPermission.with(this)
                .runtime()
                .permission(Permission.WRITE_EXTERNAL_STORAGE,
                        Permission.CAMERA,
                        Permission.READ_PHONE_STATE)
                .rationale(new RuntimeRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        saveIMEI();
                        if(!PreferenceHelper.getIsLogin(HomeActivity.this)){
                            mHomePresenter.getUnLoginUid(PreferenceHelper.getIMEI(HomeActivity.this));
                        }
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        saveIMEI();
                        if(!PreferenceHelper.getIsLogin(HomeActivity.this)){
                            mHomePresenter.getUnLoginUid(PreferenceHelper.getIMEI(HomeActivity.this));
                        }
                    }
                })
                .start();

    }

    @Override
    public void onUpdateInfo(B8UpdateInfo b8UpdateInfo, boolean auto) {
        if(b8UpdateInfo != null && b8UpdateInfo.getData() != null && !b8UpdateInfo.getData().isIsNew()){
            check(b8UpdateInfo, true, b8UpdateInfo.getData().isIsForce(), false);
        }
    }

    @Override
    public void onUpdateInfoError(Throwable e) {

    }

    @Override
    public void onUnLoginUidInfo(UnLoginUidInfo unLoginUidInfo) {
        if(unLoginUidInfo != null){
            PreferenceHelper.setUid(this,String.valueOf(unLoginUidInfo.getUid()));
            Log.e("zy","uid = " + PreferenceHelper.getUid(this));
        }
    }

    private void saveIMEI(){
        if(!TextUtils.isEmpty(PreferenceHelper.getIMEI(getApplicationContext()))){
            return;
        }
        if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            String str = PhoneUtils.getIMEI();
            PreferenceHelper.setIMEI(this,str);
        }

    }

    private void check(final B8UpdateInfo b8UpdateInfo, final boolean hasUpdate, final boolean isForce, final boolean isSilent) {
        UpdateManager.create(this).setChecker(new IUpdateChecker() {
            @Override
            public void check(ICheckAgent agent, String url) {
                Log.e("ezy.update", "checking");
                agent.setInfo("");
            }
        }).setUrl("").setManual(true).setNotifyId(Constants.NOTIFY_ID).setParser(new IUpdateParser() {
            @Override
            public UpdateInfo parse(String source) throws Exception {
                UpdateInfo info = new UpdateInfo();
                info.hasUpdate = hasUpdate;
                info.updateContent = b8UpdateInfo.getData().getVersionDesc();
                info.versionCode = b8UpdateInfo.getData().getVersionCode();
                info.versionName = b8UpdateInfo.getData().getVersionName();
                info.url = b8UpdateInfo.getData().getApkUrl();
                info.md5 = b8UpdateInfo.getData().getApkMd5();
                info.size = b8UpdateInfo.getData().getApkSize();
                info.isForce = isForce;
                info.isIgnorable = false;
                info.isSilent = isSilent;
                return info;
            }
        }).check();
    }

}
