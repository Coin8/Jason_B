package com.coin.b8.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coin.b8.R;
import com.coin.b8.constant.Constants;
import com.coin.b8.help.BannerImageLoader;
import com.coin.b8.help.PreferenceHelper;
import com.coin.b8.model.SelectCoinItemModel;
import com.coin.b8.ui.activity.NativeDetailActivity;
import com.coin.b8.ui.activity.SelectCoinTypeListActivity;
import com.coin.b8.utils.CommonUtils;
import com.coin.b8.utils.EventReportUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
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
            case SELECT_HEAD: {
                BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
                SelectCoinItemModel selectCoinItemModel = mSelectCoinItemModelList.get(position);
                if (bannerViewHolder != null
                        && selectCoinItemModel != null
                        && selectCoinItemModel.getBannerDatas() != null){
                    List<String> pics = new ArrayList<>();
                    for (int i = 0; i < selectCoinItemModel.getBannerDatas().size(); i++) {
                        pics.add(selectCoinItemModel.getBannerDatas().get(i).getPic());
                    }
                    bannerViewHolder.mBanner.setImages(pics);
                    bannerViewHolder.mBanner.setOnBannerListener(new OnBannerListener() {
                        @Override
                        public void OnBannerClick(int position) {
                            StringBuilder stringBuilder = new StringBuilder(Constants.IMPORTANT_NEWS_DETAIL_BASE_URL);
                            stringBuilder.append(selectCoinItemModel.getBannerDatas().get(position).getId());
                            stringBuilder.append("&type=banner");
                            Context context = bannerViewHolder.itemView.getContext();
                            stringBuilder.append("&uid=").append(PreferenceHelper.getUid(context));
                            String imei = PreferenceHelper.getIMEI(context);
                            if(!TextUtils.isEmpty(imei)){
                                stringBuilder.append("&imei=").append(imei);
                            }
                            String token = PreferenceHelper.getToken(context);
                            if(!TextUtils.isEmpty(token)){
                                stringBuilder.append("&token=").append(CommonUtils.encode(token));
                            }

                            String web_url =  stringBuilder.toString();
                            NativeDetailActivity.startNativeDetailActivity(bannerViewHolder.itemView.getContext(), web_url );
                        }
                    });
                    bannerViewHolder.mBanner.start();

                }
            }
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
        public Banner mBanner;
        public BannerViewHolder(View itemView) {
            super(itemView);
            mBanner = itemView.findViewById(R.id.news_banner);
            mBanner.setIndicatorGravity(BannerConfig.CENTER);
            mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            mBanner.setImageLoader(new BannerImageLoader());
            mBanner.setBannerAnimation(Transformer.DepthPage);
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
