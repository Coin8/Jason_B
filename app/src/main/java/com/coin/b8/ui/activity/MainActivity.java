package com.coin.b8.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.coin.b8.R;
import com.coin.b8.constant.Constants;
import com.coin.b8.help.DemoHelper;
import com.coin.b8.http.B8Api;
import com.coin.b8.model.B8UpdateInfo;
import com.coin.b8.model.FeedBackParameter;
import com.coin.b8.model.FeedBackResult;
import com.coin.b8.ui.dialog.FeedBackFragment;
import com.coin.b8.ui.dialog.LoadingDialog;
import com.coin.b8.ui.dialog.ShareDialogFragment;
import com.coin.b8.ui.listen.ShareListen;
import com.coin.b8.ui.presenter.MainPresenterImpl;
import com.coin.b8.permission.RuntimeRationale;
import com.coin.b8.ui.iView.IMainView;
import com.coin.b8.update.ICheckAgent;
import com.coin.b8.update.IUpdateChecker;
import com.coin.b8.update.IUpdateParser;
import com.coin.b8.update.UpdateInfo;
import com.coin.b8.update.UpdateManager;
import com.coin.b8.utils.AppUtil;
import com.coin.b8.utils.DialogUtil;
import com.coin.b8.utils.MySnackbar;
import com.coin.b8.utils.MyToast;
import com.coin.b8.utils.PhoneUtils;
import com.coin.b8.wxapi.OnResponseListener;
import com.coin.b8.wxapi.WXShare;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Setting;

import java.io.File;
import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class MainActivity extends BaseActivity implements View.OnClickListener, IMainView {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadMessageForAndroid5;
    public static int FILECHOOSER_RESULTCODE = 1;
    public static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private WebView mWebView;
    private TextView mVersionName;
    private View mPersonInfoLayout;
    private View mVersionInfoLayout;
    private View mAboutInfoLayout;
    private View mTestView1;
    private View mTestView2;
    private View mTestView3;
    private MainPresenterImpl mMainPresenter;
    private String mCacheZize = "0.00B";

    private MyToast mToast;

    private WXShare mWXShare;

    private LinearLayout mLoading;

    private LoadingDialog mLoadingDialog;


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
        public void shareWebUrl(String type,String title,String content, String webUrl) {
            if(!TextUtils.isEmpty(type) && mWXShare != null){
                int xx = Integer.parseInt(type);
                mWXShare.shareWeb(xx,title,content,webUrl);
            }
        }
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
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
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
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                requestPhonePermission(2, Permission.READ_PHONE_STATE);
            } else {
                str = PhoneUtils.getIMEI();
            }
            Log.e(TAG, "getIMEI = " + str);
            return str;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToast = new MyToast(this);
        mMainPresenter = new MainPresenterImpl(this, this);
        UpdateManager.setDebuggable(true);
        UpdateManager.setWifiOnly(false);
        UpdateManager.setUrl("b8", "lll");
        setContentView(R.layout.activity_main);
        initView();
        initNavigationView();
        initWebView();
        mMainPresenter.getUpdateInfo(true);
        initShare();
        printScreen();
    }

    private void initShare() {
        /**
         * 微信分享
         */
        mWXShare = new WXShare(this);
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
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mNavigationView = findViewById(R.id.navigationView);
        mWebView = findViewById(R.id.webView);
        mLoading = findViewById(R.id.layout_loading);
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
        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }


        mWebView.addJavascriptInterface(new JSInterface(), "AndroidWebView");

        mWebView.loadUrl(Constants.BASEURL + "#/market");
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
            }

            /**
             * 加载错误的时候会回调，在其中可做错误处理，比如再请求加载一次，或者提示404的错误页面
             */
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e(TAG, "onReceivedError url = " + failingUrl + ", errorCode = " + errorCode);
                mLoading.setVisibility(View.GONE);
            }

            /**
             * 当接收到https错误时，会回调此函数，在其中可以做错误处理
             */
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                Log.e(TAG, "onReceivedSslError url = " + error.getUrl());
                mLoading.setVisibility(View.GONE);
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
                        if (AndPermission.hasAlwaysDeniedPermission(MainActivity.this, permissions)) {
                            showSettingDialog(MainActivity.this, permissions);
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
                        if (AndPermission.hasAlwaysDeniedPermission(MainActivity.this, permissions)) {
                            showSettingDialog(MainActivity.this, permissions);
                        }
                    }
                })
                .start();
    }

    public void showSettingDialog(Context context, final List<String> permissions) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.message_permission_always_failed, TextUtils.join("\n", permissionNames));

        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(R.string.title_dialog)
                .setMessage(message)
                .setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setPermission();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    /**
     * Set permissions.
     */
    private void setPermission() {
        AndPermission.with(this)
                .runtime()
                .setting()
                .onComeback(new Setting.Action() {
                    @Override
                    public void onAction() {
                        Toast.makeText(MainActivity.this, R.string.message_setting_comeback, Toast.LENGTH_SHORT).show();
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
        View headView = mNavigationView.getHeaderView(0);
        mVersionName = headView.findViewById(R.id.version_name);
        mVersionName.setText("v" + AppUtil.getVersionName());
        mPersonInfoLayout = headView.findViewById(R.id.personal_info);
        mVersionInfoLayout = headView.findViewById(R.id.version_info);
        mAboutInfoLayout = headView.findViewById(R.id.about_info);
        mPersonInfoLayout.setOnClickListener(this);
        mVersionInfoLayout.setOnClickListener(this);
        mAboutInfoLayout.setOnClickListener(this);
        /**
         * 屏蔽抽屉手势滑动
         */
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mTestView1 = headView.findViewById(R.id.test1);
        mTestView2 = headView.findViewById(R.id.test2);
        mTestView3 = headView.findViewById(R.id.test3);
        mTestView1.setOnClickListener(this);
        mTestView2.setOnClickListener(this);
        mTestView3.setOnClickListener(this);
    }


    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawers();
                return true;
            }
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
//                startLogin("CJu9Br2lwqnsY","awa2o1fs34ha8i19y39i91g24e25ybbaf0n6f|e4y50aa3n4");
//                startLogin("xiaoming","123456");
//                startFeedBack();
//                startShare();
//                Intent intent = new Intent(this, SettingActivity.class);
//                startActivity(intent);
//                mDrawerLayout.closeDrawers();
                break;
            case R.id.version_info:
//                mDrawerLayout.closeDrawers();
                mToast.showToast("正在检测中...");
                break;
            case R.id.about_info:
//                mDrawerLayout.closeDrawers();
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
//        MyToast.showShortToast("正在检测中...");
    }

    private void startUserProtocol() {
        Intent intent = new Intent(this, UserProtocolActivity.class);
        startActivity(intent);
    }

    private void startShare() {
        ShareDialogFragment shareDialogFragment = new ShareDialogFragment();
        shareDialogFragment.setShareListen(new ShareListen() {
            @Override
            public void onClickWxChat() {
                if (mWXShare != null) {
                    mWXShare.shareImage(0, R.drawable.share_bg);
                }
            }

            @Override
            public void onClickWxCircle() {
                if (mWXShare != null) {
                    mWXShare.shareImage(1, R.drawable.share_bg);
                }
            }

            @Override
            public void onClickWeiBo() {

            }

            @Override
            public void onClickQq() {

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


    private void startLogin(String loginName, String password) {
        if (DemoHelper.getInstance().isLoggedIn()) {
            // MySnackbar.makeSnackBarBlack(mNavigationView, "已登录，请先退出登录");
            return;
        }

        EMClient.getInstance().login(loginName, password, new EMCallBack() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "login: onSuccess");

                //MySnackbar.makeSnackBarBlack(mNavigationView,"登录成功");

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
                //MySnackbar.makeSnackBarBlack(mNavigationView,"登录失败：" + message);
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
                info.updateContent = b8UpdateInfo.getData().getVersionDesc();
                info.versionCode = b8UpdateInfo.getData().getVersionCode();
                info.versionName = b8UpdateInfo.getData().getVersionName();
                info.url = b8UpdateInfo.getData().getApkUrl();
                info.md5 = b8UpdateInfo.getData().getApkMd5();
                info.size = b8UpdateInfo.getData().getApkSize();
                info.isForce = isForce;
                info.isIgnorable = false;
                info.isSilent = isSilent;
                return info;
            }
        }).check();
    }

    private void showShareLoading() {
        if (mLoadingDialog != null && mLoadingDialog.getDialog() != null && mLoadingDialog.getDialog().isShowing()) {
            mLoadingDialog.dismiss();
        }
        mLoadingDialog = new LoadingDialog();
        mLoadingDialog.show(getSupportFragmentManager(), "loading");

    }


    private void hideShareLoading(){
        if (mLoadingDialog != null && mLoadingDialog.getDialog().isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

}
