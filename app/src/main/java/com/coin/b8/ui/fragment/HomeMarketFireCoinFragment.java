package com.coin.b8.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.coin.b8.R;
import com.coin.b8.model.MarketListSearchResponse;
import com.coin.b8.ui.adapter.HomeMarketNormalAdapter;
import com.coin.b8.ui.iView.IHomeMarketNormalView;
import com.coin.b8.ui.presenter.HomeMarketNormalPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.ref.WeakReference;
import java.util.Date;

/**
 * Created by zhangyi on 2018/7/11.
 */
public class HomeMarketFireCoinFragment extends BaseFragment implements IHomeMarketNormalView,View.OnClickListener {

    private static final int DEFAULT_SORT_TYPE = 1;
    private static final int DEFAULT_SORT = -1;

    private SmartRefreshLayout mSmartRefreshLayout;
    private RecyclerView mRecyclerView;
    private HomeMarketNormalAdapter mHomeMarketNormalAdapter;
    private HomeMarketNormalPresenter mHomeMarketNormalPresenter;
    private View mHeadViewValue;
    private View mHeadViewPrice;
    private View mHeadViewOneDayJump;
    private AppCompatCheckBox mCheckBoxValueUp;
    private AppCompatCheckBox mCheckBoxValueDown;
    private AppCompatCheckBox mCheckBoxPriceUp;
    private AppCompatCheckBox mCheckBoxPriceDown;
    private AppCompatCheckBox mCheckBoxJumpUp;
    private AppCompatCheckBox mCheckBoxJumpDown;

    private ClassicsHeader mClassicsHeader;

    //排序，1为升序，-1为降序 默认为-1
    private int mSort = DEFAULT_SORT;
    //1是交易额，2是交易量 ，3是当前价格，4是涨幅 5市值
    private int mSortType = DEFAULT_SORT_TYPE;

    private int mLimit = 20;
    private String mExchange = "火币Pro";

    private static final int MESSAGE_UPDATE = 100;
    private static final int MESSAGE_DELAY_TIME = 5000;
    private MyHandler mMyHandler;


    private class MyHandler extends Handler {
        private WeakReference<HomeMarketFireCoinFragment> mWeakReference;
        public MyHandler(HomeMarketFireCoinFragment fragment) {
            mWeakReference = new WeakReference<HomeMarketFireCoinFragment>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            HomeMarketFireCoinFragment fragment = mWeakReference.get();
            if(fragment == null){
                return;
            }
            switch (msg.what){
                case MESSAGE_UPDATE:
                    fragment.startUpdate();
                    break;
            }
        }
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeMarketNormalPresenter = new HomeMarketNormalPresenter(this);
        mMyHandler = new MyHandler(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_market_fire_coin;
    }

    @Override
    protected void initView(View view) {
        mHeadViewValue = view.findViewById(R.id.head_name_layout);
        mHeadViewPrice = view.findViewById(R.id.head_latest_layout);
        mHeadViewOneDayJump = view.findViewById(R.id.head_one_day_layout);
        mCheckBoxValueUp = view.findViewById(R.id.name_up);
        mCheckBoxValueDown = view.findViewById(R.id.name_down);
        mCheckBoxPriceUp = view.findViewById(R.id.latest_up);
        mCheckBoxPriceDown = view.findViewById(R.id.latest_down);
        mCheckBoxJumpUp = view.findViewById(R.id.one_day_up);
        mCheckBoxJumpDown = view.findViewById(R.id.one_day_down);

        mClassicsHeader  = view.findViewById(R.id.classic_head);

        mSmartRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRecyclerView = view.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = getResources().getDimensionPixelSize(R.dimen.line_width);
            }
        });
        mHomeMarketNormalAdapter = new HomeMarketNormalAdapter();
        mHomeMarketNormalAdapter.setCoinType(1);
        mRecyclerView.setAdapter(mHomeMarketNormalAdapter);

        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mMyHandler.removeMessages(MESSAGE_UPDATE);
                startRefresh();
            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mMyHandler.removeMessages(MESSAGE_UPDATE);
                startLoadMore();
            }
        });
        mSmartRefreshLayout.autoRefresh();
        mHeadViewValue.setOnClickListener(this);
        mHeadViewPrice.setOnClickListener(this);
        mHeadViewOneDayJump.setOnClickListener(this);
    }

    private void startRefresh() {
        int start = 1;
        mHomeMarketNormalPresenter.getRefreshList("", mSort, start, mLimit, mSortType, mExchange);
    }

    private void startLoadMore() {
        int start = mHomeMarketNormalAdapter.getItemCount() + 1;
        mHomeMarketNormalPresenter.getLoadMoreList("", mSort, start, mLimit, mSortType, mExchange);
    }

    public void startUpdate(){
        if(mSmartRefreshLayout.isRefreshing()
                || mSmartRefreshLayout.isLoading()
                || mRecyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE
                || !getUserVisibleHint()){
            mMyHandler.removeMessages(MESSAGE_UPDATE);
            mMyHandler.sendEmptyMessageDelayed(MESSAGE_UPDATE,MESSAGE_DELAY_TIME);
            return;
        }
//        Log.e("zy","startUpdate");
        int start = 1;
        int limit = mHomeMarketNormalAdapter.getItemCount();
        mHomeMarketNormalPresenter.getRefreshList("",mSort,start,limit,mSortType,mExchange);
    }

    @Override
    public void onPause() {
        super.onPause();
        mMyHandler.removeMessages(MESSAGE_UPDATE);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMyHandler.removeMessages(MESSAGE_UPDATE);
        mMyHandler.sendEmptyMessageDelayed(MESSAGE_UPDATE,MESSAGE_DELAY_TIME);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mHomeMarketNormalPresenter != null){
            mHomeMarketNormalPresenter.onDetach();
        }
        if(mMyHandler != null){
            mMyHandler.removeMessages(MESSAGE_UPDATE);
            mMyHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void onListSuccess(int type, MarketListSearchResponse response) {
        switch (type){
            case 0:{
                if (response != null
                        && response.getData() != null
                        && response.getData().size() > 0) {
                    mHomeMarketNormalAdapter.setList(response.getData());
                    mClassicsHeader.setLastUpdateTime(new Date());
                }
                mSmartRefreshLayout.finishRefresh(0);
                mMyHandler.removeMessages(MESSAGE_UPDATE);
                mMyHandler.sendEmptyMessageDelayed(MESSAGE_UPDATE,MESSAGE_DELAY_TIME);
            }
                break;
            case 1:{
                boolean success = false;
                if (response != null
                        && response.getData() != null
                        && response.getData().size() > 0) {
                    mHomeMarketNormalAdapter.addList(response.getData());
                    success = true;
                }
                mSmartRefreshLayout.finishLoadMore(0,success,false);
                mMyHandler.removeMessages(MESSAGE_UPDATE);
                mMyHandler.sendEmptyMessageDelayed(MESSAGE_UPDATE,MESSAGE_DELAY_TIME);
            }
                break;
        }
    }

    @Override
    public void onListError(int type) {
        switch (type){
            case 0:{
                mSmartRefreshLayout.finishRefresh(0);
                mMyHandler.removeMessages(MESSAGE_UPDATE);
                mMyHandler.sendEmptyMessageDelayed(MESSAGE_UPDATE,MESSAGE_DELAY_TIME);
            }
                break;
            case 1:{
                mSmartRefreshLayout.finishLoadMore(0,false,false);
                mMyHandler.removeMessages(MESSAGE_UPDATE);
                mMyHandler.sendEmptyMessageDelayed(MESSAGE_UPDATE,MESSAGE_DELAY_TIME);
            }
                break;
        }
    }

    /**
     * @param type 0：市值,1：最新价格,2：24小时涨幅
     */
    private void setCheckBoxFalse(int type) {
        switch (type) {
            case 0:
                mCheckBoxValueUp.setChecked(false);
                mCheckBoxValueDown.setChecked(false);
                break;
            case 1:
                mCheckBoxPriceUp.setChecked(false);
                mCheckBoxPriceDown.setChecked(false);
                break;
            case 2:
                mCheckBoxJumpUp.setChecked(false);
                mCheckBoxJumpDown.setChecked(false);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.head_name_layout:
                if(mSmartRefreshLayout.isRefreshing()){
                    return;
                }
                if (mCheckBoxValueUp.isChecked()) {
                    mCheckBoxValueUp.setChecked(false);
                    mCheckBoxValueDown.setChecked(false);
                    mSort = DEFAULT_SORT;
                    mSortType = DEFAULT_SORT_TYPE;
                } else if (mCheckBoxValueDown.isChecked()) {
                    mCheckBoxValueDown.setChecked(false);
                    mCheckBoxValueUp.setChecked(true);
                    mSortType = DEFAULT_SORT_TYPE;
                    mSort = 1;
                } else {
                    setCheckBoxFalse(1);
                    setCheckBoxFalse(2);
                    mCheckBoxValueDown.setChecked(true);
                    mCheckBoxValueUp.setChecked(false);
                    mSortType = DEFAULT_SORT_TYPE;
                    mSort = -1;
                }
                mSmartRefreshLayout.autoRefresh(0);
                break;
            case R.id.head_latest_layout:
                if(mSmartRefreshLayout.isRefreshing()){
                    return;
                }
                if (mCheckBoxPriceUp.isChecked()) {
                    mCheckBoxPriceUp.setChecked(false);
                    mCheckBoxPriceDown.setChecked(false);
                    mSort = DEFAULT_SORT;
                    mSortType = DEFAULT_SORT_TYPE;
                } else if (mCheckBoxPriceDown.isChecked()) {
                    mCheckBoxPriceDown.setChecked(false);
                    mCheckBoxPriceUp.setChecked(true);
                    mSortType = 3;
                    mSort = 1;
                } else {
                    setCheckBoxFalse(0);
                    setCheckBoxFalse(2);
                    mCheckBoxPriceDown.setChecked(true);
                    mCheckBoxPriceUp.setChecked(false);
                    mSortType = 3;
                    mSort = -1;
                }
                mSmartRefreshLayout.autoRefresh(0);
                break;
            case R.id.head_one_day_layout:
                if(mSmartRefreshLayout.isRefreshing()){
                    return;
                }
                if (mCheckBoxJumpUp.isChecked()) {
                    mCheckBoxJumpUp.setChecked(false);
                    mCheckBoxJumpDown.setChecked(false);
                    mSort = DEFAULT_SORT;
                    mSortType = DEFAULT_SORT_TYPE;
                } else if (mCheckBoxJumpDown.isChecked()) {
                    mCheckBoxJumpDown.setChecked(false);
                    mCheckBoxJumpUp.setChecked(true);
                    mSortType = 4;
                    mSort = 1;
                } else {
                    setCheckBoxFalse(0);
                    setCheckBoxFalse(1);
                    mCheckBoxJumpDown.setChecked(true);
                    mCheckBoxJumpUp.setChecked(false);
                    mSortType = 4;
                    mSort = -1;
                }
                mSmartRefreshLayout.autoRefresh(0);
                break;
        }
    }
}