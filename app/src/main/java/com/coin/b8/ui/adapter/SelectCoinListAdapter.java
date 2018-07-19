package com.coin.b8.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coin.b8.R;
import com.coin.b8.model.SelectCoinItemModel;
import com.coin.b8.ui.activity.SelectCoinTypeListActivity;
import com.coin.b8.utils.EventReportUtil;

import java.util.List;

/**
 * Created by zhangyi on 2018/7/9.
 */
public class SelectCoinListAdapter extends RecyclerView.Adapter{
    public static final int SELECT_NORMAL = 0;
    public static final int SELECT_HEAD = 1;
    public static final int SELECT_TITLE = 2;

    private List<SelectCoinItemModel> mSelectCoinItemModelList;
    private int [] mIcons = {
            R.drawable.icon_xuanbi_1,
            R.drawable.icon_xuanbi_2,
            R.drawable.icon_xuanbi_3,
            R.drawable.icon_xuanbi_4,
            R.drawable.icon_xuanbi_5,
            R.drawable.icon_xuanbi_6,
            R.drawable.icon_xuanbi_7,
            R.drawable.icon_xuanbi_8,
            R.drawable.icon_xuanbi_9
    };

    private int mCount = 0;

    public SelectCoinListAdapter(List<SelectCoinItemModel> selectCoinItemModelList) {
        mSelectCoinItemModelList = selectCoinItemModelList;
    }

    public void setSelectCoinItemModelList(List<SelectCoinItemModel> selectCoinItemModelList) {
        mSelectCoinItemModelList = selectCoinItemModelList;
    }

    private int getCurrentResId(){
        if(mCount >= mIcons.length){
            mCount = 0;
        }
        int id = mIcons[mCount];
        mCount++;
        return id;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case SELECT_HEAD:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_coin_listitem_head, parent, false);
                viewHolder = new BannerViewHolder(view);
                break;
            case SELECT_TITLE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_coin_listitem_title, parent, false);
                viewHolder = new TitleViewHolder(view);
                break;
            case SELECT_NORMAL:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_coin_listitem_normal, parent, false);
                viewHolder = new NormalViewHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int type = holder.getItemViewType();
        switch (type){
            case SELECT_HEAD:
                break;
            case SELECT_TITLE:{
                TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
                if(titleViewHolder != null){
                    SelectCoinItemModel selectCoinItemModel = mSelectCoinItemModelList.get(position);
                    titleViewHolder.mTextView.setText(selectCoinItemModel.getTitle());
                }
            }
                break;
            case SELECT_NORMAL:{
                NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
                SelectCoinItemModel selectCoinItemModel = mSelectCoinItemModelList.get(position);
                if(normalViewHolder != null
                        && selectCoinItemModel != null
                        && selectCoinItemModel.getItemsBean() != null){

                    normalViewHolder.mButton.setText(selectCoinItemModel.getItemsBean().getCoinCount() + "币入选");
                    normalViewHolder.mTitle.setText(selectCoinItemModel.getItemsBean().getName());
                    normalViewHolder.mDesc.setText(selectCoinItemModel.getItemsBean().getIntroduction());
                    normalViewHolder.mImageView.setImageResource(getCurrentResId());
                    normalViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SelectCoinTypeListActivity.startSelectCoinTypeListActivity(v.getContext(),
                                    selectCoinItemModel.getItemsBean().getName(),
                                    selectCoinItemModel.getItemsBean().getId());
                            EventReportUtil.selectCoinItemClick(v.getContext(),selectCoinItemModel.getItemsBean().getName());
                        }
                    });
                }
            }
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mSelectCoinItemModelList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        if(mSelectCoinItemModelList != null){
            return mSelectCoinItemModelList.size();
        }
        return 0;
    }


    private class BannerViewHolder extends RecyclerView.ViewHolder{

        public BannerViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class TitleViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;
        public TitleViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.select_title);
        }
    }

    private class NormalViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTitle;
        public TextView mDesc;
        public TextView mButton;
        public NormalViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_icon);
            mTitle = itemView.findViewById(R.id.title);
            mDesc = itemView.findViewById(R.id.desc);
            mButton = itemView.findViewById(R.id.select_btn);
        }
    }

}
