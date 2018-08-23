package com.coin.b8.ui.iView;

import com.coin.b8.model.CoinStoreListResponse;

/**
 * Created by zhangyi on 2018/8/21.
 */
public interface IStoreView {
    void onSwitchStoreSuccess();
    void onSwitchStoreError(String message);
    void onCreateStoreSuccess();
    void onCreateStoreError(String message);
    void onDeleteStoreSuccess();
    void onDeleteStoreError(String message);
    void onRenameStoreSuccess();
    void onRenameStoreError(String message);
    void onStoreListSuccess(CoinStoreListResponse response);
    void onStoreListError();
}
