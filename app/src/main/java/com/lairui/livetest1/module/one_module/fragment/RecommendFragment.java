package com.lairui.livetest1.module.one_module.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lairui.livetest1.R;
import com.lairui.livetest1.module.one_module.activity.LiveShowActivity;
import com.lairui.livetest1.module.one_module.adapter.LiveListAdapter;
import com.lairui.livetest1.module.one_module.presenter.RecommendPresenter;
import com.wanou.framelibrary.base.BaseMvpFragment;
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;

public class RecommendFragment extends BaseMvpFragment<RecommendPresenter> {
    private RecyclerView rvLiveList;
    private LiveListAdapter liveListAdapter;

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

    }

    @Override
    protected void initData() {
        liveListAdapter = new LiveListAdapter(getActivity());
        rvLiveList.setAdapter(liveListAdapter);
    }

    @Override
    protected void isHiddenListener(boolean hidden) {
        liveListAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                startActivity(RecommendFragment.this, null, LiveShowActivity.class);
            }
        });
    }
}
