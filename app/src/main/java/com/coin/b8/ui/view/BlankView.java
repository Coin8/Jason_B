package com.coin.b8.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coin.b8.R;
import com.coin.b8.ui.activity.SearchActivity;

/**
 * Created by zhangyi on 2018/7/9.
 */
public class BlankView extends LinearLayout{
    public static final int BLANK_WIFI = 0;
    public static final int BLANK_SEARCH = 1;
    public static final int BLANK_SELF = 2;
    public static final int BLANK_COLLECT = 3;
    public static final int BLANK_YUJING = 4;
    private ImageView mImageView;
    private TextView mDesc;
    private TextView mClickBtn;
    private LinearLayout mLoadingLayout;
    private LinearLayout mEmptyLayout;
    private TextView mLoadingText;
    public BlankView(Context context) {
        super(context);
        init(context);
    }

    public BlankView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BlankView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.blank_layout, this, true);
        mImageView = findViewById(R.id.blank_image);
        mDesc = findViewById(R.id.blank_desc);
        mClickBtn = findViewById(R.id.blank_btn);
        mLoadingLayout = findViewById(R.id.layout_loading);
        mEmptyLayout = findViewById(R.id.empty_layout);
        mLoadingText = findViewById(R.id.toast_text);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void setLoadingText(String text){
        mLoadingText.setText(text);
    }

    public void showLoading(){
        mLoadingLayout.setVisibility(VISIBLE);
        mEmptyLayout.setVisibility(GONE);
    }


    public void setImageViewTye(int type){
        int id = R.drawable.img_blank_wifi;
        mLoadingLayout.setVisibility(GONE);
        mEmptyLayout.setVisibility(VISIBLE);
        switch (type){
            case BLANK_COLLECT:
                id = R.drawable.img_blank_collect;
                break;
            case BLANK_SEARCH:
                id = R.drawable.img_blank_search;
                break;
            case BLANK_SELF:
                id = R.drawable.img_blank_self;
                break;
            case BLANK_WIFI:
                id = R.drawable.img_blank_wifi;
                break;
            case BLANK_YUJING:
                id = R.drawable.img_blank_yujing;
                break;
        }
        mImageView.setImageResource(id);
    }

    public void setDesc(String text){
        if(TextUtils.isEmpty(text)){
            mDesc.setVisibility(GONE);
        }else {
            mDesc.setVisibility(VISIBLE);
            mDesc.setText(text);
        }
    }

    public void setButtonOnclick(OnClickListener onclick){
        mClickBtn.setOnClickListener(onclick);
    }

    public void setButtonText(String text){
        mClickBtn.setText(text);
    }

    public void setEnableButton(boolean value){
        if(value){
            mClickBtn.setVisibility(VISIBLE);
        }else {
            mClickBtn.setVisibility(GONE);
        }
    }
}
