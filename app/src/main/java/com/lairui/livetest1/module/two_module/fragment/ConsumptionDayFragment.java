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
import com.lairui.livetest1.module.two_module.presenter.ConsumptionDayPresenter;
import com.lairui.livetest1.ui.activity.LoginActivity;
import com.lairui.livetest1.ui.panel.CircleImageView;
import com.lzy.okgo.model.HttpParams;
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

public class ConsumptionDayFragment extends BaseMvpFragment<ConsumptionDayPresenter> {
    private SmartRefreshLayout srlRefresh;
    private RecyclerView rvRanking;
    private ConstraintLayout clLoading, clError, constraintRankTitle, clEmpty;
    private CircleImageView civFirst, civSecond, civThird;
    private ImageView ivSecondGender, ivSecondLevel, ivFirstGender, ivThirdGender,
            ivFirstLevel, ivThirdLevel;
    private TextView tvSecondName, tvSecondGet, tvFirstName, tvFirstGet, tvThirdName,
            tvThirdGet;
    private HttpParams httpParams = new HttpParams();
    private int page = 1;
    private List<RankingBean.ListBean> tempList = new ArrayList<>();
    private RankingAdapter rankingAdapter;
    private RankBeanParams rankBeanParams = new RankBeanParams();

    @Override
    protected ConsumptionDayPresenter getPresenter() {
        return new ConsumptionDayPresenter();
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

        viewGone(clError, clEmpty, constraintRankTitle);
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
        rankBeanParams.way = "2";
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
                tvFirstGet.setText(UiTools.getString(R.string.earnings).replace("%s", total));
            }

            if (tempList.size() > 1) {
                RankingBean.ListBean listBean = tempList.get(1);
                String totalSecond = listBean.getTotal();
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
                tvSecondGet.setText(UiTools.getString(R.string.earnings).replace("%s", totalSecond));
            }

            if (tempList.size() > 2) {
                RankingBean.ListBean listBean = tempList.get(2);
                String totalSecond = listBean.getTotal();
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
                tvThirdGet.setText(UiTools.getString(R.string.earnings).replace("%s", totalSecond));
            }
            if (tempList.size() > 3) {
                rankingAdapter.setList(tempList.subList(3, tempList.size()));
            }
            viewVisible(rvRanking);
            viewGone(clEmpty);
        } else {
            viewVisible(clEmpty);
            viewGone(rvRanking, constraintRankTitle);
        }
    }

    public void setRankingError(SimpleResponse simpleResponse, String httpParams) {
        if (simpleResponse != null) {
            if (simpleResponse.code == -1) {
                startActivity(ConsumptionDayFragment.this, null, LoginActivity.class);
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

//    //bmp原图(前景) bm资源图片(背景)
//    private void addFrameToImage(Bitmap bm) {
//        Bitmap drawBitmap = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
//        canvas = new Canvas(drawBitmap);
//        paint = new Paint();
//        canvas.drawBitmap(bmp, 0, 0, paint);
//        paint.setXfermode(new PorterDuffXfermode(android.
//                graphics.PorterDuff.Mode.LIGHTEN));
//        //对边框进行缩放
//        int w = bm.getWidth();
//        int h = bm.getHeight();
//        //缩放比 如果图片尺寸超过边框尺寸 会自动匹配
//        float scaleX = bmp.getWidth() * 1F / w;
//        float scaleY = bmp.getHeight() * 1F / h;
//        Matrix matrix = new Matrix();
//        matrix.postScale(scaleX, scaleY);   //缩放图片
//        Bitmap copyBitmap = Bitmap.createBitmap(bm, 0, 0, w, h, matrix, true);
//        canvas.drawBitmap(copyBitmap, 0, 0, paint);
//        imageShow.setImageBitmap(drawBitmap);
//    }
}
