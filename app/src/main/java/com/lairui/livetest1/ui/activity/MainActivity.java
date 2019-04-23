package com.lairui.livetest1.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.entity.bean.UserInfoBean;
import com.lairui.livetest1.entity.bean.UserInfoBean_;
import com.lairui.livetest1.fragmentfactory.MainFragmentFactory;
import com.lairui.livetest1.module.three_module.activity.LivePrepareActivity;
import com.lairui.livetest1.module.three_module.activity.LiveProtocolActivity;
import com.lairui.livetest1.presenter.MainPresenter;
import com.lairui.livetest1.utils.ChatroomKit;
import com.lairui.livetest1.utils.ObjectBox;
import com.lairui.livetest1.widget.LiveDialog;
import com.tencent.rtmp.TXLiveBase;
import com.wanou.framelibrary.base.BaseMvpActivity;
import com.wanou.framelibrary.manager.ActivityManage;
import com.wanou.framelibrary.utils.SpUtils;
import com.wanou.framelibrary.utils.UiTools;

import java.util.List;

import io.objectbox.Box;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends BaseMvpActivity<MainPresenter> implements View.OnClickListener {
    private BottomNavigationView navigation;
    private ImageView ivMiddleMenu;
    private Bundle bundle = new Bundle();
    private double mTime;
    // 是否选择的直播
    private boolean isLive = true;

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
                        MainActivityPermissionsDispatcher.applyPermissionsWithPermissionCheck(MainActivity.this);
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
        String sdkver = TXLiveBase.getSDKVersionStr();
        Log.d("liteavsdk", "liteav sdk version is : " + sdkver);
    }

    private void addFragment(int position, String title) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(title);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            fragment.setUserVisibleHint(false);
            fragmentTransaction.hide(fragment);
        }
        if (fragmentByTag != null) {
            fragmentByTag.setUserVisibleHint(true);
            fragmentTransaction.show(fragmentByTag);
        } else {
            MainFragmentFactory.getFragment(position).setUserVisibleHint(true);
            fragmentTransaction.add(R.id.fl_container, MainFragmentFactory.getFragment(position), title);
            fragmentTransaction.show(MainFragmentFactory.getFragment(position));
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivMiddleMenu:
                LiveDialog liveDialog = new LiveDialog(MainActivity.this);
                liveDialog.showDialog();
                liveDialog.setViewOnClickListener(new LiveDialog.ViewOnClickListener() {
                    @Override
                    public void onLiveClick() {
                        isLive = true;
                        MainActivityPermissionsDispatcher.applyPermissionsWithPermissionCheck(MainActivity.this);
                    }

                    @Override
                    public void onVideoClick() {
                        isLive = false;
                        MainActivityPermissionsDispatcher.applyPermissionsWithPermissionCheck(MainActivity.this);
                    }
                });

                break;
            default:
        }
    }


    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void applyPermissions() {
        if (isLive) {
            Box<UserInfoBean> userInfoBeanBox = ObjectBox.getBoxStore().boxFor(UserInfoBean.class);
            String userId = (String) SpUtils.get("userId", "");
            if (UiTools.noEmpty(userId)) {
                UserInfoBean userInfoBean = userInfoBeanBox.query().equal(UserInfoBean_.userId, userId).build().findFirst();
                if (userInfoBean != null) {
                    String roomId = userInfoBean.getRoomId();

                }
            }
            boolean isAgreeProtocol = (boolean) SpUtils.get("isAgreeProtocol", false);
            if (!isAgreeProtocol) {
                bundle.clear();
                bundle.putString("url", "http://ilvb.fanwe.net/wap/index.php?ctl=settings&act=article_show&cate_id=8");
                startActivity(MainActivity.this, bundle, LiveProtocolActivity.class);
            } else {
                startActivity(MainActivity.this, null, LivePrepareActivity.class);
            }
        } else {
            UiTools.showToast("进入录制小视频页面");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void deniedPermission() {
        // todo 这个方法只有在全部权限被拒绝时才调用
        UiTools.showToast("权限被拒绝,无法正常使用");
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mTime) > AppConstant.CLICK_TIME_OUT) {
                UiTools.showToast("再按一次退出程序");
                mTime = System.currentTimeMillis();
            } else {
                ChatroomKit.logout();
                ActivityManage.getInstance().finishAll();
            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
