package com.lairui.livetest1.control;

import android.os.Bundle;
import android.view.View;

import com.lairui.livetest1.entity.livebean.LiveBeautyConfig;
import com.lairui.livetest1.entity.livebean.LiveQualityData;
import com.tencent.rtmp.ITXLivePushListener;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

public class LivePushSdkTX implements IPushSdk,
        ITXLivePushListener,
        TXLivePusher.OnBGMNotify {

    private static LivePushSdkTX sInstance;
    private TXLivePusher mPusher;
    private TXLivePushConfig mConfig;
    private TXCloudVideoView mVideoView;
    /**
     * 推流是否已经启动
     */
    private boolean mIsPushStarted;
    /**
     * 推流地址
     */
    private String mUrl;
    /**
     * 当前摄像头id
     */
    private int mCameraId = CAMERA_FRONT;
    private LiveBeautyConfig mBeautyConfig;
    /**
     * 是否镜像
     */
    private boolean mIsMirror = false;
    /**
     * 音乐是否已被暂停
     */
    private boolean mIsBGMPaused;
    /**
     * 音乐是否已经开始
     */
    private boolean mIsBGMStarted;
    private boolean mIsMicEnable = true;
    /**
     * 视频质量
     */
    private LiveQualityData mLiveQualityData;
    /**
     * 音乐当前播放位置
     */
    private long mBGMPosition;
    private PushCallback mPushCallback;
    private BGMPlayerCallback mBgmPlayerCallback;

    private LivePushSdkTX() {
        mLiveQualityData = new LiveQualityData();
    }

    public static LivePushSdkTX getInstance() {
        if (sInstance == null) {
            synchronized (LivePushSdkTX.class) {
                if (sInstance == null) {
                    sInstance = new LivePushSdkTX();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void init(View view) {

    }

    @Override
    public void setConfigDefault() {

    }

    @Override
    public void setConfigLinkMicMain() {

    }

    @Override
    public void setConfigLinkMicSub() {

    }

    @Override
    public boolean isPushStarted() {
        return false;
    }

    @Override
    public void setUrl(String url) {

    }

    @Override
    public void startPush() {

    }

    @Override
    public void pausePush() {

    }

    @Override
    public void resumePush() {

    }

    @Override
    public void stopPush() {

    }

    @Override
    public void startCameraPreview() {

    }

    @Override
    public void stopCameraPreview() {

    }

    @Override
    public void onPushEvent(int i, Bundle bundle) {

    }

    @Override
    public void onNetStatus(Bundle bundle) {

    }

    @Override
    public void onBGMStart() {

    }

    @Override
    public void onBGMProgress(long l, long l1) {

    }

    @Override
    public void onBGMComplete(int i) {

    }

    @Override
    public void setMirror(boolean mirror) {

    }


    @Override
    public boolean isMirror() {
        return false;
    }


    @Override
    public void enableBeauty(boolean enable) {

    }


    @Override
    public void enableFlashLight(boolean enable) {

    }


    @Override
    public void enableMic(boolean enable) {

    }

    @Override
    public void setMicVolume(int progress) {

    }

    @Override
    public void switchCamera() {

    }

    @Override
    public boolean isBackCamera() {
        return false;
    }

    @Override
    public LiveQualityData getLiveQualityData() {
        return null;
    }

    @Override
    public void setPushCallback(PushCallback pushCallback) {

    }

    @Override
    public void setBgmPlayerCallback(BGMPlayerCallback bgmPlayerCallback) {

    }

    @Override
    public boolean isBGMPlaying() {
        return false;
    }

    @Override
    public boolean isBGMStarted() {
        return false;
    }

    @Override
    public boolean playBGM(String path) {
        return false;
    }

    @Override
    public boolean pauseBGM() {
        return false;
    }

    @Override
    public boolean resumeBGM() {
        return false;
    }

    @Override
    public boolean stopBGM() {
        return false;
    }

    @Override
    public void setBGMVolume(int progress) {

    }

    @Override
    public long getBGMPosition() {
        return 0;
    }

    @Override
    public void onDestroy() {

    }


}
