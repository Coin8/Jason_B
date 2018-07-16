package com.coin.b8.ui.iView;

import com.coin.b8.model.CommonResponse;
import com.coin.b8.model.MarketSelfListResponse;

/**
 * Created by zhangyi on 2018/7/13.
 */
public interface IHomeMarketSelfView {
    void onListSuccess(int type, MarketSelfListResponse response);
    void onListError(int type);
    void onTopSuccess(CommonResponse response);
    void onTopError();
    void onDeleteSuccess(CommonResponse response);
    void onDeleteError();
}
