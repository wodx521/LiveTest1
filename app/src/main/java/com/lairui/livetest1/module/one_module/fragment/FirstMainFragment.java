package com.lairui.livetest1.module.one_module.fragment;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.entity.bean.CategoryBean;
import com.lairui.livetest1.fragmentfactory.HomeFragmentFactory;
import com.lairui.livetest1.module.one_module.adapter.LiveClassificationAdapter;
import com.lairui.livetest1.module.one_module.presenter.FirstPresenter;
import com.lairui.livetest1.ui.activity.SearchActivity;
import com.lairui.livetest1.utils.ObjectBox;
import com.lzy.okgo.model.HttpParams;
import com.wanou.framelibrary.base.BaseFragment;
import com.wanou.framelibrary.base.BaseMvpFragment;
import com.wanou.framelibrary.utils.UiTools;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;

public class FirstMainFragment extends BaseMvpFragment<FirstPresenter> implements View.OnClickListener {
    private TabLayout tlClassification;
    private ViewPager viewPager;
    private ImageView ivLeft;
    private TextView tvToolbarTitle;
    private ImageView ivRight1;
    private ImageView ivRight2;
    private ConstraintLayout clError;
    private ConstraintLayout clLoading;
    private HttpParams httpParams = new HttpParams();
    private List<CategoryBean> categoryTemp = new ArrayList<>();

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
        clError = view.findViewById(R.id.clError);
        clLoading = view.findViewById(R.id.clLoading);

        tvToolbarTitle.setText(R.string.bottom_one);
        ivLeft.setImageResource(R.drawable.search_black);
        ivRight1.setImageResource(R.drawable.location);
        ivRight2.setImageResource(R.drawable.notice);

        viewVisible(ivLeft, clLoading);
        viewGone(clError);
        ivLeft.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        httpParams.clear();
        categoryTemp.clear();
        httpParams.put("operate", "roomGroup-categoryList");
        mPresenter.getCategoryList(httpParams);
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

    public void setCategory(List<CategoryBean> categoryListBean) {
        viewGone(clLoading, clError);
        LiveClassificationAdapter liveClassificationAdapter = new LiveClassificationAdapter(getChildFragmentManager());
        String[] stringArray = UiTools.getStringArray(R.array.liveClassification);
        for (int i = 0; i < stringArray.length; i++) {
            CategoryBean categoryBean = new CategoryBean();
            categoryBean.setId(i + 1);
            categoryBean.setName(stringArray[i]);
            categoryTemp.add(categoryBean);
        }
        Box<CategoryBean> categoryBeanBox = ObjectBox.getBoxStore().boxFor(CategoryBean.class);
        List<CategoryBean> all = categoryBeanBox.getAll();
        if (!all.containsAll(categoryListBean)) {
            categoryBeanBox.put(categoryListBean);
        }
        categoryTemp.addAll(categoryListBean);
        liveClassificationAdapter.setCategoryListBean(categoryTemp);
        if (categoryTemp != null && categoryTemp.size() > 0) {
            viewVisible(tlClassification);
            for (CategoryBean categoryBean : categoryTemp) {
                tlClassification.addTab(tlClassification.newTab().setText(categoryBean.getName()));
            }
            for (int i = 0; i < categoryTemp.size(); i++) {
                AttentionFragment attentionFragment = new AttentionFragment();
                attentionFragment.setUserVisibleHint(false);
                liveClassificationAdapter.addFragment(attentionFragment);
            }
        }

        tlClassification.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        tlClassification.getTabAt(0).select();
    }

    private void addFragment(int position, String title) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        Fragment fragmentByTag = getChildFragmentManager().findFragmentByTag(title);
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            fragment.setUserVisibleHint(false);
            fragmentTransaction.hide(fragment);
        }
        if (fragmentByTag != null) {
            fragmentByTag.setUserVisibleHint(true);
            fragmentTransaction.show(fragmentByTag);
        } else {
            BaseFragment fragment = HomeFragmentFactory.getFragment(position);
            fragment.setUserVisibleHint(true);
            fragmentTransaction.add(R.id.fl_container, fragment, title);
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.commit();
    }

    public void setCategoryError() {
        viewGone(clLoading, tlClassification);
        viewVisible(clError);
        clError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewVisible(clLoading);
                viewGone(clError, tlClassification);
                initData();
            }
        });
    }
}
