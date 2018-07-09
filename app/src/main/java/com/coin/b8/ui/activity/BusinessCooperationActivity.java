package com.coin.b8.ui.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.coin.b8.R;
import com.coin.b8.utils.MyToast;

/**
 * Created by zhangyi on 2018/7/3.
 */
public class BusinessCooperationActivity extends BaseActivity{
    private TextView mEmail;
    private TextView mCopyBtn;
    private MyToast mMyToast;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_cooperation);
        setInitToolBar(getString(R.string.business_cooperation));
        mMyToast = new MyToast(this);
        mEmail = findViewById(R.id.business_email);
        mEmail .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mCopyBtn = findViewById(R.id.copy_btn);
        mCopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyTextToClipBoard();
            }
        });
    }

    private void copyTextToClipBoard(){
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(mEmail.getText());
        mMyToast.showToast("复制成功");
    }


    public static void startBusinessCooperationActivity(Context context){
        if(context == null){
            return;
        }
        Intent intent = new Intent(context, BusinessCooperationActivity.class);
        context.startActivity(intent);
    }
}
