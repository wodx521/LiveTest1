package com.lairui.livetest1.module.two_module.fragment;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lairui.livetest1.R;
import com.lairui.livetest1.entity.bean.RankingBean;
import com.lairui.livetest1.module.two_module.adapter.RankingAdapter;
import com.lairui.livetest1.module.two_module.adapter.RankingAdapter1;
import com.lairui.livetest1.module.two_module.presenter.IncomeTotalPresenter;
import com.lairui.livetest1.ui.activity.LoginActivity;
import com.lairui.livetest1.ui.panel.CircleImageView;
import com.lzy.okgo.model.HttpParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wanou.framelibrary.base.BaseMvpFragment;
import com.wanou.framelibrary.bean.SimpleResponse;
import com.wanou.framelibrary.manager.ActivityManage;
import com.wanou.framelibrary.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

public class IncomeTotalFragment extends BaseMvpFragment<IncomeTotalPresenter> {
    private SmartRefreshLayout srlRefresh;
    private CircleImageView civFirst;
    private CircleImageView civSecond;
    private CircleImageView civThird;
    private RecyclerView rvRanking;
    private ConstraintLayout clLoading;
    private ConstraintLayout clError;
    private ConstraintLayout clEmpty;
    private HttpParams httpParams = new HttpParams();
    private int page = 1;
    private List<RankingBean.ListBean> tempList = new ArrayList<>();
    private RankingAdapter rankingAdapter;

    @Override
    protected IncomeTotalPresenter getPresenter() {
        return new IncomeTotalPresenter();
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
        rankingAdapter = new RankingAdapter(getActivity());
        rvRanking.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        rvRanking.setAdapter(rankingAdapter);

        srlRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page += 1;
                getIncomeList(page);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 1;
                tempList.clear();
                getIncomeList(page);
            }
        });
    }

    private void getIncomeList(int page) {
        httpParams.clear();
        httpParams.put("operate", "ranklistGroup-getList");
        httpParams.put("type", "3");
        httpParams.put("way", "1");
        httpParams.put("page", page);
        mPresenter.getRankingList(httpParams);
    }


    public void setRankingBean(RankingBean rankingBean) {
        viewVisible(srlRefresh);
        viewGone(clEmpty, clError, clLoading);
        String pageNum = rankingBean.getPageNum();
        int totalPage = Integer.parseInt(pageNum);
        srlRefresh.setEnableLoadMore(page < totalPage);
        List<RankingBean.ListBean> list = rankingBean.getList();
        tempList.addAll(list);
        rankingAdapter.setList(tempList);
        if (tempList != null && tempList.size() > 0) {
            viewVisible(rvRanking);
            viewGone(clEmpty);
        } else {
            viewVisible(clEmpty);
            viewGone(rvRanking);
        }
    }

    public void setRankingError(SimpleResponse simpleResponse, HttpParams httpParams) {
        if (simpleResponse != null) {
            if (simpleResponse.code == -1) {
                startActivity(IncomeTotalFragment.this, null, LoginActivity.class);
                ActivityManage.getInstance().finishAll();
            }
        } else {
            viewVisible(clError);
            viewGone(clEmpty, srlRefresh, clLoading);
            clError.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewVisible(clLoading);
                    viewGone(clEmpty, srlRefresh, clError);
                    mPresenter.getRankingList(httpParams);
                }
            });
        }
    }

    @Override
    public void onResume() {
        if (getUserVisibleHint()) {
            tempList.clear();
            getIncomeList(page);
        }
        super.onResume();
    }
}
