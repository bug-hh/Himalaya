package com.bughh.himalaya.fragments;

import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bughh.himalaya.R;
import com.bughh.himalaya.adapters.RecommendListAdapter;
import com.bughh.himalaya.base.BaseFragment;
import com.bughh.himalaya.interfaces.IRecommendPresenter;
import com.bughh.himalaya.interfaces.IRecommendViewCallback;
import com.bughh.himalaya.presenters.RecommendPresenter;
import com.bughh.himalaya.utils.Constants;
import com.bughh.himalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendFragment extends BaseFragment implements IRecommendViewCallback {

    private static final String TAG = "RecommendFragment";

    private View mRootView;
    private RecyclerView mRecommendRv;
    private RecommendListAdapter mRecommendListAdapter;
    private RecommendPresenter mRecommendPresenter;

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
        // 添加每个 item 间的间距
        mRecommendRv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = UIUtil.dip2px(view.getContext(), 5);
                outRect.bottom = UIUtil.dip2px(view.getContext(), 5);
                outRect.left = UIUtil.dip2px(view.getContext(), 5);
                outRect.right = UIUtil.dip2px(view.getContext(), 5);
            }
        });


//        3、设置适配器
        mRecommendListAdapter = new RecommendListAdapter();
        mRecommendRv.setAdapter(mRecommendListAdapter);


        // 获取到逻辑层的对象
        mRecommendPresenter = RecommendPresenter.getInstance();
//        注册通知接口, 换句话说，mRecommendPresenter 就持有了 RecommendFragment 的引用
        mRecommendPresenter.registerViewCallback(this);
//        获取推荐列表
        mRecommendPresenter.getRecommendList();
//        getRecommendData();

        return mRootView;
    }



//    private void updateRecommendUI(List<Album> albumList) {
//        mRecommendListAdapter.setData(albumList);
//
//    }

    @Override
    public void onRecommendListLoaded(List<Album> result) {
        // 当我们成功获取到推荐内容的时候，这个方法就会被调用
        mRecommendListAdapter.setData(result);
    }

    @Override
    public void onLoadMore(List<Album> result) {

    }

    @Override
    public void onRefreshMore(List<Album> result) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        取消接口的注册
        if (mRecommendPresenter != null) {
            mRecommendPresenter.unRegisterViewCallback(this);
        }
    }

}
