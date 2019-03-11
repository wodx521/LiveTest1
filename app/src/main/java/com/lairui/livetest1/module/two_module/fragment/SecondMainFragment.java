package com.lairui.livetest1.module.two_module.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.fragmentfactory.SecondFragmentFactory;
import com.lairui.livetest1.module.two_module.adapter.RankingListAdapter;
import com.lairui.livetest1.module.two_module.presenter.SecondMainPresenter;
import com.lairui.livetest1.ui.activity.SearchActivity;
import com.wanou.framelibrary.base.BaseMvpFragment;

public class SecondMainFragment extends BaseMvpFragment<SecondMainPresenter> implements View.OnClickListener {
    private ImageView ivLeft;
    private TabLayout tlRanking;
    private ImageView ivRight;
    private ViewPager vpRanking;

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
        ivLeft = view.findViewById(R.id.ivLeft);
        tlRanking = view.findViewById(R.id.tlRanking);
        ivRight = view.findViewById(R.id.ivRight);
        vpRanking = view.findViewById(R.id.vpRanking);

        ivLeft.setOnClickListener(this);
        ivRight.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        RankingListAdapter rankingListAdapter = new RankingListAdapter(getChildFragmentManager());

        rankingListAdapter.addFragment(SecondFragmentFactory.getFragment(0));
        rankingListAdapter.addFragment(SecondFragmentFactory.getFragment(1));
        vpRanking.setAdapter(rankingListAdapter);
        tlRanking.setupWithViewPager(vpRanking);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivLeft:
                startActivity(SecondMainFragment.this, null, SearchActivity.class);
                break;
            case R.id.ivRight:

                break;
            default:
        }
    }
}
