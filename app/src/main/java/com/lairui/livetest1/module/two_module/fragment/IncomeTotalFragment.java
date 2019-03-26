package com.lairui.livetest1.module.two_module.fragment;

import android.view.View;

import com.lairui.livetest1.R;
import com.lairui.livetest1.module.two_module.presenter.IncomeTotalPresenter;
import com.wanou.framelibrary.base.BaseMvpFragment;

public class IncomeTotalFragment extends BaseMvpFragment<IncomeTotalPresenter> {
    @Override
    protected IncomeTotalPresenter getPresenter() {
        return new IncomeTotalPresenter();
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_income_day;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }
}
