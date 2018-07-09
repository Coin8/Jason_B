package com.coin.b8.ui.iView;

import com.coin.b8.model.ModifyUserHeadResponse;
import com.coin.b8.model.ModifyUserResponse;
import com.coin.b8.model.UserInfoResponse;

/**
 * Created by zhangyi on 2018/7/5.
 */
public interface IPersonalInfoView {
    void onUserInfo(UserInfoResponse userInfoResponse);
    void onModifyUserSuccess(ModifyUserResponse modifyUserResponse);
    void onModifyUserFail();
    void onModifyUserHeadSuccess(ModifyUserHeadResponse modifyUserHeadResponse);
    void onModifyUserHeadFail();
}
