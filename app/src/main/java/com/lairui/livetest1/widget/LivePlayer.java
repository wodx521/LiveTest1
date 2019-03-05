package com.lairui.livetest1.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.lairui.livetest1.R;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public class LivePlayer extends StandardGSYVideoPlayer{
    public LivePlayer(Context context) {
        super(context);
    }

    public LivePlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public LivePlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.video_layout_live;
    }

    @Override
    protected void changeUiToError() {
        super.changeUiToError();
        // 显示封面
        setViewShowState(mThumbImageViewLayout, VISIBLE);
        // 隐藏开始按钮
        setViewShowState(mStartButton, VISIBLE);
    }

    @Override
    protected void updateStartImage() {
        if(mStartButton instanceof ImageView) {
            ImageView imageView = (ImageView) mStartButton;
            if (mCurrentState == CURRENT_STATE_PLAYING) {
                imageView.setImageResource(com.shuyu.gsyvideoplayer.R.drawable.video_click_pause_selector);
            } else if (mCurrentState == CURRENT_STATE_ERROR) {
                imageView.setImageResource(R.drawable.connect_error);
            } else {
                imageView.setImageResource(com.shuyu.gsyvideoplayer.R.drawable.video_click_play_selector);
            }
        }
    }
}
