package com.coin.b8.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.coin.b8.R;

/**
 * Created by zhangyi on 2018/6/8.
 */
public class AboutUsActivity extends BaseActivity{

    private TextView mUerProtocol;
    private TextView mToolbarTitle;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        mToolbar = findViewById(R.id.toolbar);
        mToolbarTitle = mToolbar.findViewById(R.id.toolbar_title);
        mUerProtocol = findViewById(R.id.user_protocol);
        initToolBar();

        mUerProtocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutUsActivity.this,UserProtocolActivity.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolBar(){
        initToolBar(mToolbar,"",R.drawable.b8_ic_back_black);
        mToolbarTitle.setText("关于我们");
    }
}
