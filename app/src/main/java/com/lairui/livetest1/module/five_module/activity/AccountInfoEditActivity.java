package com.lairui.livetest1.module.five_module.activity;

import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.module.five_module.presenter.AccountInfoEditPresenter;
import com.lairui.livetest1.ui.panel.CircleImageView;
import com.wanou.framelibrary.base.BaseMvpActivity;

public class AccountInfoEditActivity extends BaseMvpActivity<AccountInfoEditPresenter> implements View.OnClickListener {
    private ImageView ivLeft, ivGender;
    private LinearLayout linearLayout1, linearLayout2, linearLayout3, linearLayout4,
            linearLayout5, linearLayout6, linearLayout7, linearLayout8, linearLayout9;
    private CircleImageView ivUserIcon;
    private TextView tvToolbarTitle, tvOperate, tvNickName, tvAccountNumber, tvSignature,
            tvBirthday, tvEmotion, tvHometown, tvCareer;

    @Override
    protected AccountInfoEditPresenter getPresenter() {
        return new AccountInfoEditPresenter();
    }

    @Override
    protected int getResId() {
        return R.layout.activity_account_edit;
    }

    @Override
    protected void initView() {
        ivLeft = findViewById(R.id.ivLeft);
        tvToolbarTitle = findViewById(R.id.tvToolbarTitle);
        tvOperate = findViewById(R.id.tvOperate);
        linearLayout1 = findViewById(R.id.linearLayout1);
        ivUserIcon = findViewById(R.id.ivUserIcon);
        linearLayout2 = findViewById(R.id.linearLayout2);
        tvNickName = findViewById(R.id.tvNickName);
        linearLayout3 = findViewById(R.id.linearLayout3);
        tvAccountNumber = findViewById(R.id.tvAccountNumber);
        linearLayout4 = findViewById(R.id.linearLayout4);
        ivGender = findViewById(R.id.ivGender);
        linearLayout5 = findViewById(R.id.linearLayout5);
        tvSignature = findViewById(R.id.tvSignature);
        linearLayout6 = findViewById(R.id.linearLayout6);
        tvBirthday = findViewById(R.id.tvBirthday);
        linearLayout7 = findViewById(R.id.linearLayout7);
        tvEmotion = findViewById(R.id.tvEmotion);
        linearLayout8 = findViewById(R.id.linearLayout8);
        tvHometown = findViewById(R.id.tvHometown);
        linearLayout9 = findViewById(R.id.linearLayout9);
        tvCareer = findViewById(R.id.tvCareer);

        ivLeft.setOnClickListener(this);
        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);
        linearLayout4.setOnClickListener(this);
        linearLayout5.setOnClickListener(this);
        linearLayout6.setOnClickListener(this);
        linearLayout7.setOnClickListener(this);
        linearLayout8.setOnClickListener(this);
        linearLayout9.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if (mBundle != null) {
            UserAccountInfo userInfo = mBundle.getParcelable("userInfo");

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.linearLayout1:
                startActivityForResult(AccountInfoEditActivity.this, null, AppConstant.CHANG_INFO, ChangeAvatarActivity.class);
                break;
            case R.id.linearLayout2:

                break;
            case R.id.linearLayout3:

                break;
            case R.id.linearLayout4:

                break;
            case R.id.linearLayout5:

                break;
            case R.id.linearLayout6:

                break;
            case R.id.linearLayout7:

                break;
            case R.id.linearLayout8:

                break;
            case R.id.linearLayout9:

                break;
            default:
        }
    }
}
