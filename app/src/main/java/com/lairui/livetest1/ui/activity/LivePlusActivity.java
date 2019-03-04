package com.lairui.livetest1.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alivc.live.pusher.AlivcLivePushConfig;
import com.alivc.live.pusher.AlivcLivePushInfoListener;
import com.alivc.live.pusher.AlivcLivePusher;
import com.alivc.live.pusher.SurfaceStatus;
import com.lairui.livetest1.MyApplication;
import com.lairui.livetest1.R;
import com.lairui.livetest1.presenter.LivePresenter;
import com.lairui.livetest1.utils.NetWorkUtils;
import com.wanou.framelibrary.base.BaseMvpActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class LivePlusActivity extends BaseMvpActivity<LivePresenter> implements View.OnClickListener
        , AlivcLivePushInfoListener {
    private SurfaceView mPreviewView;
    private EditText etLiveTitle;
    private TextView tvStartLive;
    private AlivcLivePusher mAlivcLivePusher;
    private boolean mAsync = true;
    private AlivcLivePushConfig mAlivcLivePushConfig;
    private boolean videoThreadOn = false;
    private boolean isPause = false;
    private boolean audioThreadOn = false;

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

        tvStartLive.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mAlivcLivePushConfig = new AlivcLivePushConfig();
        mAlivcLivePusher = new AlivcLivePusher();
        mNetWork = NetWorkUtils.getAPNType(this);
        try {
            mAlivcLivePusher.init(MyApplication.getContext(), mAlivcLivePushConfig);
            mAlivcLivePusher.setLivePushInfoListener(this);
//            mAlivcLivePusher.setLivePushNetworkListener(this);
//            mAlivcLivePusher.setLivePushBGMListener(this);
//            mAlivcLivePusher.setLivePushErrorListener(this);

            mAlivcLivePusher.switchCamera();
        } catch (Exception e) {
            e.printStackTrace();
            showDialog(this, e.getMessage());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvStartLive:
                String pushurl = "rtmp://heh.play.htcrm.net/lairui/100004?auth_key=1551520317-0-0-f8f8711a18b17930fd94bb9601c0127d";
                mAlivcLivePusher.startPushAysnc(pushurl);
                break;
            default:
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAlivcLivePusher != null) {
            try {
                if (!isPause) {
                    if (mAsync) {
                        mAlivcLivePusher.resumeAsync();
                    } else {
                        mAlivcLivePusher.resume();
                    }
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAlivcLivePusher != null) {
            try {
                mAlivcLivePusher.pause();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
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

    private SurfaceStatus mSurfaceStatus = SurfaceStatus.UNINITED;
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (mSurfaceStatus == SurfaceStatus.DESTROYED) {
                mSurfaceStatus = SurfaceStatus.RECREATED;
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            mSurfaceStatus = SurfaceStatus.CHANGED;
//            if (mLivePushFragment != null) {
//                mLivePushFragment.setSurfaceView(mPreviewView);
//            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            mSurfaceStatus = SurfaceStatus.DESTROYED;
        }
    };


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

    private int mNetWork = 0;
    // pushInfoListener
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
    //    pushInfoListener


    class ConnectivityChangedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {

                if (mNetWork != NetWorkUtils.getAPNType(context)) {
                    mNetWork = NetWorkUtils.getAPNType(context);
                    if (mAlivcLivePusher != null) {
                        if (mAlivcLivePusher.isPushing()) {
                            try {
                                mAlivcLivePusher.reconnectPushAsync(null);
                            } catch (IllegalStateException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

        }
    }

    private void showDialog(Context context, String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("error");
        dialog.setMessage(message);
        dialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        dialog.show();
    }
}
