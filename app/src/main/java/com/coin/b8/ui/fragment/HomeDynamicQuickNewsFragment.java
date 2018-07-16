package com.coin.b8.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.coin.b8.R;
import com.coin.b8.model.QuickNewsResponse;
import com.coin.b8.ui.adapter.DynamicQuickNewsAdapter;
import com.coin.b8.ui.iView.IQuickNewsView;
import com.coin.b8.ui.presenter.DynamicQuickNewsPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * Created by zhangyi on 2018/6/28.
 */
public class HomeDynamicQuickNewsFragment extends BaseFragment implements IQuickNewsView{

    private SmartRefreshLayout mSmartRefreshLayout;
    private RecyclerView mRecyclerView;
    private DynamicQuickNewsAdapter mDynamicQuickNewsAdapter;
    private DynamicQuickNewsPresenter mDynamicQuickNewsPresenter;
    private int mCurrentPage = 1;

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
    protected void initView(View view) {
        mSmartRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRecyclerView = view.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mDynamicQuickNewsAdapter = new DynamicQuickNewsAdapter(null);
        mRecyclerView.setAdapter(mDynamicQuickNewsAdapter);

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
        mSmartRefreshLayout.finishRefresh();
    }

    @Override
    public void onNewsError() {
        mSmartRefreshLayout.finishRefresh();
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
        mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public void onNewsMoreError() {
        mSmartRefreshLayout.finishLoadMore();
    }
}
