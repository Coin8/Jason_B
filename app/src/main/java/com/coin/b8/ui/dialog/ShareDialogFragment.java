package com.coin.b8.ui.dialog;

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
import android.widget.TextView;

import com.coin.b8.R;
import com.coin.b8.ui.listen.ShareListen;

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

    public void setShareListen(ShareListen shareListen) {
        mShareListen = shareListen;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NO_TITLE;
        int theme = R.style.ShareDialog;
        setStyle(style,theme);
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
        return v;
    }

    @Override
    public void onClick(View v) {
        if(v == null){
            return;
        }

        switch (v.getId()){
            case R.id.button_cancel:

                break;
            case R.id.mRlWechat:
                if(mShareListen != null){
                    mShareListen.onClickWxChat();
                }
                break;
            case R.id.mRlWeixinCircle:
                if(mShareListen != null){
                    mShareListen.onClickWxCircle();
                }
                break;
            case R.id.mRlWeibo:
                if(mShareListen != null){
                    mShareListen.onClickWeiBo();
                }
                break;
            case R.id.mRlQQ:
                if(mShareListen != null){
                    mShareListen.onClickQq();
                }
                break;
        }

        dismiss();

    }
}
