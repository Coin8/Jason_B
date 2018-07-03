package com.coin.b8.ui.fragment;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coin.b8.R;
import com.coin.b8.app.AppLogger;
import com.coin.b8.constant.Constants;
import com.coin.b8.http.B8Api;
import com.coin.b8.model.B8UpdateInfo;
import com.coin.b8.model.FeedBackParameter;
import com.coin.b8.model.FeedBackResult;
import com.coin.b8.ui.activity.CollectionActivity;
import com.coin.b8.ui.activity.PersonalInfoActivity;
import com.coin.b8.ui.activity.SettingActivity;
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
    private WXShare mWXShare;
    private MyToast mToast;
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
        mUserLayout.setOnClickListener(this);
        mToast = new MyToast(getActivity());
        initShare();
        mItemCheckUpdate.setRightText(getResources().getString(R.string.already_new_version));
        mHomeMinePresenter = new HomeMinePresenterImpl(this);
        mHomeMinePresenter.getUpdateInfo(true);

        TextPaint tp = mViewUserName.getPaint();
        tp.setFakeBoldText(true);
        initHead();
    }

    private void initHead(){
        String path = getContext().getFilesDir() + Constants.LOCAL_HEAD_ICON_FILE_NAME;
        File file = new File(path);
        if(file.exists()){
            GlideUtil.setImageRes(getContext(),mUserImageView,file,R.drawable.icon_head,R.drawable.icon_head,true);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mWXShare != null) {
            mWXShare.register();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWXShare != null) {
            mWXShare.unregister();
        }
    }

    @Override
    public void onClick(View v) {
        if(v == null){
            return;
        }
        switch (v.getId()){
            case R.id.mine_invite_friends:
                startShare();
                break;
            case R.id.mine_collection:
                startCollection();
                break;
            case R.id.mine_early_warning_record:
                break;
            case R.id.mine_business_cooperation:
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

    private void initShare() {
        /**
         * 微信分享
         */
        mWXShare = new WXShare(getContext());
        mWXShare.setListener(new OnResponseListener() {
            @Override
            public void onSuccess() {
                // 分享成功
            }

            @Override
            public void onCancel() {
                // 分享取消
            }

            @Override
            public void onFail(String message) {
                // 分享失败
            }
        });
    }


    private void startShare() {
        ShareDialogFragment shareDialogFragment = new ShareDialogFragment();
        shareDialogFragment.setShareListen(new ShareListen() {
            @Override
            public void onClickWxChat() {
                if (mWXShare != null) {
                    mWXShare.shareImage(0, R.drawable.share_bg);
                }
            }

            @Override
            public void onClickWxCircle() {
                if (mWXShare != null) {
                    mWXShare.shareImage(1, R.drawable.share_bg);
                }
            }

            @Override
            public void onClickWeiBo() {

            }

            @Override
            public void onClickQq() {

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
}
