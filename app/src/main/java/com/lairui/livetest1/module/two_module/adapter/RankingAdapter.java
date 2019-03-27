package com.lairui.livetest1.module.two_module.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lairui.livetest1.MyApplication;
import com.lairui.livetest1.R;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.entity.bean.RankingBean;
import com.lairui.livetest1.ui.panel.CircleImageView;
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;
import com.wanou.framelibrary.glidetools.GlideApp;
import com.wanou.framelibrary.utils.UiTools;

import java.util.List;

public class RankingAdapter extends BaseRecycleViewAdapter {
    private List<RankingBean.ListBean> list;

    public RankingAdapter(Context context) {
        super(context);
    }

    public void setList(List<RankingBean.ListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    protected int getItemRes() {
        return R.layout.item_ranking;
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(View view) {
        return new RankingViewHolder(view);
    }

    @Override
    protected void bindClickListener(RecyclerView.ViewHolder viewHolder, int position) {
        RankingViewHolder rankingViewHolder = (RankingViewHolder) viewHolder;
        RankingBean.ListBean listBean = list.get(position);
        RankingBean.ListBean.UidBean uid = listBean.getUid();
        String nickname = uid.getNickname();
        String portrait = uid.getPortrait();
        String sex = uid.getSex();
        if ("男".equals(sex)) {
            rankingViewHolder.ivGender.setImageResource(R.drawable.selected_male);
        } else {
            rankingViewHolder.ivGender.setImageResource(R.drawable.selected_female);
        }
        GlideApp.with(MyApplication.getContext())
                .load(AppConstant.BASE_URL + portrait)
                .placeholder(R.drawable.chatroom_01)
                .error(R.drawable.chatroom_01)
                .into(rankingViewHolder.ivUserIcon);
        if (UiTools.noEmpty(nickname)) {
            rankingViewHolder.tvUserName.setText(nickname);
        } else {
            rankingViewHolder.tvUserName.setText("");
        }
    }

    @Override
    public int getItemCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        }
        return 0;
    }

    static class RankingViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView ivUserIcon;
        private TextView tvUserName;
        private ImageView ivGender;
        private TextView tvUserDes;

        public RankingViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUserIcon = itemView.findViewById(R.id.ivUserIcon);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            ivGender = itemView.findViewById(R.id.ivSecondGender);
            tvUserDes = itemView.findViewById(R.id.tvUserDes);
        }
    }

}
