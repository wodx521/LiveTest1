package com.lairui.livetest1.fragmentfactory;

import android.util.SparseArray;

import com.lairui.livetest1.module.two_module.fragment.ConsumptionFragment;
import com.lairui.livetest1.module.two_module.fragment.IncomeFragment;
import com.wanou.framelibrary.base.BaseFragment;

/**
 * @author wodx521
 * @date on 2018/8/17
 */
public class SecondFragmentFactory {
    public static SparseArray<BaseFragment> fragmentMainMap = new SparseArray<>();

    public static BaseFragment getFragment(int position) {
        BaseFragment baseFragment = null;
        //尝试从内存中读取需要的对象
        baseFragment = fragmentMainMap.get(position);
        if (baseFragment != null) {
            return baseFragment;
        } else {
            switch (position) {
                case 0:
                    // 收入榜
                    baseFragment = new IncomeFragment();
                    break;
                case 1:
                    // 消费榜
                    baseFragment = new ConsumptionFragment();
                    break;
                default:
            }
            fragmentMainMap.put(position, baseFragment);
            return baseFragment;
        }
    }
}
