package com.lairui.livetest1.module.two_module.fragment;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lairui.livetest1.R;
import com.lairui.livetest1.entity.bean.RankingBean;
import com.lairui.livetest1.module.two_module.presenter.IncomeDayPresenter;
import com.lzy.okgo.model.HttpParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wanou.framelibrary.base.BaseMvpFragment;
import com.wanou.framelibrary.utils.SpUtils;

public class IncomeDayFragment extends BaseMvpFragment<IncomeDayPresenter> {
    private SmartRefreshLayout srlRefresh;
    private RecyclerView rvRanking;
    private ConstraintLayout clLoading;
    private ConstraintLayout clError;
    private ConstraintLayout clEmpty;
    private HttpParams httpParams = new HttpParams();
    private int page = 0;

    @Override
    protected IncomeDayPresenter getPresenter() {
        return new IncomeDayPresenter();
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_income_day;
    }

    @Override
    protected void initView(View view) {
        srlRefresh = view.findViewById(R.id.srlRefresh);
        rvRanking = view.findViewById(R.id.rvRanking);
        clLoading = view.findViewById(R.id.clLoading);
        clError = view.findViewById(R.id.clError);
        clEmpty = view.findViewById(R.id.clEmpty);
        viewGone(clError, clEmpty);
        viewVisible(clLoading);
    }

    @Override
    protected void initData() {
        String token = (String) SpUtils.get("token", "");
        httpParams.clear();
        httpParams.put("operate", "ranklistGroup-getList");
        httpParams.put("type", "1");
        httpParams.put("way", "1");
        httpParams.put("page", "1");
        httpParams.put("token", token);
        mPresenter.getRankingList(httpParams);
    }

    public void setRankingBean(RankingBean rankingBean) {
        String pageNum = rankingBean.getPageNum();
    }
}
