package com.lairui.livetest1.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;

import java.util.List;

public class BeautySettingAdapter extends BaseRecycleViewAdapter {
    private List<String> beautyTypeList;

    public BeautySettingAdapter(Context context) {
        super(context);
    }

    public void setBeautyTypeList(List<String> beautyTypeList) {
        this.beautyTypeList = beautyTypeList;
        notifyDataSetChanged();
    }

    @Override
    protected int getItemRes() {
        return R.layout.item_beauty_type;
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(View view) {
        return new BeautySettingViewHolder(view);
    }

    @Override
    protected void bindClickListener(RecyclerView.ViewHolder viewHolder, int position) {
        BeautySettingViewHolder beautySettingViewHolder = (BeautySettingViewHolder) viewHolder;
        String beautyType = beautyTypeList.get(position);
        beautySettingViewHolder.tvBeautyType.setText(beautyType);
    }

    @Override
    public int getItemCount() {
        if (beautyTypeList != null && beautyTypeList.size() > 0) {
            return beautyTypeList.size();
        }
        return 0;
    }

    static class BeautySettingViewHolder extends RecyclerView.ViewHolder {
        private TextView tvBeautyType;

        public BeautySettingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBeautyType = itemView.findViewById(R.id.tvBeautyType);
        }
    }
}
