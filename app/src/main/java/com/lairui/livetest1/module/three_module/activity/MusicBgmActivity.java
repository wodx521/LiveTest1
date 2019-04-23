package com.lairui.livetest1.module.three_module.activity;

import com.lairui.livetest1.R;
import com.lairui.livetest1.module.three_module.presenter.MusicBgmPresenter;
import com.wanou.framelibrary.base.BaseMvpActivity;

public class MusicBgmActivity extends BaseMvpActivity<MusicBgmPresenter> {
    @Override
    protected MusicBgmPresenter getPresenter() {
        return new MusicBgmPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getResId() {
        return R.layout.activity_music_bgm;
    }

    @Override
    protected void initView() {

    }
}
