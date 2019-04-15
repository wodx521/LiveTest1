package com.lairui.livetest1.module.five_module.presenter;

import com.google.gson.reflect.TypeToken;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.entity.bean.UserAccountInfo;
import com.lairui.livetest1.module.five_module.fragment.FiveMainFragment;
import com.lairui.livetest1.ui.activity.LoginActivity;
import com.lzy.okgo.request.base.Request;
import com.wanou.framelibrary.base.BasePresenterImpl;
import com.wanou.framelibrary.bean.GeneralResult;
import com.wanou.framelibrary.bean.SimpleResponse;
import com.wanou.framelibrary.manager.ActivityManage;
import com.wanou.framelibrary.okgoutil.CustomizeStringCallback;
import com.wanou.framelibrary.okgoutil.OkGoUtils;

import java.lang.reflect.Type;

public class FiveMainPresenter extends BasePresenterImpl<FiveMainFragment> {
    public void getUserInfo(String json) {
        OkGoUtils.postRequest(AppConstant.BASE_URL, "userInfo", json, new CustomizeStringCallback() {
            @Override
            public Type getResultType() {
                return new TypeToken<GeneralResult<UserAccountInfo>>() {
                }.getType();
            }

            @Override
            public void onRequestSuccess(GeneralResult generalResult) {
                UserAccountInfo userInfo = (UserAccountInfo) generalResult.data;
                mPresenterView.setUserInfo(userInfo);
            }

            @Override
            public void onRequestError(SimpleResponse simpleResponse) {
                if (simpleResponse != null) {
                    if (simpleResponse.code == -1) {
                        mPresenterView.startActivity(mPresenterView, null, LoginActivity.class);
                        ActivityManage.getInstance().finishAll();
                    }
                } else {
                    mPresenterView.setUserInfoError(json);
                }
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
