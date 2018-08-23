package com.coin.b8.ui.dialog;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coin.b8.R;
import com.coin.b8.help.PreferenceHelper;
import com.coin.b8.model.ShareCommentResponse;
import com.coin.b8.ui.iView.IStoreShareView;
import com.coin.b8.ui.listen.ShareListen;
import com.coin.b8.ui.presenter.StoreShareDialogPresenter;
import com.coin.b8.ui.view.SwitchButton;
import com.coin.b8.utils.CommonUtils;
import com.coin.b8.utils.GlideUtil;
import com.umeng.analytics.MobclickAgent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by zhangyi on 2018/8/21.
 */
public class CoinStoreShareDialog extends DialogFragment implements View.OnClickListener,IStoreShareView{
    private DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy.MM.dd");
    private ImageView mUserIcon;
    private TextView mUserName;
    private TextView mDate;

    private TextView mBtnCancel;
    private View mWxChat;
    private View mWxCircle;
    private View mWeiBo;
    private View mQq;

    private ShareListen mShareListen;
    private LinearLayout mContent_layout;
    private String mStrMoney = "0";
    private String mStrRate = "0%";
    private TextView mMoneyView;
    private ImageView mMoneyViewLogo;
    private ImageView mMoneyViewLeft;
    private ImageView mMoneyViewRight;
    private SwitchButton mSwitchButton;
    private TextView mDescView;
    private LinearLayout mDescSwitchBtn;
    private StoreShareDialogPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            mStrMoney = bundle.getString("money","0");
            mStrRate = bundle.getString("rate","0%");
        }
        int style = DialogFragment.STYLE_NO_TITLE;
        int theme = R.style.ShareDialog;
        setStyle(style,theme);
        mPresenter = new StoreShareDialogPresenter(this);
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
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter != null){
            mPresenter.onDetach();
        }
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

    public void setShareListen(ShareListen shareListen) {
        mShareListen = shareListen;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.store_share_dialog_layout, container, false);
        mSwitchButton = v.findViewById(R.id.switch_button);
        mDescView = v.findViewById(R.id.beautiful_desc);
        mDescSwitchBtn = v.findViewById(R.id.switch_btn_desc);
        mMoneyView = v.findViewById(R.id.money_view);
        mMoneyViewLogo = v.findViewById(R.id.money_view_logo);
        mMoneyViewLeft = v.findViewById(R.id.money_view_left);
        mMoneyViewRight = v.findViewById(R.id.money_view_right);
        mContent_layout = v.findViewById(R.id.content_layout);
        mUserIcon = v.findViewById(R.id.user_icon);
        mUserName = v.findViewById(R.id.user_name);
        mDate = v.findViewById(R.id.date);
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

        mSwitchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if(isChecked){
                    mMoneyView.setText(mStrMoney);
                }else {
                    mMoneyView.setText(mStrRate);
                }
            }
        });

        mDate.setText(CommonUtils.millis2String(System.currentTimeMillis(),DEFAULT_FORMAT));
        GlideUtil.setCircleImageRes(getContext(),mUserIcon, PreferenceHelper.getHeadIcon(getContext()),R.drawable.icon_head);
        mUserName.setText(PreferenceHelper.getNickName(getContext()));

        mMoneyView.setText(mStrRate);
        if(!TextUtils.isEmpty(mStrRate)
                && mStrRate.startsWith("-")){
            mMoneyViewLogo.setImageResource(R.drawable.share_down);
            mMoneyViewLeft.setImageResource(R.drawable.money_down_left);
            mMoneyViewRight.setImageResource(R.drawable.money_down_right);
            mContent_layout.setBackgroundResource(R.drawable.store_share_down_bg);
        }else {
            mMoneyViewLogo.setImageResource(R.drawable.share_up);
            mMoneyViewLeft.setImageResource(R.drawable.money_up_left);
            mMoneyViewRight.setImageResource(R.drawable.money_up_right);
            mContent_layout.setBackgroundResource(R.drawable.store_share_up_bg);
        }

        mDescSwitchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getShareComment();
            }
        });

        mPresenter.getShareComment();

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
                bm = makeView2Bitmap(mContent_layout);
                if(mShareListen != null){
                    mShareListen.onClickWxChat(bm);
                }
                break;
            case R.id.mRlWeixinCircle:
                bm = makeView2Bitmap(mContent_layout);
                if(mShareListen != null){
                    mShareListen.onClickWxCircle(bm);
                }
                break;
            case R.id.mRlWeibo:
                bm = makeView2Bitmap(mContent_layout);
                if(mShareListen != null){
                    mShareListen.onClickWeiBo(bm);
                }
                break;
            case R.id.mRlQQ:
                bm = makeView2Bitmap(mContent_layout);
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

    @Override
    public void onComment(ShareCommentResponse response) {
        if(response != null && response.getData() != null){
            mDescView.setText(response.getData().getComment());
        }
    }
}
