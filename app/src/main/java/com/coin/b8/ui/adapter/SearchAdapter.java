package com.coin.b8.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coin.b8.R;
import com.coin.b8.model.ExchangeListResponse;
import com.coin.b8.model.HotCoinResponse;
import com.coin.b8.ui.activity.SearchOnExchangeActivity;
import com.coin.b8.ui.view.flowlayout.FlowLayout;
import com.coin.b8.ui.view.flowlayout.TagAdapter;
import com.coin.b8.ui.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyi on 2018/7/16.
 */
public class SearchAdapter extends RecyclerView.Adapter{
    public static final int SEARCH_TYPE_NORMAL = 0;
    public static final int SEARCH_TYPE_HISTORY = 1;
    public static final int SEARCH_TYPE_HOT_COIN = 2;
    public static final int SEARCH_TYPE_EXCHANGE_TITLE = 3;

    private List<ExchangeListResponse.DataBean> mList;

    private HotCoinResponse mHotCoinResponse;
    private List<String> mHistoryList;

    public interface OnBtnClickListen{
        void onHistoryDeleteClick();
        void onBtnClick(String text);
    }

    private OnBtnClickListen mOnBtnClickListen;

    public void setOnBtnClickListen(OnBtnClickListen onHistoryListen) {
        mOnBtnClickListen = onHistoryListen;
    }

    public void setHotCoinResponse(HotCoinResponse hotCoinResponse) {
        mHotCoinResponse = hotCoinResponse;
    }

    public void setList(List<ExchangeListResponse.DataBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void setHistoryList(List<String> historyList) {
        mHistoryList = historyList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = null;
        switch (viewType){
            case SEARCH_TYPE_NORMAL:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item_exchange, parent, false);
                viewHolder = new ExchangeViewHolder(view);
                break;
            case SEARCH_TYPE_HISTORY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item_history, parent, false);
                viewHolder = new HistoryViewHolder(view);
                break;
            case SEARCH_TYPE_HOT_COIN:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item_hot_coin, parent, false);
                viewHolder = new HotCoinViewHolder(view);
                break;
            case SEARCH_TYPE_EXCHANGE_TITLE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item_exhange_title, parent, false);
                viewHolder = new ExchangeTitleViewHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int type = holder.getItemViewType();
        ExchangeListResponse.DataBean dataBean = mList.get(position);
        switch (type){
            case SEARCH_TYPE_NORMAL:{
                ExchangeViewHolder exchangeViewHolder = (ExchangeViewHolder) holder;
                if(exchangeViewHolder != null){
                    exchangeViewHolder.mName.setText(dataBean.getName());
                    exchangeViewHolder.mRightDesc.setText(String.valueOf(dataBean.getSymbolCount()));
                    exchangeViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SearchOnExchangeActivity.startSearchOnExchangeActivity(v.getContext(),dataBean.getName());
                        }
                    });
                }
            }
                break;
            case SEARCH_TYPE_HISTORY:{

                HistoryViewHolder historyViewHolder = (HistoryViewHolder) holder;
                if(mHistoryList == null){
                    return;
                }
                historyViewHolder.mTagFlowLayout.setAdapter(new TagAdapter<String>(mHistoryList) {
                    @Override
                    public View getView(FlowLayout parent, int position, String s) {
                        TextView tv = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.flow_layout_tv,
                                parent, false);
                        tv.setText(s);
                        return tv;
                    }

                });

                historyViewHolder.mTagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        if(mOnBtnClickListen != null){
                            mOnBtnClickListen.onBtnClick(mHistoryList.get(position));
                        }
                        return true;
                    }
                });
            }
                break;
            case SEARCH_TYPE_HOT_COIN:{
                HotCoinViewHolder hotCoinViewHolder = (HotCoinViewHolder) holder;
                if(hotCoinViewHolder != null && mHotCoinResponse != null
                        && mHotCoinResponse.getData() != null
                        && mHotCoinResponse.getData().size() > 0){
                    hotCoinViewHolder.mTagFlowLayout.setAdapter(new TagAdapter<HotCoinResponse.DataBean>(mHotCoinResponse.getData()) {
                        @Override
                        public View getView(FlowLayout parent, int position, HotCoinResponse.DataBean o) {
                            TextView tv = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.flow_layout_tv,
                                    parent, false);
                            tv.setText(o.getName());
                            return tv;
                        }
                    });
                    hotCoinViewHolder.mTagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                        @Override
                        public boolean onTagClick(View view, int position, FlowLayout parent) {
                            if(mOnBtnClickListen != null){
                                mOnBtnClickListen.onBtnClick(mHotCoinResponse.getData().get(position).getName());
                            }
                            return true;
                        }
                    });
                }
            }
                break;
        }


    }

    @Override
    public int getItemCount() {
        if(mList != null){
            return mList.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getViewType();
    }

    private class HistoryViewHolder extends RecyclerView.ViewHolder{
        public TagFlowLayout mTagFlowLayout;
        public ImageView mImageView;
        public HistoryViewHolder(View itemView) {
            super(itemView);
            mTagFlowLayout = itemView.findViewById(R.id.tag_flow_layout);
            mImageView = itemView.findViewById(R.id.item_delete);
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnBtnClickListen != null){
                        mOnBtnClickListen.onHistoryDeleteClick();
                    }
                }
            });
        }
    }

    private class HotCoinViewHolder extends RecyclerView.ViewHolder{
        public TagFlowLayout mTagFlowLayout;
        public HotCoinViewHolder(View itemView) {
            super(itemView);
            mTagFlowLayout = itemView.findViewById(R.id.tag_flow_layout);
        }
    }

    private class ExchangeTitleViewHolder extends RecyclerView.ViewHolder{

        public ExchangeTitleViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class ExchangeViewHolder extends RecyclerView.ViewHolder{
        public TextView mName;
        public TextView mRightDesc;
        public ExchangeViewHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.name);
            mRightDesc = itemView.findViewById(R.id.right_des);
        }
    }
}
