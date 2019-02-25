package com.lairui.livetest1.module.one_module;

import android.view.View;

import com.lairui.livetest1.R;
import com.lairui.livetest1.module.one_module.presenter.FirstPresenter;
import com.wanou.framelibrary.base.BaseMvpFragment;

public class FirstMainFragment extends BaseMvpFragment<FirstPresenter> {
    @Override
    protected FirstPresenter getPresenter() {
        return new FirstPresenter();
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_first;
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
