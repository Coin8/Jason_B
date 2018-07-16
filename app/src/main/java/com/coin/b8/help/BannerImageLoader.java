package com.coin.b8.help;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.coin.b8.R;
import com.coin.b8.utils.GlideUtil;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by zhangyi on 2018/7/10.
 */
public class BannerImageLoader extends ImageLoader{
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        GlideUtil.setImageRes(context,imageView,path, R.drawable.share_smal_icon,R.drawable.share_smal_icon,false);
    }
}
