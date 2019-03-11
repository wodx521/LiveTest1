package com.lairui.livetest1.module.one_module.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.lairui.livetest1.entity.bean.CategoryBean;
import com.wanou.framelibrary.base.BaseFragmentPagerAdapter;

import java.util.List;

public class LiveClassificationAdapter extends BaseFragmentPagerAdapter {

    private List<CategoryBean> categoryListBean;

    public LiveClassificationAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setCategoryListBean(List<CategoryBean> categoryListBean) {
        this.categoryListBean = categoryListBean;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (categoryListBean != null && categoryListBean.size() > 0) {
            return categoryListBean.size();
        }
        return 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return categoryListBean.get(position).getName();
    }
}
