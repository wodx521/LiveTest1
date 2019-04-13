package com.lairui.livetest1.module.five_module.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;
import com.wanou.framelibrary.utils.UiTools;

public class SettingAdapter extends BaseRecycleViewAdapter {

    private String[] stringArray;

    public SettingAdapter(Context context) {
        super(context);
        stringArray = UiTools.getStringArray(R.array.settingList);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    protected int getItemRes() {
        return R.layout.item_setting;
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(View view) {
        return new SettingViewHolder(view);
    }

    @Override
    protected void bindClickListener(RecyclerView.ViewHolder viewHolder, int position) {
        SettingViewHolder settingViewHolder = (SettingViewHolder) viewHolder;

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class SettingViewHolder extends RecyclerView.ViewHolder {
        private TextView tv25;
        private TextView tv26;

        public SettingViewHolder(@NonNull View itemView) {
            super(itemView);
            tv25 = itemView.findViewById(R.id.tv25);
            tv26 = itemView.findViewById(R.id.tv26);
        }
    }
}
