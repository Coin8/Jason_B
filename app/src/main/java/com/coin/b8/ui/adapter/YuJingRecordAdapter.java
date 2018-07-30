package com.coin.b8.ui.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coin.b8.R;
import com.coin.b8.model.YujingListResponse;
import com.coin.b8.ui.view.SwitchButton;

import java.util.List;

/**
 * Created by zhangyi on 2018/7/10.
 */
public class YuJingRecordAdapter extends RecyclerView.Adapter{

    private boolean mIsDeleteEnable = false;
    private List<YujingListResponse.DataBean> mList;
    private OnYuJingItemClickListen mOnYuJingItemClickListen;

    public interface OnYuJingItemClickListen{
        void onSwitchBtnClick(long id,boolean value);
        void onDelete(long id,int pos);
    }

    public void setOnYuJingItemClickListen(OnYuJingItemClickListen onYuJingItemClickListen) {
        mOnYuJingItemClickListen = onYuJingItemClickListen;
    }

    public void setList(List<YujingListResponse.DataBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void remove(int position){
        if(mList == null){
            return;
        }
        mList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mList.size() - position);
    }

    public void setDeleteEnable(boolean deleteEnable) {
        mIsDeleteEnable = deleteEnable;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_yujing_list_item, parent, false);
        return new NormalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
        YujingListResponse.DataBean dataBean = mList.get(position);
        if(normalViewHolder != null && dataBean != null){
            if(dataBean.getOpenStatus() == 1){
                normalViewHolder.mSwitchButton.setChecked(true);
            }else {
                normalViewHolder.mSwitchButton.setChecked(false);
            }
            if(mIsDeleteEnable){
                normalViewHolder.mDeleteBtn.setVisibility(View.VISIBLE);
            }else {
                normalViewHolder.mDeleteBtn.setVisibility(View.INVISIBLE);
            }
            String base = dataBean.getBase() == null ? "":dataBean.getBase().toUpperCase();
            String quote = dataBean.getQuote() == null ? "":dataBean.getQuote().toUpperCase();
            String title = dataBean.getExchangeName() + " " + base + "/"
                    + quote + "的最新价格";
            normalViewHolder.mRecordTitle.setText(title);
            String desc = "下跌至 ";
            boolean isUp = false;
            if(dataBean.getType() == 1){
                desc = "上涨至 ";
                isUp = true;
            }else {
                desc = "下跌至 ";
                isUp = false;
            }
            setDesc(normalViewHolder.mRecordDesc, desc, "¥"+dataBean.getPrice(), " 时",isUp);
//            normalViewHolder.mSwitchButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(mOnYuJingItemClickListen != null){
//                        boolean isChecked = !normalViewHolder.mSwitchButton.isChecked();
//                        if(normalViewHolder.mSwitchButton.isChecked()){
//
//                        }else {
//
//                        }
//                        mOnYuJingItemClickListen.onSwitchBtnClick(dataBean.getId(),isChecked);
//                        if(isChecked){
//                            mList.get(position).setOpenStatus(1);
//                        }else {
//                            mList.get(position).setOpenStatus(2);
//                        }
//                    }
//
//                }
//            });
            normalViewHolder.mSwitchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                    if(mOnYuJingItemClickListen != null){
                        mOnYuJingItemClickListen.onSwitchBtnClick(dataBean.getId(),isChecked);
//                        if(isChecked){
//                            mList.get(position).setOpenStatus(1);
//                        }else {
//                            mList.get(position).setOpenStatus(2);
//                        }
                    }
                }
            });

            normalViewHolder.mDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnYuJingItemClickListen != null){
                        mOnYuJingItemClickListen.onDelete(dataBean.getId(),position);
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

    private void setDesc(TextView textView,String first,String second, String three, boolean isUp){

        ForegroundColorSpan normalSpan = new ForegroundColorSpan(Color.parseColor("#191919"));
        ForegroundColorSpan yellowSpan;
        if(isUp){
            yellowSpan = new ForegroundColorSpan(Color.parseColor("#ff0000"));
        }else {
            yellowSpan = new ForegroundColorSpan(Color.parseColor("#008B00"));
        }

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(first).append(second).append(three);

        builder.setSpan(normalSpan, 0, first.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(yellowSpan, first.length(), first.length() + second.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(normalSpan, first.length() + second.length(),builder.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(builder);
    }

    private class NormalViewHolder extends RecyclerView.ViewHolder{
        public SwitchButton mSwitchButton;
        public TextView mDeleteBtn;
        public TextView mRecordTitle;
        public TextView mRecordDesc;
        public NormalViewHolder(View itemView) {
            super(itemView);
            mSwitchButton = itemView.findViewById(R.id.switch_button);
            mDeleteBtn = itemView.findViewById(R.id.delete_btn);
            mRecordTitle = itemView.findViewById(R.id.record_title);
            mRecordDesc = itemView.findViewById(R.id.record_desc);
        }
    }
}
