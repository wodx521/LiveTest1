package com.lairui.livetest1.module.five_module;

import android.view.View;

import com.lairui.livetest1.R;
import com.lairui.livetest1.module.five_module.presenter.FiveMainPresenter;
import com.wanou.framelibrary.base.BaseMvpFragment;

public class FiveMainFragment extends BaseMvpFragment<FiveMainPresenter> {
    @Override
    protected FiveMainPresenter getPresenter() {
        return new FiveMainPresenter();
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_five;
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
