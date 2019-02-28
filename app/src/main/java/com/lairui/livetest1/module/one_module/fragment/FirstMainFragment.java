package com.lairui.livetest1.module.one_module.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.fragmentfactory.HomeFragmentFactory;
import com.lairui.livetest1.fragmentfactory.MainFragmentFactory;
import com.lairui.livetest1.module.one_module.adapter.LiveClassificationAdapter;
import com.lairui.livetest1.module.one_module.presenter.FirstPresenter;
import com.lairui.livetest1.utils.DataInterface;
import com.wanou.framelibrary.base.BaseMvpFragment;
import com.wanou.framelibrary.utils.UiTools;

import java.util.List;

public class FirstMainFragment extends BaseMvpFragment<FirstPresenter> {
    private TextView tvSearch;
    private TabLayout tlClassification;
    private TextView tvMsg;
    private ViewPager viewPager;

    @Override
    protected FirstPresenter getPresenter() {
        return new FirstPresenter();
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_first;
    }

    @Override
    protected void initView(View view) {
        tvSearch = view.findViewById(R.id.tvSearch);
        tlClassification = view.findViewById(R.id.tlClassification);
        tvMsg = view.findViewById(R.id.tvMsg);
        viewPager = view.findViewById(R.id.viewPager);

    }

    @Override
    protected void initData() {

        String[] stringArray = UiTools.getStringArray(R.array.liveClassification);
        for (String classificationName : stringArray) {
            tlClassification.addTab(tlClassification.newTab().setText(classificationName));
        }

        LiveClassificationAdapter liveClassificationAdapter = new LiveClassificationAdapter(getChildFragmentManager());
        liveClassificationAdapter.addFragment(HomeFragmentFactory.getFragment(0));
        liveClassificationAdapter.addFragment(HomeFragmentFactory.getFragment(1));
        viewPager.setAdapter(liveClassificationAdapter);
        tlClassification.setupWithViewPager(viewPager);
    }

    @Override
    protected void isHiddenListener(boolean hidden) {

    }

    private void addFragment(int position, String title) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        Fragment fragmentByTag = getChildFragmentManager().findFragmentByTag(title);
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            fragmentTransaction.hide(fragment);
        }
        if (fragmentByTag != null) {
            fragmentTransaction.show(fragmentByTag);
        } else {
            fragmentTransaction.add(R.id.fl_container, MainFragmentFactory.getFragment(position), title);
            fragmentTransaction.show(MainFragmentFactory.getFragment(position));
        }
        fragmentTransaction.commitAllowingStateLoss();
    }
}
