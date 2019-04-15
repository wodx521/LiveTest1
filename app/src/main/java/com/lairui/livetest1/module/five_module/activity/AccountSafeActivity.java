package com.lairui.livetest1.module.five_module.activity;

import com.lairui.livetest1.R;
import com.lairui.livetest1.module.five_module.presenter.AccountSafePresenter;
import com.wanou.framelibrary.base.BaseMvpActivity;

public class AccountSafeActivity extends BaseMvpActivity<AccountSafePresenter> {
    @Override
    protected AccountSafePresenter getPresenter() {
        return new AccountSafePresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getResId() {
        return R.layout.activity_account_safe;
    }

    @Override
    protected void initView() {

    }
}
