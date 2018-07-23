package com.coin.b8.ui.iView;

import com.coin.b8.model.BannerResponse;
import com.coin.b8.model.SelectCoinListResponse;

/**
 * Created by zhangyi on 2018/7/9.
 */
public interface ISelectCoinView {
    void onSelectCoinSuccess(SelectCoinListResponse selectCoinListResponse);
    void onSelectCoinFail();
    void onBannerSuccess(BannerResponse response);
    void onBannerFail();
}
