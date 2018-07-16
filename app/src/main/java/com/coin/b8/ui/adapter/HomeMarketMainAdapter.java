package com.coin.b8.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.coin.b8.ui.fragment.HomeMarketFireCoinFragment;
import com.coin.b8.ui.fragment.HomeMarketSelfFragment;
import com.coin.b8.ui.fragment.HomeMarketValueFragment;

import java.util.List;

/**
 * Created by zhangyi on 2018/7/11.
 */
public class HomeMarketMainAdapter extends FragmentPagerAdapter {

    private Fragment[] mFragments;
    private List<String> mStringList;

    public HomeMarketMainAdapter(FragmentManager fm, List<String> list) {
        super(fm);
        this.mStringList = list;
        if(mStringList != null){
            mFragments = new Fragment[list.size()];
        }
    }

    @Override
    public Fragment getItem(int position) {
        if (mFragments[position] == null) {
            switch (position) {
                case 0:
                    mFragments[position] = new HomeMarketSelfFragment();
                    break;
                case 1:
                    mFragments[position] = new HomeMarketValueFragment();
                    break;
                case 2:
                    mFragments[position] = new HomeMarketFireCoinFragment();
                    break;
            }
        }
        return mFragments[position];
    }

    @Override
    public int getCount() {
        if(mStringList != null){
            return mStringList.size();
        }
        return 0;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mStringList.get(position);
    }
}
