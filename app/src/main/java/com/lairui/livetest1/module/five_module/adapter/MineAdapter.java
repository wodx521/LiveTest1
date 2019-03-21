package com.lairui.livetest1.module.five_module.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lairui.livetest1.MyApplication;
import com.lairui.livetest1.R;
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;
import com.wanou.framelibrary.glidetools.GlideApp;
import com.wanou.framelibrary.utils.UiTools;

public class MineAdapter extends BaseRecycleViewAdapter {

    private String[] stringArray;
    private String[] stringContent;
    private int[] mineIcon = {R.drawable.account, R.drawable.my_gain, R.drawable.pay_ranklist,
            R.drawable.ic_use_diamonds, R.drawable.ic_level, R.drawable.ic_anchor,
            R.drawable.ic_devote, R.drawable.ic_distribution, R.drawable.ic_show_podcast_goods,
            R.drawable.ic_show_user_order, R.drawable.ic_show_shopping_cart, R.drawable.ic_show_user_pai,
            R.drawable.ic_show_podcast_pai, R.drawable.ic_open_podcast_goods, R.drawable.ic_setting};

    public MineAdapter(Context context) {
        super(context);
        stringArray = UiTools.getStringArray(R.array.mine);
        stringContent = UiTools.getStringArray(R.array.mineContent);
    }

    @Override
    protected int getItemRes() {
        return R.layout.item_mine;
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(View view) {
        return new MineViewHolder(view);
    }

    @Override
    protected void bindClickListener(RecyclerView.ViewHolder viewHolder, int position) {
        MineViewHolder mineViewHolder = (MineViewHolder) viewHolder;
        GlideApp.with(MyApplication.getContext())
                .load(mineIcon[position])
                .into(mineViewHolder.iv1);
        mineViewHolder.tv1.setText(stringArray[position]);
        mineViewHolder.tv2.setText(stringContent[position]);
        if (position == 3) {
            mineViewHolder.tv2.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.arrow_right_gray_place, 0);
        } else {
            mineViewHolder.tv2.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.arrow_right_gray, 0);
        }

    }

    @Override
    public int getItemCount() {
        return stringArray.length;
    }

    static class MineViewHolder extends RecyclerView.ViewHolder {
        private TextView tv1;
        private TextView tv2;
        private ImageView iv1;

        public MineViewHolder(@NonNull View itemView) {
            super(itemView);
            iv1 = itemView.findViewById(R.id.iv1);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
        }
    }
}
