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
import com.coin.b8.help.PreferenceHelper;
import com.coin.b8.model.DeleteYuJingResponse;
import com.coin.b8.model.ModifyYuJingResponse;
import com.coin.b8.model.YujingListResponse;
import com.coin.b8.ui.adapter.YuJingRecordAdapter;
import com.coin.b8.ui.iView.IYuJingRecordView;
import com.coin.b8.ui.presenter.YuJingRecordPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * Created by zhangyi on 2018/7/10.
 */
public class YuJingRecordActivity extends BaseActivity implements IYuJingRecordView{
    private YuJingRecordPresenter mYuJingRecordPresenter;
    private SmartRefreshLayout mSmartRefreshLayout;
    private RecyclerView mRecyclerView;
    private YuJingRecordAdapter mYuJingRecordAdapter;
    private boolean mEnableDelete = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yujing_record);
        setInitToolBar("预警记录");
        mYuJingRecordPresenter = new YuJingRecordPresenter(this);
        initView();
    }

    private void initView(){

        mSmartRefreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = getResources().getDimensionPixelSize(R.dimen.feedback_marin_20);
            }
        });

        mYuJingRecordAdapter = new YuJingRecordAdapter();
        mRecyclerView.setAdapter(mYuJingRecordAdapter);
        mYuJingRecordAdapter.setOnYuJingItemClickListen(new YuJingRecordAdapter.OnYuJingItemClickListen() {
            @Override
            public void onSwitchBtnClick(long id, boolean value) {
                int status = value? 1:0;
                mYuJingRecordPresenter.modifyYuJing(PreferenceHelper.getUid(YuJingRecordActivity.this),
                       id,
                       status );
            }

            @Override
            public void onDelete(long id,int pos) {
                mYuJingRecordPresenter.deleteYuJing(PreferenceHelper.getUid(YuJingRecordActivity.this),
                        id);
                mYuJingRecordAdapter.remove(pos);
            }
        });

        mToolbarRightTitle.setVisibility(View.VISIBLE);
        mToolbarRightTitle.setText("编辑");
        mToolbarRightTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEnableDelete){
                    mToolbarRightTitle.setText("完成");
                    mEnableDelete = false;
                }else {
                    mToolbarRightTitle.setText("编辑");
                    mEnableDelete = true;
                }
                mYuJingRecordAdapter.setDeleteEnable(mEnableDelete);
            }
        });


        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                startRefresh();
            }
        });
        mSmartRefreshLayout.setEnableLoadMore(false);
        mSmartRefreshLayout.autoRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mYuJingRecordPresenter.onDetach();
    }

    private void startRefresh(){
        mYuJingRecordPresenter.getYuJingList(PreferenceHelper.getUid(this));
    }


    @Override
    public void onYuJingListSuccess(YujingListResponse response) {
        if(response != null && response.getData() != null){
            mYuJingRecordAdapter.setList(response.getData());
        }
        mSmartRefreshLayout.finishRefresh(0);
    }

    @Override
    public void onYuJingListError() {
        mSmartRefreshLayout.finishRefresh(0);
    }

    @Override
    public void onDeleteSuccess(DeleteYuJingResponse response) {

    }

    @Override
    public void onDeleteError() {

    }

    @Override
    public void onModifySuccess(ModifyYuJingResponse response) {

    }

    @Override
    public void onModifyError() {

    }

    public static void startYuJingRecordActivity(Context context){
        if(context == null){
            return;
        }
        context.startActivity(new Intent(context,YuJingRecordActivity.class));
    }
}
