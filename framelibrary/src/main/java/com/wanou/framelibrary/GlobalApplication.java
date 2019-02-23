package com.wanou.framelibrary;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.wanou.framelibrary.okgoutil.OkGoUtils;
import com.wanou.framelibrary.utils.LogUtils;

/**
 * Author by wodx521
 * Date on 2018/11/7.
 */
public abstract class GlobalApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getAppContext();
        OkGoUtils.initOkGo(this);
        LogUtils.initLogUtils(this);
    }

    public static Context getContext() {
        return context;
    }

    protected abstract Context getAppContext();
}