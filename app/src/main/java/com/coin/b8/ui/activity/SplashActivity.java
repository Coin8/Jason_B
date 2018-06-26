package com.coin.b8.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.coin.b8.R;
import com.coin.b8.utils.StatusUtil;
import com.umeng.analytics.MobclickAgent;


/**
 * Created by zhangyi on 2018/5/25.
 */
public class SplashActivity extends AppCompatActivity {
    private ImageView mImageView;
    private MyCountDownTimer mMyCountDownTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.SplashThemeEnd);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StatusUtil.immersive(this);
        mImageView = findViewById(R.id.splash_image);
        mImageView.setImageResource(R.drawable.splash);
        mMyCountDownTimer = new MyCountDownTimer(2 * 1000);
        mMyCountDownTimer.start();

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
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            SplashActivity.this.finish();
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
