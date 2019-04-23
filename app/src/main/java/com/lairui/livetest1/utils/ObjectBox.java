package com.lairui.livetest1.utils;

import android.content.Context;
import android.util.Log;

import com.lairui.livetest1.BuildConfig;
import com.lairui.livetest1.entity.MyObjectBox;
import com.lairui.livetest1.entity.bean.UserInfoBean;
import com.lairui.livetest1.entity.bean.UserInfoBean_;
import com.wanou.framelibrary.utils.SpUtils;
import com.wanou.framelibrary.utils.UiTools;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;
import io.objectbox.query.Query;

public class ObjectBox {
    private static BoxStore boxStore;
    private static Query<UserInfoBean> userInfoBeanQuery;
    private static Box<UserInfoBean> userInfoBeanBox;

    public static void initObjectBox(Context context) {
        boxStore = MyObjectBox.builder()
                .androidContext(context.getApplicationContext())
                .build();
        if (BuildConfig.DEBUG) {
            boolean started = new AndroidObjectBrowser(boxStore).start(context.getApplicationContext());
            Log.i("ObjectBrowser", "Started: " + started);
        }
        userInfoBeanBox = boxStore.boxFor(UserInfoBean.class);
        userInfoBeanQuery = userInfoBeanBox.query().equal(UserInfoBean_.userId, "").build();
    }

    public static BoxStore getBoxStore() {
        return boxStore;
    }

    public static UserInfoBean getCurrentUserInfo() {
        String userId = (String) SpUtils.get("userId", "");
        if (UiTools.noEmpty(userId)) {
            return userInfoBeanQuery.setParameter(UserInfoBean_.userId, userId).findFirst();
        }
        return null;
    }

    public static Box<UserInfoBean> getUserInfoBeanBox() {
        return userInfoBeanBox;
    }

    public static String getToken() {
        UserInfoBean currentUserInfo = getCurrentUserInfo();
        if (currentUserInfo != null && UiTools.noEmpty(currentUserInfo.getToken())) {
            return currentUserInfo.getToken();
        } else {
            return "";
        }
    }

    public static String getIMToken() {
        UserInfoBean currentUserInfo = getCurrentUserInfo();
        if (currentUserInfo != null && UiTools.noEmpty(currentUserInfo.getImtoken())) {
            return currentUserInfo.getImtoken();
        } else {
            return "";
        }
    }
}
