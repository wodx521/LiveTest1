package com.wanou.framelibrary.okgoutil;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpParams;
import com.wanou.framelibrary.BuildConfig;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

/**
 * @author wodx521
 * @date on 2018/9/1
 */
public class OkGoUtils {
    public static OkHttpClient build;
    public static final int TIMEOUT_SECOND = 30000;

    public static void initOkGo(Application application) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        if (BuildConfig.DEBUG) {
            loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        }
        loggingInterceptor.setColorLevel(Level.WARNING);
        builder.addInterceptor(loggingInterceptor);
        builder.readTimeout(TIMEOUT_SECOND, TimeUnit.MILLISECONDS);
        builder.writeTimeout(TIMEOUT_SECOND, TimeUnit.MILLISECONDS);
        builder.connectTimeout(TIMEOUT_SECOND, TimeUnit.MILLISECONDS);
        //cookie的缓存设置
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(application)));
        build = builder.build();
        OkGo.getInstance()
                .setOkHttpClient(build)
                .setRetryCount(1)
                .init(application);
    }

    public static void getRequest(String url, Object tag, Object httpParams, StringCallback stringCallback) {
        if (httpParams == null) {
            OkGo.<String>get(url)
                    .tag(tag)
                    .execute(stringCallback);
        } else {
            if (httpParams instanceof HttpParams) {
                OkGo.<String>get(url)
                        .tag(tag)
                        .params((HttpParams) httpParams)
                        .execute(stringCallback);
            }
            if (httpParams instanceof Map) {
                OkGo.<String>get(url)
                        .tag(tag)
                        .params((Map<String, String>) httpParams)
                        .execute(stringCallback);
            }
        }
    }

    public static void postRequest(String url, Object tag, Object objParams, StringCallback stringCallback) {
        if (objParams != null) {
            if (objParams instanceof String) {
                OkGo.<String>post(url)
                        .tag(tag)
                        .upJson((String) objParams)
                        .execute(stringCallback);
            }
            if (objParams instanceof HttpParams) {
                OkGo.<String>post(url)
                        .tag(tag)
                        .params((HttpParams) objParams)
                        .execute(stringCallback);
            }
            if (objParams instanceof Map) {
                OkGo.<String>post(url)
                        .tag(tag)
                        .isSpliceUrl(true)
                        .params((Map<String, String>) objParams)
                        .execute(stringCallback);
            }
        } else {
            OkGo.<String>post(url)
                    .tag(tag)
                    .execute(stringCallback);
        }
    }
}
