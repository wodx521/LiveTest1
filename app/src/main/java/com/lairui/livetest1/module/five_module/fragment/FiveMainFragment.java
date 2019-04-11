package com.lairui.livetest1.module.five_module.fragment;

import android.view.View;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.module.five_module.presenter.FiveMainPresenter;
import com.lairui.livetest1.ui.activity.LoginActivity;
import com.lairui.livetest1.ui.panel.CircleImageView;
import com.wanou.framelibrary.base.BaseMvpFragment;
import com.wanou.framelibrary.manager.ActivityManage;
import com.wanou.framelibrary.utils.SpUtils;
import com.wanou.framelibrary.utils.UiTools;

public class FiveMainFragment extends BaseMvpFragment<FiveMainPresenter> implements View.OnClickListener {
    private CircleImageView ivHead;
    private TextView tvUserName;
    private TextView tvAccountNumber;
    private TextView tvAttentionNumber;
    private TextView tvFansNumber;
    private TextView tvRecord;
    private TextView tvApprove;
    private TextView tvSetting;
    private TextView tvExit;

    @Override
    protected FiveMainPresenter getPresenter() {
        return new FiveMainPresenter();
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_five;
    }

    @Override
    protected void initView(View view) {
        ivHead = view.findViewById(R.id.ivHead);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvAccountNumber = view.findViewById(R.id.tvAccountNumber);
        tvAttentionNumber = view.findViewById(R.id.tvAttentionNumber);
        tvFansNumber = view.findViewById(R.id.tvFansNumber);
        tvRecord = view.findViewById(R.id.tvRecord);
        tvApprove = view.findViewById(R.id.tvApprove);
        tvSetting = view.findViewById(R.id.tvSetting);
        tvExit = view.findViewById(R.id.tvExit);
        tvExit.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        String nickName = (String) SpUtils.get("nickName", "");
        String userName = (String) SpUtils.get("userName", "");

        if (UiTools.noEmpty(nickName)) {
            tvUserName.setText(nickName);
        } else {
            tvUserName.setText("");
        }
        if (UiTools.noEmpty(userName)) {
            tvAccountNumber.setText(userName);
        } else {
            tvAccountNumber.setText("");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvExit:
                startActivity(FiveMainFragment.this, null, LoginActivity.class);
                SpUtils.put("token", "");
                ActivityManage.getInstance().finishAll();
                break;
            default:
        }
    }
}
