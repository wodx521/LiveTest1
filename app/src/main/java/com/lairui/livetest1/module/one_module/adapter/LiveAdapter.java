package com.lairui.livetest1.module.one_module.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lairui.livetest1.MyApplication;
import com.lairui.livetest1.R;
import com.lairui.livetest1.entity.bean.LiveRoomBean;
import com.lairui.livetest1.ui.panel.CircleImageView;
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;
import com.wanou.framelibrary.glidetools.GlideApp;
import com.wanou.framelibrary.utils.UiTools;

import java.util.List;

public class LiveAdapter extends BaseRecycleViewAdapter {
    private List<LiveRoomBean> tempLiveRoom;

    public LiveAdapter(Context context) {
        super(context);
    }

    public void setData(List<LiveRoomBean> tempLiveRoom) {
        this.tempLiveRoom = tempLiveRoom;
        notifyDataSetChanged();
    }

    @Override
    protected int getItemRes() {
        return R.layout.itme_live;
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(View view) {
        return new LiveViewHolder(view);
    }

    @Override
    protected void bindClickListener(RecyclerView.ViewHolder viewHolder, int position) {
        LiveViewHolder liveViewHolder = (LiveViewHolder) viewHolder;
        LiveRoomBean liveRoomBean = tempLiveRoom.get(position);
        String status = liveRoomBean.getStatus();
        String title = liveRoomBean.getTitle();
        String city = liveRoomBean.getCity();
        String userNum = liveRoomBean.getUserNum();
        String portrait = liveRoomBean.getPortrait();
        // 主播大致位置
        if (UiTools.noEmpty(city)) {
            liveViewHolder.tvUserLocation.setText(city);
        } else {
            liveViewHolder.tvUserLocation.setText("");
        }
        // 观众数
        if (UiTools.noEmpty(userNum)) {
            String string = UiTools.getString(R.string.defaultScanNum);
            liveViewHolder.tvViewerNumber.setText(string.replace("0", userNum));
        } else {
            liveViewHolder.tvViewerNumber.setText(R.string.defaultScanNum);
        }
        // 主播名称
        liveViewHolder.tvUserName.setText(title);
        // 直播状态
        liveViewHolder.tvLiveStatus.setText(status);

        GlideApp.with(MyApplication.getContext())
                .load(portrait)
                .placeholder(R.drawable.chatroom_01)
                .error(R.drawable.chatroom_01)
                .into(liveViewHolder.ivUserIcon);
        GlideApp.with(MyApplication.getContext())
                .load(liveRoomBean.getCover())
                .placeholder(R.drawable.default_cover)
                .error(R.drawable.default_cover)
                .centerCrop()
                .into(liveViewHolder.ivCover);
    }

    @Override
    public int getItemCount() {
        if (tempLiveRoom != null && tempLiveRoom.size() > 0) {
            return tempLiveRoom.size();
        }
        return 0;
    }

    static class LiveViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView ivUserIcon;
        private TextView tvUserName;
        private TextView tvViewerNumber;
        private TextView tvUserLocation;
        private ImageView ivCover;
        private TextView tvLiveStatus;

        public LiveViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUserIcon = itemView.findViewById(R.id.ivUserIcon);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvViewerNumber = itemView.findViewById(R.id.tvViewerNumber);
            tvUserLocation = itemView.findViewById(R.id.tvUserLocation);
            ivCover = itemView.findViewById(R.id.ivCover);
            tvLiveStatus = itemView.findViewById(R.id.tvLiveStatus);
        }
    }
}
