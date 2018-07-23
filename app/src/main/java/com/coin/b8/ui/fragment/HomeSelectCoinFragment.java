package com.coin.b8.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.coin.b8.R;
import com.coin.b8.model.BannerResponse;
import com.coin.b8.model.SelectCoinItemModel;
import com.coin.b8.model.SelectCoinListResponse;
import com.coin.b8.ui.adapter.SelectCoinListAdapter;
import com.coin.b8.ui.iView.ISelectCoinView;
import com.coin.b8.ui.presenter.SelectCoinPresenterImpl;
import com.coin.b8.utils.CommonUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyi on 2018/6/28.
 */
public class HomeSelectCoinFragment extends BaseFragment implements ISelectCoinView{
    private SelectCoinPresenterImpl mSelectCoinPresenter;
    private SmartRefreshLayout mSmartRefreshLayout;
    private RecyclerView mRecyclerView;
    private SelectCoinListAdapter mSelectCoinListAdapter;
    private boolean mIsBannerOk = false;
    private boolean mIsListOk = false;
    private SelectCoinListResponse mSelectCoinListResponse;
    private BannerResponse mBannerResponse;

    public static HomeSelectCoinFragment getInstance() {
        HomeSelectCoinFragment fragment = new HomeSelectCoinFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_select_coin;
    }

    @Override
    protected void initView(View view) {
        mSmartRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRecyclerView = view.findViewById(R.id.recyclerview);
        mSmartRefreshLayout.setEnableLoadMore(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mSelectCoinListAdapter = new SelectCoinListAdapter(null);
        mRecyclerView.setAdapter(mSelectCoinListAdapter);

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

            }
        });

        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                startRefresh();
            }
        });
        mSmartRefreshLayout.autoRefresh();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSelectCoinPresenter = new SelectCoinPresenterImpl(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            CommonUtils.umengReport(getContext()," coin_exposure");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSelectCoinPresenter.onDetach();
    }

    private void startRefresh(){
        mIsBannerOk = false;
        mIsListOk = false;
        mSelectCoinPresenter.getBanner();
        mSelectCoinPresenter.getSelectCoinList();
    }

    private void onRefresh(){

        if(!mIsListOk || !mIsBannerOk){
            return;
        }
        List<SelectCoinItemModel> list = new ArrayList<>();
        if(mBannerResponse != null
                && mBannerResponse.getData() != null
                && mBannerResponse.getData().size() > 0){
            SelectCoinItemModel model = new SelectCoinItemModel();
            model.setType(SelectCoinListAdapter.SELECT_HEAD);
            model.setBannerDatas(mBannerResponse.getData());
            list.add(model);
        }

        if(mSelectCoinListResponse != null
                && mSelectCoinListResponse.getData() != null){
            for (int i = 0; i < mSelectCoinListResponse.getData().size(); i++) {
                SelectCoinListResponse.DataBean dataBean = mSelectCoinListResponse.getData().get(i);
                if(dataBean != null && dataBean.getItems() != null && dataBean.getItems().size() > 0){
                    SelectCoinItemModel selectCoinItemModel = new SelectCoinItemModel();
                    selectCoinItemModel.setType(SelectCoinListAdapter.SELECT_TITLE);
                    selectCoinItemModel.setTitle(dataBean.getTypeName());
                    list.add(selectCoinItemModel);
                    for (int i1 = 0; i1 < dataBean.getItems().size(); i1++) {
                        SelectCoinItemModel model1 = new SelectCoinItemModel();
                        model1.setType(SelectCoinListAdapter.SELECT_NORMAL);
                        model1.setItemsBean(dataBean.getItems().get(i1));
                        list.add(model1);
                    }
                }
            }
        }

        mSelectCoinListAdapter.setSelectCoinItemModelList(list);
        mSelectCoinListAdapter.notifyDataSetChanged();
        mSmartRefreshLayout.finishRefresh(0);

    }


    @Override
    public void onSelectCoinSuccess(SelectCoinListResponse selectCoinListResponse) {
        mSelectCoinListResponse = selectCoinListResponse;
        mIsListOk = true;
        onRefresh();
    }

    @Override
    public void onSelectCoinFail() {
        mIsListOk = true;
        onRefresh();
    }

    @Override
    public void onBannerSuccess(BannerResponse response) {
        mBannerResponse = response;
        mIsBannerOk = true;
        onRefresh();
    }

    @Override
    public void onBannerFail() {
        mIsBannerOk = true;
        onRefresh();
    }
}
