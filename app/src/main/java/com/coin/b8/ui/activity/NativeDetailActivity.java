package com.coin.b8.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.coin.b8.R;
import com.coin.b8.constant.Constants;
import com.coin.b8.help.DemoHelper;
import com.coin.b8.http.B8Api;
import com.coin.b8.model.B8UpdateInfo;
import com.coin.b8.model.FeedBackParameter;
import com.coin.b8.model.FeedBackResult;
import com.coin.b8.permission.RuntimeRationale;
import com.coin.b8.ui.dialog.CoinStoreDialog;
import com.coin.b8.ui.dialog.CoinStoreShareDialog;
import com.coin.b8.ui.dialog.FeedBackFragment;
import com.coin.b8.ui.dialog.LoadingDialog;
import com.coin.b8.ui.dialog.ShareDialogFragment;
import com.coin.b8.ui.iView.IMainView;
import com.coin.b8.ui.listen.ShareListen;
import com.coin.b8.ui.presenter.MainPresenterImpl;
import com.coin.b8.ui.view.BlankView;
import com.coin.b8.update.ICheckAgent;
import com.coin.b8.update.IUpdateChecker;
import com.coin.b8.update.IUpdateParser;
import com.coin.b8.update.UpdateInfo;
import com.coin.b8.update.UpdateManager;
import com.coin.b8.utils.AppUtil;
import com.coin.b8.utils.CommonUtils;
import com.coin.b8.utils.DialogUtil;
import com.coin.b8.utils.MyToast;
import com.coin.b8.utils.NetworkUtils;
import com.coin.b8.utils.PhoneUtils;
import com.coin.b8.wxapi.OnResponseListener;
import com.coin.b8.wxapi.WXShare;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by zhangyi on 2018/7/11.
 */
public class NativeDetailActivity extends BaseActivity implements View.OnClickListener, IMainView {

    private static final String TAG = DetailActivity.class.getSimpleName();
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadMessageForAndroid5;
    public static int FILECHOOSER_RESULTCODE = 1;
    public static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;

    private View mContentLayout;
    private WebView mWebView;
    private MainPresenterImpl mMainPresenter;

    private MyToast mToast;

    private WXShare mWXShare;

    private LinearLayout mLoading;

    private LoadingDialog mLoadingDialog;

    private BlankView mBlankView;

    private String mWebViewUrl;

    private boolean mIsLoadError = false;

    private CoinStoreDialog mCoinStoreDialog;

    @Override
    public void onUpdateInfo(B8UpdateInfo b8UpdateInfo, boolean auto) {
        if (b8UpdateInfo != null && b8UpdateInfo.getData() != null) {
            if (b8UpdateInfo.getData().isIsNew()) {
//                if (!auto) {
//                    mToast.showToast("已是最新版本");
//                }
            } else {
                check(b8UpdateInfo, true, b8UpdateInfo.getData().isIsForce(), false);
            }

        } else if (!auto) {
            //mToast.showToast("已是最新版本");
        }
    }

    @Override
    public void onUpdateInfoError(Throwable e) {

    }

    @Override
    public void onGetShareNetImageBitmap(int type,Bitmap bitmap) {
        hideShareLoading();
        if(mWXShare != null && bitmap != null){
            Log.e(TAG,"onGetShareNetImageBitmap ");
            mWXShare.shareImage(type,bitmap);
        }
        Log.e(TAG,"onGetShareNetImageBitmap 000 ");
    }

    @Override
    public void onGetShareNetImageBitmapError() {
        Log.e(TAG,"onGetShareNetImageBitmapError  ");
        hideShareLoading();
    }

    class JSInterface {

        @JavascriptInterface
        public void popUpView() {
            showCoinStoreDialog();
        }

        @JavascriptInterface
        public void share(String percent,String profit) {
            startCoinStoreShareDialog(percent,profit);
        }

        @JavascriptInterface
        public void closeView() {
            finish();
        }

        @JavascriptInterface
        public void shareWebUrl(String type,String title,String content, String webUrl) {
            if(!TextUtils.isEmpty(type) && mWXShare != null){
                int xx = Integer.parseInt(type);
                mWXShare.shareWeb(xx,title,content,webUrl);
            }
        }

        /**
         *
         * @param type 0 微信聊天，1 微信朋友圈
         * @param url
         */
        @JavascriptInterface
        public void shareImage(String type,String url) {
            if(!TextUtils.isEmpty(type) && !TextUtils.isEmpty(url)){
                showShareLoading();
                int xx = Integer.parseInt(type);
                mMainPresenter.getShareNetworkImage(xx,url);
            }else {
                Log.e(TAG,"file is null");
            }
        }

        @JavascriptInterface
        public void startBrowse(String url) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

        @JavascriptInterface
        public void logout() {
            startLogout();
        }

        @JavascriptInterface
        public void login(String loginName, String password) {
            startLogin(loginName, password);
        }

        @JavascriptInterface
        public void login() {
            startLogin();
        }

        @JavascriptInterface
        public void userProtocol() {
            startUserProtocol();
        }

        @JavascriptInterface
        public String getAppVersion() {
            return AppUtil.getVersionName();
        }

        @JavascriptInterface
        public int getVersionCode() {
            return AppUtil.getVersionCode();
        }

        @JavascriptInterface
        public void inviteFriend() {
            startShare();
        }

        @JavascriptInterface
        public void questionFeedback() {
            startFeedBack();
        }

        @JavascriptInterface
        public void systemSetting() {
            startSetting();
        }

        @JavascriptInterface
        public void checkUpdate() {
            startCheckUpdate();
        }

        @JavascriptInterface
        public String getIMSI() {
            String str = "";
            if (ActivityCompat.checkSelfPermission(NativeDetailActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                requestPhonePermission(1, Permission.READ_PHONE_STATE);
            } else {
                str = PhoneUtils.getIMSI();
            }
            Log.e(TAG, "getIMSI = " + str);
            return str;
        }

        @JavascriptInterface
        public String getIMEI() {
            String str = "";
            if (ActivityCompat.checkSelfPermission(NativeDetailActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                requestPhonePermission(2, Permission.READ_PHONE_STATE);
            } else {
                str = PhoneUtils.getIMEI();
            }
            Log.e(TAG, "getIMEI = " + str);
            return str;
        }

    }

    private void handleData(){
        Intent intent = getIntent();
        if(intent != null){
            mWebViewUrl = intent.getStringExtra("webUrl");
        }
    }

    public static void startNativeDetailActivity(Context context ,String url){
        if(context == null){
            return;
        }
        Intent intent = new Intent(context,NativeDetailActivity.class);
        intent.putExtra("webUrl",url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleData();
        mToast = new MyToast(this);
        setContentView(R.layout.activity_detail);
        initView();
        initNavigationView();
        initWebView();
        initShare();
        printScreen();
        mMainPresenter = new MainPresenterImpl(this, this);

        CommonUtils.umengReport(this,"trade_detail_exposure");

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleData();
        if(mWebView != null){
            mWebView.loadUrl(mWebViewUrl);
        }
        Log.e(TAG,"onNewIntent");
    }

    private void initShare() {
        /**
         * 微信分享
         */
        mWXShare = new WXShare(this,this);
        mWXShare.setListener(new OnResponseListener() {
            @Override
            public void onSuccess() {
                // 分享成功
            }

            @Override
            public void onCancel() {
                // 分享取消
            }

            @Override
            public void onFail(String message) {
                // 分享失败
            }
        });
    }

    private void initView() {
        mContentLayout = findViewById(R.id.content_layout);
        mWebView = findViewById(R.id.webView);
        mLoading = findViewById(R.id.layout_loading);
        mBlankView = findViewById(R.id.blank_view);
        mBlankView.setImageViewTye(BlankView.BLANK_WIFI);
        mBlankView.setEnableButton(true);
        mBlankView.setButtonText("点击刷新");
        mBlankView.setButtonOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mWebView != null){
                    mIsLoadError = false;
                    mWebView.reload();
                    mLoading.setVisibility(View.VISIBLE);
                    mBlankView.setVisibility(View.GONE);
//                    mWebView.setVisibility(View.VISIBLE);

                }
            }

        });

        mCoinStoreDialog = new CoinStoreDialog(getApplicationContext(),null);
        mCoinStoreDialog.setEnableSelfUpdate(true);
    }

    private void showCoinStoreDialog(){
        mCoinStoreDialog.showDialog(mContentLayout);
    }

    private void startCoinStoreShareDialog(String percent,String profit){
        Bundle bundle = new Bundle();
        bundle.putString("money",profit);
        bundle.putString("rate",percent);
        CoinStoreShareDialog dialog = new CoinStoreShareDialog();
        dialog.setArguments(bundle);
        dialog.setShareListen(new ShareListen() {
            @Override
            public void onClickWxChat(Bitmap bitmap) {
                if(mWXShare != null){
                    mWXShare.shareImage(0,bitmap);
                }
            }

            @Override
            public void onClickWxCircle(Bitmap bitmap) {
                if(mWXShare != null){
                    mWXShare.shareImage(1,bitmap);
                }
            }

            @Override
            public void onClickWeiBo(Bitmap bitmap) {
                if(mWXShare != null){
                    mWXShare.shareImage(2,bitmap);
                }
            }

            @Override
            public void onClickQq(Bitmap bitmap) {

            }
        });
        dialog.show(getSupportFragmentManager(),"shareDialog");
    }


    private void initWebView() {
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
        mSettings.setAppCacheEnabled(false);
        mSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }


        mWebView.addJavascriptInterface(new JSInterface(), "AndroidWebView");

        if(TextUtils.isEmpty(mWebViewUrl)){
            mWebViewUrl = Constants.BASEURL + "#/market";
        }

        mWebView.loadUrl(mWebViewUrl);
//        mWebView.loadUrl("https://test.doraemoney.com/newCube/SourceTestPage.html");
//        mWebView.loadUrl("file:///android_asset/index4.html");
//        mWebView.loadUrl("http://app.diyli.cn/static/share/index-url.html");

        //设置不用系统浏览器打开,直接显示在当前Webview
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "url = " + url);
                view.loadUrl(url);
                return true;
            }

            //设置加载前的函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                /**
                 * 开始加载
                 */
                Log.i(TAG, "onPageStarted url = " + url);
            }

            //设置结束加载函数
            @Override
            public void onPageFinished(WebView view, String url) {
                /**
                 * 结束加载了
                 */
                Log.i(TAG, "onPageFinished url = " + url);
                mLoading.setVisibility(View.GONE);
                if(!mIsLoadError){
                    mWebView.setVisibility(View.VISIBLE);
                }
            }

            /**
             * 加载错误的时候会回调，在其中可做错误处理，比如再请求加载一次，或者提示404的错误页面
             */
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e(TAG, "onReceivedError url = " + failingUrl + ", errorCode = " + errorCode);
                mLoading.setVisibility(View.GONE);
                mWebView.setVisibility(View.GONE);
                mBlankView.setVisibility(View.VISIBLE);
                if(NetworkUtils.isConnected()){
                    mBlankView.setImageViewTye(BlankView.BLANK_SELF);
                    mBlankView.setDesc("数据为空");
                }else {
                    mBlankView.setImageViewTye(BlankView.BLANK_WIFI);
                    mBlankView.setDesc("网络连接失败");
                }

                mIsLoadError = true;
            }

            /**
             * 当接收到https错误时，会回调此函数，在其中可以做错误处理
             */
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                Log.e(TAG, "onReceivedSslError url = " + error.getUrl());
                mLoading.setVisibility(View.GONE);
                mWebView.setVisibility(View.GONE);
                mBlankView.setVisibility(View.VISIBLE);
                if(NetworkUtils.isConnected()){
                    mBlankView.setImageViewTye(BlankView.BLANK_SELF);
                    mBlankView.setDesc("数据为空");
                }else {
                    mBlankView.setImageViewTye(BlankView.BLANK_WIFI);
                    mBlankView.setDesc("网络连接失败");
                }
                mIsLoadError = true;
            }
        });
        //设置WebChromeClient类
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

            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                Log.e(TAG, "< 3.0 openFileChooser");
                openFileChooserImpl(uploadMsg);
            }

            // For Android > 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                Log.e(TAG, "> 3.0 openFileChooser");
                openFileChooserImpl(uploadMsg);
            }

            // For Android > 4.1.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                        String acceptType, String capture) {
                Log.e(TAG, "> 4.1.1 openFileChooser");
                openFileChooserImpl(uploadMsg);
            }

            //For Android > 5.0支持多张上传
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                Log.e(TAG, "> 5.0 openFileChooser");

                mUploadMessageForAndroid5 = filePathCallback;
                requestPermission(Permission.Group.STORAGE);
                return true;
//                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
            }
        });
    }


    /**
     * @param type        1 imsi, 2 imei
     * @param permissions
     */
    private void requestPhonePermission(final int type, String... permissions) {
        AndPermission.with(this)
                .runtime()
                .permission(permissions)
                .rationale(new RuntimeRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        String str;
                        if (type == 1) {
                            str = PhoneUtils.getIMSI();
                        } else if (type == 2) {
                            str = PhoneUtils.getIMEI();
                        }
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
//                        toast(R.string.failure);
                        if (AndPermission.hasAlwaysDeniedPermission(NativeDetailActivity.this, permissions)) {
                            showSettingDialog(NativeDetailActivity.this, permissions);
                        }
                    }
                })
                .start();
    }


    /**
     * Request permissions.
     */
    private void requestPermission(String... permissions) {
        AndPermission.with(this)
                .runtime()
                .permission(permissions)
                .rationale(new RuntimeRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
//                        toast(R.string.successfully);
                        openFileChooserImplForAndroid5(mUploadMessageForAndroid5);
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
//                        toast(R.string.failure);
                        if (AndPermission.hasAlwaysDeniedPermission(NativeDetailActivity.this, permissions)) {
                            showSettingDialog(NativeDetailActivity.this, permissions);
                        }
                    }
                })
                .start();
    }


    private void openFileChooserImpl(ValueCallback<Uri> uploadMsg) {
        mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "文件选择"), FILECHOOSER_RESULTCODE);
    }

    private void openFileChooserImplForAndroid5(ValueCallback<Uri[]> uploadMsg) {
        mUploadMessageForAndroid5 = uploadMsg;
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("image/*");

        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "图片选择");
        startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
    }

    /**
     * 5.0以下 上传图片成功后的回调
     */
    private void uploadMessage(Intent intent, int resultCode) {
        if (null == mUploadMessage)
            return;
        Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
        mUploadMessage.onReceiveValue(result);
        mUploadMessage = null;
    }

    /**
     * 5.0以上 上传图片成功后的回调
     */
    private void uploadMessageForAndroid5(Intent intent, int resultCode) {
        if (null == mUploadMessageForAndroid5)
            return;
        Uri result = (intent == null || resultCode != RESULT_OK) ? null : intent.getData();
        if (result != null) {
            mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
        } else {
            mUploadMessageForAndroid5.onReceiveValue(new Uri[]{});
        }
        mUploadMessageForAndroid5 = null;
    }

    /**
     * 上传图片之后的回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            uploadMessage(intent, resultCode);
        } else if (requestCode == FILECHOOSER_RESULTCODE_FOR_ANDROID_5) {
            uploadMessageForAndroid5(intent, resultCode);
        }
    }

    private void initNavigationView() {

    }


    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mWXShare != null) {
            mWXShare.register();
        }
    }

    @Override
    protected void onDestroy() {

        if (mWXShare != null) {
            mWXShare.unregister();
        }

        if (mWebView != null) {
            mWebView.clearHistory();
            ViewGroup viewGroup = (ViewGroup) mWebView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(mWebView);
            }
            mWebView.destroy();
            mWebView = null;
        }
        if (mMainPresenter != null) {
            mMainPresenter.onDetach();
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.personal_info:
                break;
            case R.id.version_info:
                mToast.showToast("正在检测中...");
                break;
            case R.id.about_info:
                DialogUtil.showWelcomeDialog(this);
                break;
            case R.id.test1:
                startLogin("xiaoming", "123456");
                break;
            case R.id.test2:
                startLogin("CJu9Br2lwqnsY", "awa2o1fs34ha8i19y39i91g24e25ybbaf0n6f|e4y50aa3n4");
                break;
            case R.id.test3:
                startLogout();
                break;
        }
    }

    private void startCheckUpdate() {
        mMainPresenter.getUpdateInfo(false);
    }

    private void startUserProtocol() {
        Intent intent = new Intent(this, UserProtocolActivity.class);
        startActivity(intent);
    }

    private void startShare() {
        ShareDialogFragment shareDialogFragment = new ShareDialogFragment();
        shareDialogFragment.setShareListen(new ShareListen() {
            @Override
            public void onClickWxChat(Bitmap bitmap) {
                if (mWXShare != null) {
                    mWXShare.shareImage(0, R.drawable.share_bg);
                }
            }

            @Override
            public void onClickWxCircle(Bitmap bitmap) {
                if (mWXShare != null) {
                    mWXShare.shareImage(1, R.drawable.share_bg);
                }
            }

            @Override
            public void onClickWeiBo(Bitmap bitmap) {
                if (mWXShare != null) {
                    mWXShare.shareImage(2, R.drawable.share_bg);
                }
            }

            @Override
            public void onClickQq(Bitmap bitmap) {

            }
        });
        shareDialogFragment.show(getSupportFragmentManager(), "share");

    }

    private void startSetting() {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    private void startFeedBack() {
        FeedBackFragment feedBackFragment = new FeedBackFragment();
        feedBackFragment.setFeedBackInterface(new FeedBackFragment.FeedBackInterface() {
            @Override
            public void onPostQuestion(FeedBackParameter feedBackParameter) {

                if (feedBackParameter == null) {
                    return;
                }

                DisposableObserver<FeedBackResult> disposableObserver = new DisposableObserver<FeedBackResult>() {
                    @Override
                    public void onNext(FeedBackResult feedBackResult) {
                        mToast.showToast("提交反馈成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mToast.showToast("提交反馈失败");
                        Log.e(TAG, "feedback err = " + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                };
                B8Api.postFeedBackQuestion(disposableObserver, feedBackParameter.getType(), feedBackParameter.getContent(), feedBackParameter.getContact());

            }
        });
        feedBackFragment.show(getSupportFragmentManager(), "feedback");
    }

    private void startLogout() {

        DemoHelper.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
//                MySnackbar.makeSnackBarBlack(mNavigationView,"退出登录成功");
            }

            @Override
            public void onError(int i, String s) {
//                MySnackbar.makeSnackBarBlack(mNavigationView,"退出登录失败：" + s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }


    private void startLogin(){
        LoginActivity.startLoginActivity(this);
    }

    private void startLogin(String loginName, String password) {
        if (DemoHelper.getInstance().isLoggedIn()) {
            // MySnackbar.makeSnackBarBlack(mNavigationView, "已登录，请先退出登录");
            return;
        }

        EMClient.getInstance().login(loginName, password, new EMCallBack() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "login: onSuccess");

                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

            }

            @Override
            public void onProgress(int progress, String status) {
                Log.d(TAG, "login: onProgress");
            }

            @Override
            public void onError(final int code, final String message) {
                Log.d(TAG, "login: onError: " + code + "msg = " + message);
            }
        });
    }


    private void check(final B8UpdateInfo b8UpdateInfo, final boolean hasUpdate, final boolean isForce, final boolean isSilent) {
        UpdateManager.create(this).setChecker(new IUpdateChecker() {
            @Override
            public void check(ICheckAgent agent, String url) {
                Log.e("ezy.update", "checking");
                agent.setInfo("");
            }
        }).setUrl("").setManual(true).setNotifyId(Constants.NOTIFY_ID).setParser(new IUpdateParser() {
            @Override
            public UpdateInfo parse(String source) throws Exception {
                UpdateInfo info = new UpdateInfo();
                info.hasUpdate = hasUpdate;
                info.updateContent = b8UpdateInfo.getMessage();
//                info.versionCode = 587;
//                info.versionName = "v5.8.7";
                info.url = b8UpdateInfo.getData().getApkUrl();
//                info.url = "http://download.fir.im/v2/app/install/5a52e936ca87a8600e0002f9?download_token=cd8662357947f151de92975b46082ba6&source=update";
                info.md5 = b8UpdateInfo.getData().getApkMd5();
//                info.size = 10149314;
                info.isForce = isForce;
                info.isIgnorable = false;
                info.isSilent = isSilent;
                return info;
            }
        }).check();
    }

    private void showShareLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
        mLoadingDialog = new LoadingDialog();
        mLoadingDialog.show(getSupportFragmentManager(), "loading");

    }

    private void hideShareLoading(){
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }


}
