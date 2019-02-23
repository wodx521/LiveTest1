package com.wanou.framelibrary.base;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Author by wodx521
 * Date on 2018/12/27.
 */
public abstract class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    protected List<BaseFragment> list = new ArrayList<>();

    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(BaseFragment baseFragment) {
        list.add(baseFragment);
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // 防止销毁fragment
    }
}
