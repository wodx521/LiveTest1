package com.lairui.livetest1.module.five_module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lairui.livetest1.MyApplication;
import com.lairui.livetest1.R;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.entity.bean.UserAccountInfo;
import com.lairui.livetest1.module.five_module.presenter.AccountInfoEditPresenter;
import com.lairui.livetest1.ui.panel.CircleImageView;
import com.wanou.framelibrary.base.BaseMvpActivity;
import com.wanou.framelibrary.glidetools.GlideApp;
import com.wanou.framelibrary.utils.UiTools;

public class AccountInfoEditActivity extends BaseMvpActivity<AccountInfoEditPresenter> implements View.OnClickListener {
    private ImageView ivLeft, ivGender;
    private LinearLayout linearLayout1, linearLayout2, linearLayout3, linearLayout4,
            linearLayout5, linearLayout6, linearLayout7, linearLayout8, linearLayout9;
    private CircleImageView ivUserIcon;
    private TextView tvToolbarTitle, tvOperate, tvNickName, tvAccountNumber, tvSignature,
            tvBirthday, tvEmotion, tvHometown, tvCareer;
    private String portrait;
    private Bundle bundle = new Bundle();
    private String nickname;

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
        tvOperate.setOnClickListener(this);
        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);
        linearLayout4.setOnClickListener(this);
        linearLayout5.setOnClickListener(this);
        linearLayout6.setOnClickListener(this);
        linearLayout7.setOnClickListener(this);
        linearLayout8.setOnClickListener(this);
        linearLayout9.setOnClickListener(this);
        ivLeft.setImageResource(R.drawable.arrow_left_main_color);
        tvToolbarTitle.setText(R.string.editInfo);
        tvOperate.setTextColor(UiTools.getColor(R.color.colorGray1));
        viewVisible(ivLeft);
        tvOperate.setText(R.string.save);
    }

    @Override
    protected void initData() {
        if (mBundle != null) {
            UserAccountInfo userInfo = mBundle.getParcelable("userInfo");
            portrait = userInfo.getPortrait();
            GlideApp.with(MyApplication.getContext())
                    .load(portrait)
                    .placeholder(R.drawable.ic_head)
                    .error(R.drawable.ic_head)
                    .into(ivUserIcon);
            nickname = userInfo.getNickname();
            if (UiTools.noEmpty(nickname)) {
                tvNickName.setText(nickname);
            } else {
                tvNickName.setText("");
            }
            String sex = userInfo.getSex();
            if (UiTools.noEmpty(sex)) {
                if ("男".equals(sex)) {
                    ivGender.setImageResource(R.drawable.ic_male);
                } else {
                    ivGender.setImageResource(R.drawable.ic_female);
                }
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.linearLayout1:
                bundle.clear();
                bundle.putString("userIconUrl", portrait);
                startActivityForResult(AccountInfoEditActivity.this, bundle, AppConstant.CHANG_INFO, ChangeAvatarActivity.class);
                break;
            case R.id.linearLayout2:
                bundle.clear();
                bundle.putString("nickname", nickname);
                startActivityForResult(AccountInfoEditActivity.this, bundle, AppConstant.CHANG_INFO, EditNickActivity.class);
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
            case R.id.tvOperate:
                // TODO: 2019/3/21  保存已经设置的信息
                UiTools.showToast("保存");
                finish();
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case AppConstant.CHANG_INFO:
                    if (data != null) {
                        String nickName = data.getStringExtra("nickName");
                        if (UiTools.noEmpty(nickName)) {
                            tvNickName.setText(nickName);
                        }
                    }
                    break;
                default:
            }
        }
    }
}
