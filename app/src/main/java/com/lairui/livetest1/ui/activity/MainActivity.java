package com.lairui.livetest1.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.ImageView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.fragmentfactory.MainFragmentFactory;
import com.lairui.livetest1.presenter.MainPresenter;
import com.lairui.livetest1.utils.ChatroomKit;
import com.lairui.livetest1.widget.LiveDialog;
import com.wanou.framelibrary.base.BaseMvpActivity;
import com.wanou.framelibrary.utils.SpUtils;

import java.util.List;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

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
        // 本地存储token
        String token = (String) SpUtils.get("token", "");
        ChatroomKit.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                // TODO: 2019/2/28 token过期,需要重新请求token
                // 登录token过期重新登录
                startActivity(MainActivity.this, null, LoginActivity.class);
            }

            @Override
            public void onSuccess(String s) {
                Bundle bundle = new Bundle();
                UserInfo userInfo = new UserInfo(s, "", Uri.parse(""));
                bundle.putString("userId", s);
                SpUtils.put("IMUserId", s);
                ChatroomKit.setCurrentUser(userInfo);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                // 登录token
            }
        });
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
        // 设置启动默认选中
        navigation.setSelectedItemId(R.id.navigation_one);
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
