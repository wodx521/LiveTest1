package com.lairui.livetest1.ui.activity;

import android.content.Context;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alivc.component.custom.AlivcLivePushCustomDetect;
import com.alivc.component.custom.AlivcLivePushCustomFilter;
import com.alivc.live.detect.TaoFaceFilter;
import com.alivc.live.filter.TaoBeautyFilter;
import com.alivc.live.pusher.AlivcLivePushBGMListener;
import com.alivc.live.pusher.AlivcLivePushConfig;
import com.alivc.live.pusher.AlivcLivePushError;
import com.alivc.live.pusher.AlivcLivePushErrorListener;
import com.alivc.live.pusher.AlivcLivePushInfoListener;
import com.alivc.live.pusher.AlivcLivePushNetworkListener;
import com.alivc.live.pusher.AlivcLivePusher;
import com.alivc.live.pusher.AlivcPreviewDisplayMode;
import com.alivc.live.pusher.SurfaceStatus;
import com.lairui.livetest1.R;
import com.lairui.livetest1.presenter.LivePresenter;
import com.wanou.framelibrary.base.BaseMvpActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class LiveActivity extends BaseMvpActivity<LivePresenter> implements View.OnClickListener
        , AlivcLivePushInfoListener, AlivcLivePushNetworkListener, AlivcLivePushBGMListener,
        AlivcLivePushErrorListener {
    private EditText etLiveTitle;
    private TextView tvStartLive;
    private AlivcLivePushConfig mAlivcLivePushConfig;
    private AlivcLivePusher mAlivcLivePusher;
    private SurfaceView surfaceView;
    private boolean mAsync = false;
    private TaoBeautyFilter taoBeautyFilter;
    private TaoFaceFilter taoFaceFilter;
    private SurfaceStatus mSurfaceStatus = SurfaceStatus.UNINITED;

    @Override
    protected LivePresenter getPresenter() {
        return new LivePresenter();
    }

    @Override
    protected int getResId() {
        return R.layout.activity_live;
    }

    @Override
    protected void initView() {
        surfaceView = findViewById(R.id.surfaceView);
        etLiveTitle = findViewById(R.id.etLiveTitle);
        tvStartLive = findViewById(R.id.tvStartLive);
        surfaceView.getHolder().addCallback(mCallback);
    }

    @Override
    protected void initData() {
        mAlivcLivePushConfig = new AlivcLivePushConfig();
        //设置用户后台推流的图片
//        mAlivcLivePushConfig.setPausePushImage();
        //设置网络较差时推流的图片
//        mAlivcLivePushConfig.setNetworkPoorPushImage("图片.png");
        // 设置预览显示模式
        mAlivcLivePushConfig.setPreviewDisplayMode(AlivcPreviewDisplayMode.ALIVC_LIVE_PUSHER_PREVIEW_ASPECT_FIT);

        mAlivcLivePusher = new AlivcLivePusher();
        try {
            mAlivcLivePusher.init(getApplicationContext(), mAlivcLivePushConfig);
            mAlivcLivePusher.setLivePushInfoListener(this);
            mAlivcLivePusher.setLivePushNetworkListener(this);
            mAlivcLivePusher.setLivePushBGMListener(this);
            mAlivcLivePusher.setLivePushErrorListener(this);
            mAlivcLivePusher.setLivePushBGMListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
        mAlivcLivePusher.setCustomDetect(new AlivcLivePushCustomDetect() {
            @Override
            public void customDetectCreate() {
                taoFaceFilter = new TaoFaceFilter(getApplicationContext());
                taoFaceFilter.customDetectCreate();
            }

            @Override
            public long customDetectProcess(long data, int width, int height, int rotation, int format, long extra) {
                if (taoFaceFilter != null) {
                    return taoFaceFilter.customDetectProcess(data, width, height, rotation, format, extra);
                }
                return 0;
            }

            @Override
            public void customDetectDestroy() {
                if (taoFaceFilter != null) {
                    taoFaceFilter.customDetectDestroy();
                }
            }
        });
        mAlivcLivePusher.setCustomFilter(new AlivcLivePushCustomFilter() {
            @Override
            public void customFilterCreate() {
                taoBeautyFilter = new TaoBeautyFilter();
                taoBeautyFilter.customFilterCreate();
            }

            @Override
            public void customFilterUpdateParam(float fSkinSmooth, float fWhiten, float fWholeFacePink, float fThinFaceHorizontal, float fCheekPink, float fShortenFaceVertical, float fBigEye) {
                if (taoBeautyFilter != null) {
                    taoBeautyFilter.customFilterUpdateParam(fSkinSmooth, fWhiten, fWholeFacePink, fThinFaceHorizontal, fCheekPink, fShortenFaceVertical, fBigEye);
                }
            }

            @Override
            public void customFilterSwitch(boolean on) {
                if (taoBeautyFilter != null) {
                    taoBeautyFilter.customFilterSwitch(on);
                }
            }

            @Override
            public int customFilterProcess(int inputTexture, int textureWidth, int textureHeight, long extra) {
                if (taoBeautyFilter != null) {
                    return taoBeautyFilter.customFilterProcess(inputTexture, textureWidth, textureHeight, extra);
                }
                return inputTexture;
            }

            @Override
            public void customFilterDestroy() {
                if (taoBeautyFilter != null) {
                    taoBeautyFilter.customFilterDestroy();
                }
                taoBeautyFilter = null;
            }
        });
        if (mAsync) {
            mAlivcLivePusher.startPreviewAysnc(surfaceView);
        } else {
            mAlivcLivePusher.startPreview(surfaceView);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvStartLive:
                // 开始直播按钮

                break;
            default:
        }
    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onStoped() {

    }

    @Override
    public void onPaused() {

    }

    @Override
    public void onResumed() {

    }

    @Override
    public void onProgress(long l, long l1) {

    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onDownloadTimeout() {

    }

    @Override
    public void onOpenFailed() {

    }

    @Override
    public void onSystemError(AlivcLivePusher alivcLivePusher, AlivcLivePushError alivcLivePushError) {

    }

    @Override
    public void onSDKError(AlivcLivePusher alivcLivePusher, AlivcLivePushError alivcLivePushError) {

    }

    @Override
    public void onPreviewStarted(AlivcLivePusher alivcLivePusher) {

    }

    @Override
    public void onPreviewStoped(AlivcLivePusher alivcLivePusher) {

    }

    @Override
    public void onPushStarted(AlivcLivePusher alivcLivePusher) {

    }

    @Override
    public void onFirstAVFramePushed(AlivcLivePusher alivcLivePusher) {

    }

    @Override
    public void onPushPauesed(AlivcLivePusher alivcLivePusher) {

    }

    @Override
    public void onPushResumed(AlivcLivePusher alivcLivePusher) {

    }

    @Override
    public void onPushStoped(AlivcLivePusher alivcLivePusher) {

    }

    @Override
    public void onPushRestarted(AlivcLivePusher alivcLivePusher) {

    }

    @Override
    public void onFirstFramePreviewed(AlivcLivePusher alivcLivePusher) {

    }

    @Override
    public void onDropFrame(AlivcLivePusher alivcLivePusher, int i, int i1) {

    }

    @Override
    public void onAdjustBitRate(AlivcLivePusher alivcLivePusher, int i, int i1) {

    }

    @Override
    public void onAdjustFps(AlivcLivePusher alivcLivePusher, int i, int i1) {

    }

    @Override
    public void onNetworkPoor(AlivcLivePusher alivcLivePusher) {

    }

    @Override
    public void onNetworkRecovery(AlivcLivePusher alivcLivePusher) {

    }

    @Override
    public void onReconnectStart(AlivcLivePusher alivcLivePusher) {

    }

    @Override
    public void onConnectionLost(AlivcLivePusher alivcLivePusher) {

    }

    @Override
    public void onReconnectFail(AlivcLivePusher alivcLivePusher) {

    }

    @Override
    public void onReconnectSucceed(AlivcLivePusher alivcLivePusher) {

    }

    @Override
    public void onSendDataTimeout(AlivcLivePusher alivcLivePusher) {

    }

    @Override
    public void onConnectFail(AlivcLivePusher alivcLivePusher) {

    }

    @Override
    public String onPushURLAuthenticationOverdue(AlivcLivePusher alivcLivePusher) {
        return null;
    }

    @Override
    public void onSendMessage(AlivcLivePusher alivcLivePusher) {

    }

    @Override
    public void onPacketsLost(AlivcLivePusher alivcLivePusher) {

    }

    SurfaceHolder.Callback mCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            if (mSurfaceStatus == SurfaceStatus.UNINITED) {
                mSurfaceStatus = SurfaceStatus.CREATED;
                if (mAlivcLivePusher != null) {
                    try {
                        if (mAsync) {
                            mAlivcLivePusher.startPreviewAysnc(surfaceView);
                        } else {
                            mAlivcLivePusher.startPreview(surfaceView);
                        }
                        if (mAlivcLivePushConfig.isExternMainStream()) {
                            startYUV(getApplicationContext());
                        }
                    } catch (IllegalArgumentException e) {
                        e.toString();
                    } catch (IllegalStateException e) {
                        e.toString();
                    }
                }
            } else if (mSurfaceStatus == SurfaceStatus.DESTROYED) {
                mSurfaceStatus = SurfaceStatus.RECREATED;
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            mSurfaceStatus = SurfaceStatus.CHANGED;
//            if(mLivePushFragment != null) {
//                mLivePushFragment.setSurfaceView(surfaceView);
//            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            mSurfaceStatus = SurfaceStatus.DESTROYED;
        }
    };
    private boolean videoThreadOn = false;

    public void startYUV(final Context context) {
        new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
            private AtomicInteger atoInteger = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("LivePushActivity-readYUV-Thread" + atoInteger.getAndIncrement());
                return t;
            }
        }).execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                videoThreadOn = true;
                byte[] yuv;
                InputStream myInput = null;
                try {
                    File f = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "alivc_resource/capture0.yuv");
                    myInput = new FileInputStream(f);
                    byte[] buffer = new byte[1280 * 720 * 3 / 2];
                    int length = myInput.read(buffer);
                    //发数据
                    while (length > 0 && videoThreadOn) {
                        mAlivcLivePusher.inputStreamVideoData(buffer, 720, 1280, 720, 1280 * 720 * 3 / 2, System.nanoTime() / 1000, 0);
                        try {
                            Thread.sleep(40);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //发数据
                        length = myInput.read(buffer);
                        if (length <= 0) {
                            myInput.close();
                            myInput = new FileInputStream(f);
                            length = myInput.read(buffer);
                        }
                    }
                    myInput.close();
                    videoThreadOn = false;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
