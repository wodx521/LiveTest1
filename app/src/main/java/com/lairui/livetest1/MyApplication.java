package com.lairui.livetest1;

import android.content.Context;

import com.lairui.livetest1.app_constant.AppConstant;
import com.wanou.framelibrary.GlobalApplication;

import io.rong.imlib.RongIMClient;

public class MyApplication extends GlobalApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化融云sdk
        RongIMClient.init(this, AppConstant.RONG_CLOUD_APP_KEY);
    }

    @Override
    protected Context getAppContext() {
        return this;
    }
}
