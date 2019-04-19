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

public class ListInfoAdapter extends BaseRecycleViewAdapter {
    private List<String> msgInfo;

    public ListInfoAdapter(Context context) {
        super(context);
    }

    public void setData(List<String> msgInfo) {
        this.msgInfo = msgInfo;
        notifyDataSetChanged();
    }

    @Override
    protected int getItemRes() {
        return R.layout.item_info;
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(View view) {
        return new ListInfoViewHolder(view);
    }

    @Override
    protected void bindClickListener(RecyclerView.ViewHolder viewHolder, int position) {
        ListInfoViewHolder listInfoViewHolder = (ListInfoViewHolder) viewHolder;
        String msgContent = msgInfo.get(position);
        listInfoViewHolder.ivMsgIcon.setImageResource(R.drawable.chatroom_01);
        listInfoViewHolder.tvMsgContent.setText(msgContent);
    }

    @Override
    public int getItemCount() {
        if (msgInfo != null && msgInfo.size() > 0) {
            return msgInfo.size();
        }
        return 0;
    }


    static class ListInfoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivMsgIcon;
        private TextView tvMsgContent;

        public ListInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMsgIcon = itemView.findViewById(R.id.ivMsgIcon);
            tvMsgContent = itemView.findViewById(R.id.tvMsgContent);
        }
    }
}
