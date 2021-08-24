package com.bughh.himalaya.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bughh.himalaya.R;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RecommendListAdapter extends RecyclerView.Adapter<RecommendListAdapter.InnerHolder> {

    private  List<Album> mData = new ArrayList<>();
    private onRecommendItemClickListener mItemClickListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 这里是装载 view
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend, parent, false);

        return new InnerHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, final int position) {
        // 这里设置数据
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(position);
                }

            }
        });
        holder.setData(mData.get(position));

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<Album> albumList) {
        if (mData != null) {
            mData.clear();
            mData.addAll(albumList);
        }
        // 更新一下 UI
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setData(Album album) {
            // 找到 item_recommend.xml 中的各个控件，设置数据
            // 专辑封面
            ImageView albumCoverIv = itemView.findViewById(R.id.album_cover);
            // 专辑标题
            TextView albumTitleTv = itemView.findViewById(R.id.album_title_tv);
            // 专辑描述
            TextView albumDescTv = itemView.findViewById(R.id.album_description_tv);
            // 播放数量
            TextView albumPlayCountTv = itemView.findViewById(R.id.album_play_count);
            // 专辑内容数量
            TextView albumContentCountTv = itemView.findViewById(R.id.album_content_size);

            albumTitleTv.setText(album.getAlbumTitle());
            albumDescTv.setText(album.getAlbumIntro());
            albumPlayCountTv.setText(album.getPlayCount() + "");
            albumContentCountTv.setText(album.getIncludeTrackCount() + "");

            Picasso.get().load(album.getCoverUrlLarge()).into(albumCoverIv);

        }
    }

    public void setOnRecommendItemClickListener(onRecommendItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public interface onRecommendItemClickListener {
        public void onItemClick(int position);

    }
}
