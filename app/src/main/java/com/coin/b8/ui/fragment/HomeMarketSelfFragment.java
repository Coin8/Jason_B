package com.coin.b8.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.coin.b8.R;
import com.coin.b8.help.PreferenceHelper;
import com.coin.b8.model.CommonResponse;
import com.coin.b8.model.MarketSelfListResponse;
import com.coin.b8.ui.activity.SearchActivity;
import com.coin.b8.ui.adapter.HomeMarketSelfAdapter;
import com.coin.b8.ui.dialog.LoadingDialog;
import com.coin.b8.ui.iView.IHomeMarketSelfView;
import com.coin.b8.ui.presenter.HomeMarketSelfPresenter;
import com.coin.b8.ui.view.BlankView;
import com.coin.b8.utils.CommonUtils;
import com.coin.b8.utils.MyToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.ref.WeakReference;

/**
 * Created by zhangyi on 2018/7/11.
 */
public class HomeMarketSelfFragment extends BaseFragment implements IHomeMarketSelfView ,View.OnClickListener{

    private static final int DEFAULT_SORT_TYPE = 6;
    private static final int DEFAULT_SORT = 1;

    private MyToast mMyToast;
    private LoadingDialog mLoadingDialog;
    private BlankView mBlankView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private RecyclerView mRecyclerView;
    private HomeMarketSelfAdapter mHomeMarketSelfAdapter;
    private HomeMarketSelfPresenter mHomeMarketSelfPresenter;

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
    private ClassicsFooter mClassicsFooter;
    //排序，1为升序，-1为降序 默认为-1
    private int mSort = DEFAULT_SORT;
    //1是交易额，2是交易量 ，3是当前价格，4是涨幅 5市值 6:按自选置顶排序
    private int mSortType = DEFAULT_SORT_TYPE;

    private int mLimit = 20;


    private static final int MESSAGE_UPDATE = 100;
    private static final int MESSAGE_DELAY_TIME = 5000;
    private MyHandler mMyHandler;

    private boolean mIsFirst = true;


    private class MyHandler extends Handler {
        private WeakReference<HomeMarketSelfFragment> mWeakReference;
        public MyHandler(HomeMarketSelfFragment fragment) {
            mWeakReference = new WeakReference<HomeMarketSelfFragment>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            HomeMarketSelfFragment fragment = mWeakReference.get();
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
    protected int getLayoutId() {
        return R.layout.fragment_home_market_self;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeMarketSelfPresenter = new HomeMarketSelfPresenter(this);
        mMyHandler = new MyHandler(this);
    }

    @Override
    protected void initView(View view) {
        mMyToast = new MyToast(getActivity());
        mBlankView = view.findViewById(R.id.blank_view);
        mHeadViewValue = view.findViewById(R.id.head_name_layout);
        mHeadViewPrice = view.findViewById(R.id.head_latest_layout);
        mHeadViewOneDayJump = view.findViewById(R.id.head_one_day_layout);
        mCheckBoxValueUp = view.findViewById(R.id.name_up);
        mCheckBoxValueDown = view.findViewById(R.id.name_down);
        mCheckBoxPriceUp = view.findViewById(R.id.latest_up);
        mCheckBoxPriceDown = view.findViewById(R.id.latest_down);
        mCheckBoxJumpUp = view.findViewById(R.id.one_day_up);
        mCheckBoxJumpDown = view.findViewById(R.id.one_day_down);

        mBlankView.setImageViewTye(BlankView.BLANK_SELF);
        mBlankView.setButtonOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.startSearchActivity(v.getContext());
            }
        });
        mBlankView.setDesc("");
        mBlankView.setButtonText("添加自选行情");

        mClassicsHeader  = view.findViewById(R.id.classic_head);
        mClassicsFooter = view.findViewById(R.id.classic_footer);
        mClassicsFooter.REFRESH_FOOTER_FAILED = "没有更多数据了";
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
        mHomeMarketSelfAdapter = new HomeMarketSelfAdapter();
        mRecyclerView.setAdapter(mHomeMarketSelfAdapter);

        mHomeMarketSelfAdapter.setOnItemClickListen(new HomeMarketSelfAdapter.OnItemClickListen() {
            @Override
            public void onItemDeleteClick(MarketSelfListResponse.DataBean dataBean, int position) {
                if(dataBean != null){
                    startDelete(dataBean.getUcrid());
                }
            }

            @Override
            public void onItemTopClick(MarketSelfListResponse.DataBean dataBean, int position) {
                if(dataBean != null){
                    startTop(dataBean.getUcrid());
                }
            }
        });

        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                startRefresh();
            }
        });

        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                startLoadMore();
            }
        });

        mIsFirst = true;
        mSmartRefreshLayout.autoRefresh();


        mHeadViewValue.setOnClickListener(this);
        mHeadViewPrice.setOnClickListener(this);
        mHeadViewOneDayJump.setOnClickListener(this);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            CommonUtils.umengReport(getContext(),"hq_optional_exposure");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mMyHandler.removeMessages(MESSAGE_UPDATE);
    }

    @Override
    public void onResume() {
        super.onResume();
//        mMyHandler.removeMessages(MESSAGE_UPDATE);
        if(mIsFirst){
            mIsFirst = false;
        }else {
            mSmartRefreshLayout.autoRefresh();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mHomeMarketSelfPresenter != null){
            mHomeMarketSelfPresenter.onDetach();
        }
        hideLoading();
        if(mMyHandler != null){
            mMyHandler.removeMessages(MESSAGE_UPDATE);
            mMyHandler.removeCallbacksAndMessages(null);
        }
    }

    private void startRefresh(){
        String uid = PreferenceHelper.getUid(getContext());
        int start = 1;
        mHomeMarketSelfPresenter.getRefreshList(uid,mSort,start,mLimit,mSortType);
    }
    private void startLoadMore(){
        String uid = PreferenceHelper.getUid(getContext());
        int start = mHomeMarketSelfAdapter.getItemCount() + 1;
        mHomeMarketSelfPresenter.getLoadMoreList(uid,mSort,start,mLimit,mSortType);
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
        int start = 1;
        int limit = mHomeMarketSelfAdapter.getItemCount();
        String uid = PreferenceHelper.getUid(getContext());
        mHomeMarketSelfPresenter.getRefreshList(uid,mSort,start,limit,mSortType);
    }

    private void showBlank(){
        mBlankView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }
    private void hideBlank(){
        mBlankView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showLoading(){
        mLoadingDialog = new LoadingDialog();
        mLoadingDialog.setLoadingText("请稍后...");
        mLoadingDialog.show(getFragmentManager(),"loading");
    }

    private void hideLoading(){
        if(mLoadingDialog != null
                && mLoadingDialog.getDialog() != null
                && mLoadingDialog.getDialog().isShowing()){
            mLoadingDialog.dismiss();
        }
    }

    private void startTop(long ucid){
        showLoading();
        mHomeMarketSelfPresenter.toTop(ucid);
    }

    private void startDelete(long ucid){
        showLoading();
        mHomeMarketSelfPresenter.toDelete(ucid);
    }

    @Override
    public void onListSuccess(int type, MarketSelfListResponse response) {
        switch (type){
            case 0:
                if(response != null
                        && response.getData() != null
                        && response.getData().size() > 0){
                    hideBlank();
                    mHomeMarketSelfAdapter.setList(response.getData());
                }else {
                    showBlank();
                    mHomeMarketSelfAdapter.setList(null);
                }
                mSmartRefreshLayout.finishRefresh(0);
                mMyHandler.removeMessages(MESSAGE_UPDATE);
                mMyHandler.sendEmptyMessageDelayed(MESSAGE_UPDATE,MESSAGE_DELAY_TIME);
                break;
            case 1:
                if(response != null
                        && response.getData() != null
                        && response.getData().size() > 0){
                    mHomeMarketSelfAdapter.addList(response.getData());
                    mSmartRefreshLayout.finishLoadMore(0);
                }else {
                    mSmartRefreshLayout.finishLoadMore(0,false,false);
                }

                mMyHandler.removeMessages(MESSAGE_UPDATE);
                mMyHandler.sendEmptyMessageDelayed(MESSAGE_UPDATE,MESSAGE_DELAY_TIME);
                break;
        }
    }

    @Override
    public void onListError(int type) {
        switch (type){
            case 0:
                showBlank();
                mHomeMarketSelfAdapter.setList(null);
                mSmartRefreshLayout.finishRefresh(0,false);
                mMyHandler.removeMessages(MESSAGE_UPDATE);
                mMyHandler.sendEmptyMessageDelayed(MESSAGE_UPDATE,MESSAGE_DELAY_TIME);
                break;
            case 1:
                mSmartRefreshLayout.finishLoadMore(0,false,false);
                mMyHandler.removeMessages(MESSAGE_UPDATE);
                mMyHandler.sendEmptyMessageDelayed(MESSAGE_UPDATE,MESSAGE_DELAY_TIME);
                break;
        }
    }

    @Override
    public void onTopSuccess(CommonResponse response) {
        if(response != null && response.isResult()){
            mMyToast.showToast("置顶成功");
            startUpdate();
        }else {
            mMyToast.showToast("置顶失败");
        }
        hideLoading();
    }

    @Override
    public void onTopError() {
        mMyToast.showToast("置顶失败");
        hideLoading();
    }

    @Override
    public void onDeleteSuccess(CommonResponse response) {
        if(response != null && response.isResult()){
            mMyToast.showToast("删除成功");
            startUpdate();
        }else {
            mMyToast.showToast("删除失败");
        }
        hideLoading();
    }

    @Override
    public void onDeleteError() {
        mMyToast.showToast("删除失败");
        hideLoading();
    }

    /**
     *
     * @param type 0：市值,1：最新价格,2：24小时涨幅
     */
    private void setCheckBoxFalse(int type){
        switch (type){
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
        if(v == null){
            return;
        }
        switch (v.getId()){
            case R.id.head_name_layout:
                if(mSmartRefreshLayout.isRefreshing()){
                    return;
                }
                if(mCheckBoxValueUp.isChecked()){
                    mCheckBoxValueUp.setChecked(false);
                    mCheckBoxValueDown.setChecked(false);
                    mSort = DEFAULT_SORT;
                    mSortType = DEFAULT_SORT_TYPE;
                }else if(mCheckBoxValueDown.isChecked()){
                    mCheckBoxValueDown.setChecked(false);
                    mCheckBoxValueUp.setChecked(true);
                    mSortType = DEFAULT_SORT_TYPE;
                    mSort = 1;
                }else {
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
                if(mCheckBoxPriceUp.isChecked()){
                    mCheckBoxPriceUp.setChecked(false);
                    mCheckBoxPriceDown.setChecked(false);
                    mSort = DEFAULT_SORT;
                    mSortType = DEFAULT_SORT_TYPE;
                }else if(mCheckBoxPriceDown.isChecked()){
                    mCheckBoxPriceDown.setChecked(false);
                    mCheckBoxPriceUp.setChecked(true);
                    mSortType = 3;
                    mSort = 1;
                }else {
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
                if(mCheckBoxJumpUp.isChecked()){
                    mCheckBoxJumpUp.setChecked(false);
                    mCheckBoxJumpDown.setChecked(false);
                    mSort = DEFAULT_SORT;
                    mSortType = DEFAULT_SORT_TYPE;
                }else if(mCheckBoxJumpDown.isChecked()){
                    mCheckBoxJumpDown.setChecked(false);
                    mCheckBoxJumpUp.setChecked(true);
                    mSortType = 4;
                    mSort = 1;
                }else {
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
