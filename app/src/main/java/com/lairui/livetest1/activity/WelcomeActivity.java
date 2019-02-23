package com.lairui.livetest1.activity;

import com.lairui.livetest1.R;
import com.wanou.framelibrary.base.BaseActivity;
import com.wanou.framelibrary.utils.CountDownUtils;

public class WelcomeActivity extends BaseActivity {
    @Override
    protected int getResId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        // 设置计时开始 设置结束监听, 开启下个页面
        CountDownUtils.getTimer(3, null, "");
        CountDownUtils.setTimeFinishListener(new CountDownUtils.CountTimeFinishListener() {
            @Override
            public void onTimeFinishListener() {
                startActivity(WelcomeActivity.this, null, LoginActivity.class);
                WelcomeActivity.this.finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 页面销毁, 结束监听取消, 结束倒计时
        CountDownUtils.setTimeFinishListener(null);
        CountDownUtils.cancelTimer();
    }
}
