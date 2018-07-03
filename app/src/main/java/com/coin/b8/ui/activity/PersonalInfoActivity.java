package com.coin.b8.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.coin.b8.permission.RuntimeRationale;
import com.coin.b8.ui.dialog.CustomSelectDialog;
import com.coin.b8.ui.dialog.InputDialog;
import com.coin.b8.utils.CommonUtils;
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
public class PersonalInfoActivity extends BaseActivity implements View.OnClickListener{
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

    //调用照相机返回图片文件
    private File tempFile;
    private String mAuthority = "";
    private String mLocalHeadIconPath;
    private MyToast mMyToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
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
        mAuthority = getPackageName() + ".chosehead";
        mMyToast = new MyToast(this);
        initHead();
    }

    private void initHead(){
        mLocalHeadIconPath = getFilesDir() + Constants.LOCAL_HEAD_ICON_FILE_NAME;
        File file = new File(mLocalHeadIconPath);
        if(file.exists()){
            GlideUtil.setImageRes(this,mImageUserIcon,file,R.drawable.icon_head,R.drawable.icon_head,true);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode){
            case CAMERA_REQUEST_CODE:   //调用相机后返回
                if (resultCode == RESULT_OK) {
                    //用相机返回的照片去调用剪裁也需要对Uri进行处理
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri contentUri = FileProvider.getUriForFile(this, mAuthority, tempFile);
                        cropPhoto(contentUri);
                    } else {
                        cropPhoto(Uri.fromFile(tempFile));
                    }
                }
                break;
            case ALBUM_REQUEST_CODE:    //调用相册后返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    cropPhoto(uri);
                }
                break;
            case CROP_REQUEST_CODE:     //调用剪裁后返回
                if(intent == null){
                    return;
                }
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    //在这里获得了剪裁后的Bitmap对象，可以用于上传
                    Bitmap image = bundle.getParcelable("data");
                    saveImage(image);
                    initHead();
                }
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
                mNickName.setText(inputText.trim());
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
                        break;
                    case 1:
                        mSex.setText(names.get(position));
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

        //用于保存调用相机拍照后所生成的文件
        tempFile = new File(Environment.getExternalStorageDirectory().getPath(), System.currentTimeMillis() + ".jpg");
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {   //如果在Android7.0以上,使用FileProvider获取Uri
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(this, mAuthority, tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            Log.e("dasd", contentUri.toString());
        } else {    //否则使用Uri.fromFile(file)方法获取Uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    /**
     * 从相册获取图片
     */
    private void getPicFromAlbm() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, ALBUM_REQUEST_CODE);
    }

    /**
     * 裁剪图片
     */
    private void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, CROP_REQUEST_CODE);
    }


    private void requestStoragePermission(){
        AndPermission.with(this)
                .runtime()
                .permission(Permission.WRITE_EXTERNAL_STORAGE,Permission.CAMERA)
                .rationale(new RuntimeRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {

                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(PersonalInfoActivity.this, permissions)) {

                        }
                    }
                })
                .start();

    }

    private void saveImage(Bitmap bmp) {
        if(bmp == null){
            return ;
        }
        File file = new File(mLocalHeadIconPath);
        if(file.exists()){
            file.delete();
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startLogin(){

    }

}
