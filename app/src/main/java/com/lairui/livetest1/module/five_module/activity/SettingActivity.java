package com.lairui.livetest1.module.five_module.activity;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.entity.bean.LoginBean;
import com.lairui.livetest1.module.five_module.presenter.SettingPresenter;
import com.lairui.livetest1.ui.activity.LoginActivity;
import com.lairui.livetest1.utils.ObjectBox;
import com.wanou.framelibrary.base.BaseMvpActivity;
import com.wanou.framelibrary.manager.ActivityManage;

import io.objectbox.Box;

public class SettingActivity extends BaseMvpActivity<SettingPresenter> implements View.OnClickListener {
    private ImageView ivLeft;
    private TextView tvToolbarTitle;
    private TextView tvContent23;
    private RecyclerView rvSetting;
    private TextView tvExit;
    private ConstraintLayout constraintLayout19, constraintLayout20, constraintLayout21,
            constraintLayout22, constraintLayout23, constraintLayout24;

    @Override
    protected SettingPresenter getPresenter() {
        return new SettingPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getResId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        ivLeft = findViewById(R.id.ivLeft);
        tvToolbarTitle = findViewById(R.id.tvToolbarTitle);
        tvContent23 = findViewById(R.id.tvContent23);
        rvSetting = findViewById(R.id.rvSetting);
        tvExit = findViewById(R.id.tvExit);
        constraintLayout19 = findViewById(R.id.constraintLayout19);
        constraintLayout20 = findViewById(R.id.constraintLayout20);
        constraintLayout21 = findViewById(R.id.constraintLayout21);
        constraintLayout22 = findViewById(R.id.constraintLayout22);
        constraintLayout23 = findViewById(R.id.constraintLayout23);
        constraintLayout24 = findViewById(R.id.constraintLayout24);

        ivLeft.setImageResource(R.drawable.arrow_left_main_color);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.constraintLayout19:

                break;
            case R.id.constraintLayout20:

                break;
            case R.id.constraintLayout21:

                break;
            case R.id.constraintLayout22:

                break;
            case R.id.constraintLayout23:

                break;
            case R.id.constraintLayout24:

                break;
            case R.id.tvExit:
                startActivity(SettingActivity.this, null, LoginActivity.class);
                Box<LoginBean> loginBeanBox = ObjectBox.getBoxStore().boxFor(LoginBean.class);
                ActivityManage.getInstance().finishAll();
                break;
            default:
        }
    }
}

