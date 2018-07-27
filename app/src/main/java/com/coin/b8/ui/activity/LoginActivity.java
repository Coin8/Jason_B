package com.coin.b8.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
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
    private EditTextClear mAccountEdit;
    private EditTextClear mPasswordEdit;
    private LoadingDialog mLoadingDialog;

    private Toast mToast;

    private void showToast(String text){
        if (mToast == null) {
            mToast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

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
                    mMyToast.showToast("账号不能为空");
                    return;
                }
                if(!CommonUtils.isEmail(user)){
                    mMyToast.showToast("请输入正确的邮箱地址");
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
                    mMyToast.showToast("密码不能为空");
                    return;
                }
            }
        });

        mLoginBtn = findViewById(R.id.login_btn);
        mLoginBtn.setOnClickListener(this);
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


    int count = 0;
    @Override
    public void onClick(View v) {
        if(v == null){
//            mMyToast.showToast("222222222");
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
//                    mMyToast.showToast("111111111");
                    return;
                }
                email = mAccountEdit.getText().toString();
                password = mPasswordEdit.getText().toString();
                if(TextUtils.isEmpty(email)){
//                    Log.e("zy","5555555 = "+ count++);

                    mMyToast.showToast("账号不能为空");
//                    showToast("账号不能为空");
//                    ToastUtil.showShortToast("账号不能为空");

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
                if(PreferenceHelper.getIsLogin(this)){
                    DemoHelper.getInstance().login(loginResponseInfo.getData().getEasename(),
                            loginResponseInfo.getData().getPassword());
                }else {
                    mMyToast.showToast("登录失败");
                }
                mMyToast.showToast("登录成功");
                finish();
            }else {
                mMyToast.showToast("登录失败");
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
