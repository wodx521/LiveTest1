package com.lairui.livetest1.module.one_module.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.fragmentfactory.HomeFragmentFactory;
import com.lairui.livetest1.fragmentfactory.MainFragmentFactory;
import com.lairui.livetest1.module.one_module.adapter.LiveClassificationAdapter;
import com.lairui.livetest1.module.one_module.presenter.FirstPresenter;
import com.lairui.livetest1.ui.activity.SearchActivity;
import com.wanou.framelibrary.base.BaseMvpFragment;
import com.wanou.framelibrary.utils.UiTools;

import java.util.List;

public class FirstMainFragment extends BaseMvpFragment<FirstPresenter> implements View.OnClickListener {
    private TabLayout tlClassification;
    private ViewPager viewPager;
    private ImageView ivLeft;
    private TextView tvToolbarTitle;
    private ImageView ivRight1;
    private ImageView ivRight2;

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
        ivLeft = view.findViewById(R.id.ivLeft);
        tvToolbarTitle = view.findViewById(R.id.tvToolbarTitle);
        ivRight1 = view.findViewById(R.id.ivRight1);
        ivRight2 = view.findViewById(R.id.ivRight2);
        tlClassification = view.findViewById(R.id.tlClassification);
        viewPager = view.findViewById(R.id.viewPager);

        tvToolbarTitle.setText(R.string.bottom_one);
        ivLeft.setImageResource(R.drawable.search_black);
        ivRight1.setImageResource(R.drawable.location);
        ivRight2.setImageResource(R.drawable.notice);

        viewVisible(ivLeft);

        ivLeft.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        String[] stringArray = UiTools.getStringArray(R.array.liveClassification);
        for (String classificationName : stringArray) {
            tlClassification.addTab(tlClassification.newTab().setText(classificationName));
        }
        viewGone(tlClassification);
        LiveClassificationAdapter liveClassificationAdapter = new LiveClassificationAdapter(getChildFragmentManager());
        liveClassificationAdapter.addFragment(HomeFragmentFactory.getFragment(0));
//        liveClassificationAdapter.addFragment(HomeFragmentFactory.getFragment(1));
        viewPager.setAdapter(liveClassificationAdapter);
        tlClassification.setupWithViewPager(viewPager);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivLeft:
                startActivity(FirstMainFragment.this, null, SearchActivity.class);
                break;
            default:
        }
    }
}
