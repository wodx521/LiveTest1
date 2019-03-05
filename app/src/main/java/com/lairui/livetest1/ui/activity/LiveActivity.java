package com.lairui.livetest1.ui.activity;

import android.content.Context;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alivc.live.detect.TaoFaceFilter;
import com.alivc.live.filter.TaoBeautyFilter;
import com.alivc.live.pusher.AlivcLivePushCameraTypeEnum;
import com.alivc.live.pusher.AlivcLivePushConfig;
import com.alivc.live.pusher.AlivcLivePusher;
import com.alivc.live.pusher.SurfaceStatus;
import com.lairui.livetest1.MyApplication;
import com.lairui.livetest1.R;
import com.lairui.livetest1.entity.bean.LiveAddressBean;
import com.lairui.livetest1.presenter.LivePresenter;
import com.lzy.okgo.model.HttpParams;
import com.wanou.framelibrary.base.BaseMvpActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class LiveActivity extends BaseMvpActivity<LivePresenter> {
    private EditText etLiveTitle;
    private TextView tvStartLive;
    private AlivcLivePushConfig mAlivcLivePushConfig;
    private AlivcLivePusher mAlivcLivePusher;
    private SurfaceView mPreviewView;
    private boolean mAsync = false;
    private TaoBeautyFilter taoBeautyFilter;
    private TaoFaceFilter taoFaceFilter;
    private SurfaceStatus mSurfaceStatus = SurfaceStatus.UNINITED;
    private HttpParams httpParams = new HttpParams();

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
        mPreviewView = findViewById(R.id.surfaceView);
        etLiveTitle = findViewById(R.id.etLiveTitle);
        tvStartLive = findViewById(R.id.tvStartLive);
        mPreviewView.getHolder().addCallback(mCallback);
    }

    @Override
    protected void initData() {
        mAlivcLivePushConfig = new AlivcLivePushConfig();
        mAlivcLivePushConfig.setCameraType(AlivcLivePushCameraTypeEnum.CAMERA_TYPE_BACK);
        mAlivcLivePusher = new AlivcLivePusher();
        try {
            mAlivcLivePusher.init(MyApplication.getContext(), mAlivcLivePushConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        httpParams.put("operate", "roomGroup-liveAddress");
        mPresenter.getPushAddress(httpParams);
    }

    SurfaceHolder.Callback mCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            if (mSurfaceStatus == SurfaceStatus.UNINITED) {
                mSurfaceStatus = SurfaceStatus.CREATED;
                if (mAlivcLivePusher != null) {
                    try {
                        if (mAsync) {
                            mAlivcLivePusher.startPreviewAysnc(mPreviewView);
                        } else {
                            mAlivcLivePusher.startPreview(mPreviewView);
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
//                mLivePushFragment.setSurfaceView(mPreviewView);
//            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            mSurfaceStatus = SurfaceStatus.DESTROYED;
        }
    };

    private boolean videoThreadOn = false;
    private boolean audioThreadOn = false;

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

    @Override
    protected void onDestroy() {
        videoThreadOn = false;
        audioThreadOn = false;
        if (mAlivcLivePusher != null) {
            try {
                mAlivcLivePusher.destroy();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
        mPreviewView = null;
        mAlivcLivePushConfig = null;
        mAlivcLivePusher = null;
        super.onDestroy();
    }

    public void setPushAddress(LiveAddressBean liveAddressBean) {
        LiveAddressBean.PushBean push = liveAddressBean.getPush();
        String rtmpurl = push.getRtmpurl();

        tvStartLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlivcLivePusher.startPushAysnc(rtmpurl);
            }
        });
    }
}
