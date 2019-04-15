package com.lairui.livetest1.module.three_module.activity;

import android.view.View;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.module.three_module.presenter.LivePushPresenter;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.wanou.framelibrary.base.BaseMvpActivity;
import com.wanou.framelibrary.utils.UiTools;

/**
 * 腾讯直播Sdk
 */
public class LivePushActivityTX extends BaseMvpActivity<LivePushPresenter> implements View.OnClickListener {
    private TXCloudVideoView videoView;
    private TXLivePushConfig mLivePushConfig;
    private TextView tvStartPush;
    private TXLivePusher mLivePusher;
    private String pushUrl;

    @Override
    protected LivePushPresenter getPresenter() {
        return new LivePushPresenter();
    }

    @Override
    protected void initData() {
        mLivePusher = new TXLivePusher(this);
        mLivePushConfig = new TXLivePushConfig();
        mLivePushConfig.enableNearestIP(false);
        mLivePushConfig.setFrontCamera(false);
        mLivePusher.setConfig(mLivePushConfig);
        mLivePusher.startCameraPreview(videoView);
        if (mBundle != null) {
            pushUrl = mBundle.getString("pushUrl", "");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLivePusher.stopCameraPreview(true); //停止摄像头预览
        mLivePusher.stopPusher();            //停止推流
        mLivePusher.setPushListener(null);   //解绑 listener
    }

    @Override
    protected int getResId() {
        return R.layout.activity_live_push;
    }

    @Override
    protected void initView() {
        videoView = findViewById(R.id.video_view);
        tvStartPush = findViewById(R.id.tvStartPush);

        tvStartPush.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvStartPush:
                if (UiTools.noEmpty(pushUrl)) {
                    mLivePusher.startPusher(pushUrl);
                }
                break;
            default:
        }
    }
}
