package com.lairui.livetest1.ui.activity;

import android.Manifest;
import android.net.Uri;
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
import com.lairui.livetest1.fragmentfactory.MainFragmentFactory;
import com.lairui.livetest1.presenter.MainPresenter;
import com.lairui.livetest1.utils.ChatroomKit;
import com.tencent.rtmp.TXLiveBase;
import com.wanou.framelibrary.base.BaseMvpActivity;
import com.wanou.framelibrary.manager.ActivityManage;
import com.wanou.framelibrary.utils.LogUtils;
import com.wanou.framelibrary.utils.SpUtils;
import com.wanou.framelibrary.utils.UiTools;

import java.util.List;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends BaseMvpActivity<MainPresenter> implements View.OnClickListener {
    private BottomNavigationView navigation;
    private ImageView ivMiddleMenu;
    private Bundle bundle = new Bundle();
    private double mTime;

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
//                        addFragment(2, title);
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
//        MainActivityPermissionsDispatcher.applyPermissionsWithPermissionCheck(this);
        String imtoken = (String) SpUtils.get("imtoken", "");
        if (UiTools.noEmpty(imtoken)) {
            ChatroomKit.connect(imtoken, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {
                    // TODO: 2019/2/28 token过期,需要重新请求token
//                ExitNoticeDialog.getDialog(MainActivity.this, "登录超时", "是否重新登录?");
                    UiTools.showToast("token过期了");
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
                    UiTools.showToast("链接融云失败");
                }
            });
            RongIMClient.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
                @Override
                public void onChanged(RongIMClient.ConnectionStatusListener.ConnectionStatus status) {
                    switch (status) {
                        case CONNECTED://连接成功。
                            LogUtils.i("连接成功");
                            break;
                        case DISCONNECTED://断开连接。
                            LogUtils.i("断开连接");
                            break;
                        case CONNECTING://连接中。
                            LogUtils.i("连接中");
                            break;
                        case NETWORK_UNAVAILABLE://网络不可用。
                            LogUtils.i("网络不可用");
                            break;
                        case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线
                            LogUtils.i("用户账户在其他设备登录");
                            UiTools.showToast("用户账户在其他设备登录");
                            SpUtils.put("token", "");
                            ChatroomKit.logout();
                            ActivityManage.getInstance().finishAll();
                            bundle.clear();
                            bundle.putInt("loginStatus", 1);
                            startActivity(MainActivity.this, bundle, LoginActivity.class);
//                            ExitNoticeDialog.getDialog(MainActivity.this, "用户账户在其他设备登录", "是否重新登录?");
                            break;
                        default:
                    }
                }
            });
        } else {
            ChatroomKit.logout();
            ActivityManage.getInstance().finishAll();
            startActivity(MainActivity.this, null, LoginActivity.class);
        }
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
                MainActivityPermissionsDispatcher.applyPermissionsWithPermissionCheck(this);
//                startActivity(MainActivity.this, null, LiveActivity.class);
                break;
            default:
        }
    }


    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void applyPermissions() {
        startActivity(MainActivity.this, null, LiveActivity.class);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void deniedPermission() {
        // todo 这个方法只有在全部权限被拒绝时才调用,
        UiTools.showToast("权限被拒绝,无法正常开启直播");
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
