package com.lairui.livetest1.fragmentfactory;

import android.util.SparseArray;

import com.lairui.livetest1.module.two_module.fragment.ConsumptionDayFragment;
import com.lairui.livetest1.module.two_module.fragment.ConsumptionTotalFragment;
import com.lairui.livetest1.module.two_module.fragment.ConsumptionWeekFragment;
import com.lairui.livetest1.module.two_module.fragment.IncomeDayFragment;
import com.lairui.livetest1.module.two_module.fragment.IncomeTotalFragment;
import com.lairui.livetest1.module.two_module.fragment.IncomeWeekFragment;
import com.wanou.framelibrary.base.BaseFragment;

/**
 * @author wodx521
 * @date on 2018/8/17
 */
public class ConsumptionFactory {
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
                    // 日榜
                    baseFragment = new ConsumptionDayFragment();
                    break;
                case 1:
                    // 月榜
                    baseFragment = new ConsumptionWeekFragment();
                    break;
                case 2:
                    // 总榜
                    baseFragment = new ConsumptionTotalFragment();
                    break;
                default:
            }
            fragmentMainMap.put(position, baseFragment);
            return baseFragment;
        }
    }
}
