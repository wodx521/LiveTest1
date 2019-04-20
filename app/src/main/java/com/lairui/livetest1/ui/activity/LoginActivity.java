package com.lairui.livetest1.ui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.entity.bean.LoginInfoBean_;
import com.lairui.livetest1.entity.bean.UserInfoBean;
import com.lairui.livetest1.entity.bean.LoginInfoBean;
import com.lairui.livetest1.entity.bean.UserInfoBean_;
import com.lairui.livetest1.presenter.LoginPresenter;
import com.lairui.livetest1.utils.CustomTextChange;
import com.lairui.livetest1.utils.ObjectBox;
import com.lzy.okgo.model.HttpParams;
import com.wanou.framelibrary.base.BaseMvpActivity;
import com.wanou.framelibrary.utils.SpUtils;
import com.wanou.framelibrary.utils.UiTools;

import io.objectbox.Box;
import io.objectbox.query.Query;

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements View.OnClickListener {
    private static String TAG = "LoginActivity";
    private TextView tvToolbarTitle;
    private TextInputEditText etName;
    private TextInputEditText etPassword;
    private Button btnLogin;
    private ImageView ivLeft;
    private TextView tvOperate;
    private HttpParams httpParams = new HttpParams();
    private Query<LoginInfoBean> queryLoginInfo;
    private LoginInfoBean loginInfoBean;
    private Query<UserInfoBean> queryUserInfo;
    private Box<LoginInfoBean> loginInfoBeanBox;
    private Box<UserInfoBean> userInfoBeanBox;

    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initData() {
        // 登录信息数据库
        loginInfoBeanBox = ObjectBox.getBoxStore().boxFor(LoginInfoBean.class);
        queryLoginInfo = loginInfoBeanBox.query().equal(LoginInfoBean_.userName, "").build();
        // 用户信息状态数据库
        userInfoBeanBox = ObjectBox.getBoxStore().boxFor(UserInfoBean.class);
        queryUserInfo = userInfoBeanBox.query().equal(UserInfoBean_.userId, "").build();
        showSuccess();
        long currentID = (long) SpUtils.get("currentId", -1L);
        if (currentID != -1) {
            LoginInfoBean loginInfoBean = loginInfoBeanBox.get(currentID);
            if (UiTools.noEmpty(loginInfoBean.getUserName())) {
                etName.setText(loginInfoBean.getUserName());
            }
            if (UiTools.noEmpty(loginInfoBean.getPassword())) {
                etPassword.setText(loginInfoBean.getPassword());
            }
        }
        etName.addTextChangedListener(new CustomTextChange() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginInfoBean = queryLoginInfo.setParameter(LoginInfoBean_.userName, s.toString()).findFirst();
                if (loginInfoBean != null && loginInfoBean.getUserName().equals(s.toString())) {
                    etPassword.setText(loginInfoBean.getPassword());
                } else {
                    etPassword.setText("");
                }
            }
        });
    }

    @Override
    protected int getResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        mSimpleMultiStateView = findViewById(R.id.SimpleMultiStateView);
        tvOperate = findViewById(R.id.tvOperate);
        tvToolbarTitle = findViewById(R.id.tvToolbarTitle);
        etName = findViewById(R.id.et_name);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        ivLeft = findViewById(R.id.ivLeft);
        viewVisible(ivLeft);
        tvToolbarTitle.setText(R.string.login);
        btnLogin.setOnClickListener(this);
        viewVisible(tvOperate);
        tvOperate.setText(R.string.register);
        tvOperate.setOnClickListener(this);
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

    public void loginSuccess(UserInfoBean userInfoBean) {
        String phone = userInfoBean.getPhone();
        String token = userInfoBean.getToken();
        String roomId = userInfoBean.getRoomId();
        String imtoken = userInfoBean.getImtoken();
        String id = userInfoBean.getUserId();
        SpUtils.put("phone", phone);
        SpUtils.put("userId", id);
        SpUtils.put("token", token);
        SpUtils.put("imtoken", imtoken);
        SpUtils.put("roomId", roomId);
        SpUtils.put("nickName", userInfoBean.getNickname());
        SpUtils.put("userName", userInfoBean.getUsername());
        SpUtils.put("sex", userInfoBean.getSex());

        // 查询当数据库中有与登录名相同的对象时, 判断密码是否相同, 相同跳过存储, 不同更新存储
        loginInfoBean = queryLoginInfo.setParameter(LoginInfoBean_.userName, UiTools.getText(etName)).findFirst();
        if (loginInfoBean != null) {
            SpUtils.put("currentId", loginInfoBean.mainId);
            if (!loginInfoBean.getPassword().equals(UiTools.getText(etPassword))) {
                // 不一致时, 更新数据
                loginInfoBean.setPassword(UiTools.getText(etPassword));
                loginInfoBeanBox.put(loginInfoBean);
            }
        } else {
            loginInfoBean = new LoginInfoBean();
            loginInfoBean.setUserName(UiTools.getText(etName));
            loginInfoBean.setPassword(UiTools.getText(etPassword));
            long currentId = loginInfoBeanBox.put(loginInfoBean);
            SpUtils.put("currentId", currentId);
        }
        // 保存更新登录后的用户信息
        UserInfoBean userInfoBean1 = queryUserInfo.setParameter(UserInfoBean_.userId, userInfoBean.getUserId()).findFirst();
        SpUtils.put("userId", userInfoBean.getUserId());
        if (userInfoBean1 == null) {
            userInfoBeanBox.put(userInfoBean);
        } else {
            userInfoBean1.updateUserInfo(userInfoBean);
            userInfoBeanBox.put(userInfoBean1);
        }
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
