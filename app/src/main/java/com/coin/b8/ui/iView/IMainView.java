package com.coin.b8.ui.iView;

import com.coin.b8.model.B8UpdateInfo;

/**
 * Created by zhangyi on 2018/5/31.
 */
public interface IMainView {

    void onUpdateInfo(B8UpdateInfo b8UpdateInfo, boolean auto);

    void onUpdateInfoError(Throwable e);
}
