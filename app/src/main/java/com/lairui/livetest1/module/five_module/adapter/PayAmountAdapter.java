package com.lairui.livetest1.module.five_module.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;
import com.wanou.framelibrary.utils.UiTools;

public class PayAmountAdapter extends BaseRecycleViewAdapter {

    private String[] payAmount;
    private String type;

    public PayAmountAdapter(Context context) {
        super(context);
        payAmount = UiTools.getStringArray(R.array.payAmount);
    }

    public void setType(String type) {
        this.type = type;
        notifyDataSetChanged();
    }

    @Override
    protected int getItemRes() {
        return R.layout.item_pay_amount;
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(View view) {
        return new PayAmountViewHolder(view);
    }

    @Override
    protected void bindClickListener(RecyclerView.ViewHolder viewHolder, int position) {
        PayAmountViewHolder payAmountViewHolder = (PayAmountViewHolder) viewHolder;
        String pay = payAmount[position];
        payAmountViewHolder.tvArrival1.setText(Integer.parseInt(pay) * 10 + "");
        if ("wechat".equals(type)) {
            payAmountViewHolder.tvPayAmount1.setText("微信钻石" + Integer.parseInt(pay) * 10);
        } else {
            payAmountViewHolder.tvPayAmount1.setText("支付宝钻石" + Integer.parseInt(pay) * 10);
        }
        payAmountViewHolder.tvSpent.setText(pay);
    }

    @Override
    public int getItemCount() {
        return payAmount.length;
    }

    static class PayAmountViewHolder extends RecyclerView.ViewHolder {
        private TextView tvArrival1, tvPayAmount1, tvSpent;

        public PayAmountViewHolder(@NonNull View itemView) {
            super(itemView);
            tvArrival1 = itemView.findViewById(R.id.tvArrival1);
            tvPayAmount1 = itemView.findViewById(R.id.tvPayAmount1);
            tvSpent = itemView.findViewById(R.id.tvSpent);
        }
    }
}
