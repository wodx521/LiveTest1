package com.lairui.livetest1.module.two_module.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.lairui.livetest1.R;
import com.lairui.livetest1.module.two_module.adapter.IncomeAdapter;
import com.lairui.livetest1.module.two_module.presenter.IncomePresenter;
import com.wanou.framelibrary.base.BaseMvpFragment;

public class IncomeFragment extends BaseMvpFragment<IncomePresenter> {
    private TabLayout tlRankingDetail;
    private ViewPager vpIncome;

    @Override
    protected IncomePresenter getPresenter() {
        return new IncomePresenter();
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_income;
    }

    @Override
    protected void initView(View view) {
        tlRankingDetail = view.findViewById(R.id.tlRankingDetail);
        vpIncome = view.findViewById(R.id.vpIncome);

    }

    @Override
    protected void initData() {
        IncomeAdapter incomeAdapter = new IncomeAdapter(getChildFragmentManager());
//        incomeAdapter.addFragment(new DayRankingIncomeFragment());

        vpIncome.setAdapter(incomeAdapter);
        tlRankingDetail.setupWithViewPager(vpIncome);
    }
}
