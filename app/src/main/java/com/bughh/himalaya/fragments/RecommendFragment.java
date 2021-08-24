package com.bughh.himalaya.fragments;

import android.content.Intent;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bughh.himalaya.DetailActivity;
import com.bughh.himalaya.R;
import com.bughh.himalaya.adapters.RecommendListAdapter;
import com.bughh.himalaya.base.BaseFragment;
import com.bughh.himalaya.interfaces.IRecommendPresenter;
import com.bughh.himalaya.interfaces.IRecommendViewCallback;
import com.bughh.himalaya.presenters.RecommendPresenter;
import com.bughh.himalaya.utils.Constants;
import com.bughh.himalaya.utils.LogUtil;
import com.bughh.himalaya.views.UILoader;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendFragment extends BaseFragment implements IRecommendViewCallback, UILoader.OnRetryClickListener, RecommendListAdapter.onRecommendItemClickListener {

    private static final String TAG = "RecommendFragment";

    private View mRootView;
    private RecyclerView mRecommendRv;
    private RecommendListAdapter mRecommendListAdapter;
    private RecommendPresenter mRecommendPresenter;
    private UILoader mUILaoder;

    @Override
    protected View onSubViewLoaded(final LayoutInflater inflater, ViewGroup container) {

        mUILaoder = new UILoader(getContext()) {
            @Override
            protected View getSuccessView(ViewGroup container) {
                return createSuccessView(inflater, container);

            }
        };

        // 获取到逻辑层的对象
        mRecommendPresenter = RecommendPresenter.getInstance();
//        注册通知接口, 换句话说，mRecommendPresenter 就持有了 RecommendFragment 的引用
        mRecommendPresenter.registerViewCallback(this);
//        获取推荐列表
        mRecommendPresenter.getRecommendList();
//        getRecommendData();

        if (mUILaoder.getParent() instanceof ViewGroup) {
            ((ViewGroup) mUILaoder.getParent()).removeView(mUILaoder);
        }

        mUILaoder.setOnRetryClickListener(this);
        return mUILaoder;
    }


    private View createSuccessView(LayoutInflater inflater, ViewGroup container) {
        // view 加载完成
        mRootView = inflater.inflate(R.layout.fragment_recommend, container, false);

        // RecyclerView 的使用
        // 1、找到控件
        mRecommendRv = mRootView.findViewById(R.id.recommend_list);


        // 2、设置布局管理器
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


        // 3、设置适配器
        mRecommendListAdapter = new RecommendListAdapter();
        mRecommendRv.setAdapter(mRecommendListAdapter);
        mRecommendListAdapter.setOnRecommendItemClickListener(this);

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
        mUILaoder.updateStatus(UILoader.UIStatus.SUCCESS);
    }

    @Override
    public void onNetworkError() {
        mUILaoder.updateStatus(UILoader.UIStatus.NETWORK_ERROR);

    }

    @Override
    public void onEmpty() {
        mUILaoder.updateStatus(UILoader.UIStatus.EMPTY);
    }

    @Override
    public void onLoading() {
        mUILaoder.updateStatus(UILoader.UIStatus.LOADING);
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

    @Override
    public void onRetryClick() {
        // 重新获取数据
        if (mRecommendPresenter != null) {
            mRecommendPresenter.getRecommendList();
        }
    }

    @Override
    public void onItemClick(int position) {
        // item 被点击了，跳转详情页
        Intent intent = new Intent(getContext(), DetailActivity.class);
        startActivity(intent);

    }
}
