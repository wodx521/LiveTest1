package com.lairui.livetest1.module.one_module.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;
import com.wanou.framelibrary.glidetools.GlideApp;
import com.wanou.framelibrary.utils.UiTools;

public class LiveListAdapter extends BaseRecycleViewAdapter {

    private int[] intArray;

    public LiveListAdapter(Context context) {
        super(context);
        intArray = UiTools.getIntArray(R.array.liveList);
    }

    @Override
    protected int getItemRes() {
        return R.layout.item_live_list;
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(View view) {
        return new LiveListViewHolder(view);
    }

    @Override
    protected void bindClickListener(RecyclerView.ViewHolder viewHolder, int position) {
        LiveListViewHolder liveListViewHolder = (LiveListViewHolder) viewHolder;

        liveListViewHolder.tvLocation.setText("");
        liveListViewHolder.tvLiveDes.setText("");
        liveListViewHolder.tvViewNumber.setText("");
        GlideApp.with(mContext)
                .load(intArray[position])
                .into(liveListViewHolder.ivCover);
    }

    @Override
    public int getItemCount() {
        return intArray.length;
    }

    static class LiveListViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivCover;
        private TextView tvLocation, tvLiveDes, tvViewNumber;

        public LiveListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.ivCover);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvLiveDes = itemView.findViewById(R.id.tvLiveDes);
            tvViewNumber = itemView.findViewById(R.id.tvViewNumber);
        }
    }
}
