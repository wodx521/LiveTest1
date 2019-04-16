package com.lairui.livetest1.module.three_module.presenter;

import com.google.gson.reflect.TypeToken;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.entity.bean.UploadResultBean;
import com.lairui.livetest1.module.three_module.activity.LiveApproveActivity;
import com.lairui.livetest1.ui.activity.LoginActivity;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.base.Request;
import com.wanou.framelibrary.base.BasePresenterImpl;
import com.wanou.framelibrary.bean.GeneralResult;
import com.wanou.framelibrary.bean.SimpleResponse;
import com.wanou.framelibrary.manager.ActivityManage;
import com.wanou.framelibrary.okgoutil.CustomizeStringCallback;
import com.wanou.framelibrary.okgoutil.OkGoUtils;
import com.wanou.framelibrary.utils.UiTools;
import com.wanou.framelibrary.weight.LoadingDialog;

import java.lang.reflect.Type;
import java.util.List;

public class LiveApprovePresenter extends BasePresenterImpl<LiveApproveActivity> {

    public void uploadImage(HttpParams httpParams, int requestCode) {
        OkGoUtils.postRequest(AppConstant.BASE_URL, "uploadImage", httpParams, new CustomizeStringCallback() {
            @Override
            public Type getResultType() {
                return new TypeToken<GeneralResult<UploadResultBean>>() {
                }.getType();
            }

            @Override
            public void onRequestSuccess(GeneralResult generalResult) {
                UploadResultBean uploadResultBean = (UploadResultBean) generalResult.data;
                mPresenterView.setUploadSuccess(uploadResultBean, requestCode);
            }

            @Override
            public void onRequestError(SimpleResponse simpleResponse) {
                if (simpleResponse != null) {
                    if (simpleResponse.code == -1) {
                        mPresenterView.startActivity(mPresenterView, null, LoginActivity.class);
                        ActivityManage.getInstance().finishAll();
                    }
                }
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

    public void applyApprove(String json) {
        OkGoUtils.postRequest(AppConstant.BASE_URL, "applyapprove", json, new CustomizeStringCallback() {
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
                mPresenterView.finish();
            }

            @Override
            public void onRequestError(SimpleResponse simpleResponse) {
                if (simpleResponse != null) {
                    if (simpleResponse.code == -1) {
                        mPresenterView.startActivity(mPresenterView, null, LoginActivity.class);
                        ActivityManage.getInstance().finishAll();
                    }
                }
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
