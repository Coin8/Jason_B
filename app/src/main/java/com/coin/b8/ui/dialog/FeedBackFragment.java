package com.coin.b8.ui.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.coin.b8.R;
import com.coin.b8.model.FeedBackParameter;
import com.coin.b8.utils.MyToast;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by zhangyi on 2018/6/8.
 */
public class FeedBackFragment extends DialogFragment {

    private TextView mPosButton;
    private AppCompatRadioButton mFuncRadioButton;
    private FeedBackInterface mFeedBackInterface;
    private EditText mContentEdit;
    private EditText mContactEdit;
    private ImageView mBackImageView;

    private MyToast mToast;

    public interface FeedBackInterface {
        void onPostQuestion(FeedBackParameter feedBackParameter);
    }

    public void setFeedBackInterface(FeedBackInterface feedBackInterface) {
        mFeedBackInterface = feedBackInterface;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToast = new MyToast(this.getActivity());
        int style = DialogFragment.STYLE_NO_TITLE;
        int theme = R.style.FeedbackDialog;
        setStyle(style, theme);
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_dialog_feedback, container, false);
        mFuncRadioButton = v.findViewById(R.id.radio_btn_func_suggestion);
        mBackImageView = v.findViewById(R.id.iv_back);
        mContentEdit = v.findViewById(R.id.content_edit);
        mContactEdit = v.findViewById(R.id.contact_edit);
        mPosButton = v.findViewById(R.id.post_button);

        mPosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mContentEdit.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    mToast.showToast("请填写问题描述");
                    return;
                }
                if (content.length() < 10) {
                    mToast.showToast("问题描述最少10个字符");
                    return;
                }

                if (mFeedBackInterface != null) {
                    FeedBackParameter feedBackParameter = new FeedBackParameter();
                    if (mFuncRadioButton.isChecked()) {
                        feedBackParameter.setType(1);
                    } else {
                        feedBackParameter.setType(2);
                    }
                    feedBackParameter.setContent(mContentEdit.getText().toString());
                    feedBackParameter.setContact(mContactEdit.getText().toString());
                    mFeedBackInterface.onPostQuestion(feedBackParameter);
                }
                dismiss();
            }
        });

        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return v;
    }
}
