package com.lairui.livetest1.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lairui.livetest1.R;
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;

public class ShareAdapter extends BaseRecycleViewAdapter {

    public ShareAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemRes() {
        return R.layout.item_share_content;
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(View view) {
        return null;
    }

    @Override
    protected void bindClickListener(RecyclerView.ViewHolder viewHolder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
