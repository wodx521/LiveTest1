package com.lairui.livetest1.module.two_module.fragment;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lairui.livetest1.R;
import com.lairui.livetest1.entity.bean.RankingBean;
import com.lairui.livetest1.fragmentfactory.IncomeRankingFragmentFactory;
import com.lairui.livetest1.fragmentfactory.SecondFragmentFactory;
import com.lairui.livetest1.module.two_module.adapter.RankingAdapter;
import com.lairui.livetest1.module.two_module.adapter.RankingPagerListAdapter;
import com.lairui.livetest1.module.two_module.presenter.IncomePresenter;
import com.lairui.livetest1.module.two_module.presenter.IncomePresenter1;
import com.lairui.livetest1.ui.activity.LoginActivity;
import com.lzy.okgo.model.HttpParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wanou.framelibrary.base.BaseFragment;
import com.wanou.framelibrary.base.BaseMvpFragment;
import com.wanou.framelibrary.bean.SimpleResponse;
import com.wanou.framelibrary.manager.ActivityManage;
import com.wanou.framelibrary.utils.SpUtils;
import com.wanou.framelibrary.utils.UiTools;

import java.util.ArrayList;
import java.util.List;

public class IncomeFragment1 extends BaseMvpFragment<IncomePresenter1> {
    private ViewPager vpIncome;
    private TabLayout tlRankingDetail;
    private HttpParams httpParams = new HttpParams();
    private List<RankingBean.ListBean> tempList = new ArrayList<>();
    private String[] stringArray;

    @Override
    protected IncomePresenter1 getPresenter() {
        return new IncomePresenter1();
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_income1;
    }

    @Override
    protected void initView(View view) {
        vpIncome = view.findViewById(R.id.vpIncome);
        tlRankingDetail = view.findViewById(R.id.tlRankingDetail);
    }

    @Override
    protected void initData() {
        stringArray = UiTools.getStringArray(R.array.RankingCategory);
        for (int i = 0; i < stringArray.length; i++) {
            tlRankingDetail.addTab(tlRankingDetail.newTab().setText(stringArray[i]));
        }

        tlRankingDetail.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                addFragment(tab.getPosition(), tab.getText().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                addFragment(tab.getPosition(), tab.getText().toString());
            }
        });

        tlRankingDetail.getTabAt(0).select();
    }

    private void addFragment(int position, String title) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        Fragment fragmentByTag = getChildFragmentManager().findFragmentByTag(title);
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            fragment.setUserVisibleHint(false);
            fragmentTransaction.hide(fragment);
        }
        if (fragmentByTag != null) {
            fragmentByTag.setUserVisibleHint(true);
            fragmentTransaction.show(fragmentByTag);
        } else {
            BaseFragment fragment = IncomeRankingFragmentFactory.getFragment(position);
            fragment.setUserVisibleHint(true);
            fragmentTransaction.add(R.id.flIncome, fragment, title);
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.commit();
    }
}
