package com.coin.b8.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.coin.b8.R;
import com.coin.b8.model.CollectionListInfoResponse;
import com.coin.b8.utils.CommonUtils;
import com.coin.b8.utils.GlideUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyi on 2018/7/2.
 */
public class CollectionAdapter extends RecyclerView.Adapter{

    private List<CollectionListInfoResponse.DataBean> mList;
    private DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public interface OnItemClickListen{
        void onItemClick(CollectionListInfoResponse.DataBean dataBean,int position);
        void onItemCancelClick(CollectionListInfoResponse.DataBean dataBean,int position);
    }

    private OnItemClickListen mOnItemClickListen;

    public CollectionAdapter(List<CollectionListInfoResponse.DataBean> list) {
        mList = list;
    }

    public void setList(List<CollectionListInfoResponse.DataBean> list) {
        mList = list;
    }

    public void remove(int position){
        if(mList == null){
            return;
        }
        mList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mList.size() - position);
    }
    public void addList(List<CollectionListInfoResponse.DataBean> list){
        if(list != null && list.size() > 0){
            if(mList == null){
                mList = new ArrayList<>();
            }
            mList.addAll(list);
            notifyItemRangeInserted(mList.size() - list.size(), list.size());
        }
    }

    public void setItemClickListen(OnItemClickListen onItemClickListen){
        mOnItemClickListen = onItemClickListen;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_list_item, parent, false);
        return new CollectionVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CollectionVH collectionVH = (CollectionVH) holder;
        CollectionListInfoResponse.DataBean dataBean = mList.get(position);
        if(dataBean == null){
            return;
        }
        if(collectionVH != null ){
            int radius = collectionVH.itemView.getContext().getResources().getDimensionPixelSize(R.dimen.dp_4);
            collectionVH.title.setText(dataBean.getTitle() );
            collectionVH.time.setText(CommonUtils.millis2String(dataBean.getCreateTime(),DEFAULT_FORMAT));
            GlideUtil.setCornerImageRes(holder.itemView.getContext(),
                    collectionVH.mImageView,
                    dataBean.getPic(),
                    radius,
                    R.drawable.pic_default);
            collectionVH.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnItemClickListen != null){
                        mOnItemClickListen.onItemClick(dataBean,position);
                    }
                }
            });
            collectionVH.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnItemClickListen != null){
                        mOnItemClickListen.onItemCancelClick(dataBean,position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(mList != null){
            return mList.size();
        }
        return 0;
    }


    class CollectionVH extends RecyclerView.ViewHolder {
        View content;
        TextView btnDelete;
        TextView title;
        TextView time;
        ImageView mImageView;

        public CollectionVH(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            title = itemView.findViewById(R.id.title);
            time = itemView.findViewById(R.id.time);
            mImageView = itemView.findViewById(R.id.collect_image);
        }
    }
}
