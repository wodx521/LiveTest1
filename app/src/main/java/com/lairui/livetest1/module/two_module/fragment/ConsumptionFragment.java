package com.lairui.livetest1.module.two_module.fragment;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lairui.livetest1.R;
import com.lairui.livetest1.entity.bean.RankingBean;
import com.lairui.livetest1.module.two_module.adapter.RankingAdapter;
import com.lairui.livetest1.module.two_module.presenter.ConsumptionPresenter;
import com.lairui.livetest1.ui.activity.LoginActivity;
import com.lzy.okgo.model.HttpParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wanou.framelibrary.base.BaseMvpFragment;
import com.wanou.framelibrary.bean.SimpleResponse;
import com.wanou.framelibrary.manager.ActivityManage;
import com.wanou.framelibrary.utils.SpUtils;
import com.wanou.framelibrary.utils.UiTools;

import java.util.ArrayList;
import java.util.List;

public class ConsumptionFragment extends BaseMvpFragment<ConsumptionPresenter> {
    private TabLayout tlRankingDetail;
    private SmartRefreshLayout srlRefresh;
    private RecyclerView rvRanking;
    private ConstraintLayout clLoading;
    private ConstraintLayout clError;
    private ConstraintLayout clEmpty;
    private HttpParams httpParams = new HttpParams();
    private int page = 0;
    private List<RankingBean.ListBean> tempList = new ArrayList<>();
    private RankingAdapter rankingAdapter;
   private long mTime;;

    @Override
    protected ConsumptionPresenter getPresenter() {
        return new ConsumptionPresenter();
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_consumption;
    }

    @Override
    protected void initView(View view) {
        tlRankingDetail = view.findViewById(R.id.tlRankingDetail);
        srlRefresh = view.findViewById(R.id.srlRefresh);
        rvRanking = view.findViewById(R.id.rvRanking);
        clLoading = view.findViewById(R.id.clLoading);
        clError = view.findViewById(R.id.clError);
        clEmpty = view.findViewById(R.id.clEmpty);

        viewVisible(clLoading);
        viewGone(clEmpty, clError, srlRefresh);
    }

    @Override
    protected void initData() {
        rankingAdapter = new RankingAdapter(getActivity());
        rvRanking.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        rvRanking.setAdapter(rankingAdapter);
        String[] stringArray = UiTools.getStringArray(R.array.RankingCategory);
        for (String tabContent : stringArray) {
            tlRankingDetail.addTab(tlRankingDetail.newTab().setText(tabContent));
        }

        tlRankingDetail.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                long l = System.currentTimeMillis();
                if ((l - mTime) > 1000) {
                    mTime = l;
                    int position = tab.getPosition();
                    tempList.clear();
                    getIncomeList(position, 0);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                long l = System.currentTimeMillis();
                if ((l - mTime) > 1000) {
                    mTime = l;
                    int position = tab.getPosition();
                    tempList.clear();
                    getIncomeList(position, 0);
                }
            }
        });


        srlRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page += 1;
                getIncomeList(tlRankingDetail.getSelectedTabPosition(), page);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 0;
                tempList.clear();
                getIncomeList(tlRankingDetail.getSelectedTabPosition(), page);
            }
        });
    }

    private void getIncomeList(int tabPosition, int page) {
        httpParams.clear();
        String token = (String) SpUtils.get("token", "");
        httpParams.put("operate", "ranklistGroup-getList");
        httpParams.put("type", tabPosition + 1);
        httpParams.put("way", "2");
        httpParams.put("page", page);
        httpParams.put("token", token);
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

    @Override
    public void onResume() {
        super.onResume();
        if (!isHidden() && getUserVisibleHint()) {
            tlRankingDetail.getTabAt(tlRankingDetail.getSelectedTabPosition()).select();
        }
    }

    public void setRankingError(SimpleResponse simpleResponse, HttpParams httpParams) {
        if (simpleResponse != null) {
            if (simpleResponse.code == -1) {
                startActivity(ConsumptionFragment.this, null, LoginActivity.class);
                SpUtils.put("token","");
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
}
