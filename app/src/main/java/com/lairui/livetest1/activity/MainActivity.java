package com.lairui.livetest1.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.ImageView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.fragmentfactory.MainFragmentFactory;
import com.lairui.livetest1.presenter.MainPresenter;
import com.lairui.livetest1.widget.LiveDialog;
import com.wanou.framelibrary.base.BaseMvpActivity;

import java.util.List;

public class MainActivity extends BaseMvpActivity<MainPresenter> {
    private BottomNavigationView navigation;
    private ImageView ivMiddleMenu;

    @Override
    protected int getResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        navigation = findViewById(R.id.navigation);
        ivMiddleMenu = findViewById(R.id.ivMiddleMenu);
    }

    @Override
    protected MainPresenter getPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initData() {
        navigation.setItemIconTintList(null);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                String title = menuItem.getTitle().toString();

                switch (itemId) {
                    case R.id.navigation_one:
                        addFragment(0, title);
                        return true;
                    case R.id.navigation_two:
                        addFragment(1, title);
                        return true;
                    case R.id.navigation_third:
                        LiveDialog liveDialog = new LiveDialog(MainActivity.this);
                        liveDialog.showDialog();
                        return true;
                    case R.id.navigation_four:
                        addFragment(3, title);
                        return true;
                    case R.id.navigation_five:
                        addFragment(4, title);
                        return true;
                    default:
                }
                return false;
            }
        });
    }

    private void addFragment(int position, String title) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(title);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            fragmentTransaction.hide(fragment);
        }
        if (fragmentByTag != null) {
            fragmentTransaction.show(fragmentByTag);
        } else {
            fragmentTransaction.add(R.id.fl_container, MainFragmentFactory.getFragment(position), title);
            fragmentTransaction.show(MainFragmentFactory.getFragment(position));
        }
        fragmentTransaction.commitAllowingStateLoss();
    }
}
