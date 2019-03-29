package com.lairui.livetest1.presenter;

import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.lairui.livetest1.R;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.entity.bean.SearchListBean;
import com.lairui.livetest1.ui.activity.SearchActivity;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.base.Request;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wanou.framelibrary.base.BasePresenterImpl;
import com.wanou.framelibrary.bean.GeneralResult;
import com.wanou.framelibrary.bean.SimpleResponse;
import com.wanou.framelibrary.okgoutil.CustomizeStringCallback;
import com.wanou.framelibrary.okgoutil.OkGoUtils;
import com.wanou.framelibrary.utils.UiTools;
import com.wanou.framelibrary.weight.LoadingDialog;

import java.lang.reflect.Type;
import java.util.List;

public class SearchPresenter extends BasePresenterImpl<SearchActivity> {

    private SmartRefreshLayout srlRefresh;

    public void getSearchList(HttpParams httpParams) {
        OkGoUtils.postRequest(AppConstant.BASE_URL, "searchList", httpParams, new CustomizeStringCallback() {
            @Override
            public Type getResultType() {
                return new TypeToken<GeneralResult<SearchListBean>>() {
                }.getType();
            }

            @Override
            public void onRequestSuccess(GeneralResult generalResult) {
                SearchListBean searchListBean = (SearchListBean) generalResult.data;
                mPresenterView.setSearchSuccess(searchListBean);
            }

            @Override
            public void onRequestError(SimpleResponse simpleResponse) {
                mPresenterView.setSearchError(simpleResponse);
            }

            @Override
            public void onRequestStart(Request<String, ? extends Request> request) {
                srlRefresh = mPresenterView.findViewById(R.id.srlRefresh);
            }

            @Override
            public void onRequestFinish() {
                if (srlRefresh.isRefreshing() || srlRefresh.isLoading()) {
                    srlRefresh.finishRefresh();
                    srlRefresh.finishLoadMore();
                }
            }
        });
    }

    public void setAttention(String httpParams, View v) {
        OkGoUtils.postRequest(AppConstant.BASE_URL, "attention", httpParams, new CustomizeStringCallback() {
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
                v.setSelected(!v.isSelected());
            }

            @Override
            public void onRequestError(SimpleResponse simpleResponse) {
                mPresenterView.setAttentionError(simpleResponse);
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
