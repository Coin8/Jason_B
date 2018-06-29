package com.coin.b8.ui.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import com.coin.b8.R;
import com.coin.b8.ui.adapter.HomeDynamicMainAdapter;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyi on 2018/6/28.
 */
public class HomeDynamicFragment extends BaseFragment{

    private ViewPager mViewPager;
    private SlidingTabLayout mTabLayout;
    public static HomeDynamicFragment getInstance() {
        HomeDynamicFragment fragment = new HomeDynamicFragment();
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_dynamic;
    }

    @Override
    protected void initView(View view) {

        mTabLayout = view.findViewById(R.id.tablayout_dynamic);
        mViewPager = view.findViewById(R.id.vp_dynamic);
        List<String> list = new ArrayList<>();
        list.add(getString(R.string.home_dynamic_quick_news));
        list.add(getString(R.string.home_dynamic_important_news));
        HomeDynamicMainAdapter homeDynamicMainAdapter = new HomeDynamicMainAdapter(getFragmentManager(),list);
        mViewPager.setAdapter(homeDynamicMainAdapter);
        mTabLayout.setViewPager(mViewPager);
    }
}
