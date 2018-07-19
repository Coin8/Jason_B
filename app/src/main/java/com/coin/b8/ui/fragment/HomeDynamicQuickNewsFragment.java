package com.coin.b8.ui.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.coin.b8.R;
import com.coin.b8.model.QuickNewsResponse;
import com.coin.b8.ui.activity.HomeActivity;
import com.coin.b8.ui.adapter.DynamicQuickNewsAdapter;
import com.coin.b8.ui.dialog.ShareDialogFragment;
import com.coin.b8.ui.iView.IQuickNewsView;
import com.coin.b8.ui.listen.ShareListen;
import com.coin.b8.ui.presenter.DynamicQuickNewsPresenter;
import com.coin.b8.utils.CommonUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by zhangyi on 2018/6/28.
 */
public class HomeDynamicQuickNewsFragment extends BaseFragment implements IQuickNewsView{

    private SmartRefreshLayout mSmartRefreshLayout;
    private RecyclerView mRecyclerView;
    private DynamicQuickNewsAdapter mDynamicQuickNewsAdapter;
    private DynamicQuickNewsPresenter mDynamicQuickNewsPresenter;
    private int mCurrentPage = 1;
    private DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_dynamic_quick;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDynamicQuickNewsPresenter = new DynamicQuickNewsPresenter(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            CommonUtils.umengReport(getContext(),"dynamic_flash_exposure");
        }
    }

    @Override
    protected void initView(View view) {
        mSmartRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRecyclerView = view.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mDynamicQuickNewsAdapter = new DynamicQuickNewsAdapter(null);
        mRecyclerView.setAdapter(mDynamicQuickNewsAdapter);

        mDynamicQuickNewsAdapter.setOnItemClickListen(new DynamicQuickNewsAdapter.OnItemClickListen() {
            @Override
            public void onShare(QuickNewsResponse.DataBean.ContentBean contentBean) {
                startShare(contentBean);
            }
        });

        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mCurrentPage = 1;
                mDynamicQuickNewsPresenter.getQuickNews();
            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                int page = mCurrentPage+1;
                mDynamicQuickNewsPresenter.getQuickNewsMore(page);
            }
        });

        mSmartRefreshLayout.autoRefresh();

    }

    private void startShare(QuickNewsResponse.DataBean.ContentBean contentBean){
        if(contentBean == null){
            return;
        }

        ShareDialogFragment shareDialogFragment = new ShareDialogFragment();
        shareDialogFragment.setDisplayQuickNews(true);
        String time = CommonUtils.getWeek(contentBean.getPublishTime()*1000) + " "
                + CommonUtils.millis2String(contentBean.getPublishTime()*1000,DEFAULT_FORMAT);
        shareDialogFragment.setQuickNewDesc(contentBean.getContentWithoutTag(), time);
        shareDialogFragment.setShareListen(new ShareListen() {
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
        shareDialogFragment.show(getFragmentManager(), "share");


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mDynamicQuickNewsPresenter.onDetach();
    }

    @Override
    public void onNewsSuccess(QuickNewsResponse quickNewsResponse) {
        if(quickNewsResponse != null
                && quickNewsResponse.getData() != null
                && quickNewsResponse.getData().getContent() != null
                && quickNewsResponse.getData().getContent().size() > 0){
            mDynamicQuickNewsAdapter.setContentBeanList(quickNewsResponse.getData().getContent());
        }
        mSmartRefreshLayout.finishRefresh(0);
    }

    @Override
    public void onNewsError() {
        mSmartRefreshLayout.finishRefresh(0);
    }

    @Override
    public void onNewsMoreSuccess(QuickNewsResponse quickNewsResponse) {
        if(quickNewsResponse != null
                && quickNewsResponse.getData() != null
                && quickNewsResponse.getData().getContent() != null
                && quickNewsResponse.getData().getContent().size() > 0){
            mDynamicQuickNewsAdapter.addList(quickNewsResponse.getData().getContent());
            mCurrentPage++;
        }
        mSmartRefreshLayout.finishLoadMore(0);
    }

    @Override
    public void onNewsMoreError() {
        mSmartRefreshLayout.finishLoadMore(0);
    }
}
