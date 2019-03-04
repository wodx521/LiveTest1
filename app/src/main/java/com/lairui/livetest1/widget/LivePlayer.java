package com.lairui.livetest1.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.lairui.livetest1.R;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public class LivePlayer extends StandardGSYVideoPlayer {
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
    protected void onClickUiToggle() {
        super.onClickUiToggle();
        changeUiToPrepareingClear();
    }
}
