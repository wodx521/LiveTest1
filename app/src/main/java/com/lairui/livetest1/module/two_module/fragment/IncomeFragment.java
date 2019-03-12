package com.lairui.livetest1.module.two_module.fragment;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lairui.livetest1.R;
import com.lairui.livetest1.entity.bean.RankingBean;
import com.lairui.livetest1.module.two_module.presenter.IncomePresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wanou.framelibrary.base.BaseMvpFragment;
import com.wanou.framelibrary.utils.UiTools;

public class IncomeFragment extends BaseMvpFragment<IncomePresenter> {
    private TabLayout tlRankingDetail;
    private SmartRefreshLayout srlRefresh;
    private RecyclerView rvRanking;
    private ConstraintLayout clLoading;
    private ConstraintLayout clError;
    private ConstraintLayout clEmpty;

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
        srlRefresh = view.findViewById(R.id.srlRefresh);
        rvRanking = view.findViewById(R.id.rvRanking);
        clLoading = view.findViewById(R.id.clLoading);
        clError = view.findViewById(R.id.clError);
        clEmpty = view.findViewById(R.id.clEmpty);

        viewVisible(clLoading);
        viewGone(clEmpty,clError,srlRefresh);
    }

    @Override
    protected void initData() {
        String[] stringArray = UiTools.getStringArray(R.array.RankingCategory);
        for (String tabContent : stringArray) {
            tlRankingDetail.addTab(tlRankingDetail.newTab().setText(tabContent));
        }

        tlRankingDetail.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void setRankingBean(RankingBean rankingBean) {

    }
}
