package com.coin.b8.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.coin.b8.R;
import com.coin.b8.help.BottomNavigationViewHelper;
import com.coin.b8.permission.RuntimeRationale;
import com.coin.b8.ui.fragment.BaseFragment;
import com.coin.b8.ui.fragment.HomeDynamicFragment;
import com.coin.b8.ui.fragment.HomeMarketFragment;
import com.coin.b8.ui.fragment.HomeMineFragment;
import com.coin.b8.ui.fragment.HomeSelectCoinFragment;
import com.coin.b8.utils.MyToast;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyi on 2018/6/27.
 */
public class HomeActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mFragments = new ArrayList<>();
        initFragment();
        initBottomNavigationView();
        mToast = new MyToast(this);
        requestAppPermission();
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
                        Permission.CAMERA)
                .rationale(new RuntimeRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {

                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(HomeActivity.this, permissions)) {

                        }
                    }
                })
                .start();

    }

}
