package com.coin.b8.ui.iView;

import com.coin.b8.model.B8UpdateInfo;
import com.coin.b8.model.UnLoginUidInfo;

/**
 * Created by zhangyi on 2018/7/4.
 */
public interface IHomeView {
    void onUpdateInfo(B8UpdateInfo b8UpdateInfo, boolean auto);
    void onUpdateInfoError(Throwable e);
    void onUnLoginUidInfo(UnLoginUidInfo unLoginUidInfo);
}
