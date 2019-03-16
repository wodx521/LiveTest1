package com.lairui.livetest1.module.four_module.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lairui.livetest1.MyApplication;
import com.lairui.livetest1.R;
import com.lairui.livetest1.entity.bean.VideoBean;
import com.lairui.livetest1.ui.panel.CircleImageView;
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;
import com.wanou.framelibrary.glidetools.GlideApp;
import com.wanou.framelibrary.utils.UiTools;

import java.util.List;

public class VideoAdapter extends BaseRecycleViewAdapter {
    private List<VideoBean> videoBeanList;

    public VideoAdapter(Context context) {
        super(context);
    }

    public void setData(List<VideoBean> videoBeanList) {
        this.videoBeanList = videoBeanList;
        notifyDataSetChanged();
    }

    @Override
    protected int getItemRes() {
        return R.layout.item_video;
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(View view) {
        return new VideoViewHolder(view);
    }

    @Override
    protected void bindClickListener(RecyclerView.ViewHolder viewHolder, int position) {
        VideoViewHolder videoViewHolder = (VideoViewHolder) viewHolder;
        VideoBean videoBean = videoBeanList.get(position);
        // 头像
        String portrait = videoBean.getPortrait();
        String nickname = videoBean.getNickname();
        VideoBean.PraiseBean praise = videoBean.getPraise();
        String num = praise.getNum();
        if (UiTools.noEmpty(nickname)) {
            videoViewHolder.tvVideoAuthor.setText(nickname);
        }else{
            videoViewHolder.tvVideoAuthor.setText("");
        }
        if (UiTools.noEmpty(num)) {
            videoViewHolder.tvWatchNumber.setText(num);
        }else{
            videoViewHolder.tvWatchNumber.setText("0");
        }
        GlideApp.with(MyApplication.getContext())
                .load(portrait)
                .placeholder(R.drawable.chatroom_01)
                .error(R.drawable.chatroom_01)
                .into(videoViewHolder.ivVideoAuthor);

        GlideApp.with(MyApplication.getContext())
                .load("")
                .placeholder(R.drawable.chatroom_01)
                .error(R.drawable.chatroom_01)
                .into(videoViewHolder.ivVideoCover);
    }

    @Override
    public int getItemCount() {
        if (videoBeanList != null && videoBeanList.size() > 0) {
            return videoBeanList.size();
        }
        return 0;
    }


    static class VideoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivVideoCover;
        private TextView tvVideoAuthor;
        private CircleImageView ivVideoAuthor;
        private TextView tvWatchNumber;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            ivVideoCover = itemView.findViewById(R.id.ivVideoCover);
            tvVideoAuthor = itemView.findViewById(R.id.tvVideoAuthor);
            ivVideoAuthor = itemView.findViewById(R.id.ivVideoAuthor);
            tvWatchNumber = itemView.findViewById(R.id.tvWatchNumber);
        }
    }
}
