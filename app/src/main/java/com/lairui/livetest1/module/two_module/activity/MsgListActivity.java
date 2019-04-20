package com.lairui.livetest1.module.two_module.activity;

import com.lairui.livetest1.R;
import com.lairui.livetest1.module.two_module.presenter.MsgListPresenter;
import com.wanou.framelibrary.base.BaseMvpActivity;

public class MsgListActivity extends BaseMvpActivity<MsgListPresenter> {
    @Override
    protected MsgListPresenter getPresenter() {
        return new MsgListPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getResId() {
        return R.layout.layout_msg_list;
    }

    @Override
    protected void initView() {

    }
}
