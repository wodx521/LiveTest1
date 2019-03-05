package com.lairui.livetest1.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.entity.bean.LiveAddressBean;
import com.lairui.livetest1.presenter.LivePushPresenter;
import com.lzy.okgo.model.HttpParams;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.wanou.framelibrary.base.BaseMvpActivity;

public class LivePushActivity1 extends BaseMvpActivity<LivePushPresenter> {
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
    protected int getResId() {
        return R.layout.activity_live_push;
    }

    @Override
    protected void initView() {
        videoView = findViewById(R.id.video_view);
        tvStartPush = findViewById(R.id.tvStartPush);
    }

    @Override
    protected void initData() {
        mLivePusher = new TXLivePusher(this);
        mLivePushConfig = new TXLivePushConfig();
        mLivePushConfig.enableNearestIP(false);
        mLivePushConfig.setFrontCamera(false);
        mLivePusher.setConfig(mLivePushConfig);
        mLivePusher.startCameraPreview(videoView);
        httpParams.put("operate","roomGroup-liveAddress");
        mPresenter.getPushAddress(httpParams);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLivePusher.stopCameraPreview(true); //停止摄像头预览
        mLivePusher.stopPusher();            //停止推流
        mLivePusher.setPushListener(null);   //解绑 listener
    }

    public void setPushAddress(LiveAddressBean liveAddressBean) {
        LiveAddressBean.PushBean push = liveAddressBean.getPush();
        String rtmpurl = push.getRtmpurl();

        tvStartPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String rtmpUrl = "rtmp://192.168.199.216:1935/live/home";
//                String rtmpUrl = "rtmp://heh.play.htcrm.net/lairui/100004?auth_key=1551666659-0-0-c8166e4ac31091184fbf16ad0e1bd0d7";
                mLivePusher.startPusher(rtmpurl);
            }
        });
    }
}
