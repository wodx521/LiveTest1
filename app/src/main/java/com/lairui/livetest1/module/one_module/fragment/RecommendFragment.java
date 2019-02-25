package com.lairui.livetest1.module.one_module.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lairui.livetest1.R;
import com.lairui.livetest1.module.one_module.presenter.RecommendPresenter;
import com.wanou.framelibrary.base.BaseMvpFragment;

public class RecommendFragment extends BaseMvpFragment<RecommendPresenter> {
    private RecyclerView rvLiveList;

    @Override
    protected RecommendPresenter getPresenter() {
        return new RecommendPresenter();
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void initView(View view) {
        rvLiveList = view.findViewById(R.id.rvLiveList);

//        rvLiveList.setAdapter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void isHiddenListener(boolean hidden) {

    }
}
