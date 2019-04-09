package com.lairui.livetest1.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.lairui.livetest1.R;
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;

public class ShareListAdapter extends BaseRecycleViewAdapter {

    private int[] shareListArray = {R.drawable.selector_share_qq, R.drawable.selector_share_wechat,
            R.drawable.selector_share_moments, R.drawable.selector_share_qqzone,
            R.drawable.selector_share_weibo};

    public ShareListAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemRes() {
        return R.layout.item_share;
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(View view) {
        return new ShareListViewHolder(view);
    }

    @Override
    protected void bindClickListener(RecyclerView.ViewHolder viewHolder, int position) {
        ShareListViewHolder shareListViewHolder = (ShareListViewHolder) viewHolder;
        shareListViewHolder.ivShare.setImageResource(shareListArray[position]);
    }

    @Override
    public int getItemCount() {
        return shareListArray.length;
    }

    static class ShareListViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivShare;

        public ShareListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivShare = itemView.findViewById(R.id.ivShare);
        }
    }
}
