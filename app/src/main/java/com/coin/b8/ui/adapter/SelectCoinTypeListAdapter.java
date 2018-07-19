package com.coin.b8.ui.adapter;

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
import com.coin.b8.model.SelectCoinTypeListResponse;
import com.coin.b8.ui.activity.NativeDetailActivity;
import com.coin.b8.utils.CommonUtils;
import com.coin.b8.utils.EventReportUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by zhangyi on 2018/7/10.
 */
public class SelectCoinTypeListAdapter extends RecyclerView.Adapter{

    private List<SelectCoinTypeListResponse.DataBean.ItemsBean> mList;
    private String mHeadContent = "";
    private long  mHeadTime;
    private DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String mCoinTypeTitle;

    public void setCoinTypeTitle(String coinTypeTitle) {
        mCoinTypeTitle = coinTypeTitle;
    }

    public void setList(List<SelectCoinTypeListResponse.DataBean.ItemsBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void setHead(String content,long time){
        mHeadContent = content;
        mHeadTime = time;
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getViewType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if(viewType == 1){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coin_type_list_item_total_head, parent, false);
            viewHolder = new HeadViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coin_type_list_item_normal, parent, false);
            viewHolder = new NormalViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        SelectCoinTypeListResponse.DataBean.ItemsBean itemsBean = mList.get(position);

        if(holder.getItemViewType() == 1){
            HeadViewHolder headViewHolder = (HeadViewHolder) holder;
            if(headViewHolder != null){
                headViewHolder.mContent.setText(mHeadContent);
                headViewHolder.mTime.setText(CommonUtils.millis2String(mHeadTime,DEFAULT_FORMAT));
            }
        }else {
            NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
            if(normalViewHolder != null && itemsBean != null){
                normalViewHolder.mSymbol.setText(itemsBean.getSymbol());
                normalViewHolder.mChineseName.setText(itemsBean.getChineseName());
                normalViewHolder.mPrice.setText("¥"+itemsBean.getCloseCny());

                normalViewHolder.mVol.setText("¥"+itemsBean.getVol());
                if(!TextUtils.isEmpty(itemsBean.getRateStr())){
                    if(itemsBean.getRateStr().startsWith("-")){
                        normalViewHolder.mRate.setBackgroundResource(R.drawable.corner_bg_red);
                        normalViewHolder.mRate.setText(itemsBean.getRateStr());
                    }else {
                        normalViewHolder.mRate.setBackgroundResource(R.drawable.corner_bg_green);
                        normalViewHolder.mRate.setText("+"+itemsBean.getRateStr());
                    }
                }

                normalViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(Constants.GLOBAL_DETAIL_URL);
                        stringBuilder.append("open").append("=").append(CommonUtils.encode(itemsBean.getOpen())).append("&")
                                .append("close").append("=").append(CommonUtils.encode(itemsBean.getClose())).append("&")
                                .append("closeCny").append("=").append(CommonUtils.encode(itemsBean.getCloseCny())).append("&")
                                .append("amount").append("=").append(CommonUtils.encode(itemsBean.getAmount())).append("&")
                                .append("count").append("=").append(CommonUtils.encode(itemsBean.getCount())).append("&")
                                .append("vol").append("=").append(CommonUtils.encode(itemsBean.getVol())).append("&")
                                .append("symbol").append("=").append(CommonUtils.encode(itemsBean.getSymbol())).append("&")
                                .append("base").append("=").append(CommonUtils.encode(itemsBean.getBase())).append("&")
                                .append("quote").append("=").append(CommonUtils.encode(itemsBean.getQuote())).append("&")
                                .append("rank").append("=").append(itemsBean.getRank()).append("&")
                                .append("rateStr").append("=").append(CommonUtils.encode(itemsBean.getRateStr())).append("&")
                                .append("rate").append("=").append(itemsBean.getRate()).append("&")
                                .append("exchangeName").append("=").append(CommonUtils.encode(itemsBean.getExchangeName())).append("&")
                                .append("stockValue").append("=").append(CommonUtils.encode(itemsBean.getStockValue())).append("&")
                                .append("isSelect").append("=").append(itemsBean.getIsSelect()).append("&")
                                .append("coinId").append("=").append(itemsBean.getCoinId()).append("&")
                                .append("exchangeAndSymbol").append("=").append(CommonUtils.encode(itemsBean.getExchangeAndSymbol())).append("&")
                                .append("ucrid").append("=").append(itemsBean.getUcrid()).append("&")
                                .append("chineseName").append("=").append(CommonUtils.encode(itemsBean.getChineseName())).append("&")
                                .append("openCny").append("=").append(CommonUtils.encode(itemsBean.getOpenCny())).append("&")
                                .append("low").append("=").append(CommonUtils.encode(itemsBean.getLow())).append("&")
                                .append("high").append("=").append(CommonUtils.encode(itemsBean.getHigh())).append("&")
                                .append("lowCny").append("=").append(CommonUtils.encode(itemsBean.getLowCny())).append("&")
                                .append("highCny").append("=").append(CommonUtils.encode(itemsBean.getHighCny()));

                        String web_url = stringBuilder.toString();
//                        Log.e("zy","web_url = " +web_url );
                        NativeDetailActivity.startNativeDetailActivity(v.getContext(),web_url);
                        EventReportUtil.selectCoinSubjectItemClick(v.getContext(),mCoinTypeTitle,itemsBean.getBase());
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

    private class HeadViewHolder extends RecyclerView.ViewHolder{
        private TextView mContent;
        private TextView mTime;

        public HeadViewHolder(View itemView) {
            super(itemView);
            mContent = itemView.findViewById(R.id.des_content);
            mTime = itemView.findViewById(R.id.update_time);
        }
    }

    private class NormalViewHolder extends RecyclerView.ViewHolder{

        private TextView mSymbol;
        private TextView mChineseName;
        private TextView mVol;
        private TextView mPrice;
        private TextView mRate;

        public NormalViewHolder(View itemView) {
            super(itemView);
            mSymbol = itemView.findViewById(R.id.symbol);
            mChineseName = itemView.findViewById(R.id.chinese_name);
            mVol = itemView.findViewById(R.id.vol);
            mPrice = itemView.findViewById(R.id.price);
            mRate = itemView.findViewById(R.id.rate_str);
            mRate.setBackgroundResource(R.drawable.corner_bg_green);
        }
    }

}
