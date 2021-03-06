package com.lairui.livetest1.module.four_module;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.entity.bean.VideoBean;
import com.lairui.livetest1.module.four_module.activity.VideoDetailActivity;
import com.lairui.livetest1.module.four_module.adapter.VideoAdapter;
import com.lairui.livetest1.module.four_module.presenter.FourMainPresenter;
import com.lairui.livetest1.ui.activity.SearchActivity;
import com.lzy.okgo.model.HttpParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wanou.framelibrary.base.BaseMvpFragment;
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;
import com.wanou.framelibrary.utils.SpUtils;

import java.util.List;

public class FourMainFragment extends BaseMvpFragment<FourMainPresenter> implements View.OnClickListener {
    private ImageView ivLeft;
    private TextView tvToolbarTitle;
    private ImageView ivRight1;
    private SmartRefreshLayout srlRefresh;
    private RecyclerView rvVideoList;
    private ConstraintLayout clEmpty;
    private ConstraintLayout clError;
    private ConstraintLayout clLoading;
    private VideoAdapter videoAdapter;
    private HttpParams httpParams = new HttpParams();
    private Bundle bundle = new Bundle();

    @Override
    protected FourMainPresenter getPresenter() {
        return new FourMainPresenter();
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_four;
    }

    @Override
    protected void initView(View view) {
        ivLeft = view.findViewById(R.id.ivLeft);
        tvToolbarTitle = view.findViewById(R.id.tvToolbarTitle);
        ivRight1 = view.findViewById(R.id.ivRight1);
        srlRefresh = view.findViewById(R.id.srlRefresh);
        rvVideoList = view.findViewById(R.id.rvVideoList);
        clEmpty = view.findViewById(R.id.clEmpty);
        clError = view.findViewById(R.id.clError);
        clLoading = view.findViewById(R.id.clLoading);
        srlRefresh.setEnableLoadMore(false);
        viewGone(clEmpty, clError, srlRefresh);
        viewVisible(clLoading, ivLeft, ivRight1);
        tvToolbarTitle.setText(R.string.smallVideo);
        ivLeft.setImageResource(R.drawable.search_black);
        ivRight1.setImageResource(R.drawable.notice);

        ivLeft.setOnClickListener(this);
        ivRight1.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        videoAdapter = new VideoAdapter(getActivity());
        rvVideoList.setAdapter(videoAdapter);


        srlRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getVideo();
            }
        });

        getVideo();
    }

    private void getVideo() {
        String token = (String) SpUtils.get("token", "");
        httpParams.clear();
        httpParams.put("operate", "videoGroup-videoList");
        httpParams.put("token", token);
        mPresenter.getVideoList(httpParams);
    }

    public void setVideoSuccess(List<VideoBean> videoBeanList) {
        viewGone(clEmpty, clError, clLoading);
        viewVisible(srlRefresh);
        videoAdapter.setData(videoBeanList);
        videoAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                bundle.clear();
                VideoBean videoBean = videoBeanList.get(position);
                bundle.putParcelable("videoBean", videoBean);
                startActivity(FourMainFragment.this, bundle, VideoDetailActivity.class);
            }
        });
    }

    public void setVideoError() {
        viewGone(clEmpty, clLoading, srlRefresh);
        viewVisible(clError);
        clError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVideo();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivLeft:
                startActivity(FourMainFragment.this, null, SearchActivity.class);
                break;
            case R.id.ivRight1:

                break;
            default:
        }
    }
}
