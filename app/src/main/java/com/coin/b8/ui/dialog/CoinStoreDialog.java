package com.coin.b8.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.coin.b8.R;
import com.coin.b8.help.PreferenceHelper;
import com.coin.b8.model.CoinStoreListResponse;
import com.coin.b8.ui.activity.PersonalInfoActivity;
import com.coin.b8.ui.adapter.CoinStoreDialogAdapter;
import com.coin.b8.ui.iView.IStoreView;
import com.coin.b8.ui.presenter.StoreDialogPresenterImpl;
import com.coin.b8.ui.view.BlankView;
import com.coin.b8.utils.MyToast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyi on 2018/8/20.
 */
public class CoinStoreDialog implements View.OnClickListener,IStoreView{
    private CoinStoreListResponse mResponse;
    private Context mContext;
    private PopupWindow mCoinStoreWindow;
    private TextView mCloseBtn;
    private TextView mEditBtn;
    private RecyclerView mRecyclerView;
    private BlankView mBlankView;
    private CoinStoreDialogAdapter mCoinStoreDialogAdapter;
    private boolean mIsEditEnable = false;
    private StoreDialogPresenterImpl mPresenter;
    private MyToast mToast;
    private boolean mIsEnableSelfUpdate = true;

    public interface DisMissListen{
        void onDissMiss();
    }

    private DisMissListen mDisMissListen;

    public CoinStoreDialog(Context context,CoinStoreListResponse response) {
        mResponse = response;
        mContext = context;
        mPresenter = new StoreDialogPresenterImpl(this);
        init(context);
    }

    private void init(Context context){
        if(context == null){
            return;
        }
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        mToast = new MyToast(layoutInflater);
        View contentView = LayoutInflater.from(context).inflate(R.layout.coin_store_dialog, null);
        mBlankView = contentView.findViewById(R.id.blank_view);
        mEditBtn = contentView.findViewById(R.id.edit_btn);
        mCloseBtn = contentView.findViewById(R.id.close_btn);
        mRecyclerView = contentView.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mCoinStoreDialogAdapter = new CoinStoreDialogAdapter();
        mRecyclerView.setAdapter(mCoinStoreDialogAdapter);

        int y = context.getResources().getDimensionPixelSize(R.dimen.dp_135);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int height = dm.heightPixels;

        mCoinStoreWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                height-y, true);
        mCoinStoreWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.corner_bg_coin_store));
        mCoinStoreWindow.setTouchable(true);
        mCoinStoreWindow.setFocusable(true);
        mCoinStoreWindow.setOutsideTouchable(true);
        mCoinStoreWindow.setAnimationStyle(R.style.CoinStoreDialog);
        mCoinStoreWindow.setContentView(contentView);
        mCloseBtn.setOnClickListener(this);
        mEditBtn.setOnClickListener(this);

        mCoinStoreWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if(mDisMissListen != null){
                    mDisMissListen.onDissMiss();
                }
                switchEditBtnStatus(true);
            }
        });

        mCoinStoreDialogAdapter.setOnItemEventListen(new CoinStoreDialogAdapter.OnItemEventListen() {
            @Override
            public void onCreateStoreClick() {
                showCreateDialog();
            }

            @Override
            public void onItemClick(int type, int pos, long id) {
                if(type == CoinStoreDialogAdapter.TYPE_DELETE){
                    deleteStore(id);
                }else if(type == CoinStoreDialogAdapter.TYPE_RENAME){
                    showRenameDialog(id);
                }else if(type == CoinStoreDialogAdapter.TYPE_ITEM){
                    switchStore(id);
                }
            }
        });
    }

    private void switchStore(long id){
        mPresenter.switchCoinStore(id);
        mCoinStoreWindow.dismiss();
    }

    private void deleteStore(long id){
        showLoading();
        mPresenter.deleteCoinStore(id);
    }

    private void showRenameDialog(long id){
        CoinStoreInputDialog inputDialog = new CoinStoreInputDialog(mContext, "组合名称", new CoinStoreInputDialog.InputDialogOkClickListener() {
            @Override
            public void onClick(Dialog dialog, String inputText) {
                if(TextUtils.isEmpty(inputText) || TextUtils.isEmpty(inputText.trim())){
                    mToast.showToast("不能输入空字符");
                    return;
                }
                if(inputText.length() > 10){
                    mToast.showToast("请输入6个字符以内");
                    return;
                }
                showLoading();
                mPresenter.renameCoinStore(id,inputText);

            }
        });
        inputDialog.showDialog();
    }


    private void showCreateDialog(){
        CoinStoreInputDialog inputDialog = new CoinStoreInputDialog(mContext, "组合名称",  new CoinStoreInputDialog.InputDialogOkClickListener() {
            @Override
            public void onClick(Dialog dialog, String inputText) {
                if(TextUtils.isEmpty(inputText) || TextUtils.isEmpty(inputText.trim())){
                    mToast.showToast("不能输入空字符");
                    return;
                }
                if(inputText.length() > 10){
                    mToast.showToast("请输入6个字符以内");
                    return;
                }
                showLoading();
                mPresenter.createCoinStore(inputText);

            }
        });
        inputDialog.showDialog();
    }

    private void showLoading(){
        mBlankView.setVisibility(View.VISIBLE);
        mBlankView.showLoading();
    }
    private void hideLoading(){
        mBlankView.setVisibility(View.GONE);
    }

    public void setDismissListen(DisMissListen listen){
        mDisMissListen = listen;
    }

    public void setEnableSelfUpdate(boolean enableSelfUpdate) {
        mIsEnableSelfUpdate = enableSelfUpdate;
    }

    public void setResponse(CoinStoreListResponse response){
        if(response != null && response.getData() != null){
            CoinStoreListResponse.DataBean dataBean = new CoinStoreListResponse.DataBean();
            dataBean.setViewType(1);
            response.getData().add(dataBean);
            mCoinStoreDialogAdapter.setList(response.getData());
        }else {
            CoinStoreListResponse.DataBean dataBean = new CoinStoreListResponse.DataBean();
            dataBean.setViewType(1);
            List<CoinStoreListResponse.DataBean> list = new ArrayList<>();
            list.add(dataBean);
            mCoinStoreDialogAdapter.setList(list);
        }
    }

    public void showDialog(View parent) {
        if(parent == null || mContext == null){
            return;
        }
        if(mIsEnableSelfUpdate){
            mPresenter.getCoinStoreList();
        }
        int y = mContext.getResources().getDimensionPixelSize(R.dimen.dp_135);
        if (mCoinStoreWindow != null) {
            mCoinStoreWindow.showAtLocation(parent, Gravity.TOP, 0, y);
        }
    }


    @Override
    public void onClick(View v) {
        if(v == null){
            return;
        }
        switch (v.getId()){
            case R.id.close_btn:
                mCoinStoreWindow.dismiss();
                break;
            case R.id.edit_btn:
                switchEditBtnStatus(false);
                break;
        }
    }

    private void switchEditBtnStatus(boolean isForceInit){
        if(isForceInit){
            mEditBtn.setText("编辑");
            mIsEditEnable = false;
            mCoinStoreDialogAdapter.setEditEnabled(mIsEditEnable);
            return;
        }
        if(mIsEditEnable){
            mEditBtn.setText("编辑");
            mIsEditEnable = false;
            mCoinStoreDialogAdapter.setEditEnabled(mIsEditEnable);
        }else {
            mEditBtn.setText("完成");
            mIsEditEnable = true;
            mCoinStoreDialogAdapter.setEditEnabled(mIsEditEnable);
        }

    }

    public void destroy(){
        if(mPresenter != null){
            mPresenter.onDetach();
        }
    }

    @Override
    public void onSwitchStoreSuccess() {
        mPresenter.getCoinStoreList();
    }

    @Override
    public void onSwitchStoreError(String message) {

    }

    @Override
    public void onCreateStoreSuccess() {
        mPresenter.getCoinStoreList();
    }

    @Override
    public void onCreateStoreError(String message) {
        hideLoading();
        mToast.showToast(message);
    }

    @Override
    public void onDeleteStoreSuccess() {
        mPresenter.getCoinStoreList();
    }

    @Override
    public void onDeleteStoreError(String message) {
        hideLoading();
        mToast.showToast(message);
    }

    @Override
    public void onRenameStoreSuccess() {
        mPresenter.getCoinStoreList();
    }

    @Override
    public void onRenameStoreError(String message) {
        hideLoading();
        mToast.showToast(message);
    }

    @Override
    public void onStoreListSuccess(CoinStoreListResponse response) {
        if(response != null
                && response.isResult()
                && response.getData() != null){
            setResponse(response);
        }else {
            setResponse(null);
        }
        hideLoading();
    }

    @Override
    public void onStoreListError() {
        hideLoading();
    }
}
