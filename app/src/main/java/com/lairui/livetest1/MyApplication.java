package com.lairui.livetest1;

import android.content.Context;

import com.wanou.framelibrary.GlobalApplication;

public class MyApplication extends GlobalApplication {
    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    protected Context getAppContext() {
        return this;
    }
}
