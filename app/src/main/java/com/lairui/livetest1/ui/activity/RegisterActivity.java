package com.lairui.livetest1.ui.activity;

import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.presenter.RegisterPresenter;
import com.lzy.okgo.model.HttpParams;
import com.wanou.framelibrary.base.BaseMvpActivity;
import com.wanou.framelibrary.utils.CountDownUtils;
import com.wanou.framelibrary.utils.SpUtils;
import com.wanou.framelibrary.utils.UiTools;

public class RegisterActivity extends BaseMvpActivity<RegisterPresenter> implements View.OnClickListener {
    private ImageView ivBack;
    private TextView tvToolbarTitle;
    private TextView tvOperate;
    private TextInputEditText etName;
    private TextInputEditText etPassword;
    private TextInputEditText etPhone;
    private TextInputEditText etVerificationCode;
    private TextView tvSendVerCode;
    private Button btRegister;
    private HttpParams httpParams = new HttpParams();

    @Override
    protected RegisterPresenter getPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected int getResId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        mSimpleMultiStateView = findViewById(R.id.SimpleMultiStateView);
        ivBack = findViewById(R.id.ivLeft);
        tvToolbarTitle = findViewById(R.id.tvToolbarTitle);
        etName = findViewById(R.id.et_name);
        etPassword = findViewById(R.id.et_password);
        etPhone = findViewById(R.id.etPhone);
        etVerificationCode = findViewById(R.id.etVerificationCode);
        tvSendVerCode = findViewById(R.id.tvSendVerCode);
        btRegister = findViewById(R.id.btRegister);
        btRegister.setText(R.string.register);
        tvToolbarTitle.setText(R.string.register);
        viewVisible(ivBack);

        ivBack.setOnClickListener(this);
        tvSendVerCode.setOnClickListener(this);
        btRegister.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        showSuccess();
    }


    public void verificationCodeSuccess(String code) {
        etVerificationCode.setText(code);
        CountDownUtils.getTimer(30, tvSendVerCode, "重新发送");
    }

    public void registerSuccess() {
        String phone = UiTools.getText(etPhone);
        SpUtils.put("loginNumber",phone);
        finish();
    }

    @Override
    public void onClick(View v) {
        httpParams.clear();
        String phone = UiTools.getText(etPhone);
        switch (v.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.tvSendVerCode:
                if (UiTools.noEmpty(phone)) {
                    httpParams.put("operate", "registerCode");
                    httpParams.put("phone", phone);
                    mPresenter.getVerificationCode(httpParams);
                } else {
                    UiTools.showToast(R.string.hintPhone);
                }
                break;
            case R.id.btRegister:
                String nickName = UiTools.getText(etName);
                String passWord = UiTools.getText(etPassword);
                String verificationCode = UiTools.getText(etVerificationCode);
                if (UiTools.noEmpty(phone, nickName, passWord, verificationCode)) {
                    httpParams.put("operate", "register");
                    httpParams.put("nickname", nickName);
                    httpParams.put("password", passWord);
                    httpParams.put("phone", phone);
                    httpParams.put("sex", "0");
                    httpParams.put("code", verificationCode);
                    mPresenter.register(httpParams);
                }
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CountDownUtils.cancelTimer();
    }
}
