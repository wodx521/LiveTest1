package com.lairui.livetest1.module.five_module.presenter;

import com.google.gson.reflect.TypeToken;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.module.five_module.activity.EditNickActivity;
import com.lairui.livetest1.ui.activity.LoginActivity;
import com.lzy.okgo.request.base.Request;
import com.wanou.framelibrary.base.BasePresenterImpl;
import com.wanou.framelibrary.bean.GeneralResult;
import com.wanou.framelibrary.bean.SimpleResponse;
import com.wanou.framelibrary.manager.ActivityManage;
import com.wanou.framelibrary.okgoutil.CustomizeStringCallback;
import com.wanou.framelibrary.okgoutil.OkGoUtils;
import com.wanou.framelibrary.weight.LoadingDialog;

import java.lang.reflect.Type;
import java.util.List;

public class EditNickPresenter extends BasePresenterImpl<EditNickActivity> {
    public void saveEdit(String json) {
        OkGoUtils.postRequest(AppConstant.BASE_URL, "saveEdit", json, new CustomizeStringCallback() {
            @Override
            public Type getResultType() {
                return new TypeToken<GeneralResult<List<Void>>>() {
                }.getType();
            }

            @Override
            public void onRequestSuccess(GeneralResult generalResult) {
                mPresenterView.setSaveSuccess();
            }

            @Override
            public void onRequestError(SimpleResponse simpleResponse) {
                mPresenterView.startActivity(mPresenterView, null, LoginActivity.class);
                ActivityManage.getInstance().finishAll();
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
}
