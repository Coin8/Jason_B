package com.coin.b8.ui.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coin.b8.R;
import com.coin.b8.model.ResetPasswordResponseInfo;
import com.coin.b8.ui.dialog.LoadingDialog;
import com.coin.b8.ui.iView.IForgetPasswordView;
import com.coin.b8.ui.presenter.ForgetPasswordPresenterImpl;
import com.coin.b8.ui.view.EditTextClear;
import com.coin.b8.utils.CommonUtils;
import com.coin.b8.utils.MyToast;

import java.lang.ref.WeakReference;

/**
 * Created by zhangyi on 2018/7/3.
 */
public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener,IForgetPasswordView,View.OnFocusChangeListener{
    private TextView mForgetTitle;
    private TextView mBtnSubmit;
    private TextView mBtnVerifyCode;
    private TextView mBtnComplete;
    private EditTextClear mAccountEdit;
    private EditTextClear mPasswordEdit;
    private EditTextClear mPasswordConfirmEdit;
    private EditTextClear mVerifyCodeEdit;
    private boolean mCodeIsSend = false;
    private LoadingDialog mLoadingDialog;
    private MyCountDownTimer mMyCountDownTimer;
    private TextView mToast;
    private ForgetPasswordPresenterImpl mForgetPasswordPresenter;

    private LinearLayout mStep1;
    private LinearLayout mStep2;

    private String mPhoneNumber;
    private String mPhoneVerifyCode;


    private static final int MESSAGE_DISMISS = 100;
    private static final int MESSAGE_DELAY_TIME = 2000;

    private class MyHandler extends Handler {
        private WeakReference<ForgetPasswordActivity> mWeakReference;
        public MyHandler(ForgetPasswordActivity activity) {
            mWeakReference = new WeakReference<ForgetPasswordActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            ForgetPasswordActivity activity = mWeakReference.get();
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
        setContentView(R.layout.activity_forget_password);
        mToast = findViewById(R.id.toast_text);
        mMyHandler = new MyHandler(this);
        mForgetPasswordPresenter = new ForgetPasswordPresenterImpl(this);
        setInitToolBar("");
        mStep1 = findViewById(R.id.step1);
        mStep2 = findViewById(R.id.step2);
        mForgetTitle = findViewById(R.id.content_title);
        mBtnSubmit = findViewById(R.id.submit_btn);
        mBtnComplete = findViewById(R.id.complete_btn);
        mAccountEdit = findViewById(R.id.login_account_edit);
        mPasswordEdit = findViewById(R.id.login_password_edit);
        mPasswordConfirmEdit = findViewById(R.id.login_password_edit_confirm);
        mVerifyCodeEdit = findViewById(R.id.verify_confirm_edit);
        mBtnVerifyCode = findViewById(R.id.verify_confirm_btn);
        mForgetTitle .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        mAccountEdit.setLeftIcon(R.drawable.username_icon);
        mVerifyCodeEdit.setLeftIcon(R.drawable.verify_icon);

        mPasswordEdit.setLeftIcon(R.drawable.password_icon);
        mPasswordConfirmEdit.setLeftIcon(R.drawable.password_icon);

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

        mAccountEdit.addTextChangedListener(textWatcher);
        mVerifyCodeEdit.addTextChangedListener(textWatcher);

        mPasswordEdit.addTextChangedListener(passwordTextWatcher);
        mPasswordConfirmEdit.addTextChangedListener(passwordTextWatcher);

        mBtnSubmit.setOnClickListener(this);
        mBtnVerifyCode.setOnClickListener(this);
        mBtnComplete.setOnClickListener(this);
    }

    private void checkState(){
        String username = mAccountEdit.getText().toString();
        String verify = mVerifyCodeEdit.getText().toString();
        if(TextUtils.isEmpty(username)
                || TextUtils.isEmpty(verify)){
            mBtnSubmit.setBackgroundResource(R.drawable.corner_bg_light_personsal);
        }else {
            mBtnSubmit.setBackgroundResource(R.drawable.feedback_btn_bg);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mForgetPasswordPresenter.onDetach();
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
            case R.id.submit_btn:
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

                mPhoneNumber = email;
                mPhoneVerifyCode = code;
                showLoading("正在验证");
                mForgetPasswordPresenter.checkPhoneCode(email,code,2);
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

                showLoading("正在修改");
                mForgetPasswordPresenter.resetPassword(null,mPhoneNumber,password,mPhoneVerifyCode);
                break;
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
                mForgetPasswordPresenter.sendVerifyCode(null,email,2);
                mCodeIsSend = true;
                mMyCountDownTimer = new MyCountDownTimer(60*1000);
                mMyCountDownTimer.start();
                break;
        }

    }

    private void hideLoading(){
        if(mLoadingDialog != null && mLoadingDialog.getDialog() != null && mLoadingDialog.getDialog().isShowing()){
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
        mStep1.setVisibility(View.GONE);
        mStep2.setVisibility(View.VISIBLE);
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
    public void onResetPasswordSuccess(ResetPasswordResponseInfo resetPasswordResponseInfo) {
        hideLoading();
        if(resetPasswordResponseInfo != null){
            if(resetPasswordResponseInfo.isResult()){
                showToast("修改密码成功");
                finish();
            }else {
                showToast(resetPasswordResponseInfo.getMessage());
            }
        }
    }

    @Override
    public void onResetPasswordFail() {
        hideLoading();
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
                if(code.length() < 4){
                    showToast("验证码不能小于4位");
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
            mBtnVerifyCode.setText(s);
        }

        /**
         * Execute different action according to tagForCountDownTimer when countdown timer is done.
         */
        @Override
        public void onFinish() {
            mBtnVerifyCode.setText("重新发送");
            mCodeIsSend = false;
        }
    }

}
