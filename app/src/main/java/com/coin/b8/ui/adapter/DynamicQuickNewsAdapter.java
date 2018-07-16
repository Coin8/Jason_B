package com.coin.b8.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coin.b8.R;
import com.coin.b8.model.QuickNewsResponse;
import com.coin.b8.utils.CommonUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyi on 2018/7/10.
 */
public class DynamicQuickNewsAdapter extends RecyclerView.Adapter{
    private DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private List<QuickNewsResponse.DataBean.ContentBean> mContentBeanList;

    public DynamicQuickNewsAdapter(List<QuickNewsResponse.DataBean.ContentBean> contentBeanList) {
        mContentBeanList = contentBeanList;
    }

    public void setContentBeanList(List<QuickNewsResponse.DataBean.ContentBean> contentBeanList) {
        mContentBeanList = contentBeanList;
        notifyDataSetChanged();
    }

    public void addList(List<QuickNewsResponse.DataBean.ContentBean> list){
        if(list != null && list.size() > 0){
            if(mContentBeanList == null){
                mContentBeanList = new ArrayList<>();
            }
            mContentBeanList.addAll(list);
            notifyItemRangeInserted(mContentBeanList.size() - list.size(), list.size());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quick_news_list_item_normal, parent, false);
        return new NormalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
        QuickNewsResponse.DataBean.ContentBean contentBean = mContentBeanList.get(position);
        if(normalViewHolder != null && contentBean != null){
            normalViewHolder.mTime.setText(CommonUtils.millis2String(contentBean.getPublishTime()*1000,DEFAULT_FORMAT));
            normalViewHolder.mContent.setText(contentBean.getContentWithoutTag());
            normalViewHolder.mContent.setMaxLines(3);
        }
    }

    @Override
    public int getItemCount() {
        if(mContentBeanList != null){
            return mContentBeanList.size();
        }
        return 0;
    }

    private class NormalViewHolder extends RecyclerView.ViewHolder{
        public TextView mTime;
        public TextView mContent;
        public TextView mShareBtn;

        private int mMaxLine = 3;

        public NormalViewHolder(View itemView) {
            super(itemView);
            mTime = itemView.findViewById(R.id.time);
            mContent = itemView.findViewById(R.id.content);
            mShareBtn = itemView.findViewById(R.id.share_btn);
            mContent.setMaxLines(3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mMaxLine == 3){
                        mMaxLine = Integer.MAX_VALUE;
                    }else {
                        mMaxLine = 3;
                    }
                    mContent.setMaxLines(mMaxLine);
                }
            });
        }
    }
}
