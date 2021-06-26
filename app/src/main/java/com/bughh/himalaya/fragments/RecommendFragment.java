package com.bughh.himalaya.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bughh.himalaya.R;
import com.bughh.himalaya.adapters.RecommendListAdapter;
import com.bughh.himalaya.base.BaseFragment;
import com.bughh.himalaya.utils.Constants;
import com.bughh.himalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendFragment extends BaseFragment {

    private static final String TAG = "RecommendFragment";

    private View mRootView;
    private RecyclerView mRecommendRv;
    private RecommendListAdapter mRecommendListAdapter;

    @Override
    protected View onSubViewLoaded(LayoutInflater inflater, ViewGroup container) {
        // view 加载完成
        mRootView = inflater.inflate(R.layout.fragment_recommend, container, false);


//        RecyclerView 的使用
//        1、找到控件
        mRecommendRv = mRootView.findViewById(R.id.recommend_list);


//        2、设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecommendRv.setLayoutManager(linearLayoutManager);


//        3、设置适配器
        mRecommendListAdapter = new RecommendListAdapter();
        mRecommendRv.setAdapter(mRecommendListAdapter);


        // 获取数据
        getRecommendData();

        return mRootView;
    }

    private void getRecommendData() {
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
                    updateRecommendUI(albumList);
                }

            }

            @Override
            public void onError(int i, String s) {
                LogUtil.d(TAG, "error --> " + i);
                LogUtil.d(TAG, "errorMsg --> " + s);

            }
        });
    }

    private void updateRecommendUI(List<Album> albumList) {
        mRecommendListAdapter.setData(albumList);

    }
}
