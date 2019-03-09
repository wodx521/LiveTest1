package com.lairui.livetest1.module.four_module;

import android.view.View;

import com.lairui.livetest1.R;
import com.lairui.livetest1.module.four_module.presenter.FourMainPresenter;
import com.wanou.framelibrary.base.BaseMvpFragment;

public class FourMainFragment extends BaseMvpFragment<FourMainPresenter> {
    @Override
    protected FourMainPresenter getPresenter() {
        return new FourMainPresenter();
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_four;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }

}
