package com.coin.b8.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.coin.b8.R;
import com.coin.b8.help.DemoHelper;
import com.coin.b8.help.PreferenceHelper;
import com.coin.b8.model.LoginResponseInfo;
import com.coin.b8.permission.RuntimeRationale;
import com.coin.b8.ui.dialog.LoadingDialog;
import com.coin.b8.ui.iView.ILoginView;
import com.coin.b8.ui.presenter.LoginPresenterImpl;
import com.coin.b8.ui.view.EditTextClear;
import com.coin.b8.utils.ActivityManagerUtil;
import com.coin.b8.utils.CommonUtils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by zhangyi on 2018/8/15.
 */
public class LoginForPhoneCodeActivity extends BaseActivity implements View.OnClickListener,ILoginView{

    private TextView mToast;

    private static final int MESSAGE_DISMISS = 100;
    private static final int MESSAGE_DELAY_TIME = 2000;

    private TextView mTitle;
    private TextView mLoginBtn;
    private LoginPresenterImpl mLoginPresenter;
    private EditTextClear mAccountEdit;
    private LoadingDialog mLoadingDialog;
    private EditTextClear mVerifyCodeEdit;
    private TextView mBtnVerifyCode;
    private boolean mCodeIsSend = false;
    private MyCountDownTimer mMyCountDownTimer;


    private class MyHandler extends Handler {
        private WeakReference<LoginForPhoneCodeActivity> mWeakReference;
        public MyHandler(LoginForPhoneCodeActivity activity) {
            mWeakReference = new WeakReference<LoginForPhoneCodeActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            LoginForPhoneCodeActivity activity = mWeakReference.get();
            if(activity == null){
                return;
            }
            switch (msg.what){
                case MESSAGE_DISMISS:
                    activity.hideToast();
                    break;
            }
        }
    }

    private MyHandler mMyHandler;

    public void showToast(String text){
        mToast.setVisibility(View.VISIBLE);
        mToast.setText(text);
        mMyHandler.removeMessages(MESSAGE_DISMISS);
        mMyHandler.sendEmptyMessageDelayed(MESSAGE_DISMISS,MESSAGE_DELAY_TIME);
    }

    public void hideToast(){
        mToast.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_for_phone_code);
        mMyHandler = new MyHandler(this);
        mLoginPresenter = new LoginPresenterImpl(this);
        initView();
        requestPhonePermission();
    }

    private void initView(){
        mToast = findViewById(R.id.toast_text);
        setInitToolBar("");
        mTitle = findViewById(R.id.content_title);
        mTitle .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mAccountEdit = findViewById(R.id.login_account_edit);
        mAccountEdit.setLeftIcon(R.drawable.username_icon);
        mVerifyCodeEdit = findViewById(R.id.verify_confirm_edit);
        mVerifyCodeEdit.setLeftIcon(R.drawable.verify_icon);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkCanLogin();
            }
        };



        mAccountEdit.addTextChangedListener(textWatcher);
        mVerifyCodeEdit.addTextChangedListener(textWatcher);

        mAccountEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    return;
                }
                String user = mAccountEdit.getText().toString();
                if(TextUtils.isEmpty(user)){
                    showToast("手机号不能为空");
                    return;
                }
                if(!CommonUtils.isMobileSimple(user)){
                    showToast("手机号格式错误");
                    return;
                }

            }
        });

        mLoginBtn = findViewById(R.id.login_btn);
        mLoginBtn.setOnClickListener(this);
        mBtnVerifyCode = findViewById(R.id.verify_confirm_btn);
        mBtnVerifyCode.setOnClickListener(this);
    }

    private void checkCanLogin(){
        String user = mAccountEdit.getText().toString();
        String code = mVerifyCodeEdit.getText().toString();
        if(TextUtils.isEmpty(user) || TextUtils.isEmpty(code)){
            mLoginBtn.setBackgroundResource(R.drawable.corner_bg_light_personsal);
        }else {
            mLoginBtn.setBackgroundResource(R.drawable.feedback_btn_bg);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.onDetach();
        if(mMyCountDownTimer != null){
            mMyCountDownTimer.cancel();
            mMyCountDownTimer = null;
        }
        mMyHandler.removeMessages(MESSAGE_DISMISS);
        mMyHandler.removeCallbacksAndMessages(null);
    }

    private void requestPhonePermission(){
        AndPermission.with(this)
                .runtime()
                .permission(Permission.READ_PHONE_STATE)
                .rationale(new RuntimeRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        saveIMEI();
                        CommonUtils.getUnLoginUid();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        saveIMEI();
                        CommonUtils.getUnLoginUid();
                        if (AndPermission.hasAlwaysDeniedPermission(LoginForPhoneCodeActivity.this, permissions)) {
                            showSettingDialog(LoginForPhoneCodeActivity.this, permissions);
                        }
                    }
                })
                .start();

    }

    @Override
    public void onClick(View v) {
        if(v == null){
            return;
        }
        String email;
        String code;

        switch (v.getId()){

            case R.id.verify_confirm_btn:

                if(mCodeIsSend){
                    return;
                }
                email = mAccountEdit.getText().toString();
                if(TextUtils.isEmpty(email)){
                    showToast("手机号不能为空");
                    return;
                }
                if(!CommonUtils.isMobileSimple(email)){
                    showToast("手机号格式错误");
                    return;
                }
                showLoading("正在发送");
                mLoginPresenter.sendVerifyCode(null,email,3);
                mCodeIsSend = true;
                mMyCountDownTimer = new MyCountDownTimer(60*1000);
                mMyCountDownTimer.start();

                break;
            case R.id.login_btn:
                if(!checkPhonePermission()){
                    return;
                }
                email = mAccountEdit.getText().toString();
                code = mVerifyCodeEdit.getText().toString();
                if(TextUtils.isEmpty(email)){
                    showToast("手机号不能为空");
                    return;
                }
                if(!CommonUtils.isMobileSimple(email)){
                    showToast("手机号格式错误");
                    return;
                }
                if(TextUtils.isEmpty(code)){
                    showToast("验证码不能为空");
                    return;
                }
                showLoading("正在登录");
                mLoginPresenter.getLoginInfo(null,code,email,"2");
                break;
        }
    }

    private boolean checkPhonePermission(){
        boolean value = AndPermission.hasPermissions(this,Permission.READ_PHONE_STATE);
        if(!value){
            requestPhonePermission();
        }
        return value;
    }


    private void hideLoading(){
        if(mLoadingDialog != null){
            mLoadingDialog.dismiss();
        }
    }
    private void showLoading(String text){
        mLoadingDialog = new LoadingDialog();
        mLoadingDialog.setLoadingText(text);
        mLoadingDialog.show(getSupportFragmentManager(),"login");
    }

    @Override
    public void onVerifyCodeSuccess() {
        hideLoading();
        showToast("发送成功");
    }

    @Override
    public void onVerifyCodeFail(String message) {
        hideLoading();
        showToast(message);
    }

    @Override
    public void onLoginSuccess(LoginResponseInfo loginResponseInfo) {
        hideLoading();
        if(loginResponseInfo != null){
            if(loginResponseInfo.isResult()
                    && loginResponseInfo.getData() != null
                    && !TextUtils.isEmpty(loginResponseInfo.getData().getToken())){
                PreferenceHelper.saveLoinInfo(this,loginResponseInfo);
                if(DemoHelper.getInstance().isLoggedIn()){
                    DemoHelper.getInstance().logout();
                }
                DemoHelper.getInstance().login(loginResponseInfo.getData().getEasename(),
                        loginResponseInfo.getData().getPassword());
                showToast("登录成功");
                Activity activity = ActivityManagerUtil.getSecTopActivity();
                if(activity != null && activity instanceof LoginActivity){
                    activity.finish();
                }
                finish();
            }else {
                showToast(loginResponseInfo.getMessage());
            }
        }
    }

    @Override
    public void onLoginFail() {
        hideLoading();
        showToast("登录失败");
    }

    private class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisInFuture) {
            super(millisInFuture, 1000);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            String s = "重新发送(" + millisUntilFinished/1000 + ")";
            mBtnVerifyCode.setText(s);
        }

        @Override
        public void onFinish() {
            mBtnVerifyCode.setText("重新发送");
            mCodeIsSend = false;
        }
    }

    public static void startLoginForPhoneCodeActivity(Context context){
        if(context == null){
            return;
        }
        context.startActivity(new Intent(context,LoginForPhoneCodeActivity.class));
    }
}
