package com.lairui.livetest1.fragmentfactory;

import com.lairui.livetest1.module.two_module.fragment.IncomeDayFragment;
import com.wanou.framelibrary.base.BaseFragment;

import java.util.HashMap;

/**
 * @author wodx521
 * @date on 2018/8/17
 */
public class IncomeRankingFragmentFactory {
    public static HashMap<Integer, BaseFragment> fragmentMainMap = new HashMap<>();

    public static BaseFragment getFragment(int position) {
        BaseFragment baseFragment = null;
        //尝试从内存中读取需要的对象
        baseFragment = fragmentMainMap.get(position);
        if (baseFragment != null) {
            return baseFragment;
        } else {
            switch (position) {
                case 0:
                    // 关注列表
                    baseFragment = new IncomeDayFragment();
                    break;
                case 1:
                    // 热门列表
//                    baseFragment = new IncomeWeekFragment();
                    break;
                case 2:
                    // 热门列表
//                    baseFragment = new IncomeTotalFragment();
                    break;
                default:
            }
            fragmentMainMap.put(position, baseFragment);
            return baseFragment;
        }
    }
}
