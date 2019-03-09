package com.lairui.livetest1.module.one_module.fragment;

import android.view.View;

import com.lairui.livetest1.R;
import com.lairui.livetest1.module.one_module.presenter.VideoPresenter;
import com.wanou.framelibrary.base.BaseMvpFragment;

public class VideoFragment extends BaseMvpFragment<VideoPresenter> {
    @Override
    protected VideoPresenter getPresenter() {
        return new VideoPresenter();
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }

}
