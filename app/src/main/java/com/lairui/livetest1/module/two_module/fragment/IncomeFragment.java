package com.lairui.livetest1.module.two_module.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.lairui.livetest1.R;
import com.lairui.livetest1.fragmentfactory.IncomeRankingFragmentFactory;
import com.wanou.framelibrary.base.BaseFragment;
import com.wanou.framelibrary.utils.UiTools;

import java.util.List;

public class IncomeFragment extends BaseFragment {
    private TabLayout tlRankingDetail;
    private String[] stringArray;

    @Override
    protected int getResId() {
        return R.layout.fragment_income;
    }

    @Override
    protected void initView(View view) {
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
