package com.coin.b8.ui.iView;

import com.coin.b8.model.AddMarketSelfResponse;
import com.coin.b8.model.CommonResponse;
import com.coin.b8.model.ExchangeListResponse;
import com.coin.b8.model.HotCoinResponse;
import com.coin.b8.model.MarketListSearchResponse;

/**
 * Created by zhangyi on 2018/7/17.
 */
public interface ISearchView {
    void onHotCoinSuccess(HotCoinResponse response);
    void onHotCoinError();
    void onExchangeSuccess(ExchangeListResponse response);
    void onExchangeError();
    void onSearchSuccess(MarketListSearchResponse response);
    void onSearchError();
    void onSearchMoreSuccess(MarketListSearchResponse response);
    void onSearchMoreError();
    void onAddSelf(AddMarketSelfResponse response);
    void onDeleteSelf(CommonResponse response);
}
