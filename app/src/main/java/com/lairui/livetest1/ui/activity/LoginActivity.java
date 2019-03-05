package com.lairui.livetest1.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.entity.bean.LoginBean;
import com.lairui.livetest1.presenter.LoginPresenter;
import com.lairui.livetest1.utils.ChatroomKit;
import com.lzy.okgo.model.HttpParams;
import com.wanou.framelibrary.base.BaseMvpActivity;
import com.wanou.framelibrary.utils.SpUtils;
import com.wanou.framelibrary.utils.UiTools;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements View.OnClickListener {
    private TextView tvToolbarTitle;
    private TextInputEditText etName;
    private TextInputEditText etPassword;
    private Button btnLogin;
    private TextView tvOperate;
    private HttpParams httpParams = new HttpParams();

    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int getResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        tvOperate = findViewById(R.id.tvOperate);
        tvToolbarTitle = findViewById(R.id.tv_toolbar_title);
        etName = findViewById(R.id.et_name);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);

        tvToolbarTitle.setText(R.string.login);
        btnLogin.setOnClickListener(this);
        viewVisible(tvOperate);
        tvOperate.setText(R.string.register);
        tvOperate.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        showSuccess();
        String userName = (String) SpUtils.get("userName", "");
        if (UiTools.noEmpty(userName)) {
            etName.setText(userName);
        }
        String token = (String) SpUtils.get("token", "");
        if (UiTools.noEmpty(token)) {
            ChatroomKit.connect(token, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {
                    // TODO: 2019/2/28 token过期,需要重新请求token
                }

                @Override
                public void onSuccess(String s) {
                    Bundle bundle = new Bundle();
                    UserInfo userInfo = new UserInfo(s, "", Uri.parse(""));
                    bundle.putString("userId", s);
                    SpUtils.put("IMUserId", s);
                    ChatroomKit.setCurrentUser(userInfo);
                    startActivity(LoginActivity.this, bundle, MainActivity.class);
                    finish();
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String userName = UiTools.getText(etName);
                String password = UiTools.getText(etPassword);
                if (UiTools.noEmpty(userName, password)) {
                    httpParams.put("operate", "login");
                    httpParams.put("username", userName);
                    httpParams.put("password", password);
                    mPresenter.login(httpParams);
                }
                break;
            case R.id.tvOperate:
                // 打开注册页面
                startActivity(LoginActivity.this, null, RegisterActivity.class);
                break;
            default:
        }
    }

    private static String TAG = "SplashActivity";

    public void getTokenSuccess(String token) {
        SpUtils.put("token", token);
        ChatroomKit.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                // TODO: 2019/2/28 token过期,需要重新请求token
            }

            @Override
            public void onSuccess(String s) {
                Bundle bundle = new Bundle();
                UserInfo userInfo = new UserInfo(s, "", Uri.parse(""));
                bundle.putString("userId", s);
                SpUtils.put("IMUserId", s);
                ChatroomKit.setCurrentUser(userInfo);
                startActivity(LoginActivity.this, bundle, MainActivity.class);
                finish();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });

        RongIMClient.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
            @Override
            public void onChanged(ConnectionStatus status) {
                switch (status) {
                    case CONNECTED://连接成功。
                        Log.i(TAG, "连接成功");
                        break;
                    case DISCONNECTED://断开连接。
                        Log.i(TAG, "断开连接");
                        break;
                    case CONNECTING://连接中。
                        Log.i(TAG, "连接中");
                        break;
                    case NETWORK_UNAVAILABLE://网络不可用。
                        Log.i(TAG, "网络不可用");
                        break;
                    case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线
                        Log.i(TAG, "用户账户在其他设备登录");
                        break;
                    default:
                }
            }
        });
    }

    public void loginSuccess(LoginBean loginBean) {
        String phone = loginBean.getPhone();
        String token = loginBean.getToken();
        String roomId = loginBean.getRoomId();
        SpUtils.put("phone", phone);
        SpUtils.put("userName", UiTools.getText(etName));
        SpUtils.put("token", token);
        SpUtils.put("roomId", roomId);
        SpUtils.put("nickName", loginBean.getNickname());
        SpUtils.put("userName", loginBean.getUsername());

        ChatroomKit.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                // TODO: 2019/2/28 token过期,需要重新请求token
            }

            @Override
            public void onSuccess(String s) {
                Bundle bundle = new Bundle();
                UserInfo userInfo = new UserInfo(s, "", Uri.parse(""));
                bundle.putString("userId", s);
                SpUtils.put("IMUserId", s);
                ChatroomKit.setCurrentUser(userInfo);
                startActivity(LoginActivity.this, bundle, MainActivity.class);
                finish();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });

        RongIMClient.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
            @Override
            public void onChanged(ConnectionStatus status) {
                switch (status) {
                    case CONNECTED://连接成功。
                        Log.i(TAG, "连接成功");
                        break;
                    case DISCONNECTED://断开连接。
                        Log.i(TAG, "断开连接");
                        break;
                    case CONNECTING://连接中。
                        Log.i(TAG, "连接中");
                        break;
                    case NETWORK_UNAVAILABLE://网络不可用。
                        Log.i(TAG, "网络不可用");
                        break;
                    case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线
                        Log.i(TAG, "用户账户在其他设备登录");
                        break;
                    default:
                }
            }
        });
    }
}
