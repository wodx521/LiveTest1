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
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;
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

    @Override
    public int getItemViewType(int position) {
        if (position > 2) {
            return ITEM_TYPE1;
        }
        return ITEM_TYPE2;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if (i == ITEM_TYPE1) {
            view = inflater.inflate(R.layout.ranking_title_layout, viewGroup, false);
            return null;
        } else {
            view = inflater.inflate(R.layout.item_ranking, viewGroup, false);
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }


    public void setList(List<RankingBean.ListBean> list) {
        this.list = list;
        notifyDataSetChanged();
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
