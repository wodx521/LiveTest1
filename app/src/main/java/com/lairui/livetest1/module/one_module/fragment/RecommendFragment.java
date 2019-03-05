package com.lairui.livetest1.module.one_module.fragment;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.lairui.livetest1.R;
import com.lairui.livetest1.entity.bean.LiveListBean;
import com.lairui.livetest1.entity.bean.LiveRoomBean;
import com.lairui.livetest1.module.one_module.activity.LiveShowActivity;
import com.lairui.livetest1.module.one_module.adapter.LiveListAdapter;
import com.lairui.livetest1.module.one_module.presenter.RecommendPresenter;
import com.lairui.livetest1.utils.ChatroomKit;
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

public class RecommendFragment extends BaseMvpFragment<RecommendPresenter> {
    private RecyclerView rvLiveList;
    private ConstraintLayout clError;
    private Button btRetry;
    private SmartRefreshLayout srlRefresh;
    private LiveListAdapter liveListAdapter;
    private Bundle bundle = new Bundle();
    private HttpParams httpParams = new HttpParams();
    private List<LiveRoomBean> tempLiveRoom = new ArrayList<>();

    @Override
    protected RecommendPresenter getPresenter() {
        return new RecommendPresenter();
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void initView(View view) {
        rvLiveList = view.findViewById(R.id.rvLiveList);
        clError = view.findViewById(R.id.clError);
        btRetry = view.findViewById(R.id.bt_retry);
        srlRefresh = view.findViewById(R.id.srlRefresh);

        viewGone(srlRefresh, clError);
    }

    @Override
    protected void initData() {
        tempLiveRoom.clear();
        liveListAdapter = new LiveListAdapter(getActivity());
        rvLiveList.setAdapter(liveListAdapter);
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

    @Override
    protected void isHiddenListener(boolean hidden) {

    }

    public void setLiveList(LiveListBean liveListBean) {
        viewGone(clError);
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
        liveListAdapter.setData(tempLiveRoom);
        liveListAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                if (RongIMClient.getInstance().getCurrentConnectionStatus() == RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED) {
                    bundle.clear();
                    LiveRoomBean liveRoomBean = tempLiveRoom.get(position);
                    bundle.putParcelable("liveInfo",liveRoomBean);
                    startActivity(RecommendFragment.this, bundle, LiveShowActivity.class);
                } else {
                    UiTools.showToast("未连接");
                }
            }
        });
    }

    public void getLiveError(SimpleResponse simpleResponse) {
        viewGone(srlRefresh);
        viewVisible(clError);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ChatroomKit.logout();
    }
}
