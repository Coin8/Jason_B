package com.coin.b8.ui.iView;

import com.coin.b8.model.B8UpdateInfo;

/**
 * Created by zhangyi on 2018/6/29.
 */
public interface IHomeMine {
    void onUpdateInfo(B8UpdateInfo b8UpdateInfo, boolean auto);
    void onUpdateInfoError(Throwable e);
}
