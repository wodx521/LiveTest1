package com.lairui.livetest1.utils;

import android.content.Context;
import android.util.Log;

import com.lairui.livetest1.BuildConfig;
import com.lairui.livetest1.entity.bean.MyObjectBox;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

public class ObjectBox {
    private static BoxStore boxStore;

    public static void initObjectBox(Context context) {
        boxStore = MyObjectBox.builder()
                .androidContext(context.getApplicationContext())
                .build();
        if (BuildConfig.DEBUG) {
            boolean started = new AndroidObjectBrowser(boxStore).start(context.getApplicationContext());
            Log.i("ObjectBrowser", "Started: " + started);
        }
    }

    public static BoxStore getBoxStore() {
        return boxStore;
    }
}
