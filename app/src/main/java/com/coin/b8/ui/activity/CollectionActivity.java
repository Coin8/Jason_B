package com.coin.b8.ui.activity;


import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coin.b8.R;
import com.coin.b8.constant.Constants;
import com.coin.b8.help.PreferenceHelper;
import com.coin.b8.model.CancelCollectionResponse;
import com.coin.b8.model.CollectionListInfoResponse;
import com.coin.b8.ui.adapter.CollectionAdapter;
import com.coin.b8.ui.iView.IMyCollectionView;
import com.coin.b8.ui.presenter.MyCollectionPresenterImpl;
import com.coin.b8.ui.view.BlankView;
import com.coin.b8.utils.MySnackbar;
import com.coin.b8.utils.MyToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * Created by zhangyi on 2018/6/29.
 */
public class CollectionActivity extends BaseActivity implements IMyCollectionView{
    private RecyclerView mRecyclerView;
    private CollectionAdapter mCollectionAdapter;
    private BlankView mBlankView;
    private MyCollectionPresenterImpl mMyCollectionPresenter;
    private SmartRefreshLayout mSmartRefreshLayout;
    private ClassicsFooter mClassicsFooter;

    private long mCurrentPosition = 0;
    private MyToast mMyToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        setInitToolBar(getString(R.string.mine_collection));
        mMyCollectionPresenter = new MyCollectionPresenterImpl(this);
        initView();
    }

    private void initView(){
        mMyToast = new MyToast(this);

        mBlankView = findViewById(R.id.blank_view);
        mBlankView.setImageViewTye(BlankView.BLANK_COLLECT);

        mRecyclerView = findViewById(R.id.recyclerview);
        mCollectionAdapter = new CollectionAdapter(null);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mCollectionAdapter);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = getResources().getDimensionPixelSize(R.dimen.line_width);
            }
        });
        mCollectionAdapter.setItemClickListen(new CollectionAdapter.OnItemClickListen() {
            @Override
            public void onItemClick(CollectionListInfoResponse.DataBean dataBean, int position) {
                if(dataBean == null){
                    return;
                }
                switch (dataBean.getTargetType()){
                    case 1: {
                        //要闻

                        StringBuilder stringBuilder = new StringBuilder(Constants.IMPORTANT_NEWS_DETAIL_BASE_URL);
                        stringBuilder.append(dataBean.getArticleId());
                        Context context = mRecyclerView.getContext();
                        stringBuilder.append("&uid=").append(PreferenceHelper.getUid(context));
                        String imei = PreferenceHelper.getIMEI(context);
                        if(!TextUtils.isEmpty(imei)){
                            stringBuilder.append("&imei=").append(imei);
                        }
                        String token = PreferenceHelper.getToken(context);
                        if(!TextUtils.isEmpty(token)){
                            stringBuilder.append("&token=").append(token);
                        }
                        String web_url = stringBuilder.toString();
                        NativeDetailActivity.startNativeDetailActivity(mRecyclerView.getContext(), web_url );
                    }
                        break;
                    case 2:
                        //干货
                        break;
                    case 3: {
                        StringBuilder stringBuilder = new StringBuilder(Constants.IMPORTANT_NEWS_DETAIL_BASE_URL);
                        stringBuilder.append(dataBean.getArticleId());
                        stringBuilder.append("&type=banner");
                        Context context = mRecyclerView.getContext();
                        stringBuilder.append("&uid=").append(PreferenceHelper.getUid(context));
                        String imei = PreferenceHelper.getIMEI(context);
                        if(!TextUtils.isEmpty(imei)){
                            stringBuilder.append("&imei=").append(imei);
                        }
                        String token = PreferenceHelper.getToken(context);
                        if(!TextUtils.isEmpty(token)){
                            stringBuilder.append("&token=").append(token);
                        }

                        String web_url =  stringBuilder.toString();
//                        Log.e("zy","web_url = " + web_url);
                        NativeDetailActivity.startNativeDetailActivity(context, web_url );
                        //banner
                    }
                        break;
                }

            }

            @Override
            public void onItemCancelClick(CollectionListInfoResponse.DataBean dataBean, int position) {
                mCollectionAdapter.remove(position);
                if(dataBean != null){
                    mMyCollectionPresenter.deleteCollection(dataBean.getArticleId(),dataBean.getTargetType());
                }
                if(mCollectionAdapter.getItemCount() == 0){
                    setContent(null);
                }
            }
        });

        mSmartRefreshLayout = findViewById(R.id.smart_refresh_layout);
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

        mBlankView.setButtonOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRefresh();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSmartRefreshLayout.autoRefresh();
    }

    private void setBlankCollection(){
        mCollectionAdapter.setList(null);
        mSmartRefreshLayout.setVisibility(View.GONE);
        mBlankView.setVisibility(View.VISIBLE);
        mBlankView.setDesc("还没有收藏哦");
        mBlankView.setImageViewTye(BlankView.BLANK_COLLECT);
        mBlankView.setEnableButton(false);
    }

    private void setContent(CollectionListInfoResponse response){
        if(response != null
                && response.getData() != null
                && response.getData().size() > 0){
            mBlankView.setVisibility(View.GONE);
            mSmartRefreshLayout.setVisibility(View.VISIBLE);
            mCollectionAdapter.setList(response.getData());
            mCollectionAdapter.notifyDataSetChanged();
            mSmartRefreshLayout.finishRefresh(true);
        }else {
            setBlankCollection();
            mSmartRefreshLayout.finishRefresh(false);
        }
    }

    private void startRefresh(){
        mCurrentPosition = 0;
        String uid = PreferenceHelper.getUid(this);
        mMyCollectionPresenter.getCollectionList(uid,1,20);
    }

    private void startLoadMore(){
        String uid = PreferenceHelper.getUid(this);

        long start = mCollectionAdapter.getItemCount() + 1;

        mMyCollectionPresenter.getCollectionListMore(uid,start,20);
    }


    @Override
    public void onCollectionList(CollectionListInfoResponse collectionListInfoResponse) {
        setContent(collectionListInfoResponse);
    }

    @Override
    public void onCollectionError() {
        setContent(null);
        mSmartRefreshLayout.finishRefresh(false);
    }

    @Override
    public void onCollectionMore(CollectionListInfoResponse collectionListInfoResponse) {
        if(collectionListInfoResponse != null ){
            if(collectionListInfoResponse.getData() == null || collectionListInfoResponse.getData().size() == 0){
//                mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
//                MySnackbar.makeSnackBarBlack(mRecyclerView,"没有更多数据了");
            }else {
                mCurrentPosition = mCurrentPosition + 21;
                mCollectionAdapter.addList(collectionListInfoResponse.getData());
            }

        }

        mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public void onCollectionMoreError() {
        mMyToast.showToast("没有更多数据了");
//        mSmartRefreshLayout.setNoMoreData(true);
//        MySnackbar.makeSnackBarBlack(mRecyclerView,"没有更多数据了");
        mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public void onDeleteCollectionSuccess(CancelCollectionResponse cancelCollectionResponse) {

    }

    @Override
    public void onDeleteCollectionError() {

    }
}
