package com.coin.b8.ui.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.coin.b8.R;
import com.coin.b8.constant.Constants;
import com.coin.b8.help.PreferenceHelper;
import com.coin.b8.model.DynamicImportNewsResponse;
import com.coin.b8.model.BannerResponse;
import com.coin.b8.ui.activity.HomeActivity;
import com.coin.b8.ui.adapter.DynamicImportNewsAdapter;
import com.coin.b8.ui.dialog.ShareDialogFragment;
import com.coin.b8.ui.iView.IDynamicImportView;
import com.coin.b8.ui.listen.ShareListen;
import com.coin.b8.ui.presenter.DynamicImportPresenter;
import com.coin.b8.ui.view.BlankView;
import com.coin.b8.utils.CommonUtils;
import com.coin.b8.utils.NetworkUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyi on 2018/6/28.
 */
public class HomeDynamicImportant extends BaseFragment implements IDynamicImportView{
    private BlankView mBlankView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private RecyclerView mRecyclerView;
    private DynamicImportPresenter mDynamicImportPresenter;
    private int mCurrentPage = 1;
    private DynamicImportNewsAdapter mDynamicImportNewsAdapter;

    private DynamicImportNewsResponse mDynamicImportNewsResponse;
    private BannerResponse mBannerResponse;

    private boolean mIsBannerOk = false;
    private boolean mIsListOk = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_dynamic_important;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDynamicImportPresenter = new DynamicImportPresenter(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            CommonUtils.umengReport(getContext(),"dynamic_news_exposure");
        }
    }

    @Override
    protected void initView(View view) {
        mSmartRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRecyclerView = view.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mDynamicImportNewsAdapter = new DynamicImportNewsAdapter(null);
        mRecyclerView.setAdapter(mDynamicImportNewsAdapter);

        mBlankView = view.findViewById(R.id.blank_view);
        mBlankView.setImageViewTye(BlankView.BLANK_SELF);
        mBlankView.setButtonOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRefresh(true);
            }
        });

        mDynamicImportNewsAdapter.setItemOnclickListen(new DynamicImportNewsAdapter.ItemOnclickListen() {
            @Override
            public void onShareClick(DynamicImportNewsResponse.DataBean.ContentBean contentBean) {
                startShare(contentBean);
            }
        });

        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                startRefresh(false);
            }
        });

        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                startLoadMore();
            }
        });
        startRefresh(true);

    }

    private String getShareUrl(long id){
        StringBuilder stringBuilder = new StringBuilder(Constants.IMPORTANT_NEWS_SHARE_BASE_URL);
        stringBuilder.append("&id=");
        stringBuilder.append(id);
        Context context = getContext();
        stringBuilder.append("&uid=").append(PreferenceHelper.getUid(context));
        String imei = PreferenceHelper.getIMEI(context);
        if(!TextUtils.isEmpty(imei)){
            stringBuilder.append("&imei=").append(imei);
        }
        String token = PreferenceHelper.getToken(context);
        if(!TextUtils.isEmpty(token)){
            stringBuilder.append("&token=").append(token);
        }
        return stringBuilder.toString();
    }

    private void startShare(DynamicImportNewsResponse.DataBean.ContentBean contentBean){
        if(contentBean == null){
            return;
        }

        ShareDialogFragment shareDialogFragment = new ShareDialogFragment();
        shareDialogFragment.setDisplayImage(false);
        shareDialogFragment.setShareListen(new ShareListen() {
            @Override
            public void onClickWxChat(Bitmap bitmap) {
                HomeActivity activity = (HomeActivity) getActivity();
                if(activity != null){

                    String url = getShareUrl(contentBean.getId());
                    activity.shareWebUrl(0,contentBean.getTitle(),contentBean.getAbs(),url );
                }
            }

            @Override
            public void onClickWxCircle(Bitmap bitmap) {
                HomeActivity activity = (HomeActivity) getActivity();
                if(activity != null){
                    String url = getShareUrl(contentBean.getId());
                    activity.shareWebUrl(1,contentBean.getTitle(),contentBean.getAbs(),url );
                }
            }

            @Override
            public void onClickWeiBo(Bitmap bitmap) {
                HomeActivity activity = (HomeActivity) getActivity();
                if(activity != null){
                    String url = getShareUrl(contentBean.getId());
                    activity.shareWebUrl(2,contentBean.getTitle(),contentBean.getAbs(),url );
                }
            }

            @Override
            public void onClickQq(Bitmap bitmap) {

            }
        });
        shareDialogFragment.show(getFragmentManager(), "share");


    }

    private void showFragmentLoading(){
        mSmartRefreshLayout.setVisibility(View.GONE);
        mBlankView.showLoading();
        mBlankView.setVisibility(View.VISIBLE);
    }

    private void showBlank(){
        if(NetworkUtils.isConnected()){
            mBlankView.setImageViewTye(BlankView.BLANK_SELF);
            mBlankView.setDesc(getResources().getString(R.string.no_data));
        }else {
            mBlankView.setImageViewTye(BlankView.BLANK_WIFI);
            mBlankView.setDesc("网络连接失败");
        }
        mBlankView.setVisibility(View.VISIBLE);
        mSmartRefreshLayout.setVisibility(View.GONE);

    }

    private void hideBlank(){
        mBlankView.setVisibility(View.GONE);
        mSmartRefreshLayout.setVisibility(View.VISIBLE);
    }


    private void startLoadMore(){
        int page = mCurrentPage + 1;
        mDynamicImportPresenter.getImportNewsLoadMore(page);
    }

    private void startRefresh( boolean isShowLoading){
        if(isShowLoading){
            showFragmentLoading();
        }
        mIsBannerOk = false;
        mIsListOk = false;
        mCurrentPage = 1;
        mDynamicImportPresenter.getImportNews();
        mDynamicImportPresenter.getBanner();
    }

    private void onRefresh(){
        if(!mIsListOk || !mIsBannerOk){
            return;
        }
        List<DynamicImportNewsResponse.DataBean.ContentBean> list = new ArrayList<>();
        if(mBannerResponse != null
                && mBannerResponse.getData() != null
                && mBannerResponse.getData().size() > 0){
            DynamicImportNewsResponse.DataBean.ContentBean contentBean = new DynamicImportNewsResponse.DataBean.ContentBean();
            contentBean.setViewType(1);
            list.add(0,contentBean);
            mDynamicImportNewsAdapter.setBannerBeanList(mBannerResponse.getData());
        }

        if(mDynamicImportNewsResponse != null
                && mDynamicImportNewsResponse.getData() != null
                && mDynamicImportNewsResponse.getData().getContent() != null
                && mDynamicImportNewsResponse.getData().getContent().size() > 0){
            list.addAll(mDynamicImportNewsResponse.getData().getContent());
        }
        mDynamicImportNewsAdapter.setList(list);
        mDynamicImportNewsAdapter.notifyDataSetChanged();
        mSmartRefreshLayout.finishRefresh(0);
        if(list.size() > 0){
            hideBlank();
        }else {
            showBlank();
        }

    }

    @Override
    public void onNewsSuccess(DynamicImportNewsResponse dynamicImportNewsResponse) {
        mDynamicImportNewsResponse = dynamicImportNewsResponse;
        mIsListOk = true;
        onRefresh();

    }

    @Override
    public void onNewError() {
        mIsListOk = true;
        onRefresh();
//        mSmartRefreshLayout.finishRefresh(0,false);
    }

    @Override
    public void onNewsLoadMoreSuccess(DynamicImportNewsResponse dynamicImportNewsResponse) {
        if(dynamicImportNewsResponse != null
                && dynamicImportNewsResponse.getData() != null
                && dynamicImportNewsResponse.getData().getContent() != null
                && dynamicImportNewsResponse.getData().getContent().size() > 0){
            mDynamicImportNewsAdapter.addList(dynamicImportNewsResponse.getData().getContent());
            mCurrentPage++;
        }
        mSmartRefreshLayout.finishLoadMore(0);
    }

    @Override
    public void onNewsLoadMoreError() {
        mSmartRefreshLayout.finishLoadMore(0);
    }

    @Override
    public void onBannerSuccess(BannerResponse importantNewsBannerResponse) {

        mBannerResponse = importantNewsBannerResponse;
        mIsBannerOk = true;
        onRefresh();
    }

    @Override
    public void onBannerError() {
        mIsBannerOk = true;
        onRefresh();
    }
}
