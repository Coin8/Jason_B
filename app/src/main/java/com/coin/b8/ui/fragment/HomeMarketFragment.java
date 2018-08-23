package com.coin.b8.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.coin.b8.R;
import com.coin.b8.ui.activity.SearchActivity;
import com.coin.b8.ui.adapter.HomeMarketMainAdapter;
import com.coin.b8.utils.CommonUtils;
import com.coin.b8.utils.EventReportUtil;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyi on 2018/6/28.
 */
public class HomeMarketFragment extends BaseFragment{

    private ViewPager mViewPager;
    private SlidingTabLayout mTabLayout;
    private ImageView mSearchBtn;

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
        mTabLayout = view.findViewById(R.id.tablayout_market);
        mViewPager = view.findViewById(R.id.vp_market);
        mSearchBtn = view.findViewById(R.id.btn_search);

        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                EventReportUtil.marketTabClick(getContext(),position);
            }

            @Override
            public void onTabReselect(int position) {
                EventReportUtil.marketTabClick(getContext(),position);
            }
        });

        List<String> list = new ArrayList<>();
        list.add(getString(R.string.home_market_self));
        list.add(getString(R.string.home_market_value));
        list.add(getString(R.string.home_market_fire_coin));

        HomeMarketMainAdapter homeMarketMainAdapter = new HomeMarketMainAdapter(getFragmentManager(),list);
        mViewPager.setAdapter(homeMarketMainAdapter);
        mTabLayout.setViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(3);

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.startSearchActivity(v.getContext());
                CommonUtils.umengReport(getContext(),"hq_top_search_click");
            }
        });

    }



}
