package com.coin.b8.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coin.b8.R;
import com.coin.b8.constant.Constants;
import com.coin.b8.help.PreferenceHelper;
import com.coin.b8.model.MarketListSearchResponse;
import com.coin.b8.ui.activity.NativeDetailActivity;
import com.coin.b8.utils.CommonUtils;
import com.coin.b8.utils.EventReportUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyi on 2018/7/11.
 */
public class HomeMarketNormalAdapter extends RecyclerView.Adapter{
    // 0 市值，1 火币
    private int mCoinType = 0;

    private List<MarketListSearchResponse.DataBean> mList;

    public void setCoinType(int coinType) {
        mCoinType = coinType;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_market_listitem_normal, parent, false);
        return new NormalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
        if(normalViewHolder == null){
            return;
        }
        MarketListSearchResponse.DataBean dataBean = mList.get(position);
        normalViewHolder.mBase.setText(dataBean.getBase());
        if(mCoinType == 1){
            normalViewHolder.mChineseName.setText(dataBean.getQuote());
            normalViewHolder.mVol.setText("量"+dataBean.getAmount());
            normalViewHolder.mCloseCny.setText(dataBean.getClose());
            normalViewHolder.mClose.setText("¥"+dataBean.getCloseCny());
        }else {
            normalViewHolder.mChineseName.setText(dataBean.getChineseName());
            normalViewHolder.mVol.setText("¥"+dataBean.getVol());
            normalViewHolder.mCloseCny.setText("¥"+dataBean.getCloseCny());
            normalViewHolder.mClose.setText("$"+dataBean.getClose());
        }

        normalViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuilder stringBuilder = new StringBuilder();
                if(mCoinType == 1){
                    EventReportUtil.marketListItemReport(v.getContext(),dataBean.getBase(),"value");
                    stringBuilder.append(Constants.MART_DETAIL_URL);
                }else {
                    EventReportUtil.marketListItemReport(v.getContext(),dataBean.getBase(),"hotCoin");
                    stringBuilder.append(Constants.GLOBAL_DETAIL_URL);
                }

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
        public TextView mChineseName;
        public TextView mVol;
        public TextView mCloseCny;
        public TextView mClose;
        public TextView mRateStr;
        public NormalViewHolder(View itemView) {
            super(itemView);
            mBase = itemView.findViewById(R.id.symbol);
            mChineseName = itemView.findViewById(R.id.chinese_name);
            mVol = itemView.findViewById(R.id.vol);
            mCloseCny = itemView.findViewById(R.id.price);
            mClose = itemView.findViewById(R.id.price2);
            mRateStr = itemView.findViewById(R.id.rate_str);
            mRateStr.setBackgroundResource(R.drawable.corner_bg_green);
        }
    }

}
