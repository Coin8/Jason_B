package com.coin.b8.ui.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coin.b8.R;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by zhangyi on 2018/6/20.
 */
public class LoadingDialog extends DialogFragment {

    private TextView mLoadingTextView;
    private String mLoadingText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NO_TITLE;
        int theme = R.style.LoadingDialog;
        setStyle(style, theme);
    }

    public void setLoadingText(String loadingText) {
        mLoadingText = loadingText;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog_loading, container, false);
        mLoadingTextView = v.findViewById(R.id.toast_text);
        if(!TextUtils.isEmpty(mLoadingText)){
            mLoadingTextView.setText(mLoadingText);
        }
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setCancelable(false);
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
}
