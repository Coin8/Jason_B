package com.coin.b8.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coin.b8.R;
import com.coin.b8.constant.Constants;
import com.coin.b8.utils.MyToast;

public class UserProtocolActivity extends BaseActivity {
    private MyToast mToast;
    private LinearLayout mLoading;
    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_protocol);
        mToast = new MyToast(this);
        mLoading = findViewById(R.id.layout_loading);
        mWebView = findViewById(R.id.webView);
        setInitToolBar("用户协议");
        if (!this.isNetworkConnected()) {
            mToast.showToast(getString(R.string.network_disconnect));
        } else {
            this.initWeb();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                finish();
                //  return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initWeb() {
        mLoading.setVisibility(View.VISIBLE);
        WebSettings mSettings = mWebView.getSettings();
        mSettings.setJavaScriptEnabled(true);//开启javascript
        mSettings.setDomStorageEnabled(true);//开启DOM
        mSettings.setDefaultTextEncodingName("utf-8");//设置字符编码
        //设置web页面
        mSettings.setUseWideViewPort(true);// 调整到适合webview大小
        mSettings.setLoadWithOverviewMode(true);// 调整到适合webview大小
        mSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);// 屏幕自适应网页
        mSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        mSettings.setJavaScriptEnabled(true);

        mWebView.loadUrl(Constants.BASEURL + "static/agreement.html");

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            //设置加载前的函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                /**
                 * 开始加载
                 */
            }

            //设置结束加载函数
            @Override
            public void onPageFinished(WebView view, String url) {
                /**
                 * 结束加载了
                 */
                mLoading.setVisibility(View.GONE);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                /**
                 * 获取网站标题
                 */
            }

            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                /**
                 * 获取加载进度
                 */
                if (newProgress >= 99) {
                    mLoading.setVisibility(View.GONE);
                }
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        }
        return false;
    }
}
