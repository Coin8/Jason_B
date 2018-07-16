package com.coin.b8.ui.iView;

import com.coin.b8.model.MarketListSearchResponse;

/**
 * Created by zhangyi on 2018/7/11.
 */
public interface IHomeMarketNormalView {
    void onListSuccess(int type, MarketListSearchResponse response);
    void onListError(int type);

}
