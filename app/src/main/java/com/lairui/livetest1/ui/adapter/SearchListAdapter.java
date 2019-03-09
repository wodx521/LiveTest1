package com.lairui.livetest1.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lairui.livetest1.MyApplication;
import com.lairui.livetest1.R;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.entity.bean.SearchListBean;
import com.lairui.livetest1.ui.panel.CircleImageView;
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;
import com.wanou.framelibrary.glidetools.GlideApp;
import com.wanou.framelibrary.utils.UiTools;

import java.util.List;

public class SearchListAdapter extends BaseRecycleViewAdapter {
    private List<SearchListBean.DataBean> searchListBeanList;

    public SearchListAdapter(Context context) {
        super(context);
    }

    public void setSearchListBeanList(List<SearchListBean.DataBean> searchListBeanList) {
        this.searchListBeanList = searchListBeanList;
        notifyDataSetChanged();
    }

    @Override
    protected int getItemRes() {
        return R.layout.item_search_list;
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(View view) {
        return new SearchListViewHolder(view);
    }

    @Override
    protected void bindClickListener(RecyclerView.ViewHolder viewHolder, int position) {
        SearchListViewHolder searchListViewHolder = (SearchListViewHolder) viewHolder;
        SearchListBean.DataBean dataBean = searchListBeanList.get(position);
        String portrait = dataBean.getPortrait();
        String sex = dataBean.getSex();
        String phone = dataBean.getPhone();
        if ("男".equals(sex)) {
            searchListViewHolder.ivGender.setImageResource(R.drawable.selected_male);
        } else {
            searchListViewHolder.ivGender.setImageResource(R.drawable.selected_female);
        }
        GlideApp.with(MyApplication.getContext())
                .load(AppConstant.BASE_URL + portrait)
                .placeholder(R.drawable.chatroom_01)
                .error(R.drawable.chatroom_01)
                .into(searchListViewHolder.ivUserIcon);
        if (UiTools.noEmpty(phone)) {
            searchListViewHolder.tvUserName.setText(phone);
        } else {
            searchListViewHolder.tvUserName.setText("");
        }
        searchListViewHolder.tvUserDes.setText("");
        searchListViewHolder.ivFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (followClickListener != null) {
                    followClickListener.onFollowClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (searchListBeanList != null && searchListBeanList.size() > 0) {
            return searchListBeanList.size();
        }
        return 0;
    }

    static class SearchListViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView ivUserIcon;
        private TextView tvUserName;
        private ImageView ivGender;
        private ImageView ivFollow;
        private TextView tvUserDes;

        public SearchListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUserIcon = itemView.findViewById(R.id.ivUserIcon);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            ivGender = itemView.findViewById(R.id.ivGender);
            ivFollow = itemView.findViewById(R.id.ivFollow);
            tvUserDes = itemView.findViewById(R.id.tvUserDes);
        }
    }

    interface FollowClickListener {
        void onFollowClick(int position);
    }

    FollowClickListener followClickListener;

    public void setFollowClickListener(FollowClickListener followClickListener) {
        this.followClickListener = followClickListener;
    }
}

