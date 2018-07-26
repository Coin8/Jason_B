package com.coin.b8.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.coin.b8.app.AppLogger;
import com.coin.b8.ui.view.RoundedCornersTransformation;

import java.io.File;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Created by zhangyi on 2018/7/3.
 */
public class GlideUtil {

    public static void setImageRes(Context context, ImageView imageView, Object url,
                                    int placeHolderRes, int errorRes, boolean isCircle) {
        if (imageView == null || context == null) {
            AppLogger.e("Failed to set image resource to certain ImageView, since url or imageView or context is unavailable.");
            return;
        }

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(placeHolderRes)
                .error(errorRes);
        if (isCircle) {
            requestOptions = requestOptions.circleCrop();
        }
        Glide.with(context).load(url).apply(requestOptions).into(imageView);
    }

    public static void setImageRes(Context context, ImageView imageView, File url,
                                    int placeHolderRes, int errorRes, boolean isCircle) {
        if (imageView == null || context == null) {
            AppLogger.e("Failed to set image resource to certain ImageView, since url or imageView or context is unavailable.");
            return;
        }

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(placeHolderRes)
                .error(errorRes);
        if (isCircle) {
            requestOptions = requestOptions.circleCrop();
        }
        Glide.with(context).load(url).apply(requestOptions).into(imageView);
    }


    public static void setCornerImageRes(Context context ,ImageView imageView ,String url, int radius, final int placeHolderRes){
        if (imageView == null || context == null) {
            AppLogger.e("Failed to set image resource to certain ImageView, since url or imageView or context is unavailable.");
            return;
        }
        Glide.with(context)
                .load(url)
                .apply(bitmapTransform(new RoundedCornersTransformation(radius, 0,
                        RoundedCornersTransformation.CornerType.ALL)).placeholder(placeHolderRes).error(placeHolderRes))
                .into(imageView);
    }
}
