package com.bughh.himalaya.interfaces;

public interface IRecommendPresenter {
    /**
     * 获取推荐内容
     */
    void getRecommendList();

    /**
     * 下拉刷新更多内容
     */
    void pull2RefreshMore();

    /**
     * 上拉加载更多
     */
    void loadMore();

    /**
     * 这个方法用于注册 UI 的回调
     * @param callBack
     */
    void registerViewCallback(IRecommendViewCallback callBack);


    /**
     * 这个方法用于取消注册 UI 的回调
     * @param callBack
     */
    void unRegisterViewCallback(IRecommendViewCallback callBack);

}
