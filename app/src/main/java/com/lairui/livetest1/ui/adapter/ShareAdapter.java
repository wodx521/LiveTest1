package com.lairui.livetest1.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.entity.bean.ShareBean;
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;

import java.util.List;

public class ShareAdapter extends BaseRecycleViewAdapter {
    private List<ShareBean> shareBeanList;

    public ShareAdapter(Context context) {
        super(context);
    }

    public void setShareBeanList(List<ShareBean> shareBeanList) {
        this.shareBeanList = shareBeanList;
        notifyDataSetChanged();
    }

    @Override
    protected int getItemRes() {
        return R.layout.item_share_content;
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(View view) {
        return new ShareViewHolder(view);
    }

    @Override
    protected void bindClickListener(RecyclerView.ViewHolder viewHolder, int position) {
        ShareViewHolder shareViewHolder = (ShareViewHolder) viewHolder;
        ShareBean shareBean = shareBeanList.get(position);

        shareViewHolder.ivImage.setImageResource(shareBean.shareImageId);
        shareViewHolder.tvShare.setText(shareBean.shareName);
    }

    @Override
    public int getItemCount() {
        if (shareBeanList != null && shareBeanList.size() > 0) {
            return shareBeanList.size();
        }
        return 0;
    }

    static class ShareViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivImage;
        private TextView tvShare;

        public ShareViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvShare = itemView.findViewById(R.id.tvShare);
        }
    }
}
