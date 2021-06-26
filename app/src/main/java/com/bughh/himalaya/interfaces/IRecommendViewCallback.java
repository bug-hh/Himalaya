package com.bughh.himalaya.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.List;

public interface IRecommendViewCallback {
    /**
     * 获取到推荐内容结果后的回调
     */
    void onRecommendListLoaded(List<Album> result);

    /**
     * 加载更多
     */
    void onLoadMore(List<Album> result);

    /**
     * 下拉加载更多的结果
     */
    void onRefreshMore(List<Album> result);
}
