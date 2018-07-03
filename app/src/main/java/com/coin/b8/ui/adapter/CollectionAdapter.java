package com.coin.b8.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.coin.b8.R;
import com.coin.b8.utils.CommonUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by zhangyi on 2018/7/2.
 */
public class CollectionAdapter extends RecyclerView.Adapter{

//    private DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_list_item, parent, false);
        return new CollectionVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CollectionVH collectionVH = (CollectionVH) holder;
        if(collectionVH != null ){
            collectionVH.title.setText("这是 " + position );
            collectionVH.time.setText(CommonUtils.millis2String(System.currentTimeMillis(),DEFAULT_FORMAT));
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }


    class CollectionVH extends RecyclerView.ViewHolder {
        View content;
        TextView btnDelete;
        TextView title;
        TextView time;

        public CollectionVH(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            title = itemView.findViewById(R.id.title);
            time = itemView.findViewById(R.id.time);
        }
    }
}
