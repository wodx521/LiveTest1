package com.lairui.livetest1.module.three_module.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.lairui.livetest1.MyApplication;
import com.lairui.livetest1.R;
import com.lairui.livetest1.dialog.ShareDialog;
import com.lairui.livetest1.entity.bean.ShareBean;
import com.lairui.livetest1.entity.bean.UserInfoBean;
import com.lairui.livetest1.module.three_module.presenter.LiveFinishPresenter;
import com.lairui.livetest1.ui.panel.CircleImageView;
import com.lairui.livetest1.utils.ObjectBox;
import com.wanou.framelibrary.base.BaseMvpActivity;
import com.wanou.framelibrary.glidetools.GlideApp;
import com.wanou.framelibrary.utils.UiTools;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class LiveFinishActivity extends BaseMvpActivity<LiveFinishPresenter> implements View.OnClickListener {
    private CircleImageView ivUserIcon;
    private ImageView ivBackground;
    private TextView tvUserName, tvScanNumber, tvGetNum;
    private List<ShareBean> shareBeanList = new ArrayList<>();
    private int[] shareImageResArr = {R.drawable.umeng_socialize_wechat, R.drawable.umeng_socialize_wxcircle
            , R.drawable.umeng_socialize_qq, R.drawable.umeng_socialize_qzone, R.drawable.umeng_socialize_sina};

    @Override
    protected LiveFinishPresenter getPresenter() {
        return new LiveFinishPresenter();
    }

    @Override
    protected void initData() {
        UserInfoBean currentUserInfo = ObjectBox.getCurrentUserInfo();
        if (currentUserInfo != null) {
            String portrait = currentUserInfo.getPortrait();
            String nickname = currentUserInfo.getNickname();
            tvUserName.setText(nickname);
            GlideApp.with(MyApplication.getContext())
                    .load(portrait)
                    .placeholder(R.drawable.shape_gray5_round5)
                    .error(R.drawable.shape_gray5_round5)
                    .apply(RequestOptions.bitmapTransform(new MultiTransformation<>(new CenterCrop(), new BlurTransformation(50))))
                    .into(ivBackground);

            GlideApp.with(MyApplication.getContext())
                    .load(portrait)
                    .placeholder(R.drawable.ic_head)
                    .error(R.drawable.ic_head)
                    .into(ivUserIcon);
        }
        shareBeanList.clear();
        String[] stringArray = UiTools.getStringArray(R.array.shareList);
        for (int i = 0; i < stringArray.length; i++) {
            shareBeanList.add(new ShareBean(stringArray[i], shareImageResArr[i]));
        }
        tvScanNumber.setText(UiTools.formatNumber(Math.random() * 100, "0"));
        tvGetNum.setText(UiTools.formatNumber(Math.random() * 100, "0"));
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
        TextView tvShare = findViewById(R.id.tvShare);
        TextView tvBackHome = findViewById(R.id.tvBackHome);

        tvShare.setOnClickListener(this);
        tvBackHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvShare:
                ShareDialog.getDialog(LiveFinishActivity.this, shareBeanList);
                ShareDialog.setOnItemClickListener(new ShareDialog.OnItemClickListener() {
                    @Override
                    public void itemClickListener(int position) {
                        ShareBean shareBean = shareBeanList.get(position);
                        UiTools.showToast(shareBean.shareName);
                    }
                });
                break;
            case R.id.tvBackHome:
                finish();
                break;
            default:
        }
    }
}
