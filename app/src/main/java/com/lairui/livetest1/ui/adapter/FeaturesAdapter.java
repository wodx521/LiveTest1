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

public class FeaturesAdapter extends BaseRecycleViewAdapter {
    private List<String> featuresList;
    private List<Integer> featuresImage;

    public FeaturesAdapter(Context context) {
        super(context);
    }

    public void setData(List<String> featuresList, List<Integer> featuresImage) {
        this.featuresList = featuresList;
        this.featuresImage = featuresImage;
        notifyDataSetChanged();
    }

    @Override
    protected int getItemRes() {
        return R.layout.item_features;
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(View view) {
        return new FeaturesViewHolder(view);
    }

    @Override
    protected void bindClickListener(RecyclerView.ViewHolder viewHolder, int position) {
        FeaturesViewHolder featuresViewHolder = (FeaturesViewHolder) viewHolder;
        String featuresName = featuresList.get(position);
        Integer featuresIcon = featuresImage.get(position);

        featuresViewHolder.ivFeatures.setImageResource(featuresIcon);
        featuresViewHolder.tvFeatures.setText(featuresName);
    }

    @Override
    public int getItemCount() {
        if (featuresList != null && featuresList.size() > 0) {
            return featuresList.size();
        }
        return 0;
    }

    static class FeaturesViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivFeatures;
        private TextView tvFeatures;

        public FeaturesViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFeatures = itemView.findViewById(R.id.ivFeatures);
            tvFeatures = itemView.findViewById(R.id.tvFeatures);
        }
    }

}
