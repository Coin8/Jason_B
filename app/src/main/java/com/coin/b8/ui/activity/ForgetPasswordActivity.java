package com.coin.b8.ui.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.coin.b8.R;
import com.coin.b8.model.ResetPasswordResponseInfo;
import com.coin.b8.ui.dialog.LoadingDialog;
import com.coin.b8.ui.iView.IForgetPasswordView;
import com.coin.b8.ui.presenter.ForgetPasswordPresenterImpl;
import com.coin.b8.utils.CommonUtils;
import com.coin.b8.utils.MyToast;

/**
 * Created by zhangyi on 2018/7/3.
 */
public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener,IForgetPasswordView{
    private TextView mForgetTitle;
    private TextView mBtnSubmit;
    private TextView mBtnVerifyCode;
    private EditText mAccountEdit;
    private EditText mPasswordEdit;
    private EditText mPasswordConfirmEdit;
    private EditText mVerifyCodeEdit;
    private boolean mCodeIsSend = false;
    private LoadingDialog mLoadingDialog;
    private MyCountDownTimer mMyCountDownTimer;
    private MyToast mMyToast;
    private ForgetPasswordPresenterImpl mForgetPasswordPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        mMyToast = new MyToast(this);
        mForgetPasswordPresenter = new ForgetPasswordPresenterImpl(this);
        setInitToolBar("");
        mForgetTitle = findViewById(R.id.content_title);
        mBtnSubmit = findViewById(R.id.submit_btn);
        mAccountEdit = findViewById(R.id.login_account_edit);
        mPasswordEdit = findViewById(R.id.login_password_edit);
        mPasswordConfirmEdit = findViewById(R.id.login_password_edit_confirm);
        mVerifyCodeEdit = findViewById(R.id.verify_confirm_edit);
        mBtnVerifyCode = findViewById(R.id.verify_confirm_btn);
        mForgetTitle .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mBtnSubmit.setOnClickListener(this);
        mBtnVerifyCode.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mForgetPasswordPresenter.onDetach();
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
                password = mPasswordEdit.getText().toString();
                password2 = mPasswordConfirmEdit.getText().toString();
                code = mVerifyCodeEdit.getText().toString();
                if(TextUtils.isEmpty(email)){
                    mMyToast.showToast("账号不能为空");
                    return;
                }
                if(!CommonUtils.isEmail(email)){
                    mMyToast.showToast("请输入正确的邮箱地址");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mMyToast.showToast("密码不能为空");
                    return;
                }
                if(password.length() < 6){
                    mMyToast.showToast("密码不能小于6位");
                    return;
                }
                if(password.length() > 32){
                    mMyToast.showToast("密码不能大于32位");
                    return;
                }
                if(!TextUtils.equals(password,password2)){
                    mMyToast.showToast("两次密码不一样");
                    return;
                }

                if(TextUtils.isEmpty(code)){
                    mMyToast.showToast("验证码不能为空");
                    return;
                }
                showLoading("正在修改");
                mForgetPasswordPresenter.resetPassword(email,password,code);
                break;
            case R.id.verify_confirm_btn:
                if(mCodeIsSend){
                    return;
                }
                email = mAccountEdit.getText().toString();
                if(TextUtils.isEmpty(email)){
                    mMyToast.showToast("账号不能为空");
                    return;
                }
                if(!CommonUtils.isEmail(email)){
                    mMyToast.showToast("请输入正确的邮箱地址");
                    return;
                }
                showLoading("正在发送");
                mForgetPasswordPresenter.sendVerifyCode(email,2);
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
    public void onVerifyCodeSuccess() {
        hideLoading();
        mMyToast.showToast("发送成功");
    }

    @Override
    public void onVerifyCodeFail() {
        hideLoading();
        mMyToast.showToast("发送失败");
    }

    @Override
    public void onResetPasswordSuccess(ResetPasswordResponseInfo resetPasswordResponseInfo) {
        hideLoading();
        if(resetPasswordResponseInfo != null){
            if(resetPasswordResponseInfo.isResult()){
                mMyToast.showToast("修改密码成功");
                finish();
            }else {
                mMyToast.showToast(resetPasswordResponseInfo.getMessage());
            }
        }
    }

    @Override
    public void onResetPasswordFail() {
        hideLoading();
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
