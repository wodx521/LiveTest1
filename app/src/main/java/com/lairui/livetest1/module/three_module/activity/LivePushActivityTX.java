package com.lairui.livetest1.module.three_module.activity;

import android.view.View;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.entity.bean.LiveAddressBean;
import com.lairui.livetest1.module.three_module.presenter.LivePushPresenter;
import com.lzy.okgo.model.HttpParams;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.wanou.framelibrary.base.BaseMvpActivity;
import com.wanou.framelibrary.utils.SpUtils;

/**
 * 腾讯直播Sdk
 */
public class LivePushActivityTX extends BaseMvpActivity<LivePushPresenter> {
    private TXCloudVideoView videoView;
    private TXLivePushConfig mLivePushConfig;
    private TextView tvStartPush;
    private TXLivePusher mLivePusher;
    private HttpParams httpParams = new HttpParams();

    @Override
    protected LivePushPresenter getPresenter() {
        return new LivePushPresenter();
    }

    @Override
    protected void initData() {
        String token = (String) SpUtils.get("token", "");
        mLivePusher = new TXLivePusher(this);
        mLivePushConfig = new TXLivePushConfig();
        mLivePushConfig.enableNearestIP(false);
        mLivePushConfig.setFrontCamera(false);
        mLivePusher.setConfig(mLivePushConfig);
        mLivePusher.startCameraPreview(videoView);
        httpParams.put("operate", "roomGroup-liveAddress");
        httpParams.put("token",token);
        mPresenter.getPushAddress(httpParams);
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
    }

    public void setPushAddress(LiveAddressBean liveAddressBean) {
        LiveAddressBean.PushBean push = liveAddressBean.getPush();
        String rtmpurl = push.getRtmpurl();

        tvStartPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLivePusher.startPusher(rtmpurl);
            }
        });
    }
}