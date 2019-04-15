package com.lairui.livetest1.module.three_module.presenter;

import com.google.gson.reflect.TypeToken;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.entity.bean.LiveAddressBean;
import com.lairui.livetest1.module.three_module.activity.LivePrepareActivity;
import com.lzy.okgo.request.base.Request;
import com.wanou.framelibrary.base.BasePresenterImpl;
import com.wanou.framelibrary.bean.GeneralResult;
import com.wanou.framelibrary.bean.SimpleResponse;
import com.wanou.framelibrary.okgoutil.CustomizeStringCallback;
import com.wanou.framelibrary.okgoutil.OkGoUtils;
import com.wanou.framelibrary.weight.LoadingDialog;

import java.lang.reflect.Type;

public class LivePreparePresenter extends BasePresenterImpl<LivePrepareActivity> {
    public void getPushAddress(String json) {
        OkGoUtils.postRequest(AppConstant.BASE_URL, "pushaddress", json, new CustomizeStringCallback() {
            @Override
            public Type getResultType() {
                return new TypeToken<GeneralResult<LiveAddressBean>>() {
                }.getType();
            }

            @Override
            public void onRequestSuccess(GeneralResult generalResult) {
                LiveAddressBean liveAddressBean = (LiveAddressBean) generalResult.data;
                mPresenterView.setPushAddress(liveAddressBean);
            }

            @Override
            public void onRequestError(SimpleResponse simpleResponse) {
                mPresenterView.setPushAddressError(simpleResponse);
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
