package com.lairui.livetest1.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.animation.AnimationSet;

import java.util.Timer;

public class CountDownTimeView extends AppCompatTextView {
    private AnimationSet animationSet;

    public CountDownTimeView(Context context) {
        this(context, null);
    }

    public CountDownTimeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownTimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (animationSet == null) {
            animationSet = new AnimationSet(true);
        }
        setGravity(Gravity.CENTER);
    }



}
