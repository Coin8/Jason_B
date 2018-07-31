package com.coin.b8.ui.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.coin.b8.R;
import com.coin.b8.ui.activity.SearchActivity;
import com.coin.b8.ui.adapter.HomeDynamicMainAdapter;
import com.coin.b8.utils.CommonUtils;
import com.coin.b8.utils.EventReportUtil;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyi on 2018/6/28.
 */
public class HomeDynamicFragment extends BaseFragment{

    private ViewPager mViewPager;
    private SlidingTabLayout mTabLayout;
    private ImageView mSearchImageView;
    private boolean mIsViewInit = false;

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
        mSearchImageView = view.findViewById(R.id.btn_search);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                EventReportUtil.dynamicTabClick(getContext(),position);
            }

            @Override
            public void onTabReselect(int position) {
                EventReportUtil.dynamicTabClick(getContext(),position);
            }
        });
        mSearchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.startSearchActivity(v.getContext());
            }
        });

        List<String> list = new ArrayList<>();
        list.add(getString(R.string.home_dynamic_quick_news));
        list.add(getString(R.string.home_dynamic_important_news));
        HomeDynamicMainAdapter homeDynamicMainAdapter = new HomeDynamicMainAdapter(getFragmentManager(),list);
        mViewPager.setAdapter(homeDynamicMainAdapter);
        mTabLayout.setViewPager(mViewPager);
        mIsViewInit = true;
    }

    public void setQuickFragment(){
        if(mIsViewInit){
            mTabLayout.setCurrentTab(0);
        }
    }
}
