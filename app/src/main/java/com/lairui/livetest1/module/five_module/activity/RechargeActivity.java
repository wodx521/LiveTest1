package com.lairui.livetest1.module.five_module.activity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.module.five_module.adapter.PayAmountAdapter;
import com.lairui.livetest1.module.five_module.presenter.RechargePresenter;
import com.lairui.livetest1.utils.CustomTextChange;
import com.wanou.framelibrary.base.BaseMvpActivity;
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;
import com.wanou.framelibrary.utils.UiTools;

public class RechargeActivity extends BaseMvpActivity<RechargePresenter> implements View.OnClickListener {
    private ImageView ivLeft;
    private RadioGroup rgPayMethod;
    private RadioButton rbWechat, rbAlipay;
    private RecyclerView rvPayAmount;
    private TextView tvToolbarTitle, tvBalance, tvArrival1, tvPayAmount1, tvDiamondNumber,
            tvRecharge;
    private EditText etCustomAmount;
    private PayAmountAdapter payAmountAdapter;

    @Override
    protected RechargePresenter getPresenter() {
        return new RechargePresenter();
    }

    @Override
    protected int getResId() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initView() {
        ivLeft = findViewById(R.id.ivLeft);
        tvToolbarTitle = findViewById(R.id.tvToolbarTitle);
        tvBalance = findViewById(R.id.tvBalance);
        rgPayMethod = findViewById(R.id.rgPayMethod);
        rbWechat = findViewById(R.id.rbWechat);
        rbAlipay = findViewById(R.id.rbAlipay);
        rvPayAmount = findViewById(R.id.rvPayAmount);
        tvArrival1 = findViewById(R.id.tvArrival1);
        tvPayAmount1 = findViewById(R.id.tvPayAmount1);
        etCustomAmount = findViewById(R.id.etCustomAmount);
        tvDiamondNumber = findViewById(R.id.tvDiamondNumber);
        tvRecharge = findViewById(R.id.tvRecharge);


        viewVisible(ivLeft);
        ivLeft.setImageResource(R.drawable.arrow_left_main_color);
        tvToolbarTitle.setText(R.string.recharge);

        ivLeft.setOnClickListener(this);
        rbWechat.setOnClickListener(this);
        rbAlipay.setOnClickListener(this);
        tvRecharge.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        rbWechat.performClick();
        payAmountAdapter = new PayAmountAdapter(this);
        rvPayAmount.setAdapter(payAmountAdapter);
        rvPayAmount.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));

        payAmountAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                TextView tvSpent = view.findViewById(R.id.tvSpent);
                if (rbWechat.isChecked()) {
                    UiTools.showToast("微信支付" + tvSpent.getText());
                }
                if (rbAlipay.isChecked()) {
                    UiTools.showToast("支付宝支付" + tvSpent.getText());
                }
            }
        });

        etCustomAmount.addTextChangedListener(new CustomTextChange() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (UiTools.noEmpty(s.toString())) {
                    tvDiamondNumber.setText(UiTools.formatNumber((Double.parseDouble(s.toString()) * 10), "#.##"));
                } else {
                    tvDiamondNumber.setText("0");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.rbWechat:
                payAmountAdapter.setType("wechat");
                break;
            case R.id.rbAlipay:
                payAmountAdapter.setType("alipay");
                break;
            case R.id.tvRecharge:
                String customAmount = UiTools.getText(etCustomAmount);
                if (UiTools.noEmpty(customAmount)) {

                }
                break;
            default:
        }
    }
}
