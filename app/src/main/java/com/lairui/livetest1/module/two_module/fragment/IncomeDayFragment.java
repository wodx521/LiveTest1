package com.lairui.livetest1.module.two_module.fragment;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lairui.livetest1.MyApplication;
import com.lairui.livetest1.R;
import com.lairui.livetest1.app_constant.AppConstant;
import com.lairui.livetest1.entity.bean.RankingBean;
import com.lairui.livetest1.entity.jsonparam.RankBeanParams;
import com.lairui.livetest1.module.two_module.adapter.RankingAdapter;
import com.lairui.livetest1.module.two_module.presenter.IncomeDayPresenter;
import com.lairui.livetest1.ui.activity.LoginActivity;
import com.lairui.livetest1.ui.panel.CircleImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wanou.framelibrary.base.BaseMvpFragment;
import com.wanou.framelibrary.bean.SimpleResponse;
import com.wanou.framelibrary.glidetools.GlideApp;
import com.wanou.framelibrary.manager.ActivityManage;
import com.wanou.framelibrary.utils.GsonUtils;
import com.wanou.framelibrary.utils.UiTools;

import java.util.ArrayList;
import java.util.List;

public class IncomeDayFragment extends BaseMvpFragment<IncomeDayPresenter> {
    private SmartRefreshLayout srlRefresh;
    private RecyclerView rvRanking;
    private ConstraintLayout clLoading, clError, constraintRankTitle, clEmpty;
    private CircleImageView civFirst, civSecond, civThird;
    private ImageView ivSecondGender, ivSecondLevel, ivFirstGender, ivThirdGender,
            ivFirstLevel, ivThirdLevel;
    private TextView tvSecondName, tvSecondGet, tvFirstName, tvFirstGet, tvThirdName,
            tvThirdGet;
    private ConstraintLayout clFirst, clSecond, clThird;

    private int page = 1;
    private List<RankingBean.ListBean> tempList = new ArrayList<>();
    private RankingAdapter rankingAdapter;
    private RankBeanParams rankBeanParams = new RankBeanParams();

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
        constraintRankTitle = view.findViewById(R.id.constraintRankTitle);
        rvRanking = view.findViewById(R.id.rvRanking);
        clLoading = view.findViewById(R.id.clLoading);
        clError = view.findViewById(R.id.clError);
        clEmpty = view.findViewById(R.id.clEmpty);
        civFirst = view.findViewById(R.id.civFirst);
        civSecond = view.findViewById(R.id.civSecond);
        civThird = view.findViewById(R.id.civThird);
        tvSecondName = view.findViewById(R.id.tvSecondName);
        ivSecondGender = view.findViewById(R.id.ivSecondGender);
        ivSecondLevel = view.findViewById(R.id.ivSecondLevel);
        tvSecondGet = view.findViewById(R.id.tvSecondGet);
        tvFirstName = view.findViewById(R.id.tvFirstName);
        ivFirstGender = view.findViewById(R.id.ivFirstGender);
        ivFirstLevel = view.findViewById(R.id.ivFirstLevel);
        tvFirstGet = view.findViewById(R.id.tvFirstGet);
        tvThirdName = view.findViewById(R.id.tvThirdName);
        ivThirdGender = view.findViewById(R.id.ivThirdGender);
        ivThirdLevel = view.findViewById(R.id.ivThirdLevel);
        tvThirdGet = view.findViewById(R.id.tvThirdGet);
        clFirst = view.findViewById(R.id.clFirst);
        clSecond = view.findViewById(R.id.clSecond);
        clThird = view.findViewById(R.id.clThird);


        viewGone(clError, clEmpty, constraintRankTitle, clFirst, clSecond, clThird);
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
        rankBeanParams.type = "1";
        rankBeanParams.way = "1";
        rankBeanParams.page = page + "";

        mPresenter.getRankingList(GsonUtils.toJson(rankBeanParams));
    }

    public void setRankingBean(RankingBean rankingBean) {
        viewVisible(srlRefresh);
        viewGone(clEmpty, clError, clLoading);
        String pageNum = rankingBean.getPageNum();
        int totalPage = Integer.parseInt(pageNum);
        srlRefresh.setEnableLoadMore(page < totalPage);
        List<RankingBean.ListBean> list = rankingBean.getList();
        tempList.addAll(list);
        if (tempList != null && tempList.size() > 0) {
            if (tempList.size() > 0) {
                RankingBean.ListBean listBean = tempList.get(0);
                String total = listBean.getTotal();
                RankingBean.ListBean.UidBean uid = listBean.getUid();
                tvFirstName.setText(uid.getNickname());
                String portrait = uid.getPortrait();
                String sex = uid.getSex();
                GlideApp.with(MyApplication.getContext())
                        .load(AppConstant.BASE_URL + portrait)
                        .placeholder(R.drawable.chatroom_01)
                        .error(R.drawable.chatroom_01)
                        .into(civFirst);
                if ("男".equals(sex)) {
                    ivFirstGender.setImageResource(R.drawable.selected_male);
                } else {
                    ivFirstGender.setImageResource(R.drawable.selected_female);
                }
                tvFirstGet.setText(UiTools.getString(R.string.earnings).replace("%s", UiTools.formatNumber(total, UiTools.getString(R.string.patternTwoDigits))));
                viewVisible(clFirst);
            }

            if (tempList.size() > 1) {
                RankingBean.ListBean listBean = tempList.get(1);
                String total = listBean.getTotal();
                RankingBean.ListBean.UidBean uidSecond = listBean.getUid();
                tvSecondName.setText(uidSecond.getNickname());
                GlideApp.with(MyApplication.getContext())
                        .load(AppConstant.BASE_URL + uidSecond.getPortrait())
                        .placeholder(R.drawable.chatroom_01)
                        .error(R.drawable.chatroom_01)
                        .into(civSecond);
                if ("男".equals(uidSecond.getSex())) {
                    ivSecondGender.setImageResource(R.drawable.selected_male);
                } else {
                    ivSecondGender.setImageResource(R.drawable.selected_female);
                }
                tvSecondGet.setText(UiTools.getString(R.string.earnings).replace("%s", UiTools.formatNumber(total, UiTools.getString(R.string.patternTwoDigits))));
                viewVisible(clSecond);
            }

            if (tempList.size() > 2) {
                RankingBean.ListBean listBean = tempList.get(2);
                String total = listBean.getTotal();
                RankingBean.ListBean.UidBean uidSecond = listBean.getUid();
                tvThirdName.setText(uidSecond.getNickname());
                GlideApp.with(MyApplication.getContext())
                        .load(AppConstant.BASE_URL + uidSecond.getPortrait())
                        .placeholder(R.drawable.chatroom_01)
                        .error(R.drawable.chatroom_01)
                        .into(civThird);
                if ("男".equals(uidSecond.getSex())) {
                    ivThirdGender.setImageResource(R.drawable.selected_male);
                } else {
                    ivThirdGender.setImageResource(R.drawable.selected_female);
                }
                tvThirdGet.setText(UiTools.getString(R.string.earnings).replace("%s", UiTools.formatNumber(total, UiTools.getString(R.string.patternTwoDigits))));
                viewVisible(clThird);
            }
            if (tempList.size() > 3) {
                rankingAdapter.setList(tempList.subList(3, tempList.size()));
            }
            viewVisible(rvRanking, constraintRankTitle);
            viewGone(clEmpty);
        } else {
            viewVisible(clEmpty);
            viewGone(rvRanking, constraintRankTitle);
        }
    }

    public void setRankingError(SimpleResponse simpleResponse, String httpParams) {
        if (simpleResponse != null) {
            if (simpleResponse.code == -1) {
                startActivity(IncomeDayFragment.this, null, LoginActivity.class);
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
