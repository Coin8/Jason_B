package com.coin.b8.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.coin.b8.R;

/**
 * Created by zhangyi on 2018/6/28.
 */
public class HomeMineFragment extends BaseFragment{

    public static HomeMineFragment getInstance() {
        HomeMineFragment fragment = new HomeMineFragment();
//        Bundle args = new Bundle();
//        args.putBoolean(Constants.ARG_PARAM1, param1);
//        args.putString(Constants.ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_mine;
    }

    @Override
    protected void initView(View view) {

    }
}
