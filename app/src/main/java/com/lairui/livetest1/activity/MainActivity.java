package com.lairui.livetest1.activity;

import com.lairui.livetest1.R;
import com.lairui.livetest1.presenter.MainPresenter;
import com.wanou.framelibrary.base.BaseMvpActivity;

public class MainActivity extends BaseMvpActivity<MainPresenter> {

    @Override
    protected int getResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected MainPresenter getPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initData() {

    }
}
