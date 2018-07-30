package com.coin.b8.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.coin.b8.R;
import com.coin.b8.model.SelectCoinTypeListResponse;
import com.coin.b8.ui.adapter.SelectCoinTypeListAdapter;
import com.coin.b8.ui.iView.ISelectCoinTypeListView;
import com.coin.b8.ui.presenter.SelectCoinTypeListPresenter;
import com.coin.b8.ui.view.BlankView;
import com.coin.b8.utils.CommonUtils;
import com.coin.b8.utils.NetworkUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyi on 2018/7/10.
 */
public class SelectCoinTypeListActivity extends BaseActivity implements ISelectCoinTypeListView{
//    private SmartRefreshLayout mSmartRefreshLayout;
    private RecyclerView mRecyclerView;
    private String mCoinTypeTitle;
    private int mCoinTypeId;
    private SelectCoinTypeListAdapter mSelectCoinTypeListAdapter;
    private SelectCoinTypeListPresenter mSelectCoinTypeListPresenter;
    private DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private TextView mHeadContent;
    private TextView mHeadTime;
    private BlankView mBlankView;
    private CoordinatorLayout mCoordinatorLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_coin_type_list);
        mSelectCoinTypeListPresenter = new SelectCoinTypeListPresenter(this);
        handleIntent();
        setInitToolBar(mCoinTypeTitle);
        initView();
        CommonUtils.umengReport(this,"coin_subject_exposure");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSelectCoinTypeListPresenter.onDetach();
    }

    private void initView(){
        mCoordinatorLayout = findViewById(R.id.coordinator_content);
        mBlankView = findViewById(R.id.blank_view);
        mHeadContent = findViewById(R.id.des_content);
        mHeadTime = findViewById(R.id.update_time);
        mRecyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mSelectCoinTypeListAdapter = new SelectCoinTypeListAdapter();
        mRecyclerView.setAdapter(mSelectCoinTypeListAdapter);
        mSelectCoinTypeListAdapter.setCoinTypeTitle(mCoinTypeTitle);

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = getResources().getDimensionPixelSize(R.dimen.line_width);
            }
        });

        mCoordinatorLayout.setVisibility(View.GONE);
        mBlankView.showLoading();
        mBlankView.setEnableButton(false);
        mSelectCoinTypeListPresenter.getCoinTypeList(mCoinTypeId);

//        mSmartRefreshLayout = findViewById(R.id.refreshLayout);
//
//        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshLayout) {
//                mSelectCoinTypeListPresenter.getCoinTypeList(mCoinTypeId);
//            }
//        });
//        mSmartRefreshLayout.setEnableLoadMore(false);
//        mSmartRefreshLayout.autoRefresh(0);
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

    private void showBlank(){
        if(NetworkUtils.isConnected()){
            mBlankView.setImageViewTye(BlankView.BLANK_SELF);
            mBlankView.setDesc(getResources().getString(R.string.no_data));
        }else {
            mBlankView.setImageViewTye(BlankView.BLANK_WIFI);
            mBlankView.setDesc(getResources().getString(R.string.net_connect_fail));
        }
        mBlankView.setVisibility(View.VISIBLE);
        mCoordinatorLayout.setVisibility(View.GONE);
    }
    private void hideBlank(){
        mBlankView.setVisibility(View.GONE);
        mCoordinatorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCoinListSuccess(SelectCoinTypeListResponse selectCoinTypeListResponse) {
        if(selectCoinTypeListResponse.getCode() == 200 && selectCoinTypeListResponse.getData() != null){
            hideBlank();
            List<SelectCoinTypeListResponse.DataBean.ItemsBean> list = new ArrayList<>();
//            SelectCoinTypeListResponse.DataBean.ItemsBean itemsBean = new SelectCoinTypeListResponse.DataBean.ItemsBean();
//            itemsBean.setViewType(1);
//            list.add(itemsBean);
            if(selectCoinTypeListResponse.getData().getItems() != null){
                list.addAll(selectCoinTypeListResponse.getData().getItems());
            }
            mHeadContent.setText(selectCoinTypeListResponse.getData().getContent());
            mHeadTime.setText(CommonUtils.millis2String(selectCoinTypeListResponse.getData().getUpdateTime(),DEFAULT_FORMAT));
//            mSelectCoinTypeListAdapter.setHead(selectCoinTypeListResponse.getData().getContent(),
//                    selectCoinTypeListResponse.getData().getUpdateTime());
            mSelectCoinTypeListAdapter.setList(list);

        }else {
            showBlank();
        }
//        mSmartRefreshLayout.finishRefresh(0);
    }

    @Override
    public void onCoinListError() {
        showBlank();
//        mSmartRefreshLayout.finishRefresh(0);
    }
}
