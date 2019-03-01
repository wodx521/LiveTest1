package com.lairui.livetest1.module.three_module;

import android.view.View;

import com.lairui.livetest1.module.three_module.presenter.ThirdMainPresenter;
import com.wanou.framelibrary.base.BaseMvpFragment;

public class ThirdMainFragment extends BaseMvpFragment<ThirdMainPresenter> {
    @Override
    protected ThirdMainPresenter getPresenter() {
        return new ThirdMainPresenter();
    }

    @Override
    protected int getResId() {
        return 0;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void isHiddenListener(boolean hidden) {

    }
}
