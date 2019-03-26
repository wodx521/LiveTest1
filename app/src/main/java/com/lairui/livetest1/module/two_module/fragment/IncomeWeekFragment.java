package com.lairui.livetest1.module.two_module.fragment;

import android.view.View;

import com.lairui.livetest1.R;
import com.lairui.livetest1.module.two_module.presenter.IncomeWeekPresenter;
import com.wanou.framelibrary.base.BaseMvpFragment;

public class IncomeWeekFragment extends BaseMvpFragment<IncomeWeekPresenter> {
    @Override
    protected IncomeWeekPresenter getPresenter() {
        return new IncomeWeekPresenter();
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
