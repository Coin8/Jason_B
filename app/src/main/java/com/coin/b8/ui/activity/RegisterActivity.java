package com.coin.b8.ui.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.coin.b8.R;
import com.coin.b8.model.RegisterResponseInfo;
import com.coin.b8.ui.dialog.LoadingDialog;
import com.coin.b8.ui.iView.IRegisterView;
import com.coin.b8.ui.presenter.RegisterPresenterImpl;
import com.coin.b8.utils.CommonUtils;
import com.coin.b8.utils.MyToast;

/**
 * Created by zhangyi on 2018/7/3.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener ,IRegisterView{
    private TextView mForgetTitle;
    private TextView mBtnAgreement;
    private AppCompatCheckBox mCheckboxAgreement;
    private TextView mBtnSubmit;
    private MyCountDownTimer mMyCountDownTimer;
    private TextView mBtnGetVerifyCode;
    private EditText mAccountEdit;
    private EditText mPasswordEdit;
    private EditText mPasswordConfirmEdit;
    private EditText mVerifyCodeEdit;
    private RegisterPresenterImpl mRegisterPresenter;
    private boolean mCodeIsSend = false;
    private MyToast mMyToast;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mRegisterPresenter = new RegisterPresenterImpl(this);
        mMyToast = new MyToast(this);
        setInitToolBar("");
        initView();
    }

    private void initView(){
        mAccountEdit = findViewById(R.id.login_account_edit);
        mPasswordEdit = findViewById(R.id.login_password_edit);
        mPasswordConfirmEdit = findViewById(R.id.login_password_edit_confirm);
        mVerifyCodeEdit = findViewById(R.id.verify_confirm_edit);

        mBtnGetVerifyCode = findViewById(R.id.verify_confirm_btn);
        mBtnAgreement = findViewById(R.id.btn_agreement);
        mCheckboxAgreement = findViewById(R.id.checkbox_agreement);
        mBtnSubmit = findViewById(R.id.submit_btn);
        mForgetTitle = findViewById(R.id.content_title);
        mForgetTitle .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mBtnAgreement.setOnClickListener(this);
        mCheckboxAgreement.setOnClickListener(this);
        mBtnSubmit.setOnClickListener(this);
        mBtnGetVerifyCode.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRegisterPresenter.onDetach();
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
                    mMyToast.showToast("账号不能为空");
                    return;
                }
                if(!CommonUtils.isEmail(email)){
                    mMyToast.showToast("请输入正确的邮箱地址");
                    return;
                }
                showLoading("正在发送");
                mRegisterPresenter.sendVerifyCode(email,1);
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
                showLoading("正在注册");
                mRegisterPresenter.register(email,password,code);
                break;
            case R.id.checkbox_agreement:
                if(mCheckboxAgreement.isChecked()){
                    mBtnSubmit.setBackgroundResource(R.drawable.feedback_btn_bg);
                }else {
                    mBtnSubmit.setBackgroundResource(R.drawable.corner_bg_gray);
                }
                break;
        }

    }

    private void startUserProtocol(){
        Intent intent = new Intent(this, UserProtocolActivity.class);
        startActivity(intent);
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
    public void onRegisterSuccess(RegisterResponseInfo registerResponseInfo) {
        hideLoading();
        if(registerResponseInfo != null){
            if(registerResponseInfo.isResult()){
                mMyToast.showToast("注册成功");
                finish();
            }else {
                mMyToast.showToast(registerResponseInfo.getMessage());
            }

        }
    }

    @Override
    public void onRegisterFail() {
        hideLoading();
        mMyToast.showToast("注册失败");
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

        /**
         * Execute different action according to tagForCountDownTimer when countdown timer is done.
         */
        @Override
        public void onFinish() {
            mBtnGetVerifyCode.setText("重新发送");
            mCodeIsSend = false;
        }
    }

}
