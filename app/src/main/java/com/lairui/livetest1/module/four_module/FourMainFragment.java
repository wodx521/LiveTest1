package com.lairui.livetest1.module.four_module;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.module.four_module.presenter.FourMainPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wanou.framelibrary.base.BaseMvpFragment;

public class FourMainFragment extends BaseMvpFragment<FourMainPresenter> {
    private ImageView ivLeft;
    private TextView tvToolbarTitle;
    private ImageView ivRight1;
    private SmartRefreshLayout srlRefresh;
    private RecyclerView rvVideoList;
    private ConstraintLayout clEmpty;
    private ConstraintLayout clError;
    private ConstraintLayout clLoading;

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

        viewGone(clEmpty,clError,srlRefresh);
        viewVisible(clLoading);
    }

    @Override
    protected void initData() {

    }

}
