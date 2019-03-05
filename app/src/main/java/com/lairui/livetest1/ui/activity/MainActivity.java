package com.lairui.livetest1.ui.activity;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.fragmentfactory.MainFragmentFactory;
import com.lairui.livetest1.presenter.MainPresenter;
import com.tencent.rtmp.TXLiveBase;
import com.wanou.framelibrary.base.BaseMvpActivity;

import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends BaseMvpActivity<MainPresenter> implements View.OnClickListener {
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
//                        addFragment(1, title);
//                        LiveDialog liveDialog = new LiveDialog(MainActivity.this);
//                        liveDialog.showDialog();
//                        startActivity(MainActivity.this, null, LiveActivity.class);
                        return false;
                    case R.id.navigation_third:
                        addFragment(2, title);
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
        ivMiddleMenu.setOnClickListener(this);
        MainActivityPermissionsDispatcher.applyPermissionsWithPermissionCheck(this);

        String sdkver = TXLiveBase.getSDKVersionStr();
        Log.d("liteavsdk", "liteav sdk version is : " + sdkver);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivMiddleMenu:
                startActivity(MainActivity.this, null, LiveActivity.class);
                break;
            default:
        }
    }


    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void applyPermissions() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void deniedPermission() {
        // todo 这个方法只有在全部权限被拒绝时才调用,
    }
}
