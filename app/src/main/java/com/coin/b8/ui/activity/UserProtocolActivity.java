package com.coin.b8.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.coin.b8.R;

/**
 * Created by zhangyi on 2018/6/8.
 */
public class UserProtocolActivity extends BaseActivity{
    private TextView mToolbarTitle;
    private Toolbar mToolbar;
    private TextView mUserProtocolContent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_protocol);
        mToolbar = findViewById(R.id.toolbar);
        mToolbarTitle = mToolbar.findViewById(R.id.toolbar_title);
        mUserProtocolContent = findViewById(R.id.protocol_content);
        initToolBar();
        mUserProtocolContent.setText(Html.fromHtml(getString(R.string.user_protocol_content)));
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
        mToolbarTitle.setText("用户协议");
    }
}
