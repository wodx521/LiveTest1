package com.lairui.livetest1.module.two_module.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.fragmentfactory.SecondFragmentFactory;
import com.lairui.livetest1.module.two_module.presenter.SecondMainPresenter;
import com.lairui.livetest1.ui.activity.SearchActivity;
import com.wanou.framelibrary.base.BaseMvpFragment;
import com.wanou.framelibrary.utils.UiTools;

import java.util.List;

public class SecondMainFragment extends BaseMvpFragment<SecondMainPresenter> implements View.OnClickListener {
    private ImageView ivLeft;
    private TabLayout tlRanking;
    private ImageView ivRight;
    private String[] stringArray;

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
        ivLeft.setOnClickListener(this);
        ivRight.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        stringArray = UiTools.getStringArray(R.array.Ranking);
        for (String tabContent : stringArray) {
            tlRanking.addTab(tlRanking.newTab().setText(tabContent));
        }
        tlRanking.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        tlRanking.getTabAt(0).select();
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
            fragmentTransaction.add(R.id.flRanking, SecondFragmentFactory.getFragment(position), title);
            fragmentTransaction.show(SecondFragmentFactory.getFragment(position));
        }
        fragmentTransaction.commit();
    }

}
