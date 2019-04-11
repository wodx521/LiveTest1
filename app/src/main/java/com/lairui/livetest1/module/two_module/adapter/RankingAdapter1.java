package com.lairui.livetest1.module.two_module.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lairui.livetest1.MyApplication;
import com.lairui.livetest1.R;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.entity.bean.RankingBean;
import com.lairui.livetest1.ui.panel.CircleImageView;
import com.wanou.framelibrary.glidetools.GlideApp;
import com.wanou.framelibrary.utils.UiTools;

import java.util.List;

public class RankingAdapter1 extends RecyclerView.Adapter {
    private static final int ITEM_TYPE1 = 1;
    private static final int ITEM_TYPE2 = 2;
    private LayoutInflater inflater;
    private List<RankingBean.ListBean> list;
    private Context mContext;

    public RankingAdapter1(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    public void setList(List<RankingBean.ListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if (i == ITEM_TYPE1) {
            view = inflater.inflate(R.layout.item_ranking, viewGroup, false);
            return new RankingNormalViewHolder(view);
        } else {
            view = inflater.inflate(R.layout.ranking_title_layout, viewGroup, false);
            return new RankingTitleViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int itemViewType = getItemViewType(position);
        RankingBean.ListBean listBean = list.get(position);
        String total = listBean.getTotal();
        RankingBean.ListBean.UidBean uid = listBean.getUid();
        String nickname = uid.getNickname();
        String portrait = uid.getPortrait();
        String sex = uid.getSex();
        if (itemViewType == ITEM_TYPE1) {
            RankingNormalViewHolder rankingNormalViewHolder = (RankingNormalViewHolder) viewHolder;
            if (UiTools.getString(R.string.male).equals(sex)) {
                rankingNormalViewHolder.ivGender.setImageResource(R.drawable.ic_male);
            } else {
                rankingNormalViewHolder.ivGender.setImageResource(R.drawable.ic_female);
            }
            rankingNormalViewHolder.tvUserDes.setText(UiTools.getString(R.string.earnings).replace("%s", total));
            rankingNormalViewHolder.tvUserName.setText(nickname);
            GlideApp.with(MyApplication.getContext())
                    .load(AppConstant.BASE_URL + portrait)
                    .placeholder(R.drawable.chatroom_01)
                    .error(R.drawable.chatroom_01)
                    .into(rankingNormalViewHolder.ivUserIcon);
        } else {
            RankingTitleViewHolder rankingTitleViewHolder = (RankingTitleViewHolder) viewHolder;
            if (position == 0) {
                if (UiTools.getString(R.string.male).equals(sex)) {
                    rankingTitleViewHolder.ivFirstGender.setImageResource(R.drawable.ic_male);
                } else {
                    rankingTitleViewHolder.ivFirstGender.setImageResource(R.drawable.ic_female);
                }
                rankingTitleViewHolder.tvFirstName.setText(nickname);
                GlideApp.with(MyApplication.getContext())
                        .load(AppConstant.BASE_URL + portrait)
                        .placeholder(R.drawable.chatroom_01)
                        .error(R.drawable.chatroom_01)
                        .into(rankingTitleViewHolder.civFirst);
                rankingTitleViewHolder.tvFirstGet.setText(UiTools.getString(R.string.earnings).replace("%s", total));
            } else if (position == 1) {
                if (UiTools.getString(R.string.male).equals(sex)) {
                    rankingTitleViewHolder.ivSecondGender.setImageResource(R.drawable.ic_male);
                } else {
                    rankingTitleViewHolder.ivSecondGender.setImageResource(R.drawable.ic_female);
                }
                rankingTitleViewHolder.tvSecondName.setText(nickname);
                GlideApp.with(MyApplication.getContext())
                        .load(AppConstant.BASE_URL + portrait)
                        .placeholder(R.drawable.chatroom_01)
                        .error(R.drawable.chatroom_01)
                        .into(rankingTitleViewHolder.civSecond);
                rankingTitleViewHolder.tvSecondGet.setText(UiTools.getString(R.string.earnings).replace("%s", total));
            } else {
                if (UiTools.getString(R.string.male).equals(sex)) {
                    rankingTitleViewHolder.ivFirstGender.setImageResource(R.drawable.ic_male);
                } else {
                    rankingTitleViewHolder.ivFirstGender.setImageResource(R.drawable.ic_female);
                }
                rankingTitleViewHolder.tvThirdName.setText(nickname);
                GlideApp.with(MyApplication.getContext())
                        .load(AppConstant.BASE_URL + portrait)
                        .placeholder(R.drawable.chatroom_01)
                        .error(R.drawable.chatroom_01)
                        .into(rankingTitleViewHolder.civThird);
                rankingTitleViewHolder.tvThirdGet.setText(UiTools.getString(R.string.earnings).replace("%s", total));
            }

        }


    }

    @Override
    public int getItemViewType(int position) {
        if (position > 2) {
            return ITEM_TYPE1;
        }
        return ITEM_TYPE2;
    }

    @Override
    public int getItemCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        }
        return 0;
    }

    static class RankingNormalViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView ivUserIcon;
        private TextView tvUserName;
        private ImageView ivGender;
        private TextView tvUserDes;

        public RankingNormalViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUserIcon = itemView.findViewById(R.id.ivUserIcon);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            ivGender = itemView.findViewById(R.id.ivSecondGender);
            tvUserDes = itemView.findViewById(R.id.tvUserDes);
        }
    }

    static class RankingTitleViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView civFirst, civSecond, civThird;
        private ImageView ivSecondGender, ivSecondLevel, ivFirstGender, ivFirstLevel,
                ivThirdGender, ivThirdLevel;
        private TextView tvSecondName, tvSecondGet, tvFirstName, tvFirstGet, tvThirdName,
                tvThirdGet;

        public RankingTitleViewHolder(@NonNull View itemView) {
            super(itemView);
            civFirst = itemView.findViewById(R.id.civFirst);
            civSecond = itemView.findViewById(R.id.civSecond);
            civThird = itemView.findViewById(R.id.civThird);
            tvSecondName = itemView.findViewById(R.id.tvSecondName);
            ivSecondGender = itemView.findViewById(R.id.ivSecondGender);
            ivSecondLevel = itemView.findViewById(R.id.ivSecondLevel);
            tvSecondGet = itemView.findViewById(R.id.tvSecondGet);
            tvFirstName = itemView.findViewById(R.id.tvFirstName);
            ivFirstGender = itemView.findViewById(R.id.ivFirstGender);
            ivFirstLevel = itemView.findViewById(R.id.ivFirstLevel);
            tvFirstGet = itemView.findViewById(R.id.tvFirstGet);
            tvThirdName = itemView.findViewById(R.id.tvThirdName);
            ivThirdGender = itemView.findViewById(R.id.ivThirdGender);
            ivThirdLevel = itemView.findViewById(R.id.ivThirdLevel);
            tvThirdGet = itemView.findViewById(R.id.tvThirdGet);
        }
    }

}
