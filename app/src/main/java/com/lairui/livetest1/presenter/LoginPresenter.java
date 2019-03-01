package com.lairui.livetest1.presenter;

import com.google.gson.reflect.TypeToken;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.entity.bean.LoginBean;
import com.lairui.livetest1.ui.activity.LoginActivity;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.base.Request;
import com.wanou.framelibrary.base.BasePresenterImpl;
import com.wanou.framelibrary.bean.GeneralResult;
import com.wanou.framelibrary.bean.SimpleResponse;
import com.wanou.framelibrary.okgoutil.CustomizeStringCallback;
import com.wanou.framelibrary.okgoutil.OkGoUtils;
import com.wanou.framelibrary.utils.UiTools;
import com.wanou.framelibrary.weight.LoadingDialog;

import java.lang.reflect.Type;

public class LoginPresenter extends BasePresenterImpl<LoginActivity> {
    public void getToken(HttpParams httpParams) {
        OkGoUtils.postRequest(AppConstant.BASE_URL, "getToken", httpParams, new CustomizeStringCallback() {
            @Override
            public Type getResultType() {
                return new TypeToken<GeneralResult<String>>() {
                }.getType();
            }

            @Override
            public void onRequestSuccess(GeneralResult generalResult) {
                String token = (String) generalResult.data;
                mPresenterView.getTokenSuccess(token);
            }

            @Override
            public void onRequestError(SimpleResponse simpleResponse) {
            }

            @Override
            public void onRequestStart(Request<String, ? extends Request> request) {
                LoadingDialog.getDialog(mPresenterView, "");
            }

            @Override
            public void onRequestFinish() {
                LoadingDialog.dismiss();
            }
        });
    }

    public void login(HttpParams httpParams) {
        OkGoUtils.postRequest(AppConstant.BASE_URL, "login", httpParams, new CustomizeStringCallback() {
            @Override
            public Type getResultType() {
                return new TypeToken<GeneralResult<LoginBean>>() {
                }.getType();
            }

            @Override
            public void onRequestSuccess(GeneralResult generalResult) {
                if (UiTools.noEmpty(generalResult.msg)) {
                    UiTools.showToast(generalResult.msg);
                }
                LoginBean loginBean = (LoginBean) generalResult.data;
                mPresenterView.loginSuccess(loginBean);
            }

            @Override
            public void onRequestError(SimpleResponse simpleResponse) {
            }

            @Override
            public void onRequestStart(Request<String, ? extends Request> request) {
                mPresenterView.showLoading();
            }

            @Override
            public void onRequestFinish() {
                mPresenterView.showSuccess();
            }
        });
    }
}
