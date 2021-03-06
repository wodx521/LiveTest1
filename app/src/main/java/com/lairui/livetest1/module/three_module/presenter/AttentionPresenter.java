package com.lairui.livetest1.module.three_module.presenter;

import com.google.gson.reflect.TypeToken;
import com.lairui.livetest1.R;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.entity.bean.LiveListBean;
import com.lairui.livetest1.module.one_module.fragment.AttentionFragment;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.base.Request;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wanou.framelibrary.base.BasePresenterImpl;
import com.wanou.framelibrary.bean.GeneralResult;
import com.wanou.framelibrary.bean.SimpleResponse;
import com.wanou.framelibrary.okgoutil.CustomizeStringCallback;
import com.wanou.framelibrary.okgoutil.OkGoUtils;

import java.lang.reflect.Type;

public class AttentionPresenter extends BasePresenterImpl<AttentionFragment> {

    private SmartRefreshLayout srlRefresh;

    public void getLiveList(HttpParams httpParams) {
        OkGoUtils.postRequest(AppConstant.BASE_URL, "live_list", httpParams, new CustomizeStringCallback() {
            @Override
            public Type getResultType() {
                return new TypeToken<GeneralResult<LiveListBean>>() {
                }.getType();
            }

            @Override
            public void onRequestSuccess(GeneralResult generalResult) {
                LiveListBean liveListBean = (LiveListBean) generalResult.data;
                mPresenterView.setLiveList(liveListBean);
            }

            @Override
            public void onRequestError(SimpleResponse simpleResponse) {
                mPresenterView.getLiveError(simpleResponse);
            }

            @Override
            public void onRequestStart(Request<String, ? extends Request> request) {
                srlRefresh = mPresenterView.getView().findViewById(R.id.srlRefresh);

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
}
