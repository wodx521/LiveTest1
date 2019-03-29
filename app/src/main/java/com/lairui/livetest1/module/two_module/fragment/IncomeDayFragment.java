package com.lairui.livetest1.module.two_module.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.BitmapDrawableTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.lairui.livetest1.MyApplication;
import com.lairui.livetest1.R;
import com.lairui.livetest1.entity.bean.RankingBean;
import com.lairui.livetest1.module.two_module.adapter.RankingAdapter;
import com.lairui.livetest1.module.two_module.presenter.IncomeDayPresenter;
import com.lairui.livetest1.ui.activity.LoginActivity;
import com.lairui.livetest1.ui.panel.CircleImageView;
import com.lairui.livetest1.utils.AlphaFilter;
import com.lzy.okgo.model.HttpParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wanou.framelibrary.base.BaseMvpFragment;
import com.wanou.framelibrary.bean.SimpleResponse;
import com.wanou.framelibrary.glidetools.GlideApp;
import com.wanou.framelibrary.manager.ActivityManage;
import com.wanou.framelibrary.utils.SpUtils;
import com.wanou.framelibrary.utils.UiTools;

import java.util.ArrayList;
import java.util.List;

public class IncomeDayFragment extends BaseMvpFragment<IncomeDayPresenter> {
    private SmartRefreshLayout srlRefresh;
    private RecyclerView rvRanking;
    private ConstraintLayout clLoading;
    private ConstraintLayout clError;
    private ConstraintLayout clEmpty;
    private ConstraintLayout constraintLayout21;
    private ImageView ivSecondIcon;
    private TextView tvSecondUser;
    private ImageView ivSecondGender;
    private ImageView ivSecondLevel;
    private ImageView ivFirstIcon;
    private TextView tvFirstUser;
    private ImageView ivFirstGender;
    private ImageView ivFirstLevel;
    private ImageView ivThirdIcon;
    private TextView tvThirdUser;
    private ImageView ivThirdGender;
    private ImageView ivThirdLevel;
    private HttpParams httpParams = new HttpParams();
    private int page = 1;
    private List<RankingBean.ListBean> tempList = new ArrayList<>();
    private RankingAdapter rankingAdapter;

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
        String token = (String) SpUtils.get("token", "");
        httpParams.put("operate", "ranklistGroup-getList");
        httpParams.put("type", "1");
        httpParams.put("way", "1");
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
        if (list.size() > 3) {
            List<RankingBean.ListBean> listBeans = list.subList(0, 3);
            List<RankingBean.ListBean> listBeans1 = list.subList(3, list.size());
            tempList.addAll(listBeans1);
            for (int i = 0; i < listBeans.size(); i++) {
                RankingBean.ListBean.UidBean uid = listBeans.get(i).getUid();
                if (i == 0) {
                    tvFirstUser.setText(uid.getNickname());
                    String sex = uid.getSex();
                    if ("男".equals(sex)) {
                        ivFirstGender.setSelected(false);
                    }else{
                        ivFirstGender.setSelected(true);
                    }
                }
                if (i == 1) {
                    tvSecondUser.setText(uid.getNickname());
                    String sex = uid.getSex();
                    if ("男".equals(sex)) {
                        ivSecondGender.setSelected(false);
                    }else{
                        ivSecondGender.setSelected(true);
                    }
                }
                if (i == 2) {
                    tvThirdUser.setText(uid.getNickname());
                    String sex = uid.getSex();
                    if ("男".equals(sex)) {
                        ivThirdGender.setSelected(false);
                    }else{
                        ivThirdGender.setSelected(true);
                    }
                }
            }
        }




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
