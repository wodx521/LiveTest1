package com.lairui.livetest1.module.one_module.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.entity.bean.ChatroomInfo;
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;
import com.wanou.framelibrary.glidetools.GlideApp;
import com.wanou.framelibrary.utils.UiTools;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

public class LiveListAdapter extends BaseRecycleViewAdapter {

    private String[] strings;
    private String[] userArray;
    private String[] provicesArray;
    private Random random;
    private ArrayList<ChatroomInfo> chatRoomList;

    public LiveListAdapter(Context context) {
        super(context);
        strings = UiTools.getStringArray(R.array.liveList);
        provicesArray = UiTools.getStringArray(R.array.provices_name);
        userArray = UiTools.getStringArray(R.array.userNames);
        random = new Random(provicesArray.length);
    }

    public void setData(ArrayList<ChatroomInfo> chatRoomList) {
        this.chatRoomList = chatRoomList;
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
        ChatroomInfo chatroomInfo = chatRoomList.get(position);
        String liveName = chatroomInfo.getLiveName();
        Uri chatUri = chatroomInfo.getChatUri();
        int onlineNum = chatroomInfo.getOnlineNum();
        liveListViewHolder.tvUserName.setText(liveName);
        liveListViewHolder.tvViewNumber.setText(onlineNum + "人");
        GlideApp.with(mContext)
                .load(chatUri)
                .into(liveListViewHolder.ivCover);
            liveListViewHolder.tvLocation.setText(provicesArray[random.nextInt(provicesArray.length)]);

    }

    @Override
    public int getItemCount() {
        if (chatRoomList != null && chatRoomList.size() > 0) {
            return chatRoomList.size();
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
