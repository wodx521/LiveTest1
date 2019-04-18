package com.lairui.livetest1.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;

import java.util.List;

public class BaseToolAdapter extends BaseRecycleViewAdapter {
    private List<String> toolList;
    private List<Integer> toolImage;

    public BaseToolAdapter(Context context) {
        super(context);
    }

    public void setData(List<String> toolList, List<Integer> toolImage) {
        this.toolList = toolList;
        this.toolImage = toolImage;
        notifyDataSetChanged();
    }

    @Override
    protected int getItemRes() {
        return R.layout.item_base_tool;
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(View view) {
        return new BaseToolViewHolder(view);
    }

    @Override
    protected void bindClickListener(RecyclerView.ViewHolder viewHolder, int position) {
        BaseToolViewHolder baseToolViewHolder = (BaseToolViewHolder) viewHolder;
        String toolName = toolList.get(position);
        Integer integer = toolImage.get(position);
        baseToolViewHolder.tvBaseTool.setText(toolName);
        baseToolViewHolder.ivBaseTool.setImageResource(integer);
    }

    @Override
    public int getItemCount() {
        if (toolList != null && toolList.size() > 0) {
            return toolList.size();
        }
        return 0;
    }

    static class BaseToolViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivBaseTool;
        private TextView tvBaseTool;

        public BaseToolViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBaseTool = itemView.findViewById(R.id.ivBaseTool);
            tvBaseTool = itemView.findViewById(R.id.tvBaseTool);
        }
    }
}
