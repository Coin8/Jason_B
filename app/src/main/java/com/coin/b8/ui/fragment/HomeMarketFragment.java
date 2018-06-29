package com.coin.b8.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coin.b8.R;

/**
 * Created by zhangyi on 2018/6/28.
 */
public class HomeMarketFragment extends BaseFragment{

    public static HomeMarketFragment getInstance() {
        HomeMarketFragment fragment = new HomeMarketFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_market;
    }

    @Override
    protected void initView(View view) {

    }

}
