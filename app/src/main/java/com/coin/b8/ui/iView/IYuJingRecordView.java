package com.coin.b8.ui.iView;

import com.coin.b8.model.DeleteYuJingResponse;
import com.coin.b8.model.ModifyYuJingResponse;
import com.coin.b8.model.YujingListResponse;

/**
 * Created by zhangyi on 2018/7/10.
 */
public interface IYuJingRecordView {
    void onYuJingListSuccess(YujingListResponse response,int type);
    void onYuJingListError(int type);
    void onDeleteSuccess(DeleteYuJingResponse response);
    void onDeleteError();
    void onModifySuccess(ModifyYuJingResponse response);
    void onModifyError();
}
