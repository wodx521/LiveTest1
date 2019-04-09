package com.lairui.livetest1.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.entity.bean.CategoryBean;
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;

import java.util.List;

/**
 * Author by wodx521
 * Date on 2018/12/13.
 */
public class ChooseListAdapter extends BaseRecycleViewAdapter {
    private List<CategoryBean> mContentList;

    public ChooseListAdapter(Context context) {
        super(context);
    }

    public void setData(List<CategoryBean> contentList) {
        mContentList = contentList;
        notifyDataSetChanged();
    }

    @Override
    protected int getItemRes() {
        return R.layout.item_content;
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(View view) {
        return new ContentListViewHolder(view);
    }

    @Override
    protected void bindClickListener(RecyclerView.ViewHolder viewHolder, int position) {
        ContentListViewHolder contentListViewHolder = (ContentListViewHolder) viewHolder;
        CategoryBean coinBean = mContentList.get(position);
        contentListViewHolder.mTvContent.setText(coinBean.getName());
        if (defItem != -1) {
            if (defItem == position) {
                contentListViewHolder.itemView.setSelected(true);
            } else {
                contentListViewHolder.itemView.setSelected(false);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mContentList != null && mContentList.size() > 0) {
            return mContentList.size();
        }
        return 0;
    }


    static class ContentListViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvContent;

        ContentListViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvContent = itemView.findViewById(R.id.tvCategory);
        }
    }
}
