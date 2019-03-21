package com.lairui.livetest1.utils;

import android.app.Activity;
import android.content.pm.ActivityInfo;

import com.lairui.livetest1.app_constant.AppConstant;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;

public class ChoosePicture {
    public static void choosePicture(Activity activity) {
        Matisse.from(activity)
                .choose(MimeType.ofAll())
                // 计数
                .countable(true)
                // 选择图片数量
                .maxSelectable(1)
                // 过滤器
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                // 设置期望大小
//                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new Glide4Engine())
                .forResult(AppConstant.REQUEST_CODE_CHOOSE);
    }
}
