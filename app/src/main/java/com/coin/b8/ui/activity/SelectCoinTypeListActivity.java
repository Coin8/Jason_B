package com.coin.b8.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.coin.b8.R;
import com.coin.b8.model.SelectCoinTypeListResponse;
import com.coin.b8.ui.adapter.SelectCoinTypeListAdapter;
import com.coin.b8.ui.iView.ISelectCoinTypeListView;
import com.coin.b8.ui.presenter.SelectCoinTypeListPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyi on 2018/7/10.
 */
public class SelectCoinTypeListActivity extends BaseActivity implements ISelectCoinTypeListView{
    private SmartRefreshLayout mSmartRefreshLayout;
    private RecyclerView mRecyclerView;
    private String mCoinTypeTitle;
    private int mCoinTypeId;
    private SelectCoinTypeListAdapter mSelectCoinTypeListAdapter;
    private SelectCoinTypeListPresenter mSelectCoinTypeListPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_coin_type_list);
        mSelectCoinTypeListPresenter = new SelectCoinTypeListPresenter(this);
        handleIntent();
        setInitToolBar(mCoinTypeTitle);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSelectCoinTypeListPresenter.onDetach();
    }

    private void initView(){
        mSmartRefreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mSelectCoinTypeListAdapter = new SelectCoinTypeListAdapter();
        mRecyclerView.setAdapter(mSelectCoinTypeListAdapter);

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = getResources().getDimensionPixelSize(R.dimen.line_width);
            }
        });

        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mSelectCoinTypeListPresenter.getCoinTypeList(mCoinTypeId);
            }
        });
        mSmartRefreshLayout.setEnableLoadMore(false);
        mSmartRefreshLayout.autoRefresh(0);
    }

    private void handleIntent(){
        Intent intent = getIntent();
        if(intent != null){
            mCoinTypeTitle = intent.getStringExtra("name_title");
            mCoinTypeId = intent.getIntExtra("name_id",1);
        }
    }

    public static void startSelectCoinTypeListActivity(Context context,String title,int id){
        if(context== null){
            return;
        }
        Intent intent = new Intent(context,SelectCoinTypeListActivity.class);
        intent.putExtra("name_title",title);
        intent.putExtra("name_id",id);
        context.startActivity(intent);
    }

    @Override
    public void onCoinListSuccess(SelectCoinTypeListResponse selectCoinTypeListResponse) {
        if(selectCoinTypeListResponse.getCode() == 200 && selectCoinTypeListResponse.getData() != null){
            List<SelectCoinTypeListResponse.DataBean.ItemsBean> list = new ArrayList<>();
            SelectCoinTypeListResponse.DataBean.ItemsBean itemsBean = new SelectCoinTypeListResponse.DataBean.ItemsBean();
            itemsBean.setViewType(1);
            list.add(itemsBean);
            if(selectCoinTypeListResponse.getData().getItems() != null){
                list.addAll(selectCoinTypeListResponse.getData().getItems());
            }
            mSelectCoinTypeListAdapter.setHead(selectCoinTypeListResponse.getData().getContent(),
                    selectCoinTypeListResponse.getData().getUpdateTime());
            mSelectCoinTypeListAdapter.setList(list);
        }
        mSmartRefreshLayout.finishRefresh(0);
    }

    @Override
    public void onCoinListError() {
        mSmartRefreshLayout.finishRefresh(0);
    }
}
