package com.lairui.livetest1.widget;

import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.wanou.framelibrary.utils.LogUtils;

public class CountDownTimerUtils extends CountDownTimer {

    OnCountDownFinish onCountDownFinish;
    private TextView textview;
    private long timeInterval;
    private AnimationSet animationSet;
    private AlphaAnimation alphaAnimation;
    private ScaleAnimation scaleAnimation;

    public CountDownTimerUtils(long millisInFuture, TextView textView) {
        this(millisInFuture, 1, textView);
    }

    public CountDownTimerUtils(long millisInFuture, long countDownInterval, TextView textView) {
        super(millisInFuture * 1000 + 1500, countDownInterval * 1000);
        this.timeInterval = countDownInterval * 1000;
        this.textview = textView;
        if (textview.getVisibility() == View.GONE) {
            textview.setVisibility(View.VISIBLE);
        }
        animationSet = new AnimationSet(true);
        alphaAnimation = new AlphaAnimation(1, 0);
        scaleAnimation = new ScaleAnimation(4f, 0.5f, 4f, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setDuration(timeInterval);
        animationSet.setInterpolator(new AccelerateInterpolator());
    }

    @Override
    public void onTick(long millisUntilFinished) {

        long remainingSecond = millisUntilFinished / timeInterval - 1;
        LogUtils.d(remainingSecond + "");
        if (remainingSecond == 0) {
            //剩余0秒时,计时结束,调用结束方法
            onFinish();
        } else {
            if (textview != null) {
                textview.setText(remainingSecond + "");
                textview.startAnimation(animationSet);
            }
        }
    }

    @Override
    public void onFinish() {
        cancel();
        // 倒计时结束后, 取消 TextView 的显示
        if (textview != null) {
            textview.setVisibility(View.GONE);
            textview.clearAnimation();
        }
        if (onCountDownFinish != null) {
            onCountDownFinish.finish();
        }
    }

    public void setOnCountDownFinish(OnCountDownFinish onCountDownFinish) {
        this.onCountDownFinish = onCountDownFinish;
    }

    public interface OnCountDownFinish {
        void finish();
    }
}
