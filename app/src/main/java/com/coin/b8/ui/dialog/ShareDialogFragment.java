package com.coin.b8.ui.dialog;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.coin.b8.R;
import com.coin.b8.ui.listen.ShareListen;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by zhangyi on 2018/6/8.
 */
public class ShareDialogFragment extends DialogFragment implements View.OnClickListener{

    private TextView mBtnCancel;
    private ImageView mImageView;
    private View mWxChat;
    private View mWxCircle;
    private View mWeiBo;
    private View mQq;
    private ShareListen mShareListen;
    private ScrollView mScrollView;
    private LinearLayout mDefaultImageLayout;
    private LinearLayout mQuickNewsLayout;
    private boolean isDisplayImage = true;
    private boolean isDisplayQuickNews = false;
    private TextView mTextViewDesc;
    private TextView mTextViewTime;
    private ImageView mBottomImage;

    private String mDesc = "";
    private String mTime = "";

    public void setQuickNewDesc(String desc,String time){
        mDesc = desc;
        mTime = time;
    }
    public void setDisplayQuickNews(boolean displayQuickNews) {
        isDisplayQuickNews = displayQuickNews;
    }

    public void setShareListen(ShareListen shareListen) {
        mShareListen = shareListen;
    }

    public void setDisplayImage(boolean displayImage) {
        isDisplayImage = displayImage;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NO_TITLE;
        int theme = R.style.ShareDialog;
        setStyle(style,theme);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this.getContext());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this.getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.dialog_share, container, false);
        mDefaultImageLayout = v.findViewById(R.id.default_image_share_layout);
        mBottomImage = v.findViewById(R.id.bottom_img);
        mQuickNewsLayout = v.findViewById(R.id.quick_news_layout);
        mTextViewDesc = v.findViewById(R.id.desc);
        mTextViewTime = v.findViewById(R.id.date);
        mScrollView = v.findViewById(R.id.scroll_view);
        mBtnCancel = v.findViewById(R.id.button_cancel);
        mWxChat = v.findViewById(R.id.mRlWechat);
        mWxCircle = v.findViewById(R.id.mRlWeixinCircle);
        mWeiBo = v.findViewById(R.id.mRlWeibo);
        mQq = v.findViewById(R.id.mRlQQ);
        mBtnCancel.setOnClickListener(this);
        mWxChat.setOnClickListener(this);
        mWxCircle.setOnClickListener(this);
        mWeiBo.setOnClickListener(this);
        mQq.setOnClickListener(this);
        if(isDisplayImage){
           mScrollView.setVisibility(View.VISIBLE);
        }else {
            mScrollView.setVisibility(View.GONE);
        }
        if(isDisplayQuickNews){
            mDefaultImageLayout.setVisibility(View.GONE);
            mQuickNewsLayout.setVisibility(View.VISIBLE);
            mTextViewTime.setText(mTime);
            mTextViewDesc.setText(mDesc);
        }else {
            mDefaultImageLayout.setVisibility(View.VISIBLE);
            mQuickNewsLayout.setVisibility(View.GONE);
        }

        mBottomImage.post(new Runnable() {
            @Override
            public void run() {
                if(mBottomImage != null){
                    int width = mBottomImage.getWidth();
                    if(width > 0){
                        int h = 260*width/749;
                        ViewGroup.LayoutParams layoutParams = mBottomImage.getLayoutParams();
                        layoutParams.height = h;
                        layoutParams.width = width;
                        mBottomImage.setLayoutParams(layoutParams);
                    }
                }

            }
        });


        return v;
    }

    @Override
    public void onClick(View v) {
        if(v == null){
            return;
        }
        Bitmap bm = null;

        switch (v.getId()){
            case R.id.button_cancel:

                break;
            case R.id.mRlWechat:
                if(isDisplayQuickNews){
                    bm = makeView2Bitmap(mQuickNewsLayout);
                }
                if(mShareListen != null){
                    mShareListen.onClickWxChat(bm);
                }
                break;
            case R.id.mRlWeixinCircle:
                if(isDisplayQuickNews){
                    bm = makeView2Bitmap(mQuickNewsLayout);
                }
                if(mShareListen != null){
                    mShareListen.onClickWxCircle(bm);
                }
                break;
            case R.id.mRlWeibo:
                if(isDisplayQuickNews){
                    bm = makeView2Bitmap(mQuickNewsLayout);
                }
                if(mShareListen != null){
                    mShareListen.onClickWeiBo(bm);
                }
                break;
            case R.id.mRlQQ:
                if(isDisplayQuickNews){
                    bm = makeView2Bitmap(mQuickNewsLayout);
                }
                if(mShareListen != null){
                    mShareListen.onClickQq(bm);
                }
                break;
        }

        dismiss();

    }

    private Bitmap makeView2Bitmap(View view) {
        //View是你需要绘画的View
        int width = view.getWidth();
        int height = view.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        //如果不设置canvas画布为白色，则生成透明   							canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }
}
