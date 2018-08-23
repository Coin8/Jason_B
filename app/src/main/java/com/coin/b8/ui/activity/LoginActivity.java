package com.coin.b8.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.coin.b8.R;
import com.coin.b8.help.DemoHelper;
import com.coin.b8.help.PreferenceHelper;
import com.coin.b8.model.LoginResponseInfo;
import com.coin.b8.permission.RuntimeRationale;
import com.coin.b8.ui.dialog.LoadingDialog;
import com.coin.b8.ui.iView.ILoginView;
import com.coin.b8.ui.presenter.LoginPresenterImpl;
import com.coin.b8.ui.view.EditTextClear;
import com.coin.b8.utils.CommonUtils;
import com.coin.b8.utils.MyToast;
import com.coin.b8.utils.ToastUtil;
import com.google.gson.Gson;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by zhangyi on 2018/7/3.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener,ILoginView{
    private TextView mForgetTitle;
    private TextView mForgetBtn;
    private TextView mLoginBtn;
    private TextView mLoginPhoneCodeBtn;
    private LoginPresenterImpl mLoginPresenter;
    private EditTextClear mAccountEdit;
    private EditTextClear mPasswordEdit;
    private LoadingDialog mLoadingDialog;
    private TextView mToast;

    private static final int MESSAGE_DISMISS = 100;
    private static final int MESSAGE_DELAY_TIME = 2000;

    private class MyHandler extends Handler {
        private WeakReference<LoginActivity> mWeakReference;
        public MyHandler(LoginActivity activity) {
            mWeakReference = new WeakReference<LoginActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            LoginActivity activity = mWeakReference.get();
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
        setContentView(R.layout.activity_login);
        mMyHandler = new MyHandler(this);
        mLoginPresenter = new LoginPresenterImpl(this);
        initView();
        requestPhonePermission();
    }

    private void initView(){
        mToast = findViewById(R.id.toast_text);
        setInitToolBar("");
        mToolbarRightTitle.setVisibility(View.VISIBLE);
        mToolbarRightTitle.setText("快速注册");
        mToolbarRightTitle.setOnClickListener(this);
        mForgetTitle = findViewById(R.id.content_title);
        mForgetTitle .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mForgetBtn = findViewById(R.id.forget_password);
        mForgetBtn.setOnClickListener(this);
        mAccountEdit = findViewById(R.id.login_account_edit);
        mPasswordEdit = findViewById(R.id.login_password_edit);

        mAccountEdit.setLeftIcon(R.drawable.username_icon);
        mPasswordEdit.setLeftIcon(R.drawable.password_icon);

        mAccountEdit.addTextChangedListener(new TextWatcher() {
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
        });

        mPasswordEdit.addTextChangedListener(new TextWatcher() {
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
        });

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

        mPasswordEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    return;
                }
                String user = mPasswordEdit.getText().toString();
                if(TextUtils.isEmpty(user)){
                    showToast("密码不能为空");
                    return;
                }
            }
        });

        mLoginBtn = findViewById(R.id.login_btn);
        mLoginBtn.setOnClickListener(this);

        mLoginPhoneCodeBtn = findViewById(R.id.login_for_phone_code);
        mLoginPhoneCodeBtn.setOnClickListener(this);
    }


    private void checkCanLogin(){
        String user = mAccountEdit.getText().toString();
        String password = mPasswordEdit.getText().toString();
        if(TextUtils.isEmpty(user) || TextUtils.isEmpty(password)){
            mLoginBtn.setBackgroundResource(R.drawable.corner_bg_light_personsal);
        }else {
            mLoginBtn.setBackgroundResource(R.drawable.feedback_btn_bg);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.onDetach();
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
                        if (AndPermission.hasAlwaysDeniedPermission(LoginActivity.this, permissions)) {
                            showSettingDialog(LoginActivity.this, permissions);
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
        String password;

        switch (v.getId()){
            case R.id.right_title:
                if(!checkPhonePermission()){
                    return;
                }
                startRegister();
                break;
            case R.id.login_for_phone_code:
                if(!checkPhonePermission()){
                    return;
                }
                LoginForPhoneCodeActivity.startLoginForPhoneCodeActivity(this);
                break;
            case R.id.login_btn:
                if(!checkPhonePermission()){
                    return;
                }
                email = mAccountEdit.getText().toString();
                password = mPasswordEdit.getText().toString();
                if(TextUtils.isEmpty(email)){
                    showToast("手机号不能为空");
                    return;
                }
                if(!CommonUtils.isMobileSimple(email)){
                    showToast("手机号格式错误");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    showToast("密码不能为空");
                    return;
                }
                showLoading("正在登录");
                mLoginPresenter.getLoginInfo(null,password,email,"1");
                break;
            case R.id.forget_password:
                if(!checkPhonePermission()){
                    return;
                }
                startForgetPassword();
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

    private void startForgetPassword(){
        Intent intent = new Intent(this, ForgetPasswordActivity.class);
        startActivity(intent);
    }

    private void startRegister(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
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

    }

    @Override
    public void onVerifyCodeFail(String message) {

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


    public static void startLoginActivity(Context context){
        if(context == null){
            return;
        }
        context.startActivity(new Intent(context,LoginActivity.class));
    }
}
