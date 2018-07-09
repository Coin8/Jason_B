package com.coin.b8.ui.fragment;

import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.coin.b8.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * Created by zhangyi on 2018/6/28.
 */
public class HomeDynamicQuickNewsFragment extends BaseFragment{

    private SmartRefreshLayout mSmartRefreshLayout;
    private RecyclerView mRecyclerView;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_dynamic_quick;
    }

    @Override
    protected void initView(View view) {
        mSmartRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRecyclerView = view.findViewById(R.id.recyclerview);

    }
}
