package com.coin.b8.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coin.b8.R;
import com.coin.b8.model.DynamicImportNewsResponse;
import com.coin.b8.utils.CommonUtils;
import com.coin.b8.utils.GlideUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyi on 2018/7/9.
 */
public class DynamicImportNewsAdapter extends RecyclerView.Adapter{

    private DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private List<DynamicImportNewsResponse.DataBean.ContentBean> mList;

    public DynamicImportNewsAdapter(List<DynamicImportNewsResponse.DataBean.ContentBean> list) {
        mList = list;
    }
    public void addList(List<DynamicImportNewsResponse.DataBean.ContentBean> list){
        if(list != null && list.size() > 0){
            if(mList == null){
                mList = new ArrayList<>();
            }
            mList.addAll(list);
            notifyItemRangeInserted(mList.size() - list.size(), list.size());
        }
    }

    public void setList(List<DynamicImportNewsResponse.DataBean.ContentBean> list) {
        mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.important_news_list_item_normal, parent, false);
        return new NormalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
        DynamicImportNewsResponse.DataBean.ContentBean contentBean = mList.get(position);
        if(normalViewHolder != null){
            GlideUtil.setImageRes(normalViewHolder.itemView.getContext(),
                    normalViewHolder.mImageView,
                    contentBean.getPic(),
                    R.drawable.share_smal_icon,
                    R.drawable.share_smal_icon,
                    false);
            normalViewHolder.mTitle.setText(contentBean.getTitle());
            normalViewHolder.mTime.setText(CommonUtils.millis2String(contentBean.getPublishTime(),DEFAULT_FORMAT));
        }

    }

    @Override
    public int getItemCount() {
        if(mList != null){
            return mList.size();
        }
        return 0;
    }

    private class NormalViewHolder extends RecyclerView.ViewHolder{
        public TextView mTitle;
        public TextView mTime;
        public TextView mShareBtn;
        private ImageView mImageView;

        public NormalViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            mTime = itemView.findViewById(R.id.time);
            mShareBtn = itemView.findViewById(R.id.wrap_content);
            mImageView = itemView.findViewById(R.id.collect_image);
        }
    }
}
