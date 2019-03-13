package com.lairui.livetest1.module.two_module.presenter;

import com.google.gson.reflect.TypeToken;
import com.lairui.livetest1.R;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.entity.bean.RankingBean;
import com.lairui.livetest1.module.two_module.fragment.IncomeFragment;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.base.Request;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wanou.framelibrary.base.BasePresenterImpl;
import com.wanou.framelibrary.bean.GeneralResult;
import com.wanou.framelibrary.bean.SimpleResponse;
import com.wanou.framelibrary.okgoutil.CustomizeStringCallback;
import com.wanou.framelibrary.okgoutil.OkGoUtils;

import java.lang.reflect.Type;

public class IncomePresenter extends BasePresenterImpl<IncomeFragment> {

    private SmartRefreshLayout srlRefresh;

    public void getRankingList(HttpParams httpParams) {
        OkGoUtils.postRequest(AppConstant.BASE_URL, "RankingList", httpParams, new CustomizeStringCallback() {
            @Override
            public Type getResultType() {
                return new TypeToken<GeneralResult<RankingBean>>() {
                }.getType();
            }

            @Override
            public void onRequestSuccess(GeneralResult generalResult) {
                RankingBean rankingBean = (RankingBean) generalResult.data;
                mPresenterView.setRankingBean(rankingBean);
            }

            @Override
            public void onRequestError(SimpleResponse simpleResponse) {
                mPresenterView.setRankingError(simpleResponse, httpParams);
            }

            @Override
            public void onRequestStart(Request<String, ? extends Request> request) {
                srlRefresh = mPresenterView.getView().findViewById(R.id.srlRefresh);
            }

            @Override
            public void onRequestFinish() {
                if (srlRefresh.isLoading() || srlRefresh.isRefreshing()) {
                    srlRefresh.finishRefresh();
                    srlRefresh.finishLoadMore();
                }
            }
        });
    }
}
