package com.lairui.livetest1.module.two_module.fragment;

import android.view.View;

import com.lairui.livetest1.R;
import com.lairui.livetest1.module.two_module.presenter.ConsumptionPresenter;
import com.wanou.framelibrary.base.BaseMvpFragment;

public class ConsumptionFragment extends BaseMvpFragment<ConsumptionPresenter> {
    @Override
    protected ConsumptionPresenter getPresenter() {
        return new ConsumptionPresenter();
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_consumption;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }
}
