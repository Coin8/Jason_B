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
import com.coin.b8.help.PreferenceHelper;
import com.coin.b8.model.MarketListSearchResponse;
import com.coin.b8.ui.activity.NativeDetailActivity;
import com.coin.b8.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyi on 2018/7/17.
 */
public class SearchResultAdapter extends RecyclerView.Adapter{


    public interface OnItemClickListen{
        void onItemClick(MarketListSearchResponse.DataBean dataBean);
    }
    private OnItemClickListen mOnItemClickListen;
    private List<MarketListSearchResponse.DataBean> mList;

    public void setOnItemClickListen(OnItemClickListen onItemClickListen) {
        mOnItemClickListen = onItemClickListen;
    }

    public void setList(List<MarketListSearchResponse.DataBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void addList(List<MarketListSearchResponse.DataBean> list){
        if(list != null && list.size() > 0){
            if(mList == null){
                mList = new ArrayList<>();
            }
            mList.addAll(list);
            notifyItemRangeInserted(mList.size() - list.size(), list.size());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item_coin, parent, false);
        return new NormalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
        MarketListSearchResponse.DataBean dataBean = mList.get(position);
        if(normalViewHolder == null || dataBean == null){
            return;
        }
        if(dataBean.getBase() != null){
            normalViewHolder.mBase.setText(dataBean.getBase().toUpperCase());
        }
        if(dataBean.getQuote() != null){
            normalViewHolder.mQuote.setText(dataBean.getQuote().toUpperCase());
        }
        normalViewHolder.mExchangeName.setText(dataBean.getExchangeName());
        normalViewHolder.mClose.setText(dataBean.getClose());
        normalViewHolder.mCloseCny.setText("¥"+dataBean.getCloseCny());
        normalViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append(Constants.MART_DETAIL_URL);
                Context context = v.getContext();
                stringBuilder.append("uid=").append(PreferenceHelper.getUid(context));
                String imei = PreferenceHelper.getIMEI(context);
                if(!TextUtils.isEmpty(imei)){
                    stringBuilder.append("&imei=").append(imei);
                }
                String token = PreferenceHelper.getToken(context);
                if(!TextUtils.isEmpty(token)){
                    stringBuilder.append("&token=").append(CommonUtils.encode(token));
                }
                stringBuilder.append("&");

                stringBuilder.append("open").append("=").append(CommonUtils.encode(dataBean.getOpen())).append("&")
                        .append("close").append("=").append(CommonUtils.encode(dataBean.getClose())).append("&")
                        .append("closeCny").append("=").append(CommonUtils.encode(dataBean.getCloseCny())).append("&")
                        .append("amount").append("=").append(CommonUtils.encode(dataBean.getAmount())).append("&")
                        .append("count").append("=").append(CommonUtils.encode(dataBean.getCount())).append("&")
                        .append("vol").append("=").append(CommonUtils.encode(dataBean.getVol())).append("&")
                        .append("symbol").append("=").append(CommonUtils.encode(dataBean.getSymbol())).append("&")
                        .append("base").append("=").append(CommonUtils.encode(dataBean.getBase())).append("&")
                        .append("quote").append("=").append(CommonUtils.encode(dataBean.getQuote())).append("&")
                        .append("rank").append("=").append(dataBean.getRank()).append("&")
                        .append("rateStr").append("=").append(CommonUtils.encode(dataBean.getRateStr())).append("&")
                        .append("rate").append("=").append(dataBean.getRate()).append("&")
                        .append("exchangeName").append("=").append(CommonUtils.encode(dataBean.getExchangeName())).append("&")
                        .append("stockValue").append("=").append(CommonUtils.encode(dataBean.getStockValue())).append("&")
                        .append("isSelect").append("=").append(dataBean.getIsSelect()).append("&")
                        .append("coinId").append("=").append(dataBean.getCoinId()).append("&")
                        .append("exchangeAndSymbol").append("=").append(CommonUtils.encode(dataBean.getExchangeAndSymbol())).append("&")
                        .append("ucrid").append("=").append(dataBean.getUcrid()).append("&")
                        .append("chineseName").append("=").append(CommonUtils.encode(dataBean.getChineseName())).append("&")
                        .append("openCny").append("=").append(CommonUtils.encode(dataBean.getOpenCny())).append("&")
                        .append("low").append("=").append(CommonUtils.encode(dataBean.getLow())).append("&")
                        .append("high").append("=").append(CommonUtils.encode(dataBean.getHigh())).append("&")
                        .append("lowCny").append("=").append(CommonUtils.encode(dataBean.getLowCny())).append("&")
                        .append("highCny").append("=").append(CommonUtils.encode(dataBean.getHighCny()));

                String web_url = stringBuilder.toString();
                NativeDetailActivity.startNativeDetailActivity(v.getContext(),web_url);

            }
        });

        if(!TextUtils.isEmpty(dataBean.getRateStr())){
            if(dataBean.getRateStr().startsWith("-")){
                normalViewHolder.mRateStr.setBackgroundResource(R.drawable.corner_bg_red);
                normalViewHolder.mRateStr.setText(dataBean.getRateStr());
            }else {
                normalViewHolder.mRateStr.setBackgroundResource(R.drawable.corner_bg_green);
                normalViewHolder.mRateStr.setText("+"+dataBean.getRateStr());
            }
        }

        if(dataBean.getIsSelect() == 1){
            normalViewHolder.mBtn.setImageResource(R.drawable.icon_reduce);
        }else {
            normalViewHolder.mBtn.setImageResource(R.drawable.icon_add);
        }

        normalViewHolder.mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListen != null){
                    mOnItemClickListen.onItemClick(dataBean);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        if(mList != null){
            return mList.size();
        }
        return 0;
    }

    private class NormalViewHolder extends RecyclerView.ViewHolder{
        public TextView mBase;
        public TextView mQuote;
        public TextView mExchangeName;
        public TextView mCloseCny;
        public TextView mClose;
        public TextView mRateStr;
        public ImageView mBtn;
        public NormalViewHolder(View itemView) {
            super(itemView);
            mBase = itemView.findViewById(R.id.symbol);
            mQuote = itemView.findViewById(R.id.chinese_name);
            mExchangeName = itemView.findViewById(R.id.vol);
            mClose = itemView.findViewById(R.id.price);
            mCloseCny = itemView.findViewById(R.id.price2);
            mRateStr = itemView.findViewById(R.id.rate_str);
            mRateStr.setBackgroundResource(R.drawable.corner_bg_green);
            mBtn = itemView.findViewById(R.id.btn_add);
            mBtn.setImageResource(R.drawable.icon_add);
        }
    }
}
