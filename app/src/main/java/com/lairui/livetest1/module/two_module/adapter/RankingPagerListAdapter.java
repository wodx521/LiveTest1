package com.lairui.livetest1.module.two_module.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.lairui.livetest1.R;
import com.wanou.framelibrary.base.BaseFragmentPagerAdapter;
import com.wanou.framelibrary.utils.UiTools;

public class RankingPagerListAdapter extends BaseFragmentPagerAdapter {

    private String[] stringArray;

    public RankingPagerListAdapter(FragmentManager fm) {
        super(fm);
        stringArray = UiTools.getStringArray(R.array.RankingCategory);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return stringArray[position];
    }
}
