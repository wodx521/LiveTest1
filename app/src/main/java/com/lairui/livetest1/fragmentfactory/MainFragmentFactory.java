package com.lairui.livetest1.fragmentfactory;

import com.lairui.livetest1.module.five_module.FiveMainFragment;
import com.lairui.livetest1.module.four_module.FourMainFragment;
import com.lairui.livetest1.module.one_module.fragment.FirstMainFragment;
import com.lairui.livetest1.module.three_module.fragment.ThirdMainFragment;
import com.lairui.livetest1.module.two_module.SecondMainFragment;
import com.wanou.framelibrary.base.BaseFragment;

import java.util.HashMap;

/**
 * @author wodx521
 * @date on 2018/8/17
 */
public class MainFragmentFactory {
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
                    baseFragment = new FirstMainFragment();
                    break;
                case 1:
                    baseFragment = new SecondMainFragment();
                    break;
                case 2:
                    baseFragment = new ThirdMainFragment();
                    break;
                case 3:
                    baseFragment = new FourMainFragment();
                    break;
                case 4:
                    baseFragment = new FiveMainFragment();
                    break;
                default:
            }
            fragmentMainMap.put(position, baseFragment);
            return baseFragment;
        }
    }
}
