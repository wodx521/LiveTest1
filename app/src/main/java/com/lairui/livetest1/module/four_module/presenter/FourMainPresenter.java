package com.lairui.livetest1.module.four_module.presenter;

import com.google.gson.reflect.TypeToken;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.entity.bean.VideoBean;
import com.lairui.livetest1.module.four_module.FourMainFragment;
import com.lairui.livetest1.ui.activity.LoginActivity;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.base.Request;
import com.wanou.framelibrary.base.BasePresenterImpl;
import com.wanou.framelibrary.bean.GeneralResult;
import com.wanou.framelibrary.bean.SimpleResponse;
import com.wanou.framelibrary.manager.ActivityManage;
import com.wanou.framelibrary.okgoutil.CustomizeStringCallback;
import com.wanou.framelibrary.okgoutil.OkGoUtils;
import com.wanou.framelibrary.utils.SpUtils;

import java.lang.reflect.Type;
import java.util.List;

public class FourMainPresenter extends BasePresenterImpl<FourMainFragment> {
    public void getVideoList(HttpParams httpParams) {
        OkGoUtils.postRequest(AppConstant.BASE_URL, "videoList", httpParams, new CustomizeStringCallback() {
            @Override
            public Type getResultType() {
                return new TypeToken<GeneralResult<List<VideoBean>>>() {
                }.getType();
            }

            @Override
            public void onRequestSuccess(GeneralResult generalResult) {
                List<VideoBean> videoBeanList = (List<VideoBean>) generalResult.data;
                mPresenterView.setVideoSuccess(videoBeanList);
            }

            @Override
            public void onRequestError(SimpleResponse simpleResponse) {
                if (simpleResponse != null) {
                    if (simpleResponse.code == -1) {
                        mPresenterView.startActivity(mPresenterView, null, LoginActivity.class);
                        SpUtils.put("token", "");
                        ActivityManage.getInstance().finishAll();
                    }
                } else {
                    mPresenterView.setVideoError();
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
