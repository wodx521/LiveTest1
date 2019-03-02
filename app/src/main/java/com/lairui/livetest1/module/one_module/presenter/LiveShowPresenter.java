package com.lairui.livetest1.module.one_module.presenter;

import com.google.gson.reflect.TypeToken;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.entity.bean.LiveAddressBean;
import com.lairui.livetest1.module.one_module.activity.LiveShowActivity;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.base.Request;
import com.wanou.framelibrary.base.BasePresenterImpl;
import com.wanou.framelibrary.bean.GeneralResult;
import com.wanou.framelibrary.bean.SimpleResponse;
import com.wanou.framelibrary.okgoutil.CustomizeStringCallback;
import com.wanou.framelibrary.okgoutil.OkGoUtils;

import java.lang.reflect.Type;

public class LiveShowPresenter extends BasePresenterImpl<LiveShowActivity> {
    public void getLiveAddress(HttpParams httpParams) {
        OkGoUtils.postRequest(AppConstant.BASE_URL, "liveaddress", httpParams, new CustomizeStringCallback() {
            @Override
            public Type getResultType() {
                return new TypeToken<GeneralResult<LiveAddressBean>>() {
                }.getType();
            }

            @Override
            public void onRequestSuccess(GeneralResult generalResult) {
                LiveAddressBean liveAddressBean = (LiveAddressBean) generalResult.data;
                mPresenterView.setAddress(liveAddressBean);
            }

            @Override
            public void onRequestError(SimpleResponse simpleResponse) {
                mPresenterView.setAddressError();
            }

            @Override
            public void onRequestStart(Request<String, ? extends Request> request) {

            }

            @Override
            public void onRequestFinish() {

            }
        });
    }
}
