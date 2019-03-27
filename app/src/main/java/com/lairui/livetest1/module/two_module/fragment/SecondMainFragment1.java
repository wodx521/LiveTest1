package com.lairui.livetest1.module.two_module.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.fragmentfactory.SecondFragmentFactory;
import com.lairui.livetest1.module.two_module.adapter.RankingAdapter;
import com.lairui.livetest1.module.two_module.adapter.RankingPagerAdapter;
import com.lairui.livetest1.module.two_module.presenter.SecondMainPresenter;
import com.lairui.livetest1.module.two_module.presenter.SecondMainPresenter1;
import com.lairui.livetest1.ui.activity.SearchActivity;
import com.wanou.framelibrary.base.BaseFragment;
import com.wanou.framelibrary.base.BaseMvpFragment;
import com.wanou.framelibrary.utils.UiTools;

import java.util.List;

public class SecondMainFragment1 extends BaseMvpFragment<SecondMainPresenter1> implements View.OnClickListener {
    private ImageView ivLeft;
    private TabLayout tlRanking;
    private ImageView ivRight;
    private ViewPager vpRanking;
    private String[] stringArray;

    @Override
    protected SecondMainPresenter1 getPresenter() {
        return new SecondMainPresenter1();
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_second_test;
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
        RankingPagerAdapter rankingPagerAdapter = new RankingPagerAdapter(getChildFragmentManager());
        stringArray = UiTools.getStringArray(R.array.Ranking);
        for (int i = 0; i < stringArray.length; i++) {
            rankingPagerAdapter.addFragment(SecondFragmentFactory.getFragment(i));
        }
        vpRanking.setAdapter(rankingPagerAdapter);
        tlRanking.setupWithViewPager(vpRanking);
        SecondFragmentFactory.getFragment(tlRanking.getSelectedTabPosition()).setUserVisibleHint(true);

        tlRanking.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                for (int i = 0; i < stringArray.length; i++) {
                    if (tab.getPosition() == i) {
                        SecondFragmentFactory.getFragment(tab.getPosition()).setUserVisibleHint(true);
                    } else {
                        SecondFragmentFactory.getFragment(tab.getPosition()).setUserVisibleHint(false);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivLeft:
                startActivity(SecondMainFragment1.this, null, SearchActivity.class);
                break;
            case R.id.ivRight:

                break;
            default:
        }
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
            BaseFragment fragment = SecondFragmentFactory.getFragment(position);
            fragment.setUserVisibleHint(true);
            fragmentTransaction.add(R.id.flRanking, fragment, title);
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.commit();
    }

}
