package com.lairui.livetest1.module.one_module.presenter;

import com.google.gson.reflect.TypeToken;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.entity.bean.CategoryBean;
import com.lairui.livetest1.module.one_module.fragment.FirstMainFragment;
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

public class FirstPresenter extends BasePresenterImpl<FirstMainFragment> {
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
                mPresenterView.setCategory(categoryListBean);
            }

            @Override
            public void onRequestError(SimpleResponse simpleResponse) {
                if (simpleResponse != null) {
                    if (simpleResponse.code == -1) {
                        mPresenterView.startActivity(mPresenterView, null, LoginActivity.class);
                        SpUtils.put("token","");
                        ActivityManage.getInstance().finishAll();
                    }
                }else{
                    mPresenterView.setCategoryError();
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
