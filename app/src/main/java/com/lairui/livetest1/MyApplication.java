package com.lairui.livetest1;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.utils.ChatroomKit;
import com.wanou.framelibrary.GlobalApplication;

public class MyApplication extends GlobalApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化融云sdk
        ChatroomKit.init(this, AppConstant.RONG_CLOUD_APP_KEY);
    }

    @Override
    protected Context getAppContext() {
        return this;
    }
}
