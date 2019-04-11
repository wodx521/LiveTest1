package com.lairui.livetest1.module.three_module.presenter;

import com.google.gson.reflect.TypeToken;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.entity.bean.CategoryBean;
import com.lairui.livetest1.entity.bean.LiveAddressBean;
import com.lairui.livetest1.module.three_module.activity.LivePrepareActivity;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.base.Request;
import com.wanou.framelibrary.base.BasePresenterImpl;
import com.wanou.framelibrary.bean.GeneralResult;
import com.wanou.framelibrary.bean.SimpleResponse;
import com.wanou.framelibrary.okgoutil.CustomizeStringCallback;
import com.wanou.framelibrary.okgoutil.OkGoUtils;

import java.lang.reflect.Type;
import java.util.List;

public class LivePreparePresenter extends BasePresenterImpl<LivePrepareActivity> {
    public void getCategoryList(HttpParams httpParams) {
        OkGoUtils.postRequest(AppConstant.BASE_URL, "categoryList", httpParams, new CustomizeStringCallback() {
            @Override
            public Type getResultType() {
                return new TypeToken<GeneralResult<List<CategoryBean>>>() {
                }.getType();
            }

            @Override
            public void onRequestSuccess(GeneralResult generalResult) {
                List<CategoryBean> categoryListBean = (List<CategoryBean>) generalResult.data;
                mPresenterView.setCategorySuccess(categoryListBean);
            }

            @Override
            public void onRequestError(SimpleResponse simpleResponse) {
                mPresenterView.setCategoryError();
            }

            @Override
            public void onRequestStart(Request<String, ? extends Request> request) {

            }

            @Override
            public void onRequestFinish() {

            }
        });
    }

    public void getPushAddress(HttpParams httpParams) {
        OkGoUtils.postRequest(AppConstant.BASE_URL, "pushaddress", httpParams, new CustomizeStringCallback() {
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
