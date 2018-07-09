package com.coin.b8.ui.iView;

import com.coin.b8.model.CancelCollectionResponse;
import com.coin.b8.model.CollectionListInfoResponse;

/**
 * Created by zhangyi on 2018/7/9.
 */
public interface IMyCollectionView {
    void onCollectionList(CollectionListInfoResponse collectionListInfoResponse);
    void onCollectionError();
    void onCollectionMore(CollectionListInfoResponse collectionListInfoResponse);
    void onCollectionMoreError();
    void onDeleteCollectionSuccess(CancelCollectionResponse cancelCollectionResponse);
    void onDeleteCollectionError();
}
