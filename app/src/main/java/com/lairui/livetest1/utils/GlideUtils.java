package com.lairui.livetest1.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lairui.livetest1.MyApplication;

public class GlideUtils {
//    public static <T> DrawableTypeRequest<T> load(T paramT) {
//        return (DrawableTypeRequest) Glide.with(App.getApplication()).load(paramT).placeholder(2130837653).error(2130837653).dontAnimate();
//    }
//
//    public static <T> void loadBackground(T paramT, View paramView) {
//        Glide.with(MyApplication.getContext())
//                .asBitmap()
//                .load(paramT)
//                .centerCrop()
//                .into(new SimpleTarget(paramView) {
//            @Override
//            public void onResourceReady(@NonNull Object resource, @Nullable Transition transition) {
//                BitmapDrawable localBitmapDrawable = new BitmapDrawable(paramBitmap);
//                if (Build.VERSION.SDK_INT >= 16)
//                    this.val$view.setBackground(localBitmapDrawable);
//            }
//
//        });
//    }
//
//    public static <T> DrawableTypeRequest<T> loadHeadImage(T paramT) {
//        return (DrawableTypeRequest) load(paramT).placeholder(2130837652).error(2130837652).dontAnimate();
//    }
}
