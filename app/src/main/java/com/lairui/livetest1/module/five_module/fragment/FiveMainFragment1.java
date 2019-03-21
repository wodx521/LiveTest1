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

import com.bumptech.glide.request.RequestOptions;
import com.lairui.livetest1.MyApplication;
import com.lairui.livetest1.R;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.entity.bean.UserAccountInfo;
import com.lairui.livetest1.entity.jsonparam.BaseParams;
import com.lairui.livetest1.module.five_module.activity.AccountInfoEditActivity;
import com.lairui.livetest1.module.five_module.activity.ChangeAvatarActivity;
import com.lairui.livetest1.module.five_module.adapter.MineAdapter;
import com.lairui.livetest1.module.five_module.presenter.FiveMainPresenter1;
import com.lairui.livetest1.ui.activity.SearchActivity;
import com.lairui.livetest1.ui.panel.CircleImageView;
import com.wanou.framelibrary.base.BaseMvpFragment;
import com.wanou.framelibrary.glidetools.GlideApp;
import com.wanou.framelibrary.utils.GsonUtils;
import com.wanou.framelibrary.utils.SpUtils;
import com.wanou.framelibrary.utils.UiTools;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class FiveMainFragment1 extends BaseMvpFragment<FiveMainPresenter1> implements View.OnClickListener {
    private ImageView ivLeft, ivRight1, ivBg, ivGender, ivRank, ivEdit;
    private CircleImageView ivUserIcon;
    private TextView tvToolbarTitle, tvUserName, tvSignature, tvAccountNumber, tvReplay,
            tvSmallVideo, tvAttention, tvFans;
    private RecyclerView rvMine;
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
    protected FiveMainPresenter1 getPresenter() {
        return new FiveMainPresenter1();
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_five1;
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
        ivGender = view.findViewById(R.id.ivGender);
        ivRank = view.findViewById(R.id.ivRank);
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
        rvMine.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        toolBar.setBackgroundColor(UiTools.getColor(R.color.transparent));
        ivLeft.setImageResource(R.drawable.search_white);
        ivRight1.setImageResource(R.drawable.private_chat_white);
        tvToolbarTitle.setText(R.string.bottom_five);
        viewVisible(ivLeft, ivRight1, clLoading);
        sclAllView = view.findViewById(R.id.sclAllView);

        ivEdit.setOnClickListener(this);
        ivUserIcon.setOnClickListener(this);

        viewGone(clError, sclAllView);
    }

    @Override
    protected void initData() {
        mineAdapter = new MineAdapter(getActivity());
        rvMine.setAdapter(mineAdapter);
    }

    private void getUserInfo() {
        String token = (String) SpUtils.get("token", "");
        baseParams.operate = "userGroup-userInfo";
        baseParams.token = token;
        mPresenter.getUserInfo(GsonUtils.toJson(baseParams));
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
                    bundle.putParcelable("userInfo",userInfo);
                    startActivityForResult(FiveMainFragment1.this, bundle, AppConstant.EDIT_INFO, AccountInfoEditActivity.class);
                }
                break;
            case R.id.ivUserIcon:
                bundle.clear();
                bundle.putString("userIconUrl",portrait);
                startActivityForResult(FiveMainFragment1.this, bundle, AppConstant.CHANG_INFO, ChangeAvatarActivity.class);
                break;
            case R.id.ivLeft:
                startActivity(FiveMainFragment1.this, null, SearchActivity.class);
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
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 1)))
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
        getUserInfo();
        super.onResume();
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
