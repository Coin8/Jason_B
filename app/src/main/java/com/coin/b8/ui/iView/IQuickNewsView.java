package com.coin.b8.ui.iView;

import com.coin.b8.model.QuickNewsResponse;

/**
 * Created by zhangyi on 2018/7/10.
 */
public interface IQuickNewsView {
    void onNewsSuccess(QuickNewsResponse quickNewsResponse);
    void onNewsError();
    void onNewsMoreSuccess(QuickNewsResponse quickNewsResponse);
    void onNewsMoreError();
}
