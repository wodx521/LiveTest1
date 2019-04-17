package com.lairui.livetest1.module.five_module.fragment;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.RequestOptions;
import com.lairui.livetest1.MyApplication;
import com.lairui.livetest1.R;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.entity.bean.UserAccountInfo;
import com.lairui.livetest1.entity.jsonparam.BaseParams;
import com.lairui.livetest1.module.five_module.activity.AccountInfoEditActivity;
import com.lairui.livetest1.module.five_module.activity.ChangeAvatarActivity;
import com.lairui.livetest1.module.five_module.activity.RechargeActivity;
import com.lairui.livetest1.module.five_module.activity.SettingActivity;
import com.lairui.livetest1.module.five_module.adapter.MineAdapter;
import com.lairui.livetest1.module.five_module.presenter.FiveMainPresenter;
import com.lairui.livetest1.ui.activity.SearchActivity;
import com.lairui.livetest1.ui.panel.CircleImageView;
import com.wanou.framelibrary.base.BaseMvpFragment;
import com.wanou.framelibrary.glidetools.GlideApp;
import com.wanou.framelibrary.utils.GsonUtils;
import com.wanou.framelibrary.utils.SpUtils;
import com.wanou.framelibrary.utils.UiTools;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class FiveMainFragment extends BaseMvpFragment<FiveMainPresenter> implements View.OnClickListener {
    private ImageView ivLeft, ivRight1, ivBg, ivGender, ivRank, ivEdit;
    private CircleImageView ivUserIcon;
    private TextView tvToolbarTitle, tvUserName, tvSignature, tvAccountNumber, tvReplay,
            tvSmallVideo, tvAttention, tvFans;
    private RecyclerView rvMine;
    private TextView tvContent4, tvContent5, tvContent6, tvContent7, tvContent8, tvContent9,
            tvContent10, tvContent11, tvContent12, tvContent13, tvContent14, tvContent15,
            tvContent16, tvContent17, tvContent18;
    private ConstraintLayout constraintLayout4, constraintLayout5, constraintLayout6,
            constraintLayout7, constraintLayout8, constraintLayout9, constraintLayout10,
            constraintLayout11, constraintLayout12, constraintLayout13, constraintLayout14,
            constraintLayout15, constraintLayout16, constraintLayout17, constraintLayout18;
    private BaseParams baseParams = new BaseParams();
    private Toolbar toolBar;
    private ConstraintLayout clLoading;
    private NestedScrollView sclAllView;
    private ConstraintLayout clError;
    private MineAdapter mineAdapter;
    private Bundle bundle = new Bundle();
    private UserAccountInfo userInfo;
    private String portrait;

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
        ivLeft = view.findViewById(R.id.ivLeft);
        tvToolbarTitle = view.findViewById(R.id.tvToolbarTitle);
        ivRight1 = view.findViewById(R.id.ivRight1);
        ivBg = view.findViewById(R.id.ivBg);
        toolBar = view.findViewById(R.id.toolBar);
        ivUserIcon = view.findViewById(R.id.ivUserIcon);
        tvUserName = view.findViewById(R.id.tvUserName);
        ivGender = view.findViewById(R.id.ivSecondGender);
        ivRank = view.findViewById(R.id.ivSecondLevel);
        ivEdit = view.findViewById(R.id.ivEdit);
        clError = view.findViewById(R.id.clError);
        tvSignature = view.findViewById(R.id.tvSignature);
        tvAccountNumber = view.findViewById(R.id.tvAccountNumber);
        tvReplay = view.findViewById(R.id.tvReplay);
        tvSmallVideo = view.findViewById(R.id.tvSmallVideo);
        tvAttention = view.findViewById(R.id.tvAttention);
        tvFans = view.findViewById(R.id.tvFans);
        rvMine = view.findViewById(R.id.rvMine);
        clLoading = view.findViewById(R.id.clLoading);

        constraintLayout4 = view.findViewById(R.id.constraintLayout4);
        tvContent4 = view.findViewById(R.id.tvContent4);
        constraintLayout5 = view.findViewById(R.id.constraintLayout5);
        tvContent5 = view.findViewById(R.id.tvContent5);
        constraintLayout6 = view.findViewById(R.id.constraintLayout6);
        tvContent6 = view.findViewById(R.id.tvContent6);
        constraintLayout7 = view.findViewById(R.id.constraintLayout7);
        tvContent7 = view.findViewById(R.id.tvContent7);
        constraintLayout8 = view.findViewById(R.id.constraintLayout8);
        tvContent8 = view.findViewById(R.id.tvContent8);
        constraintLayout9 = view.findViewById(R.id.constraintLayout9);
        tvContent9 = view.findViewById(R.id.tvContent9);
        constraintLayout10 = view.findViewById(R.id.constraintLayout10);
        tvContent10 = view.findViewById(R.id.tvContent10);
        constraintLayout11 = view.findViewById(R.id.constraintLayout11);
        tvContent11 = view.findViewById(R.id.tvContent11);
        constraintLayout12 = view.findViewById(R.id.constraintLayout12);
        tvContent12 = view.findViewById(R.id.tvContent12);
        constraintLayout13 = view.findViewById(R.id.constraintLayout13);
        tvContent13 = view.findViewById(R.id.tvContent13);
        constraintLayout14 = view.findViewById(R.id.constraintLayout14);
        tvContent14 = view.findViewById(R.id.tvContent14);
        constraintLayout15 = view.findViewById(R.id.constraintLayout15);
        tvContent15 = view.findViewById(R.id.tvContent15);
        constraintLayout16 = view.findViewById(R.id.constraintLayout16);
        tvContent16 = view.findViewById(R.id.tvContent16);
        constraintLayout17 = view.findViewById(R.id.constraintLayout17);
        tvContent17 = view.findViewById(R.id.tvContent17);
        constraintLayout18 = view.findViewById(R.id.constraintLayout18);
        tvContent18 = view.findViewById(R.id.tvContent18);

        rvMine.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        toolBar.setBackgroundColor(UiTools.getColor(R.color.transparent));
        ivLeft.setImageResource(R.drawable.search_white);
        ivRight1.setImageResource(R.drawable.private_chat_white);
        tvToolbarTitle.setText(R.string.bottom_five);
        viewVisible(ivLeft, ivRight1, clLoading);
        sclAllView = view.findViewById(R.id.sclAllView);

        ivEdit.setOnClickListener(this);
        ivUserIcon.setOnClickListener(this);
        constraintLayout4.setOnClickListener(this);
        constraintLayout5.setOnClickListener(this);
        constraintLayout6.setOnClickListener(this);
        constraintLayout7.setOnClickListener(this);
        constraintLayout8.setOnClickListener(this);
        constraintLayout9.setOnClickListener(this);
        constraintLayout10.setOnClickListener(this);
        constraintLayout11.setOnClickListener(this);
        constraintLayout12.setOnClickListener(this);
        constraintLayout13.setOnClickListener(this);
        constraintLayout14.setOnClickListener(this);
        constraintLayout15.setOnClickListener(this);
        constraintLayout16.setOnClickListener(this);
        constraintLayout17.setOnClickListener(this);
        constraintLayout18.setOnClickListener(this);

        viewGone(clError, sclAllView);
    }

    @Override
    protected void initData() {
        mineAdapter = new MineAdapter(getActivity());
        rvMine.setAdapter(mineAdapter);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivEdit:
                bundle.clear();
                if (userInfo != null) {
                    bundle.putParcelable("userInfo", userInfo);
                    startActivityForResult(FiveMainFragment.this, bundle, AppConstant.EDIT_INFO, AccountInfoEditActivity.class);
                }
                break;
            case R.id.ivUserIcon:
                bundle.clear();
                bundle.putString("userIconUrl", portrait);
                startActivityForResult(FiveMainFragment.this, bundle, AppConstant.CHANG_INFO, ChangeAvatarActivity.class);
                break;
            case R.id.ivLeft:
                startActivity(FiveMainFragment.this, null, SearchActivity.class);
                break;
            case R.id.constraintLayout4:
                bundle.clear();
                startActivityForResult(FiveMainFragment.this, bundle, AppConstant.CHANG_INFO, RechargeActivity.class);
                break;
            case R.id.constraintLayout5:

                break;
            case R.id.constraintLayout6:

                break;
            case R.id.constraintLayout7:

                break;
            case R.id.constraintLayout8:

                break;
            case R.id.constraintLayout9:

                break;
            case R.id.constraintLayout10:

                break;
            case R.id.constraintLayout11:

                break;
            case R.id.constraintLayout12:

                break;
            case R.id.constraintLayout13:

                break;
            case R.id.constraintLayout14:

                break;
            case R.id.constraintLayout15:

                break;
            case R.id.constraintLayout16:

                break;
            case R.id.constraintLayout17:

                break;
            case R.id.constraintLayout18:
                startActivity(FiveMainFragment.this, null, SettingActivity.class);
                break;
            default:
        }
    }

    public void setUserInfo(UserAccountInfo userInfo) {
        this.userInfo = userInfo;
        viewGone(clError, clLoading);
        viewVisible(sclAllView);
        portrait = userInfo.getPortrait();
        GlideApp.with(MyApplication.getContext())
                .load(portrait)
                .placeholder(R.drawable.shape_gray5_round5)
                .error(R.drawable.shape_gray5_round5)
                .apply(RequestOptions.bitmapTransform(new MultiTransformation<>(new CenterCrop(), new BlurTransformation(50))))
                .into(ivBg);

        GlideApp.with(MyApplication.getContext())
                .load(portrait)
                .placeholder(R.drawable.ic_head)
                .error(R.drawable.ic_head)
                .into(ivUserIcon);

        if ("男".equals(userInfo.getSex())) {
            ivGender.setImageResource(R.drawable.ic_male);
        } else {
            ivGender.setImageResource(R.drawable.ic_female);
        }

        String nickname = userInfo.getNickname();
        if (UiTools.noEmpty(nickname)) {
            tvUserName.setText(nickname);
        } else {
            tvUserName.setText("");
        }

    }

    @Override
    public void onResume() {
        if (getUserVisibleHint()) {
            getUserInfo();
        }
        super.onResume();
    }

    private void getUserInfo() {
        String token = (String) SpUtils.get("token", "");
        baseParams.operate = "userGroup-userInfo";
        baseParams.token = token;
        mPresenter.getUserInfo(GsonUtils.toJson(baseParams));
    }

    public void setUserInfoError(String json) {
        viewGone(sclAllView, clLoading);
        viewVisible(clError);
        clError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewGone(sclAllView, clError);
                viewVisible(clLoading);
                mPresenter.getUserInfo(json);
            }
        });
    }
}
