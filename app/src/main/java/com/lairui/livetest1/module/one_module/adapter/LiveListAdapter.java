package com.lairui.livetest1.module.one_module.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.entity.bean.LiveRoomBean;
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;
import com.wanou.framelibrary.glidetools.GlideApp;
import com.wanou.framelibrary.utils.UiTools;

import java.util.List;
import java.util.Random;

public class LiveListAdapter extends BaseRecycleViewAdapter {
    private String[] provicesArray;
    private Random random;
    private List<LiveRoomBean> data;

    public LiveListAdapter(Context context) {
        super(context);
        provicesArray = UiTools.getStringArray(R.array.provices_name);
        random = new Random(provicesArray.length);
    }

    public void setData(List<LiveRoomBean> data) {
        this.data = data;
        notifyDataSetChanged();
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
        LiveRoomBean liveRoomBean = data.get(position);
        String title = liveRoomBean.getTitle();
        String cover = liveRoomBean.getCover();
        if (UiTools.noEmpty(title)) {
            liveListViewHolder.tvUserName.setText(title);
        } else {
            liveListViewHolder.tvUserName.setText("");
        }
        liveListViewHolder.tvViewNumber.setText("0人");
        GlideApp.with(mContext)
                .load(cover)
                .placeholder(R.drawable.ic_person_1)
                .centerCrop()
                .into(liveListViewHolder.ivCover);
        liveListViewHolder.tvLocation.setText(provicesArray[random.nextInt(provicesArray.length)]);

    }

    @Override
    public int getItemCount() {
        if (data != null && data.size() > 0) {
            return data.size();
        }
        return 0;
    }

    static class LiveListViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivCover;
        private TextView tvLocation, tvUserName, tvViewNumber;

        public LiveListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.ivCover);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvViewNumber = itemView.findViewById(R.id.tvViewNumber);
        }
    }
}
