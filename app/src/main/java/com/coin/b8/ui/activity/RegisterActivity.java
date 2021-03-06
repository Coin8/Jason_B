package com.coin.b8.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coin.b8.R;
import com.coin.b8.model.LoginResponseInfo;
import com.coin.b8.model.RegisterResponseInfo;
import com.coin.b8.ui.dialog.LoadingDialog;
import com.coin.b8.ui.iView.IRegisterView;
import com.coin.b8.ui.presenter.RegisterPresenterImpl;
import com.coin.b8.ui.view.EditTextClear;
import com.coin.b8.utils.ActivityManagerUtil;
import com.coin.b8.utils.CommonUtils;
import com.coin.b8.utils.MyToast;

import java.lang.ref.WeakReference;

/**
 * Created by zhangyi on 2018/7/3.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener ,IRegisterView,View.OnFocusChangeListener{
    private TextView mForgetTitle;
    private TextView mBtnAgreement;
    private AppCompatCheckBox mCheckboxAgreement;
    private TextView mBtnNext;
    private TextView mBtnComplete;
    private TextView mBtnGoLogin;
    private MyCountDownTimer mMyCountDownTimer;
    private TextView mBtnGetVerifyCode;
    private EditTextClear mAccountEdit;
    private EditTextClear mPasswordEdit;
    private EditTextClear mPasswordConfirmEdit;
    private EditTextClear mVerifyCodeEdit;
    private RegisterPresenterImpl mRegisterPresenter;
    private boolean mCodeIsSend = false;
    private TextView mToast;
    private LoadingDialog mLoadingDialog;
    private LinearLayout mStep1Layout;
    private LinearLayout mStep2Layout;

    private String mPhoneNumber;
    private String mPhoneVerifyCode;
    private String mPassword;

    private static final int MESSAGE_DISMISS = 100;
    private static final int MESSAGE_DELAY_TIME = 2000;

    private class MyHandler extends Handler {
        private WeakReference<RegisterActivity> mWeakReference;
        public MyHandler(RegisterActivity activity) {
            mWeakReference = new WeakReference<RegisterActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            RegisterActivity activity = mWeakReference.get();
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
        setContentView(R.layout.activity_register);
        mRegisterPresenter = new RegisterPresenterImpl(this);
        mToast = findViewById(R.id.toast_text);
        mMyHandler = new MyHandler(this);
        setInitToolBar("");
        initView();
    }

    private void initView(){
        mStep1Layout = findViewById(R.id.step1);
        mStep2Layout = findViewById(R.id.step2);
        mAccountEdit = findViewById(R.id.login_account_edit);
        mPasswordEdit = findViewById(R.id.login_password_edit);
        mPasswordConfirmEdit = findViewById(R.id.login_password_edit_confirm);
        mVerifyCodeEdit = findViewById(R.id.verify_confirm_edit);

        mBtnGetVerifyCode = findViewById(R.id.verify_confirm_btn);
        mBtnAgreement = findViewById(R.id.btn_agreement);
        mCheckboxAgreement = findViewById(R.id.checkbox_agreement);
        mBtnNext = findViewById(R.id.submit_btn);
        mBtnComplete = findViewById(R.id.complete_btn);
        mBtnGoLogin = findViewById(R.id.btn_go_login);
        mForgetTitle = findViewById(R.id.content_title);
        mForgetTitle .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        mAccountEdit.setLeftIcon(R.drawable.username_icon);
        mPasswordEdit.setLeftIcon(R.drawable.password_icon);
        mPasswordConfirmEdit.setLeftIcon(R.drawable.password_icon);
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
                checkState();
            }
        };

        mAccountEdit.addTextChangedListener(textWatcher);
        mVerifyCodeEdit.addTextChangedListener(textWatcher);


        TextWatcher passwordTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = mPasswordEdit.getText().toString();
                String password1 = mPasswordConfirmEdit.getText().toString();
                if(TextUtils.isEmpty(password)
                        || TextUtils.isEmpty(password1)){
                    mBtnComplete.setBackgroundResource(R.drawable.corner_bg_light_personsal);
                }else {
                    mBtnComplete.setBackgroundResource(R.drawable.feedback_btn_bg);
                }
            }
        };

        mPasswordEdit.addTextChangedListener(passwordTextWatcher);
        mPasswordConfirmEdit.addTextChangedListener(passwordTextWatcher);

//        mAccountEdit.setOnFocusChangeListener(this);
//        mPasswordEdit.setOnFocusChangeListener(this);
//        mPasswordConfirmEdit.setOnFocusChangeListener(this);
//        mVerifyCodeEdit.setOnFocusChangeListener(this);

        mBtnAgreement.setOnClickListener(this);
        mCheckboxAgreement.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);
        mBtnGetVerifyCode.setOnClickListener(this);
        mBtnComplete.setOnClickListener(this);
        mBtnGoLogin.setOnClickListener(this);
    }

    private void checkState(){
        String username = mAccountEdit.getText().toString();
        String verify = mVerifyCodeEdit.getText().toString();
        if(!mCheckboxAgreement.isChecked()
                ||TextUtils.isEmpty(username)
                || TextUtils.isEmpty(verify)){
            mBtnNext.setBackgroundResource(R.drawable.corner_bg_light_personsal);
        }else {
            mBtnNext.setBackgroundResource(R.drawable.feedback_btn_bg);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRegisterPresenter.onDetach();
        mMyHandler.removeMessages(MESSAGE_DISMISS);
        mMyHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onClick(View v) {
        if(v == null){
            return;
        }
        String email;
        String password;
        String password2;
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
                mRegisterPresenter.sendVerifyCode(null,email,1);
                mCodeIsSend = true;
                mMyCountDownTimer = new MyCountDownTimer(60*1000);
                mMyCountDownTimer.start();
                break;
            case R.id.btn_agreement:
                startUserProtocol();
                break;
            case R.id.submit_btn:
                if(!mCheckboxAgreement.isChecked()){
                    return;
                }
                email = mAccountEdit.getText().toString();
                code = mVerifyCodeEdit.getText().toString();
                if(TextUtils.isEmpty(email)){
                    showToast("账号不能为空");
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
                mPhoneNumber = email;
                mPhoneVerifyCode = code;
                showLoading("正在验证");
                mRegisterPresenter.checkPhoneCode(email,code,1);
                break;
            case R.id.checkbox_agreement:
                checkState();
                break;
            case R.id.complete_btn:
                password = mPasswordEdit.getText().toString();
                password2 = mPasswordConfirmEdit.getText().toString();
                if(TextUtils.isEmpty(password)){
                    showToast("密码不能为空");
                    return;
                }
                if(password.length() < 8){
                    showToast("密码不能小于8位");
                    return;
                }
                if(password.length() > 16){
                    showToast("密码不能大于16位");
                    return;
                }
                if(!TextUtils.equals(password,password2)){
                    showToast("两次密码不一样");
                    return;
                }
                showLoading("正在注册");
                mPassword = password;
                mRegisterPresenter.register(null,mPhoneNumber,password,mPhoneVerifyCode);
                break;

            case R.id.btn_go_login:
                finish();
                break;
        }

    }

    private void startUserProtocol(){
        Intent intent = new Intent(this, UserProtocolActivity.class);
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
        mLoadingDialog.show(getSupportFragmentManager(),"register");
    }

    @Override
    public void onCheckVerifyCodeSuccess() {
        hideLoading();
        mStep1Layout.setVisibility(View.GONE);
        mStep2Layout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCheckVerifyCodeFail(String message) {
        hideLoading();
        showToast(message);
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
    public void onRegisterSuccess(RegisterResponseInfo registerResponseInfo) {

        if(registerResponseInfo != null){
            if(registerResponseInfo.isResult()){
//                showToast("注册成功");
//                finish();
                mRegisterPresenter.getLoginInfo(mPhoneNumber,mPassword);
            }else {
                hideLoading();
                showToast(registerResponseInfo.getMessage());
            }
        }
    }

    @Override
    public void onRegisterFail() {
        hideLoading();
        showToast("注册失败");
    }

    @Override
    public void onLoginSuccess(LoginResponseInfo loginResponseInfo) {
        hideLoading();
        if(loginResponseInfo != null){

            if(loginResponseInfo.isResult()){
                showToast("注册成功");
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
    public void onLoinFailed() {
        hideLoading();
        showToast("登录失败");
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(v == null){
            return;
        }

        if(hasFocus){
            return;
        }
        String email;
        String password;
        String password2;
        String code;

        email = mAccountEdit.getText().toString();
        password = mPasswordEdit.getText().toString();
        password2 = mPasswordConfirmEdit.getText().toString();
        code = mVerifyCodeEdit.getText().toString();

        switch (v.getId()){
            case R.id.login_account_edit:
                if(TextUtils.isEmpty(email)){
                    showToast("手机号不能为空");
                    return;
                }
                if(!CommonUtils.isMobileSimple(email)){
                    showToast("手机号格式错误");
                    return;
                }

                break;
            case R.id.login_password_edit:
                if(TextUtils.isEmpty(email)){
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    showToast("密码不能为空");
                    return;
                }
                if(password.length() < 6){
                    showToast("密码不能小于6位");
                    return;
                }
                if(password.length() > 32){
                    showToast("密码不能大于32位");
                    return;
                }
                break;
            case R.id.login_password_edit_confirm:
                if(TextUtils.isEmpty(email)
                        || TextUtils.isEmpty(password)){
                    return;
                }

                if(!TextUtils.equals(password,password2)){
                    showToast("两次密码不一样");
                    return;
                }

                if(password.length() < 6){
                    showToast("密码不能小于6位");
                    return;
                }
                if(password.length() > 32){
                    showToast("密码不能大于32位");
                    return;
                }

                break;
            case R.id.verify_confirm_edit:
                if(TextUtils.isEmpty(email)
                        || TextUtils.isEmpty(password)
                        || TextUtils.isEmpty(password2)){
                    return;
                }
                if(TextUtils.isEmpty(code)){
                    showToast("验证码不能为空");
                    return;
                }
                if(code.length() < 6){
                    showToast("验证码不能小于6位");
                    return;
                }
                break;
        }
    }


    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture) {
            super(millisInFuture, 1000);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            String s = "重新发送(" + millisUntilFinished/1000 + ")";
            mBtnGetVerifyCode.setText(s);
        }

        @Override
        public void onFinish() {
            mBtnGetVerifyCode.setText("重新发送");
            mCodeIsSend = false;
        }
    }

}
