package com.lairui.livetest1.module.two_module.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.lairui.livetest1.R;
import com.wanou.framelibrary.base.BaseFragmentPagerAdapter;
import com.wanou.framelibrary.utils.UiTools;

public class IncomeAdapter extends BaseFragmentPagerAdapter {

    private String[] stringArray;

    public IncomeAdapter(FragmentManager fm) {
        super(fm);
        stringArray = UiTools.getStringArray(R.array.RankingCategory);
    }

    @Override
    public int getCount() {
        return stringArray.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return stringArray[position];
    }
}
