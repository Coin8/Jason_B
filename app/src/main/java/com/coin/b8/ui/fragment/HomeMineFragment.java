package com.coin.b8.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coin.b8.R;
import com.coin.b8.app.AppLogger;
import com.coin.b8.constant.Constants;
import com.coin.b8.help.PreferenceHelper;
import com.coin.b8.http.B8Api;
import com.coin.b8.model.B8UpdateInfo;
import com.coin.b8.model.CoinStoreListResponse;
import com.coin.b8.model.FeedBackParameter;
import com.coin.b8.model.FeedBackResult;
import com.coin.b8.model.UserInfoResponse;
import com.coin.b8.ui.activity.BusinessCooperationActivity;
import com.coin.b8.ui.activity.CollectionActivity;
import com.coin.b8.ui.activity.HomeActivity;
import com.coin.b8.ui.activity.LoginActivity;
import com.coin.b8.ui.activity.NativeDetailActivity;
import com.coin.b8.ui.activity.PersonalInfoActivity;
import com.coin.b8.ui.activity.SettingActivity;
import com.coin.b8.ui.activity.YuJingRecordActivity;
import com.coin.b8.ui.dialog.CoinStoreDialog;
import com.coin.b8.ui.dialog.CoinStoreShareDialog;
import com.coin.b8.ui.dialog.FeedBackFragment;
import com.coin.b8.ui.dialog.ShareDialogFragment;
import com.coin.b8.ui.iView.IHomeMine;
import com.coin.b8.ui.listen.ShareListen;
import com.coin.b8.ui.presenter.HomeMinePresenterImpl;
import com.coin.b8.ui.view.SettingItemView;
import com.coin.b8.update.ICheckAgent;
import com.coin.b8.update.IUpdateChecker;
import com.coin.b8.update.IUpdateParser;
import com.coin.b8.update.UpdateInfo;
import com.coin.b8.update.UpdateManager;
import com.coin.b8.utils.CommonUtils;
import com.coin.b8.utils.GlideUtil;
import com.coin.b8.utils.MyToast;
import com.coin.b8.wxapi.OnResponseListener;
import com.coin.b8.wxapi.WXShare;

import java.io.File;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by zhangyi on 2018/6/28.
 */
public class HomeMineFragment extends BaseFragment implements View.OnClickListener,IHomeMine{
    private SettingItemView mItemInviteFriend;
    private SettingItemView mItemCollection;
    private SettingItemView mItemEarlyWarning;
    private SettingItemView mItemBusinessCooperation;
    private SettingItemView mItemFeedback;
    private SettingItemView mItemSetting;
    private SettingItemView mItemCheckUpdate;
    private View mUserLayout;
    private TextView mViewUserName;
    private TextView mViewUserId;
    private ImageView mUserImageView;
    private MyToast mToast;

    private TextView mTotalProperty;
    private TextView mTodayIncome;
    private TextView mTodayIncomeRate;
    private TextView mCoinStoreShareBtn;
    private TextView mStoreTitleBtn;
    private RelativeLayout mCoinStoreLayout;
    private CoinStoreDialog mCoinStoreDialog;
    private LinearLayout mStoreBtnLayout;
    private long mStoreId;
    private TextView mStoreShareBtn;

    private String mStrIncome = "0";
    private String mStrIncomeRate = "0%";
    private String mStrTotalProperty="总资产：0";

    private ImageView mEyeView;

    private boolean mIsMoneyVisiable = false;


    private HomeMinePresenterImpl mHomeMinePresenter;
    public static HomeMineFragment getInstance() {
        HomeMineFragment fragment = new HomeMineFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_mine;
    }

    @Override
    protected void initView(View view) {
        mEyeView = view.findViewById(R.id.eye);
        mStoreShareBtn = view.findViewById(R.id.share_btn);
        mStoreBtnLayout = view.findViewById(R.id.btn_store_layout);
        mCoinStoreLayout = view.findViewById(R.id.coin_store_layout);
        mStoreTitleBtn = view.findViewById(R.id.btn_store);
        mCoinStoreShareBtn = view.findViewById(R.id.share_btn);
        mTotalProperty = view.findViewById(R.id.property);
        mTodayIncome = view.findViewById(R.id.income);
        mTodayIncomeRate = view.findViewById(R.id.income_rate);
        mUserLayout = view.findViewById(R.id.user_layout);
        mItemInviteFriend = view.findViewById(R.id.mine_invite_friends);
        mItemCollection = view.findViewById(R.id.mine_collection);
        mItemEarlyWarning = view.findViewById(R.id.mine_early_warning_record);
        mItemBusinessCooperation = view.findViewById(R.id.mine_business_cooperation);
        mItemFeedback = view.findViewById(R.id.mine_feedback);
        mItemSetting = view.findViewById(R.id.mine_system_setting);
        mItemCheckUpdate = view.findViewById(R.id.mine_check_update);
        mViewUserName = view.findViewById(R.id.user_name);
        mUserImageView = view.findViewById(R.id.user_icon);
        mViewUserId = view.findViewById(R.id.user_id);
        mItemInviteFriend.setOnClickListener(this);
        mItemCollection.setOnClickListener(this);
        mItemEarlyWarning.setOnClickListener(this);
        mItemBusinessCooperation.setOnClickListener(this);
        mItemFeedback.setOnClickListener(this);
        mItemSetting.setOnClickListener(this);
        mItemCheckUpdate.setOnClickListener(this);
        mEyeView.setOnClickListener(this);
        mUserLayout.setOnClickListener(this);
        mToast = new MyToast(getActivity());
        mItemCheckUpdate.setRightText(getResources().getString(R.string.already_new_version));
        mHomeMinePresenter = new HomeMinePresenterImpl(this);
        mHomeMinePresenter.getUpdateInfo(true);

        TextPaint tp = mViewUserName.getPaint();
        tp.setFakeBoldText(true);

        mCoinStoreLayout.setOnClickListener(this);
        mStoreBtnLayout.setOnClickListener(this);
        mCoinStoreShareBtn.setOnClickListener(this);

        mCoinStoreDialog = new CoinStoreDialog(getContext(),null);
        mCoinStoreDialog.setEnableSelfUpdate(false);
        mCoinStoreDialog.setDismissListen(new CoinStoreDialog.DisMissListen() {
            @Override
            public void onDissMiss() {
                lighton();
                mHomeMinePresenter.getCoinStoreList();
            }
        });

        mEyeView.setOnClickListener(this);

    }


    private void lighton() {
        WindowManager.LayoutParams lp=getActivity().getWindow().getAttributes();
        lp.alpha=1.0f;
        getActivity().getWindow().setAttributes(lp);
    }

    private void lightoff() {
        WindowManager.LayoutParams lp=getActivity().getWindow().getAttributes();
        lp.alpha=0.7f;
        getActivity().getWindow().setAttributes(lp);
    }

    private void initUser(){
        initNickName();
        if(PreferenceHelper.getIsLogin(getContext())) {
            mViewUserId.setText("ID:" + PreferenceHelper.getUid(getContext()));
        }
        String icon = PreferenceHelper.getHeadIcon(getContext());
        if(!TextUtils.isEmpty(icon)){
            GlideUtil.setCircleImageRes(getContext(),mUserImageView,icon,
                    R.drawable.icon_head);
        }else {
            mUserImageView.setImageResource(R.drawable.icon_head);
        }
    }

    private void initNickName(){
        String nickName = PreferenceHelper.getNickName(getContext());
        if(TextUtils.isEmpty(nickName)){
            nickName = "币发用户";
            PreferenceHelper.setNickName(getContext(),nickName);
        }
        mViewUserName.setText(nickName);
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        initUser();
        String uid  = PreferenceHelper.getUid(getContext());
        if(TextUtils.isEmpty(uid)){
            uid = "0";
        }
        dispalyMoney();
        mHomeMinePresenter.getUserInfo(uid);
        mHomeMinePresenter.getCoinStoreList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHomeMinePresenter.onDetach();
        if(mCoinStoreDialog != null){
            mCoinStoreDialog.destroy();
        }
    }

    @Override
    public void onClick(View v) {
        if(v == null){
            return;
        }
        switch (v.getId()){
            case R.id.eye:
                if(mIsMoneyVisiable){
                    mTodayIncome.setText("*****");
                    mTodayIncomeRate.setText("*****");
                    mTotalProperty.setText("总资产：*****");
                    mIsMoneyVisiable = false;
                    mEyeView.setImageResource(R.drawable.eye_close);
                }else {
                    mTodayIncome.setText(mStrIncome);
                    mTodayIncomeRate.setText(mStrIncomeRate);
                    mTotalProperty.setText(mStrTotalProperty);
                    mIsMoneyVisiable = true;
                    mEyeView.setImageResource(R.drawable.eye_open);
                }
                PreferenceHelper.setMoneyDisplay(getContext(),mIsMoneyVisiable);
                break;
            case R.id.share_btn:
                startCoinStoreShareDialog();
                break;
            case R.id.btn_store_layout:
                mCoinStoreDialog.showDialog(mUserLayout);
                lightoff();
                mHomeMinePresenter.getCoinStoreList();
                break;
            case R.id.coin_store_layout:{
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(Constants.BASEURL).append("#/asset-group?");
                stringBuilder.append("uid=").append(PreferenceHelper.getUid(getContext()));
                String imei = PreferenceHelper.getIMEI(getContext());
                if(!TextUtils.isEmpty(imei)){
                    stringBuilder.append("&imei=").append(imei);
                }
                String token = PreferenceHelper.getToken(getContext());
                if(!TextUtils.isEmpty(token)){
                    stringBuilder.append("&token=").append(CommonUtils.encode(token));
                }

                stringBuilder.append("&id=").append(mStoreId);
                NativeDetailActivity.startNativeDetailActivity(getContext(),stringBuilder.toString());
            }
                break;
            case R.id.mine_invite_friends:
                startShare();
                break;
            case R.id.mine_collection:
                startCollection();
                break;
            case R.id.mine_early_warning_record:
                if(PreferenceHelper.getIsLogin(getContext())){
                    YuJingRecordActivity.startYuJingRecordActivity(getContext());
                }else {
                    LoginActivity.startLoginActivity(getContext());
                }
                break;
            case R.id.mine_business_cooperation:
                BusinessCooperationActivity.startBusinessCooperationActivity(getContext());
                break;
            case R.id.mine_feedback:
                startFeedBack();
                break;
            case R.id.mine_system_setting:
                startSetting();
                break;
            case R.id.mine_check_update:
                mHomeMinePresenter.getUpdateInfo(false);
                break;
            case R.id.user_layout:
                startPersonalInfo();
                break;
        }
    }

    private void startCoinStoreShareDialog(){
        Bundle bundle = new Bundle();
        bundle.putString("money",mStrIncome);
        bundle.putString("rate",mStrIncomeRate);
        CoinStoreShareDialog dialog = new CoinStoreShareDialog();
        dialog.setArguments(bundle);
        dialog.setShareListen(new ShareListen() {
            @Override
            public void onClickWxChat(Bitmap bitmap) {
                HomeActivity activity = (HomeActivity) getActivity();
                if(activity != null){
                    activity.shareBitmap(0,bitmap);
                }
            }

            @Override
            public void onClickWxCircle(Bitmap bitmap) {
                HomeActivity activity = (HomeActivity) getActivity();
                if(activity != null){
                    activity.shareBitmap(1,bitmap);
                }
            }

            @Override
            public void onClickWeiBo(Bitmap bitmap) {
                HomeActivity activity = (HomeActivity) getActivity();
                if(activity != null){
                    activity.shareBitmap(2,bitmap);
                }
            }

            @Override
            public void onClickQq(Bitmap bitmap) {

            }
        });
        dialog.show(getFragmentManager(),"shareDialog");
    }

    private void startShare() {
        ShareDialogFragment shareDialogFragment = new ShareDialogFragment();
        shareDialogFragment.setShareListen(new ShareListen() {
            @Override
            public void onClickWxChat(Bitmap bitmap) {
                HomeActivity activity = (HomeActivity) getActivity();
                if(activity != null){
                    activity.shareDefault(0);
                }
            }

            @Override
            public void onClickWxCircle(Bitmap bitmap) {
                HomeActivity activity = (HomeActivity) getActivity();
                if(activity != null){
                    activity.shareDefault(1);
                }
            }

            @Override
            public void onClickWeiBo(Bitmap bitmap) {
                HomeActivity activity = (HomeActivity) getActivity();
                if(activity != null){
                    activity.shareDefault(2);
                }
            }

            @Override
            public void onClickQq(Bitmap bitmap) {

            }
        });
        shareDialogFragment.show(getFragmentManager(), "share");
    }

    private void startFeedBack() {
        FeedBackFragment feedBackFragment = new FeedBackFragment();
        feedBackFragment.setFeedBackInterface(new FeedBackFragment.FeedBackInterface() {
            @Override
            public void onPostQuestion(FeedBackParameter feedBackParameter) {

                if (feedBackParameter == null) {
                    return;
                }

                DisposableObserver<FeedBackResult> disposableObserver = new DisposableObserver<FeedBackResult>() {
                    @Override
                    public void onNext(FeedBackResult feedBackResult) {
                        mToast.showToast("提交反馈成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mToast.showToast("提交反馈失败");
                        AppLogger.e( "feedback err = " + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                };
                B8Api.postFeedBackQuestion(disposableObserver, feedBackParameter.getType(), feedBackParameter.getContent(), feedBackParameter.getContact());

            }
        });
        feedBackFragment.show(getFragmentManager(), "feedback");
    }

    private void startSetting() {
        Intent intent = new Intent(getContext(), SettingActivity.class);
        startActivity(intent);
    }

    private void startCollection() {
        Intent intent = new Intent(getContext(), CollectionActivity.class);
        startActivity(intent);
    }
    private void startPersonalInfo() {
        Intent intent = new Intent(getContext(), PersonalInfoActivity.class);
        startActivity(intent);
    }

    private void check(final B8UpdateInfo b8UpdateInfo, final boolean hasUpdate, final boolean isForce, final boolean isSilent) {
        UpdateManager.create(getContext()).setChecker(new IUpdateChecker() {
            @Override
            public void check(ICheckAgent agent, String url) {
                Log.e("ezy.update", "checking");
                agent.setInfo("");
            }
        }).setUrl("").setManual(true).setNotifyId(Constants.NOTIFY_ID).setParser(new IUpdateParser() {
            @Override
            public UpdateInfo parse(String source) throws Exception {
                UpdateInfo info = new UpdateInfo();
                info.hasUpdate = hasUpdate;
                info.updateContent = b8UpdateInfo.getData().getVersionDesc();
                info.versionCode = b8UpdateInfo.getData().getVersionCode();
                info.versionName = b8UpdateInfo.getData().getVersionName();
                info.url = b8UpdateInfo.getData().getApkUrl();
                info.md5 = b8UpdateInfo.getData().getApkMd5();
                info.size = b8UpdateInfo.getData().getApkSize();
                info.isForce = isForce;
                info.isIgnorable = false;
                info.isSilent = isSilent;
                return info;
            }
        }).check();
    }


    @Override
    public void onUpdateInfo(B8UpdateInfo b8UpdateInfo, boolean silent) {
        if(b8UpdateInfo != null && b8UpdateInfo.getData() != null){
            mItemCheckUpdate.setRedDot(true);
            mItemCheckUpdate.setRightText(getResources().getString(R.string.find_new_version));
            if(b8UpdateInfo.getData().isIsNew()){
                mItemCheckUpdate.setRedDot(false);
                mItemCheckUpdate.setRightText(getResources().getString(R.string.already_new_version));
                if(!silent){
                    mToast.showToast("已是最新版本");
                }
            }else {
                mItemCheckUpdate.setRedDot(true);
                mItemCheckUpdate.setRightText(getResources().getString(R.string.find_new_version));
                if(!silent){
                    check(b8UpdateInfo, true, b8UpdateInfo.getData().isIsForce(), false);
                }
            }
        }
    }

    @Override
    public void onUpdateInfoError(Throwable e) {

    }

    @Override
    public void onUserInfo(UserInfoResponse userInfoResponse) {
        if(userInfoResponse != null && userInfoResponse.getData() != null){
            PreferenceHelper.saveUserInfo(getContext(),userInfoResponse);
            initUser();
        }
    }

    @Override
    public void onCoinStoreList(CoinStoreListResponse response) {
        if(response != null
                && response.isResult()
                && response.getData() != null){
            mCoinStoreDialog.setResponse(response);
            for (int i = 0; i < response.getData().size(); i++) {
                if(response.getData().get(i).getStatus() == 2){
                    mStoreId = response.getData().get(i).getId();
                    mStrIncome = response.getData().get(i).getProfit();
                    mStrIncomeRate = response.getData().get(i).getRate() + "%";
                    mStrTotalProperty = "总资产：" + response.getData().get(i).getProperty();
                    dispalyMoney();
                    mStoreTitleBtn.setText(response.getData().get(i).getName());
                    if(!TextUtils.isEmpty(mStrIncomeRate) && mStrIncomeRate.startsWith("1")){
                        mStoreShareBtn.setText("亏损了，求安慰");
                    }else {
                        mStoreShareBtn.setText("赚钱了，去装逼");
                    }
                }
            }

        }else {
            mCoinStoreDialog.setResponse(null);
        }
    }

    @Override
    public void onCoinStoreListError() {
        mCoinStoreDialog.setResponse(null);
    }


    private void dispalyMoney(){
        if(PreferenceHelper.getMoneyDisplay(getContext())){
            mTodayIncome.setText(mStrIncome);
            mTodayIncomeRate.setText(mStrIncomeRate);
            mTotalProperty.setText(mStrTotalProperty);
            mIsMoneyVisiable = true;
            mEyeView.setImageResource(R.drawable.eye_open);
        }else {
            mTodayIncome.setText("*****");
            mTodayIncomeRate.setText("*****");
            mTotalProperty.setText("总资产：*****");
            mIsMoneyVisiable = false;
            mEyeView.setImageResource(R.drawable.eye_close);
        }
    }
}
