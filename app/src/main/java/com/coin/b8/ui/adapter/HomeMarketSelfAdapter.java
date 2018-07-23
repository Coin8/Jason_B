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
import com.coin.b8.model.MarketSelfListResponse;
import com.coin.b8.swipe.SwipeMenuLayout;
import com.coin.b8.ui.activity.NativeDetailActivity;
import com.coin.b8.utils.CommonUtils;
import com.coin.b8.utils.EventReportUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyi on 2018/7/13.
 */
public class HomeMarketSelfAdapter extends RecyclerView.Adapter{

    private List<MarketSelfListResponse.DataBean> mList;

    public interface OnItemClickListen{
        void onItemDeleteClick(MarketSelfListResponse.DataBean dataBean,int position);
        void onItemTopClick(MarketSelfListResponse.DataBean dataBean,int position);
    }

    private OnItemClickListen mOnItemClickListen;

    public void setOnItemClickListen(OnItemClickListen onItemClickListen) {
        mOnItemClickListen = onItemClickListen;
    }

    public void setList(List<MarketSelfListResponse.DataBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void addList(List<MarketSelfListResponse.DataBean> list){
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_market_listitem_self, parent, false);
        return new NormalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
        MarketSelfListResponse.DataBean dataBean = mList.get(position);
        if(normalViewHolder == null){
            return;
        }
        normalViewHolder.mExchangeName.setText(dataBean.getExchangeName());
        if(dataBean.getBase() != null){
            normalViewHolder.mBase.setText(dataBean.getBase().toUpperCase());
        }
        if(dataBean.getQuote() != null){
            normalViewHolder.mQuote.setText(dataBean.getQuote().toUpperCase());
        }
        normalViewHolder.mAmount.setText("量"+dataBean.getAmount());
        normalViewHolder.mClose.setText(dataBean.getClose());
        normalViewHolder.mCloseCny.setText("¥"+dataBean.getCloseCny());

        normalViewHolder.mContentView.setOnClickListener(new View.OnClickListener() {
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
                EventReportUtil.marketListItemReport(v.getContext(),dataBean.getBase(),"self");
            }
        });

        normalViewHolder.mBtnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                normalViewHolder.mSwipeMenuLayout.smoothClose();
                if(mOnItemClickListen != null){
                    mOnItemClickListen.onItemTopClick(dataBean,position);
                }
            }
        });

        normalViewHolder.mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                normalViewHolder.mSwipeMenuLayout.smoothClose();
                if(mOnItemClickListen != null){
                    mOnItemClickListen.onItemDeleteClick(dataBean,position);
                }
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
        public View mContentView;
        public TextView mExchangeName;
        public TextView mBase;
        public TextView mQuote;
        public TextView mAmount;
        public TextView mCloseCny;
        public TextView mClose;
        public TextView mRateStr;

        public TextView mBtnTop;
        public TextView mBtnDelete;
        public SwipeMenuLayout mSwipeMenuLayout;
        public NormalViewHolder(View itemView) {
            super(itemView);
            mSwipeMenuLayout = itemView.findViewById(R.id.swipe_menu_layout);
            mContentView = itemView.findViewById(R.id.content);
            mExchangeName = itemView.findViewById(R.id.exchange_name);
            mBase = itemView.findViewById(R.id.symbol);
            mQuote = itemView.findViewById(R.id.chinese_name);
            mAmount = itemView.findViewById(R.id.vol);
            mCloseCny = itemView.findViewById(R.id.price);
            mClose = itemView.findViewById(R.id.price2);
            mRateStr = itemView.findViewById(R.id.rate_str);
            mRateStr.setBackgroundResource(R.drawable.corner_bg_green);
            mBtnTop = itemView.findViewById(R.id.btn_top);
            mBtnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }

}
