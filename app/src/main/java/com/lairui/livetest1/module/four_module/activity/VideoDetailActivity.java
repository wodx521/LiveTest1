package com.lairui.livetest1.module.four_module.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.entity.bean.VideoBean;
import com.lairui.livetest1.module.four_module.presenter.VideoDetailPresenter;
import com.lairui.livetest1.ui.panel.CircleImageView;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.wanou.framelibrary.base.BaseMvpActivity;
import com.wanou.framelibrary.utils.UiTools;

public class VideoDetailActivity extends BaseMvpActivity<VideoDetailPresenter> implements View.OnClickListener {
    private ImageView ivLeft;
    private TextView tvToolbarTitle;
    private ImageView ivRight1;
    private StandardGSYVideoPlayer gsyVideoPlayer;
    private CircleImageView ivAuthorIcon;
    private TextView tvAuthorName;
    private TextView tvVideoDes;
    private TextView tvVideoWatch;
    private CheckBox cbVideoPraise;
    private RecyclerView rvCommentList;

    @Override
    protected VideoDetailPresenter getPresenter() {
        return new VideoDetailPresenter();
    }

    @Override
    protected int getResId() {
        return R.layout.activity_video_detail;
    }

    @Override
    protected void initView() {
        ivLeft = findViewById(R.id.ivLeft);
        tvToolbarTitle = findViewById(R.id.tvToolbarTitle);
        ivRight1 = findViewById(R.id.ivRight1);
        gsyVideoPlayer = findViewById(R.id.gsyVideoPlayer);
        ivAuthorIcon = findViewById(R.id.ivAuthorIcon);
        tvAuthorName = findViewById(R.id.tvAuthorName);
        tvVideoDes = findViewById(R.id.tvVideoDes);
        tvVideoWatch = findViewById(R.id.tvVideoWatch);
        cbVideoPraise = findViewById(R.id.cbVideoPraise);
        rvCommentList = findViewById(R.id.rvCommentList);

        tvToolbarTitle.setText(R.string.videoDetail);
        viewVisible(ivLeft);
        ivLeft.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if (mBundle != null) {
            VideoBean videoBean = mBundle.getParcelable("videoBean");
            if (videoBean != null) {
                String nickname = videoBean.getNickname();
                VideoBean.PraiseBean praise = videoBean.getPraise();
                String num = praise.getNum();
                if (UiTools.noEmpty(nickname)) {
                    tvAuthorName.setText(nickname);
                } else {
                    tvAuthorName.setText("");
                }
                if (UiTools.noEmpty(num)) {
                    tvVideoWatch.setText(num);
                } else {
                    tvVideoWatch.setText("0");
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
            default:
        }
    }
}
