package com.lairui.livetest1.module.three_module.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.dialog.ExitLiveDialog;
import com.lairui.livetest1.entity.jsonparam.BaseParams;
import com.lairui.livetest1.module.three_module.presenter.LivePushPresenterTX;
import com.lairui.livetest1.utils.ObjectBox;
import com.lairui.livetest1.widget.CountDownTimerUtils;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.wanou.framelibrary.base.BaseMvpActivity;
import com.wanou.framelibrary.utils.GsonUtils;
import com.wanou.framelibrary.utils.UiTools;

/**
 * 腾讯直播Sdk
 */
public class LivePushActivityTX extends BaseMvpActivity<LivePushPresenterTX> implements View.OnClickListener {
    private TXCloudVideoView videoView;
    private TXLivePushConfig mLivePushConfig;
    private TXLivePusher mLivePusher;
    private TextView tvCountDownTime;
    private String pushUrl;
    private BaseParams baseParams = new BaseParams();
    private CountDownTimerUtils countDownTimerUtils;

    @Override
    protected LivePushPresenterTX getPresenter() {
        return new LivePushPresenterTX();
    }

    @Override
    protected void initData() {
        mLivePusher = new TXLivePusher(this);
        mLivePushConfig = new TXLivePushConfig();
        mLivePushConfig.enableNearestIP(false);
        mLivePushConfig.setFrontCamera(false);
        mLivePushConfig.setTouchFocus(false);

        mLivePusher.setConfig(mLivePushConfig);
        mLivePusher.startCameraPreview(videoView);
        if (mBundle != null) {
            pushUrl = mBundle.getString("pushUrl", "");
        }
        countDownTimerUtils = new CountDownTimerUtils(3, tvCountDownTime);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimerUtils.cancel();
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
        TextView tvStartPush = findViewById(R.id.tvStartPush);
        ImageView ivCloseLive = findViewById(R.id.ivCloseLive);
        tvCountDownTime = findViewById(R.id.tvCountDownTime);

        tvStartPush.setOnClickListener(this);
        ivCloseLive.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvStartPush:
                countDownTimerUtils.start();
                countDownTimerUtils.setOnCountDownFinish(new CountDownTimerUtils.OnCountDownFinish() {
                    @Override
                    public void finish() {
                        if (UiTools.noEmpty(pushUrl)) {
                            mLivePusher.startPusher(pushUrl);
                        }
                    }
                });
                break;
            case R.id.ivCloseLive:
                ExitLiveDialog.getDialog(LivePushActivityTX.this, "提示", "确定要结束直播吗?");
                ExitLiveDialog.setOnConfirmClickListener(new ExitLiveDialog.OnConfirmClickListener() {
                    @Override
                    public void confirmClickListener() {
                        String token = ObjectBox.getToken();
                        baseParams.operate = "roomGroup-close";
                        baseParams.token = token;
                        mPresenter.exitLive(GsonUtils.toJson(baseParams));
                    }
                });
                break;
            default:
        }
    }

    public void exitSuccess() {
        startActivity(LivePushActivityTX.this, null, LiveFinishActivity.class);
        finish();
    }


}
