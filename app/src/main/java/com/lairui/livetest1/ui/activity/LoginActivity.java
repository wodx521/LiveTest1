package com.lairui.livetest1.ui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.entity.bean.LoginBean;
import com.lairui.livetest1.presenter.LoginPresenter;
import com.lzy.okgo.model.HttpParams;
import com.wanou.framelibrary.base.BaseMvpActivity;
import com.wanou.framelibrary.utils.SpUtils;
import com.wanou.framelibrary.utils.UiTools;

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
        mSimpleMultiStateView = findViewById(R.id.SimpleMultiStateView);
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
        String phone = (String) SpUtils.get("loginNumber", "");
        if (UiTools.noEmpty(phone)) {
            etName.setText(phone);
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
                startActivityForResult(LoginActivity.this, null, AppConstant.REGISTER, RegisterActivity.class);
                break;
            default:
        }
    }

    private static String TAG = "LoginActivity";

    public void loginSuccess(LoginBean loginBean) {
        String phone = loginBean.getPhone();
        String token = loginBean.getToken();
        String roomId = loginBean.getRoomId();
        SpUtils.put("phone", phone);
        SpUtils.put("loginNumber", UiTools.getText(etName));
        SpUtils.put("token", token);
        SpUtils.put("roomId", roomId);
        SpUtils.put("nickName", loginBean.getNickname());
        SpUtils.put("userName", loginBean.getUsername());
        startActivity(LoginActivity.this, null, MainActivity.class);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case AppConstant.REGISTER:
                    String phone = (String) SpUtils.get("loginNumber", "");
                    if (UiTools.noEmpty(phone)) {
                        etName.setText(phone);
                    }
                    break;
                default:
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
