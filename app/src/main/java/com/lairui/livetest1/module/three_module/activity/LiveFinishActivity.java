package com.lairui.livetest1.module.three_module.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.lairui.livetest1.MyApplication;
import com.lairui.livetest1.R;
import com.lairui.livetest1.entity.bean.UserInfoBean;
import com.lairui.livetest1.module.three_module.presenter.LiveFinishPresenter;
import com.lairui.livetest1.ui.panel.CircleImageView;
import com.lairui.livetest1.utils.ObjectBox;
import com.wanou.framelibrary.base.BaseMvpActivity;
import com.wanou.framelibrary.glidetools.GlideApp;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class LiveFinishActivity extends BaseMvpActivity<LiveFinishPresenter> implements View.OnClickListener {
    private CircleImageView ivUserIcon;
    private ImageView ivBackground;
    private TextView tvUserName, tvScanNumber, tvGetNum, tvShare, tvBackHome;

    @Override
    protected LiveFinishPresenter getPresenter() {
        return new LiveFinishPresenter();
    }

    @Override
    protected void initData() {
        UserInfoBean currentUserInfo = ObjectBox.getCurrentUserInfo();
        String portrait = currentUserInfo.getPortrait();
        String nickname = currentUserInfo.getNickname();
        tvUserName.setText(nickname);
        GlideApp.with(MyApplication.getContext())
                .load(portrait)
                .apply(RequestOptions.bitmapTransform(new MultiTransformation<>(new CenterCrop(), new BlurTransformation(25))))
                .into(ivBackground);

        GlideApp.with(MyApplication.getContext())
                .load(portrait)
                .placeholder(R.drawable.ic_head)
                .error(R.drawable.ic_head)
                .into(ivUserIcon);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_live_finish;
    }

    @Override
    protected void initView() {
        ivUserIcon = findViewById(R.id.ivUserIcon);
        ivBackground = findViewById(R.id.ivBackground);
        tvUserName = findViewById(R.id.tvUserName);
        tvScanNumber = findViewById(R.id.tvScanNumber);
        tvGetNum = findViewById(R.id.tvGetNum);
        tvShare = findViewById(R.id.tvShare);
        tvBackHome = findViewById(R.id.tvBackHome);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvShare:

                break;
            case R.id.tvBackHome:

                break;
            default:
        }
    }
}
