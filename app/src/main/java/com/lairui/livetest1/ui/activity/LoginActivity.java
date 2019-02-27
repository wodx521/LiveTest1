package com.lairui.livetest1.ui.activity;

import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.presenter.LoginPresenter;
import com.wanou.framelibrary.base.BaseMvpActivity;

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements View.OnClickListener {
    private TextView tvToolbarTitle;
    private TextInputEditText etName;
    private TextInputEditText etPassword;
    private Button btnLogin;

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
                startActivity(LoginActivity.this, null, MainActivity.class);
                finish();
                break;
            default:
        }
    }
}
