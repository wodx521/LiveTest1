package com.lairui.livetest1.presenter;

import com.google.gson.reflect.TypeToken;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.ui.activity.RegisterActivity;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.base.Request;
import com.wanou.framelibrary.base.BasePresenterImpl;
import com.wanou.framelibrary.bean.GeneralResult;
import com.wanou.framelibrary.bean.SimpleResponse;
import com.wanou.framelibrary.okgoutil.CustomizeStringCallback;
import com.wanou.framelibrary.okgoutil.OkGoUtils;
import com.wanou.framelibrary.utils.UiTools;

import java.lang.reflect.Type;
import java.util.List;

public class RegisterPresenter extends BasePresenterImpl<RegisterActivity> {
    public void getVerificationCode(HttpParams httpParams) {
        OkGoUtils.postRequest(AppConstant.BASE_URL, "verificationCode", httpParams, new CustomizeStringCallback() {
            @Override
            public Type getResultType() {
                return new TypeToken<GeneralResult<String>>() {
                }.getType();
            }

            @Override
            public void onRequestSuccess(GeneralResult generalResult) {
//                mPresenterView.showSuccess();
                String code = (String) generalResult.data;
                mPresenterView.verificationCodeSuccess(code);
            }

            @Override
            public void onRequestError(SimpleResponse simpleResponse) {
//                mPresenterView.showFaild();
            }

            @Override
            public void onRequestStart(Request<String, ? extends Request> request) {
//                mPresenterView.showLoading();
            }

            @Override
            public void onRequestFinish() {
            }
        });
    }

    public void register(HttpParams httpParams) {
        OkGoUtils.postRequest(AppConstant.BASE_URL, "register", httpParams, new CustomizeStringCallback() {
            @Override
            public Type getResultType() {
                return new TypeToken<GeneralResult<List<Void>>>() {
                }.getType();
            }

            @Override
            public void onRequestSuccess(GeneralResult generalResult) {
                if (UiTools.noEmpty(generalResult.msg)) {
                    UiTools.showToast(generalResult.msg);
                }
                mPresenterView.showSuccess();
                mPresenterView.registerSuccess();
            }

            @Override
            public void onRequestError(SimpleResponse simpleResponse) {
                mPresenterView.showFaild();
            }

            @Override
            public void onRequestStart(Request<String, ? extends Request> request) {
                mPresenterView.showLoading();
            }

            @Override
            public void onRequestFinish() {
            }
        });
    }
}
