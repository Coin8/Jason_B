package com.coin.b8.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.coin.b8.R;

/**
 * Created by zhangyi on 2018/7/10.
 */
public class SearchActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    public static void startSearchActivity(Context context){
        if(context == null){
            return;
        }
        context.startActivity(new Intent(context,SearchActivity.class));
    }
}
