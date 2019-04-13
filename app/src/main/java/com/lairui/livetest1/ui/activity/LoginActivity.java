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
import com.lairui.livetest1.entity.bean.LoginBean;
import com.lairui.livetest1.entity.bean.LoginInfoBean;
import com.lairui.livetest1.presenter.LoginPresenter;
import com.lairui.livetest1.utils.ObjectBox;
import com.lzy.okgo.model.HttpParams;
import com.wanou.framelibrary.base.BaseMvpActivity;
import com.wanou.framelibrary.utils.SpUtils;
import com.wanou.framelibrary.utils.UiTools;

import java.util.List;

import io.objectbox.Box;

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements View.OnClickListener {
    private static String TAG = "LoginActivity";
    private TextView tvToolbarTitle;
    private TextInputEditText etName;
    private TextInputEditText etPassword;
    private Button btnLogin;
    private ImageView ivLeft;
    private TextView tvOperate;
    private HttpParams httpParams = new HttpParams();

    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initData() {
        showSuccess();
        long currentID = (long) SpUtils.get("currentId", -1L);
        Box<LoginInfoBean> loginInfoBeanBox = ObjectBox.getBoxStore().boxFor(LoginInfoBean.class);
        if (currentID != -1) {
            LoginInfoBean loginInfoBean = loginInfoBeanBox.get(currentID);
            if (UiTools.noEmpty(loginInfoBean.getUserName())) {
                etName.setText(loginInfoBean.getUserName());
            }
            if (UiTools.noEmpty(loginInfoBean.getPassword())) {
                etPassword.setText(loginInfoBean.getPassword());
            }
        }
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

    public void loginSuccess(LoginBean loginBean) {
        String phone = loginBean.getPhone();
        String token = loginBean.getToken();
        String roomId = loginBean.getRoomId();
        String imtoken = loginBean.getImtoken();
        String id = loginBean.getUserId();
        SpUtils.put("phone", phone);
        SpUtils.put("userId", id);
        SpUtils.put("token", token);
        SpUtils.put("imtoken", imtoken);
        SpUtils.put("roomId", roomId);
        SpUtils.put("nickName", loginBean.getNickname());
        SpUtils.put("userName", loginBean.getUsername());
        SpUtils.put("sex", loginBean.getSex());
        SpUtils.put("loginNumber", UiTools.getText(etName));
        Box<LoginInfoBean> loginInfoBeanBox = ObjectBox.getBoxStore().boxFor(LoginInfoBean.class);
        // 创建记录信息的对象
        LoginInfoBean loginInfoBean = new LoginInfoBean();
        loginInfoBean.setUserName(UiTools.getText(etName));
        loginInfoBean.setPassword(UiTools.getText(etPassword));

        if (loginInfoBeanBox.isEmpty()) {
            // 如果数据库为空时
            long currentId = loginInfoBeanBox.put(loginInfoBean);
            SpUtils.put("currentId", currentId);
        } else {
            // 如果已经存有数据
            List<LoginInfoBean> loginInfoBeanList = loginInfoBeanBox.getAll();
            // 如果不包含对象存储
            // 判断不包含对象的类型
            for (int i = 0; i < loginInfoBeanList.size(); i++) {
                LoginInfoBean loginInfoBean1 = loginInfoBeanList.get(i);
                if (loginInfoBean1.getUserName().equals(UiTools.getText(etName))) {
                    // 如果时名称相同,密码不同,更新数据库
                    long currentId = loginInfoBeanBox.getId(loginInfoBean1);
                    LoginInfoBean loginInfoBean2 = loginInfoBeanBox.get(currentId);
                    if (!loginInfoBean2.equals(loginInfoBean)) {
                        loginInfoBean2.updateData(loginInfoBean);
                        loginInfoBeanBox.put(loginInfoBean2);
                    }
                    SpUtils.put("currentId", currentId);
                }
                if (i == loginInfoBeanList.size() - 1) {
                    long currentId = loginInfoBeanBox.put(loginInfoBean);
                    SpUtils.put("currentId", currentId);
                }
            }
        }
        long mainId = (long) SpUtils.get("mainId", -1L);
        Box<LoginBean> loginBeanBox = ObjectBox.getBoxStore().boxFor(LoginBean.class);
        if (mainId != -1) {
            LoginBean loginBean1 = loginBeanBox.get(mainId);
            loginBean1.setUpdate(loginBean);
            loginBeanBox.put(loginBean1);
        } else {
            long storeId = loginBeanBox.put(loginBean);
            SpUtils.put("mainId", storeId);
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
