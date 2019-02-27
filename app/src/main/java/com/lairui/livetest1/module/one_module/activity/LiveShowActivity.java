package com.lairui.livetest1.module.one_module.activity;

import com.lairui.livetest1.R;
import com.lairui.livetest1.module.one_module.presenter.LiveShowPresenter;
import com.wanou.framelibrary.base.BaseMvpActivity;

public class LiveShowActivity extends BaseMvpActivity<LiveShowPresenter> {
    @Override
    protected LiveShowPresenter getPresenter() {
        return new LiveShowPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getResId() {
        return R.layout.activity_live_show;
    }

    @Override
    protected void initView() {

    }
}
