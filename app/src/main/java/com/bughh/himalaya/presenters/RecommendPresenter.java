package com.bughh.himalaya.presenters;

import com.bughh.himalaya.fragments.RecommendFragment;
import com.bughh.himalaya.interfaces.IRecommendPresenter;
import com.bughh.himalaya.interfaces.IRecommendViewCallback;
import com.bughh.himalaya.utils.Constants;
import com.bughh.himalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendPresenter implements IRecommendPresenter {

    private static final String TAG = "RecommendPresenter";

    private List<IRecommendViewCallback> mCallbacks = new ArrayList<>();

    private RecommendPresenter() {
    }

    private static RecommendPresenter sInstance = null;

    public static RecommendPresenter getInstance() {
        if (sInstance == null) {
            synchronized (RecommendPresenter.class) {
                if (sInstance == null) {
                    sInstance = new RecommendPresenter();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void getRecommendList() {
        updateLoading();
        // 封装参数
        Map<String, String> map = new HashMap<>();
//        这个参数表示一页获取多少条内容
        map.put(DTransferConstants.LIKE_COUNT, Constants.RECOMMEND_COUNT + "");
        CommonRequest.getGuessLikeAlbum(map, new IDataCallBack<GussLikeAlbumList>() {
            @Override
            public void onSuccess(GussLikeAlbumList gussLikeAlbumList) {
                if (gussLikeAlbumList != null) {
                    List<Album> albumList = gussLikeAlbumList.getAlbumList();
                    //  数据回来后，更新 UI
                    handleRecommendResult(albumList);
                }

            }

            @Override
            public void onError(int i, String s) {
                LogUtil.d(TAG, "error --> " + i);
                LogUtil.d(TAG, "errorMsg --> " + s);
                handleError();
            }
        });
    }

    private void handleError() {
        // 通知 UI
        if (mCallbacks != null) {
            for (IRecommendViewCallback callback : mCallbacks) {
                callback.onNetworkError();
            }
        }

    }


    private void handleRecommendResult(List<Album> albumList) {
        // 通知 UI
        if (mCallbacks != null) {
            if (mCallbacks.size() == 0) {
                for (IRecommendViewCallback callback : mCallbacks) {
                    callback.onEmpty();
                }
            } else {
                for (IRecommendViewCallback callback : mCallbacks) {
                    callback.onRecommendListLoaded(albumList);
                }
            }

        }
    }

    private void updateLoading() {
        for (IRecommendViewCallback callback : mCallbacks) {
            callback.onLoading();
        }
    }

    @Override
    public void pull2RefreshMore() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void registerViewCallback(IRecommendViewCallback callBack) {
        if (mCallbacks != null && !mCallbacks.contains(callBack)) {
            mCallbacks.add(callBack);
        }

    }

    @Override
    public void unRegisterViewCallback(IRecommendViewCallback callBack) {
        if (mCallbacks != null) {
            mCallbacks.remove(callBack);
        }

    }

}
