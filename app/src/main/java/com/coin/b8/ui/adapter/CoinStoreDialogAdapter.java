package com.coin.b8.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coin.b8.R;
import com.coin.b8.model.CoinStoreListResponse;

import java.util.List;

/**
 * Created by zhangyi on 2018/8/20.
 */
public class CoinStoreDialogAdapter extends RecyclerView.Adapter{

    private List<CoinStoreListResponse.DataBean> mList;
    private boolean mIsEditEnabled = false;
    private OnItemEventListen mOnItemEventListen;

    public static final int TYPE_CREATE = 0;
    public static final int TYPE_DELETE = 1;
    public static final int TYPE_RENAME = 2;
    public static final int TYPE_ITEM = 3;

    public interface OnItemEventListen{
        void onCreateStoreClick();
        void onItemClick(int type,int pos,long id);
    }


    public void setOnItemEventListen(OnItemEventListen onItemEventListen) {
        mOnItemEventListen = onItemEventListen;
    }

    public void setEditEnabled(boolean editEnabled) {
        mIsEditEnabled = editEnabled;
        notifyDataSetChanged();
    }

    public void setList(List<CoinStoreListResponse.DataBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder ;
        View view;
        if(viewType == 1){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coin_store_dialog_footer_item, parent, false);
            viewHolder = new FootViewHold(view);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coin_store_dialog_normal_item, parent, false);
            viewHolder = new ViewHold(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder == null){
            return;
        }
        CoinStoreListResponse.DataBean dataBean = mList.get(position);
        if(dataBean == null){
            return;
        }

        switch (holder.getItemViewType()){
            case 0:
                ViewHold viewHold = (ViewHold) holder;
                if(viewHold == null){
                    return;
                }
                viewHold.mTitle.setText(dataBean.getName());
                if(dataBean.getStatus() == 2){
                    viewHold.mState.setSelected(true);
                }else {
                    viewHold.mState.setSelected(false);
                }
                if(mIsEditEnabled){
                    viewHold.mDeleteBtn.setVisibility(View.VISIBLE);
                    viewHold.mRenameBtn.setVisibility(View.VISIBLE);
                    viewHold.mState.setVisibility(View.GONE);
                }else {
                    viewHold.mDeleteBtn.setVisibility(View.GONE);
                    viewHold.mRenameBtn.setVisibility(View.GONE);
                    viewHold.mState.setVisibility(View.VISIBLE);
                }
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(v == null){
                            return;
                        }
                        int type = TYPE_ITEM;
                        switch (v.getId()){
                            case R.id.rename_btn:
                                type = TYPE_RENAME;
                                break;
                            case R.id.delete_btn:
                                type = TYPE_DELETE;
                                break;
                            case R.id.store_item_id:
                                type = TYPE_ITEM;
                                break;
                        }

                        if(mOnItemEventListen != null){
                            mOnItemEventListen.onItemClick(type,position,dataBean.getId());
                        }
                    }
                };
                viewHold.mRenameBtn.setOnClickListener(listener);
                viewHold.mDeleteBtn.setOnClickListener(listener);
                viewHold.itemView.setOnClickListener(listener);
                break;
            case 1:
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mOnItemEventListen != null){
                            mOnItemEventListen.onCreateStoreClick();
                        }
                    }
                });
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        if(mList != null){
            return mList.size();
        }
        return 0;
    }

    private class FootViewHold extends RecyclerView.ViewHolder {
        public FootViewHold(View itemView) {
            super(itemView);
        }
    }

    private class ViewHold extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public TextView mState;
        public TextView mRenameBtn;
        public ImageView mDeleteBtn;
        public ViewHold(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            mState = itemView.findViewById(R.id.status_btn);
            mRenameBtn = itemView.findViewById(R.id.rename_btn);
            mDeleteBtn = itemView.findViewById(R.id.delete_btn);
        }
    }

}
