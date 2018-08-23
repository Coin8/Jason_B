package com.coin.b8.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;

import com.coin.b8.R;
import com.coin.b8.db.SearchHistoryDB;
import com.coin.b8.help.PreferenceHelper;
import com.coin.b8.model.AddMarketSelfResponse;
import com.coin.b8.model.CommonResponse;
import com.coin.b8.model.ExchangeListResponse;
import com.coin.b8.model.HotCoinResponse;
import com.coin.b8.model.MarketListSearchResponse;
import com.coin.b8.ui.adapter.SearchResultAdapter;
import com.coin.b8.ui.dialog.LoadingDialog;
import com.coin.b8.ui.divider.RecycleViewDivider;
import com.coin.b8.ui.iView.ISearchView;
import com.coin.b8.ui.presenter.SearchPresenterImpl;
import com.coin.b8.ui.view.BlankView;
import com.coin.b8.ui.view.EditTextClear;
import com.coin.b8.utils.MyToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.ref.WeakReference;

/**
 * Created by zhangyi on 2018/7/17.
 */
public class SearchOnExchangeActivity extends BaseActivity implements ISearchView{
    private String mExchange = "";

    private LoadingDialog mLoadingDialog;
    private EditTextClear mEditTextClear;
    private SmartRefreshLayout mSmartRefreshLayout;
    private RecyclerView mSearchResultRecyclerView;
    private SearchResultAdapter mSearchResultAdapter;
    private BlankView mBlankView;
    private ClassicsFooter mClassicsFooter;

    private SearchPresenterImpl mSearchPresenter;
    private MyToast mMyToast;
    private SearchHistoryDB mSearchHistoryDB;

    private final int MESSAGE_SEARCH = 100;
    private MyHandler mMyHandler;

    private class MyHandler extends Handler {
        private WeakReference<SearchOnExchangeActivity> mWeakReference;

        public MyHandler(SearchOnExchangeActivity activity) {
            mWeakReference = new WeakReference<SearchOnExchangeActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            SearchOnExchangeActivity activity = mWeakReference.get();
            if(activity == null){
                return;
            }
            switch (msg.what){
                case MESSAGE_SEARCH:
                    activity.handlerSearch();
                    break;
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleData();
        mSearchPresenter = new SearchPresenterImpl(this);
        mSearchHistoryDB = SearchHistoryDB.getIntstance();
        setContentView(R.layout.activity_search_on_exchange);
        mMyToast = new MyToast(this);
        mMyHandler = new MyHandler(this);
        setInitToolBar(mExchange);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mMyHandler != null){
            mMyHandler.removeMessages(MESSAGE_SEARCH);
            mMyHandler.removeCallbacksAndMessages(null);
        }
        if(mSearchPresenter != null){
            mSearchPresenter.onDetach();
        }
    }

    private void initView(){
        mEditTextClear = findViewById(R.id.search_edit);
        mSmartRefreshLayout = findViewById(R.id.refreshLayout);
        mClassicsFooter = findViewById(R.id.classic_footer);
        mClassicsFooter.REFRESH_FOOTER_FAILED = "没有更多数据了";
        mSearchResultRecyclerView = findViewById(R.id.search_result_recyclerview);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        mSearchResultRecyclerView.setLayoutManager(linearLayoutManager1);
//        mSearchResultRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
//                outRect.bottom = getResources().getDimensionPixelSize(R.dimen.line_width);
//            }
//        });
        mSearchResultRecyclerView.addItemDecoration(new RecycleViewDivider(this,
                LinearLayoutManager.VERTICAL,
                getResources().getDimensionPixelSize(R.dimen.line_width),
                getResources().getColor(R.color.middle_line_color)
        ));
        mSearchResultAdapter = new SearchResultAdapter();
        mSearchResultRecyclerView.setAdapter(mSearchResultAdapter);
        mBlankView = findViewById(R.id.search_blank_view);
        mBlankView.setImageViewTye(BlankView.BLANK_SEARCH);
        mBlankView.setDesc("暂无数据");
        mBlankView.setEnableButton(false);
        mSearchResultAdapter.setOnItemClickListen(new SearchResultAdapter.OnItemClickListen() {
            @Override
            public void onItemClick(MarketListSearchResponse.DataBean dataBean) {
                if(dataBean == null){
                    return;
                }
                if(dataBean.getIsSelect() == 1){
                    startDeleteSelf(dataBean.getUcrid());
                }else {
                    startAddSelf(dataBean.getExchangeName(),dataBean.getSymbol());
                }

            }

            @Override
            public void onWholeItemClick() {
                String string = mEditTextClear.getText().toString();
                if(!TextUtils.isEmpty(string)){
                    mSearchHistoryDB.deleteHistory(string);
                    mSearchHistoryDB.insertHistory(string);
                }
            }
        });
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                startSearch(mEditTextClear.getText().toString());
            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                startSearchMore(mEditTextClear.getText().toString());
            }
        });

        mEditTextClear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                String text = mEditTextClear.getText().toString();
//                if(TextUtils.isEmpty(text)){
//                    startSearch("");
//                }else {
//                    startSearch(text);
//                }
                mMyHandler.removeMessages(MESSAGE_SEARCH);
                mMyHandler.sendEmptyMessageDelayed(MESSAGE_SEARCH,200);
            }
        });

        mEditTextClear.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    String string = mEditTextClear.getText().toString();
                    if(!TextUtils.isEmpty(string)){
                        startSearch(mEditTextClear.getText().toString());
                    }
                }
                return false;
            }
        });

        showLoading();
        mSearchPresenter.search("",1,20,mExchange);

    }

    private void handlerSearch(){
        String text = mEditTextClear.getText().toString();
        if(TextUtils.isEmpty(text)){
            text = "";
        }
        startSearch(text);
    }

    private void startSearch(String text){
//        showLoading();
        mSearchPresenter.search(text,1,20,mExchange);
//        if(!TextUtils.isEmpty(text)){
//            mSearchHistoryDB.deleteHistory(text);
//            mSearchHistoryDB.insertHistory(text);
//        }
    }

    private void startSearchMore(String text){
        int start = mSearchResultAdapter.getItemCount() + 1;
        mSearchPresenter.searchMore(text,start,20,mExchange);
    }


    private void showLoading(){
        hideLoading();
        mLoadingDialog = new LoadingDialog();
        mLoadingDialog.show(getSupportFragmentManager(),"loading");
    }

    private void hideLoading(){
        if(mLoadingDialog != null){
            mLoadingDialog.dismiss();
        }
    }

    private void updateSearchData(MarketListSearchResponse response){
        mSmartRefreshLayout.finishRefresh(0);
        hideLoading();
        if(response != null && response.getData() != null && response.getData().size() > 0){
            mBlankView.setVisibility(View.GONE);
            mSearchResultRecyclerView.setVisibility(View.VISIBLE);
            mSearchResultAdapter.setList(response.getData());
        }else {
            mSearchResultAdapter.setList(null);
            mBlankView.setVisibility(View.VISIBLE);
            mSearchResultRecyclerView.setVisibility(View.GONE);
        }

    }

    private void updateSearchDataMore(MarketListSearchResponse response){
        if(response != null && response.getData() != null && response.getData().size() > 0){
            mBlankView.setVisibility(View.GONE);
            mSearchResultRecyclerView.setVisibility(View.VISIBLE);
            mSearchResultAdapter.addList(response.getData());
            mSmartRefreshLayout.finishLoadMore(0,true,false);
        }else {
            mSmartRefreshLayout.finishLoadMore(0,false,false);
        }
    }

    private void startAddSelf(String exchangeName,String symbol){
        showLoading();
        String uid = PreferenceHelper.getUid(this);
        mSearchPresenter.addSelf(uid,exchangeName,symbol);
    }

    private void startDeleteSelf(long ucrid){
        showLoading();
        mSearchPresenter.deleteSelf(ucrid);
    }

    private void refreshCurrent(){
        int limit = mSearchResultAdapter.getItemCount();
        mSearchPresenter.search(mEditTextClear.getText().toString(),1,limit,mExchange);
    }


    private void handleData(){
        Intent intent = getIntent();
        if(intent != null){
            mExchange = intent.getStringExtra("exchange");
        }
    }

    public static void startSearchOnExchangeActivity(Context context,String exchange){
        if(context == null){
            return;
        }
        Intent intent = new Intent(context,SearchOnExchangeActivity.class);
        intent.putExtra("exchange",exchange);
        context.startActivity(intent);
    }


    @Override
    public void onHotCoinSuccess(HotCoinResponse response) {

    }

    @Override
    public void onHotCoinError() {

    }

    @Override
    public void onExchangeSuccess(ExchangeListResponse response) {

    }

    @Override
    public void onExchangeError() {

    }

    @Override
    public void onSearchSuccess(MarketListSearchResponse response) {
        updateSearchData(response);
    }

    @Override
    public void onSearchError() {
        updateSearchData(null);
    }

    @Override
    public void onSearchMoreSuccess(MarketListSearchResponse response) {
        updateSearchDataMore(response);
    }

    @Override
    public void onSearchMoreError() {
        updateSearchDataMore(null);
    }

    @Override
    public void onAddSelf(AddMarketSelfResponse response) {
        if(response != null && response.isResult()){
            mMyToast.showToast("添加成功");
        }else {
            mMyToast.showToast("添加失败");
        }
        refreshCurrent();
    }

    @Override
    public void onDeleteSelf(CommonResponse response) {
        if(response != null && response.isResult()){
            mMyToast.showToast("删除成功");
        }else {
            mMyToast.showToast("删除失败");
        }
        refreshCurrent();
    }
}
