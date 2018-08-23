package com.coin.b8.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.coin.b8.R;
import com.coin.b8.help.PreferenceHelper;
import com.coin.b8.ui.adapter.GuideAdapter;
import com.coin.b8.utils.StatusUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhangyi on 2018/5/25.
 */
public class SplashActivity extends AppCompatActivity {
    private ImageView mImageView;
    private MyCountDownTimer mMyCountDownTimer;
    private ViewPager mViewPager;
    private int [] mImageIdArray;
    private List<View> mViewList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.SplashThemeEnd);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StatusUtil.immersive(this);
        mImageView = findViewById(R.id.splash_image);
        mImageView.setImageResource(R.drawable.splash);
        if(PreferenceHelper.getFirstStart(this)){
            initViewPager();
        }else {
            mMyCountDownTimer = new MyCountDownTimer(2 * 1000);
            mMyCountDownTimer.start();
        }
    }

    private void initViewPager() {
        mImageView.setVisibility(View.GONE);

        mViewPager = (ViewPager) findViewById(R.id.guide_vp);
        mViewPager.setVisibility(View.VISIBLE);
        //实例化图片资源
        mImageIdArray = new int[]{
                R.drawable.guide1,
                R.drawable.guide2,
                R.drawable.guide3
        };
        mViewList = new ArrayList<>();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        int len = mImageIdArray.length;
        for (int i = 0;i<len;i++){
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(mImageIdArray[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mViewList.add(imageView);
            if(i == len -1){
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startHomeActivity(true);
                    }
                });
            }

        }
        mViewPager.setAdapter(new GuideAdapter(mViewList));
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture) {
            super(millisInFuture, 1000);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        /**
         * Execute different action according to tagForCountDownTimer when countdown timer is done.
         */
        @Override
        public void onFinish() {
            startHomeActivity(false);
        }
    }

    private void startHomeActivity(boolean isFirst){
        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        SplashActivity.this.finish();
        if(isFirst){
            PreferenceHelper.setFirstStart(this,false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMyCountDownTimer != null) {
            mMyCountDownTimer.cancel();
            mMyCountDownTimer = null;
        }
    }
}
