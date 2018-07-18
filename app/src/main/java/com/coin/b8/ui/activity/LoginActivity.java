package com.coin.b8.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.coin.b8.R;
import com.coin.b8.help.DemoHelper;
import com.coin.b8.help.PreferenceHelper;
import com.coin.b8.model.LoginResponseInfo;
import com.coin.b8.permission.RuntimeRationale;
import com.coin.b8.ui.dialog.LoadingDialog;
import com.coin.b8.ui.iView.ILoginView;
import com.coin.b8.ui.presenter.LoginPresenterImpl;
import com.coin.b8.utils.CommonUtils;
import com.coin.b8.utils.MyToast;
import com.google.gson.Gson;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.List;

/**
 * Created by zhangyi on 2018/7/3.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener,ILoginView{
    private TextView mForgetTitle;
    private TextView mForgetBtn;
    private TextView mLoginBtn;
    private MyToast mMyToast;
    private LoginPresenterImpl mLoginPresenter;
    private EditText mAccountEdit;
    private EditText mPasswordEdit;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mMyToast = new MyToast(this);
        mLoginPresenter = new LoginPresenterImpl(this);
        initView();
        requestPhonePermission();
    }

    private void initView(){
        setInitToolBar("");
        mToolbarRightTitle.setVisibility(View.VISIBLE);
        mToolbarRightTitle.setText("注册账号");
        mToolbarRightTitle.setOnClickListener(this);
        mForgetTitle = findViewById(R.id.content_title);
        mForgetTitle .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mForgetBtn = findViewById(R.id.forget_password);
        mForgetBtn.setOnClickListener(this);
        mAccountEdit = findViewById(R.id.login_account_edit);
        mPasswordEdit = findViewById(R.id.login_password_edit);
        mLoginBtn = findViewById(R.id.login_btn);
        mLoginBtn.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.onDetach();
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
            case R.id.login_btn:
                if(!checkPhonePermission()){
                    return;
                }
                email = mAccountEdit.getText().toString();
                password = mPasswordEdit.getText().toString();
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
                showLoading("正在登录");
                mLoginPresenter.getLoginInfo(email,password);
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
        if(mLoadingDialog != null && mLoadingDialog.getDialog() != null && mLoadingDialog.getDialog().isShowing()){
            mLoadingDialog.dismiss();
        }
    }
    private void showLoading(String text){
        mLoadingDialog = new LoadingDialog();
        mLoadingDialog.setLoadingText(text);
        mLoadingDialog.show(getSupportFragmentManager(),"login");
    }

    @Override
    public void onLoginSuccess(LoginResponseInfo loginResponseInfo) {
        hideLoading();
        if(loginResponseInfo != null){
            if(loginResponseInfo.isResult() && loginResponseInfo.getData() != null){
                PreferenceHelper.saveLoinInfo(this,loginResponseInfo);
                if(DemoHelper.getInstance().isLoggedIn()){
                    DemoHelper.getInstance().logout();
                }
                DemoHelper.getInstance().login(loginResponseInfo.getData().getEasename(),
                        loginResponseInfo.getData().getPassword());
                mMyToast.showToast("登录成功");
                finish();
            }else {
                mMyToast.showToast(loginResponseInfo.getMessage());
            }
        }
    }


    @Override
    public void onLoginFail() {
        hideLoading();
        mMyToast.showToast("登录失败");
    }


    public static void startLoginActivity(Context context){
        if(context == null){
            return;
        }
        context.startActivity(new Intent(context,LoginActivity.class));
    }
}
