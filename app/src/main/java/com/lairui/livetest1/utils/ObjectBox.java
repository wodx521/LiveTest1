package com.lairui.livetest1.utils;

import android.content.Context;
import android.util.Log;

import com.lairui.livetest1.BuildConfig;
import com.lairui.livetest1.entity.bean.MyObjectBox;
import com.lairui.livetest1.entity.bean.UserInfoBean;
import com.wanou.framelibrary.utils.SpUtils;

import io.objectbox.Box;
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

    public static UserInfoBean getCurrentUserInfo() {
        Box<UserInfoBean> userInfoBeanBox = boxStore.boxFor(UserInfoBean.class);
        long mainId = (long) SpUtils.get("mainId", -1L);
        if (mainId != -1) {
            return userInfoBeanBox.get(mainId);
        }
        return null;
    }

    public static String getToken() {
        try {
            Box<UserInfoBean> userInfoBeanBox = boxStore.boxFor(UserInfoBean.class);
            long mainId = (long) SpUtils.get("mainId", -1L);
            if (mainId != -1) {
                UserInfoBean userInfoBean = userInfoBeanBox.get(mainId);
                return userInfoBean.getToken();
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
