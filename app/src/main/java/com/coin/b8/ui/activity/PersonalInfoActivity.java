package com.coin.b8.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.coin.b8.R;
import com.coin.b8.app.AppLogger;
import com.coin.b8.constant.Constants;
import com.coin.b8.help.PreferenceHelper;
import com.coin.b8.model.ModifyUserHeadResponse;
import com.coin.b8.model.ModifyUserResponse;
import com.coin.b8.model.UserInfoResponse;
import com.coin.b8.permission.RuntimeRationale;
import com.coin.b8.ui.dialog.CustomSelectDialog;
import com.coin.b8.ui.dialog.InputDialog;
import com.coin.b8.ui.iView.IPersonalInfoView;
import com.coin.b8.ui.presenter.PersonalInfoPresenterImpl;
import com.coin.b8.utils.CameraAlbumUtil;
import com.coin.b8.utils.CommonUtils;
import com.coin.b8.utils.FileProviderUtils;
import com.coin.b8.utils.GlideUtil;
import com.coin.b8.utils.MyToast;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyi on 2018/7/2.
 */
public class PersonalInfoActivity extends BaseActivity implements View.OnClickListener,IPersonalInfoView{
    //相册请求码
    private static final int ALBUM_REQUEST_CODE = 1;
    //相机请求码
    private static final int CAMERA_REQUEST_CODE = 2;
    //剪裁请求码
    private static final int CROP_REQUEST_CODE = 3;
    private TextView mNickName;
    private TextView mSex;
    private View mNickNameLayout;
    private View mSexLayout;
    private ImageView mImageUserIcon;
    private TextView mLoginBtn;
    private PersonalInfoPresenterImpl mPersonalInfoPresenter;

    private MyToast mMyToast;
    private boolean mIsHeadNewCreate = false;

    private String mTmpPicture = Environment.getExternalStorageDirectory().getPath()+File.separator +  System.currentTimeMillis() + ".jpg";
    private String mTmpOutPicture = Environment.getExternalStorageDirectory().getPath()+File.separator +  System.currentTimeMillis() + "out.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        mPersonalInfoPresenter = new PersonalInfoPresenterImpl(this);
        setInitToolBar(getString(R.string.personal_info));
        mNickName = findViewById(R.id.nicheng_text);
        mSex = findViewById(R.id.xb_text);
        mNickNameLayout = findViewById(R.id.nick_name_layout);
        mSexLayout = findViewById(R.id.sex_layout);
        mImageUserIcon = findViewById(R.id.user_icon);
        mLoginBtn = findViewById(R.id.login_btn);
        mNickNameLayout.setOnClickListener(this);
        mSexLayout.setOnClickListener(this);
        mImageUserIcon.setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);
        mMyToast = new MyToast(this);

    }

    private void updateUser(){
        String name = PreferenceHelper.getNickName(this);
        if(TextUtils.isEmpty(name)){
            mNickName.setText("无");
        }else {
            mNickName.setText(name);
        }
        if(PreferenceHelper.getSex(this) == 1){
            mSex.setText("男");
        }else {
            mSex.setText("女");
        }
    }

    private void updateHead(){
        String iconUrl = PreferenceHelper.getHeadIcon(this);
        if(!TextUtils.isEmpty(iconUrl)){
            GlideUtil.setCircleImageRes(this,mImageUserIcon,iconUrl,R.drawable.icon_head);
        }else {
            mImageUserIcon.setImageResource(R.drawable.icon_head);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(PreferenceHelper.getIsLogin(this)){
            mLoginBtn.setText("切换账号");
        }
        updateUser();
        if(mIsHeadNewCreate){
            mIsHeadNewCreate = false;
        }else {
            updateHead();
        }
        mPersonalInfoPresenter.getUserInfo(PreferenceHelper.getUid(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPersonalInfoPresenter.onDetach();
    }

    private void initHead(File file){
        if(file != null && file.exists()) {
            mIsHeadNewCreate = true;
            GlideUtil.setCircleImageRes(this, mImageUserIcon, file, R.drawable.icon_head);
            mPersonalInfoPresenter.modifyUserHead(mTmpOutPicture);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.nick_name_layout:
                showDialogNickName();
                break;
            case R.id.sex_layout:
                showDialogSex();
                break;
            case R.id.user_icon:
                showDialogAlbum();
                break;
            case R.id.login_btn:
                startLogin();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }
        Uri filtUri;
        File outputFile = new File(mTmpOutPicture);//裁切后输出的图片
//        File outputFile = new File("/mnt/sdcard/tupian_out.jpg");//裁切后输出的图片
        switch (requestCode) {
            case CameraAlbumUtil.REQUEST_CODE_PAIZHAO:
                //拍照完成，进行图片裁切
                File file = new File(mTmpPicture);
//                File file = new File("/mnt/sdcard/tupian.jpg");
                filtUri = FileProviderUtils.uriFromFile(this, file);
                CameraAlbumUtil.Caiqie(this, filtUri, outputFile);
                break;
            case CameraAlbumUtil.REQUEST_CODE_ZHAOPIAN:
                //相册选择图片完毕，进行图片裁切
                if (data == null ||  data.getData()==null) {
                    return;
                }
                filtUri = data.getData();
                CameraAlbumUtil.Caiqie(this, filtUri, outputFile);
                break;
            case CameraAlbumUtil.REQUEST_CODE_CAIQIE:
                //图片裁切完成，显示裁切后的图片
                initHead(outputFile);
                break;
        }

    }

    private void showDialogNickName(){
        InputDialog inputDialog = new InputDialog(this, "请输入姓名", "", new InputDialog.InputDialogOkButtonClickListener() {
            @Override
            public void onClick(Dialog dialog, String inputText) {
                if(TextUtils.isEmpty(inputText) || TextUtils.isEmpty(inputText.trim())){
                    mMyToast.showToast("不能输入空字符");
                    return;
                }
                if(inputText.length() > 10){
                    mMyToast.showToast("请输入10个字符以内");
                    return;
                }
                mNickName.setText(inputText);
                PreferenceHelper.setNickName(PersonalInfoActivity.this,inputText);
                mPersonalInfoPresenter.modifyUserInfo(PreferenceHelper.getSex(PersonalInfoActivity.this),
                        inputText,
                        PreferenceHelper.getUid(PersonalInfoActivity.this));
            }
        },"");
        inputDialog.showDialog();
    }

    private void showDialogSex() {
        List<String> names = new ArrayList<>();
        names.add("男");
        names.add("女");
        showDialog(new CustomSelectDialog.SelectDialogListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mSex.setText(names.get(position));
                        PreferenceHelper.setSex(PersonalInfoActivity.this,1);
                        mPersonalInfoPresenter.modifyUserInfo(1,
                                PreferenceHelper.getNickName(PersonalInfoActivity.this),
                                PreferenceHelper.getUid(PersonalInfoActivity.this));

                        break;
                    case 1:
                        mSex.setText(names.get(position));
                        PreferenceHelper.setSex(PersonalInfoActivity.this,2);
                        mPersonalInfoPresenter.modifyUserInfo(2,
                                PreferenceHelper.getNickName(PersonalInfoActivity.this),
                                PreferenceHelper.getUid(PersonalInfoActivity.this));
                        break;
                }
            }
        }, names);
    }

    boolean checkPermission(){
        if(AndPermission.hasPermissions(this,Permission.WRITE_EXTERNAL_STORAGE,
                Permission.CAMERA)){
            return true;
        }
        return false;
    }

    private void requestPermission(){
        AndPermission.with(this)
                .runtime()
                .permission(Permission.WRITE_EXTERNAL_STORAGE,Permission.CAMERA)
                .rationale(new RuntimeRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        getPicFromCamera();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(PersonalInfoActivity.this, permissions)) {
                            showSettingDialog(PersonalInfoActivity.this, permissions);
                        }
                    }
                })
                .start();
    }

    private void showDialogAlbum() {
        List<String> names = new ArrayList<>();
        names.add("拍照");
        names.add("相册");
        showDialog(new CustomSelectDialog.SelectDialogListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:                 //拍照
                        if(checkPermission()){
                            getPicFromCamera();
                        }else {
                            requestPermission();
                        }
                        break;
                    case 1:                 //相册
                        getPicFromAlbm();
                        break;
                }
            }
        }, names);
    }

    private CustomSelectDialog showDialog(CustomSelectDialog.SelectDialogListener listener, List<String> names) {
        CustomSelectDialog dialog = new CustomSelectDialog(this, R.style.transparentFrameWindowStyle, listener, names);
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }

    /**
     * 从相机获取图片
     */
    private void getPicFromCamera() {
        CameraAlbumUtil.paizhao(this, new File(mTmpPicture));
    }

    /**
     * 从相册获取图片
     */
    private void getPicFromAlbm() {
        if(AndPermission.hasPermissions(this,Permission.WRITE_EXTERNAL_STORAGE)) {
            CameraAlbumUtil.zhaopian(this);
        }else {
            requestStoragePermission();
        }
    }

    private void requestStoragePermission(){
        AndPermission.with(this)
                .runtime()
                .permission(Permission.WRITE_EXTERNAL_STORAGE)
                .rationale(new RuntimeRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        getPicFromAlbm();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(PersonalInfoActivity.this, permissions)) {
                            showSettingDialog(PersonalInfoActivity.this, permissions);
                        }
                    }
                })
                .start();

    }

    private void startLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onUserInfo(UserInfoResponse userInfoResponse) {
        if(userInfoResponse != null && userInfoResponse.getData() != null){
            PreferenceHelper.saveUserInfo(this,userInfoResponse);
            updateUser();
            updateHead();
        }
    }

    @Override
    public void onModifyUserSuccess(ModifyUserResponse modifyUserResponse) {

    }

    @Override
    public void onModifyUserFail() {

    }

    @Override
    public void onModifyUserHeadSuccess(ModifyUserHeadResponse modifyUserHeadResponse) {
        if(modifyUserHeadResponse != null && modifyUserHeadResponse.isResult()){
            PreferenceHelper.setHeadIcon(this,modifyUserHeadResponse.getData());
            updateHead();
            mMyToast.showToast("修改头像成功");
        }else {
            mMyToast.showToast("修改头像失败");
        }
    }

    @Override
    public void onModifyUserHeadFail() {
        mMyToast.showToast("修改头像失败");
    }
}
