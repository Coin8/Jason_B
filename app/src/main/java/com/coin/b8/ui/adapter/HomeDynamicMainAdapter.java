package com.coin.b8.ui.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.coin.b8.ui.fragment.HomeDynamicImportant;
import com.coin.b8.ui.fragment.HomeDynamicQuickNewsFragment;

import java.util.List;

/**
 * Created by zhangyi on 2018/6/28.
 */
public class HomeDynamicMainAdapter extends FragmentPagerAdapter {
    private Fragment[] mFragments;
    private List<String> mStringList;
    public HomeDynamicMainAdapter(FragmentManager fm, List<String> list) {
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
                    mFragments[position] = new HomeDynamicQuickNewsFragment();
                    break;
                case 1:
                    mFragments[position] = new HomeDynamicImportant();
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
