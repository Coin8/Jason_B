package com.coin.b8.ui.fragment;

import android.view.View;

import com.coin.b8.R;

/**
 * Created by zhangyi on 2018/6/28.
 */
public class HomeSelectCoinFragment extends BaseFragment{

    public static HomeSelectCoinFragment getInstance() {
        HomeSelectCoinFragment fragment = new HomeSelectCoinFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_select_coin;
    }

    @Override
    protected void initView(View view) {

    }
}
