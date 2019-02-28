package com.lairui.livetest1.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.presenter.LoginPresenter;
import com.lairui.livetest1.utils.ChatroomKit;
import com.lzy.okgo.model.HttpParams;
import com.wanou.framelibrary.base.BaseMvpActivity;
import com.wanou.framelibrary.utils.SpUtils;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements View.OnClickListener {
    private TextView tvToolbarTitle;
    private TextInputEditText etName;
    private TextInputEditText etPassword;
    private Button btnLogin;
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
        tvToolbarTitle = findViewById(R.id.tv_toolbar_title);
        etName = findViewById(R.id.et_name);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);

        tvToolbarTitle.setText(R.string.login);
        btnLogin.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                httpParams.put("operate", "liveGroup-getToken");
                mPresenter.getToken(httpParams);
                break;
            default:
        }
    }
    private static String TAG = "SplashActivity";
    public void getTokenSuccess(String token) {
//        SpUtils.put("token", token);
        String token1 = "b1cFvaYJKszUh9Ask1b0roXKuPFneKzNNfg0ckjJm/YnAS8qZ/HCYyueB0HkM60rkTqLs01+dun6DP39/AfIsA==";
        String token2 = "oxzgGNh5dAnis+drf3yPSYXKuPFneKzNNfg0ckjJm/YnAS8qZ/HCY6gNtEPFqm8hoYIvB3O9/QVJflRUwMBCrw==";
//        SpUtils.put("token", token1);
        SpUtils.put("token", token2);
        ChatroomKit.connect(token2, new RongIMClient.ConnectCallback() {
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
                }
            }
        });
    }
}
