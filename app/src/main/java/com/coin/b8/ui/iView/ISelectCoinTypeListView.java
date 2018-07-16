package com.coin.b8.ui.iView;

import com.coin.b8.model.SelectCoinTypeListResponse;

/**
 * Created by zhangyi on 2018/7/10.
 */
public interface ISelectCoinTypeListView {
    void onCoinListSuccess(SelectCoinTypeListResponse selectCoinTypeListResponse);
    void onCoinListError();
}
