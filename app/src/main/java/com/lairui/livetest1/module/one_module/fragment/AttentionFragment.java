package com.lairui.livetest1.module.one_module.fragment;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.entity.bean.LiveListBean;
import com.lairui.livetest1.entity.bean.LiveRoomBean;
import com.lairui.livetest1.module.one_module.activity.LiveShowActivity;
import com.lairui.livetest1.module.one_module.adapter.LiveAdapter;
import com.lairui.livetest1.module.three_module.presenter.AttentionPresenter;
import com.lairui.livetest1.utils.DataInterface;
import com.lzy.okgo.model.HttpParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wanou.framelibrary.base.BaseMvpFragment;
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;
import com.wanou.framelibrary.bean.SimpleResponse;
import com.wanou.framelibrary.utils.UiTools;

import java.util.ArrayList;
import java.util.List;

import io.rong.imlib.RongIMClient;

public class AttentionFragment extends BaseMvpFragment<AttentionPresenter> {
    private SmartRefreshLayout srlRefresh;
    private RecyclerView rvLiveList;
    private TextView tvErrorNet;
    private ConstraintLayout clErrorNet, clEmpty, clLoading;
    private HttpParams httpParams = new HttpParams();
    private List<LiveRoomBean> tempLiveRoom = new ArrayList<>();
    private Bundle bundle = new Bundle();
    private LiveAdapter liveAdapter;

    @Override
    protected AttentionPresenter getPresenter() {
        return new AttentionPresenter();
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_attention;
    }

    @Override
    protected void initView(View view) {
        srlRefresh = view.findViewById(R.id.srlRefresh);
        rvLiveList = view.findViewById(R.id.rvLiveList);
        clErrorNet = view.findViewById(R.id.clErrorNet);
        clEmpty = view.findViewById(R.id.clEmpty);
        clLoading = view.findViewById(R.id.clLoading);
        tvErrorNet = view.findViewById(R.id.tvErrorNet);
        viewGone(srlRefresh, clErrorNet, clEmpty);
        viewVisible(clLoading);

    }

    @Override
    protected void initData() {
        liveAdapter = new LiveAdapter(getActivity());
        rvLiveList.setAdapter(liveAdapter);
        tempLiveRoom.clear();
        getLiveRoom();

        // todo 正常使用需要删除
        DataInterface.initUserInfo();
        DataInterface.setLoginStatus(true);
    }

    private void getLiveRoom() {
        httpParams.clear();
        httpParams.put("operate", "roomGroup-list");
        mPresenter.getLiveList(httpParams);
    }

    public void setLiveList(LiveListBean liveListBean) {
        viewGone(clLoading, clErrorNet, clEmpty);
        viewVisible(srlRefresh);
        int lastPage = liveListBean.getLastPage();
        int currentPage = liveListBean.getCurrentPage();
        srlRefresh.setEnableLoadMore(currentPage < lastPage);
        srlRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getLiveRoom();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                tempLiveRoom.clear();
                getLiveRoom();
            }
        });
        List<LiveRoomBean> data = liveListBean.getData();
        tempLiveRoom.addAll(data);
        liveAdapter.setData(tempLiveRoom);
        if (tempLiveRoom != null && tempLiveRoom.size() > 0) {
            liveAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClickListener(View view, int position) {
                    if (RongIMClient.getInstance().getCurrentConnectionStatus() == RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED) {
                        bundle.clear();
                        LiveRoomBean liveRoomBean = tempLiveRoom.get(position);
                        bundle.putParcelable("liveInfo", liveRoomBean);
                        startActivity(AttentionFragment.this, bundle, LiveShowActivity.class);
                    } else {
                        UiTools.showToast("未连接");
                    }
                }
            });
        } else {
            viewVisible(clEmpty);
        }
    }

    public void getLiveError(SimpleResponse simpleResponse) {
        if (simpleResponse == null) {
            viewGone(clLoading, clEmpty, srlRefresh);
            viewVisible(clErrorNet);
            clErrorNet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewGone(clErrorNet, clEmpty, srlRefresh);
                    viewVisible(clLoading);
                    getLiveRoom();
                }
            });
        }
    }
}
