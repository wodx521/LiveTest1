package com.lairui.livetest1.module.two_module;

import android.view.View;

import com.lairui.livetest1.R;
import com.lairui.livetest1.module.two_module.presenter.SecondMainPresenter;
import com.wanou.framelibrary.base.BaseMvpFragment;

public class SecondMainFragment extends BaseMvpFragment<SecondMainPresenter> {

    @Override
    protected SecondMainPresenter getPresenter() {
        return new SecondMainPresenter();
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_second;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }
}
