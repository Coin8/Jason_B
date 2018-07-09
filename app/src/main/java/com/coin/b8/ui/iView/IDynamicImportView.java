package com.coin.b8.ui.iView;

import com.coin.b8.model.DynamicImportNewsResponse;

/**
 * Created by zhangyi on 2018/7/9.
 */
public interface IDynamicImportView {
    void onNewsSuccess(DynamicImportNewsResponse dynamicImportNewsResponse);
    void onNewError();
    void onNewsLoadMoreSuccess(DynamicImportNewsResponse dynamicImportNewsResponse);
    void onNewsLoadMoreError();
}
