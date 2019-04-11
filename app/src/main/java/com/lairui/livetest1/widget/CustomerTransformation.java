package com.lairui.livetest1.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.lairui.livetest1.utils.AlphaFilter;

import java.security.MessageDigest;

import jp.wasabeef.glide.transformations.BitmapTransformation;


public class CustomerTransformation extends BitmapTransformation {
    private static Paint paint;
    private static Canvas canvas;
    private static AlphaFilter alphaFilter;
    private int drawableRes;

    public CustomerTransformation(Context context, int drawableRes) {
        this.drawableRes = drawableRes;
        paint = new Paint();
        canvas = new Canvas();
        alphaFilter = new AlphaFilter();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

    }

    @Override
    protected Bitmap transform(Context context, BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableRes);
        // 位图边框
        Bitmap bitmapFrame = BitmapFactory.decodeResource(context.getResources(), drawableRes);
        alphaFilter.overlay(toTransform, bitmapFrame);

        canvas.drawBitmap(bitmapFrame, 0, 0, paint);

        return null;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
