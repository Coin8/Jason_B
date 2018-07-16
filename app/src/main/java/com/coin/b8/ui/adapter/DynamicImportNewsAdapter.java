package com.coin.b8.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coin.b8.R;
import com.coin.b8.constant.Constants;
import com.coin.b8.help.BannerImageLoader;
import com.coin.b8.help.PreferenceHelper;
import com.coin.b8.model.DynamicImportNewsResponse;
import com.coin.b8.model.ImportantNewsBannerResponse;
import com.coin.b8.ui.activity.NativeDetailActivity;
import com.coin.b8.utils.CommonUtils;
import com.coin.b8.utils.GlideUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyi on 2018/7/9.
 */
public class DynamicImportNewsAdapter extends RecyclerView.Adapter{

    private DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private List<DynamicImportNewsResponse.DataBean.ContentBean> mList;
    private List<ImportantNewsBannerResponse.DataBean> mBannerBeanList;



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

    public void addBanner(List<ImportantNewsBannerResponse.DataBean> list){
        if(list == null || list.size() <1){
            return;
        }
        mBannerBeanList  = list;
        if(mList != null && mList.size() > 0){
            if(mList.get(0).getViewType() == 1){
                mList.get(0).setTitle("test");
                notifyItemChanged(0);
            }else {
                DynamicImportNewsResponse.DataBean.ContentBean contentBean = new DynamicImportNewsResponse.DataBean.ContentBean();
                contentBean.setViewType(1);
                mList.add(0,contentBean);
                notifyItemInserted(0);
            }
        }else {

            if(mList == null){
                mList = new ArrayList<>();
            }
            DynamicImportNewsResponse.DataBean.ContentBean contentBean = new DynamicImportNewsResponse.DataBean.ContentBean();
            contentBean.setViewType(1);
            mList.add(0,contentBean);
            notifyItemInserted(0);
        }
    }

    public void setList(List<DynamicImportNewsResponse.DataBean.ContentBean> list) {
        if(mList != null
                && mList.size() > 0
                && mList.get(0).getViewType() == 1
                && list != null){
            DynamicImportNewsResponse.DataBean.ContentBean contentBean = new DynamicImportNewsResponse.DataBean.ContentBean();
            contentBean.setViewType(1);
            list.add(0,contentBean);
        }
        mList = list;


    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if(viewType == 1){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.important_news_list_item_banner, parent, false);
            viewHolder = new BannerViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.important_news_list_item_normal, parent, false);
            viewHolder = new NormalViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder == null){
            return;
        }

        if(holder.getItemViewType() == 1){
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            List<String> pics = new ArrayList<>();
            if(mBannerBeanList != null){
                for (int i = 0; i < mBannerBeanList.size(); i++) {
                    pics.add(mBannerBeanList.get(i).getPic());
                }
            }
            bannerViewHolder.mBanner.setImages(pics);
            bannerViewHolder.mBanner.start();
            bannerViewHolder.mBanner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    StringBuilder stringBuilder = new StringBuilder(Constants.IMPORTANT_NEWS_DETAIL_BASE_URL);
                    stringBuilder.append(mBannerBeanList.get(position).getId());
                    stringBuilder.append("&type=banner");
                    Context context = bannerViewHolder.itemView.getContext();
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
//                    Log.e("zy","web_url = " + web_url);
                    NativeDetailActivity.startNativeDetailActivity(bannerViewHolder.itemView.getContext(), web_url );
                }
            });

        }else {
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
                normalViewHolder.mTime.setText(CommonUtils.millis2String(contentBean.getPublishTime()*1000,DEFAULT_FORMAT));
                normalViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        StringBuilder stringBuilder = new StringBuilder(Constants.IMPORTANT_NEWS_DETAIL_BASE_URL);
                        stringBuilder.append(contentBean.getId());
                        Context context = v.getContext();
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
                        NativeDetailActivity.startNativeDetailActivity(v.getContext(), web_url );
                    }
                });
            }
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
        DynamicImportNewsResponse.DataBean.ContentBean contentBean = mList.get(position);
        return contentBean.getViewType();
//        return super.getItemViewType(position);
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
}
