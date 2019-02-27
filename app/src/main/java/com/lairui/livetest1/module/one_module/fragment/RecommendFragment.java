package com.lairui.livetest1.module.one_module.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lairui.livetest1.R;
import com.lairui.livetest1.entity.bean.ChatroomInfo;
import com.lairui.livetest1.module.one_module.activity.LiveShowActivity;
import com.lairui.livetest1.module.one_module.adapter.LiveListAdapter;
import com.lairui.livetest1.module.one_module.presenter.RecommendPresenter;
import com.lairui.livetest1.utils.DataInterface;
import com.wanou.framelibrary.base.BaseMvpFragment;
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;

import java.util.ArrayList;

public class RecommendFragment extends BaseMvpFragment<RecommendPresenter> {
    private RecyclerView rvLiveList;
    private LiveListAdapter liveListAdapter;
    private Bundle bundle = new Bundle();

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

    }

    @Override
    protected void initData() {

        ArrayList<ChatroomInfo> chatRoomList = DataInterface.getChatroomList();

        liveListAdapter = new LiveListAdapter(getActivity());
        rvLiveList.setAdapter(liveListAdapter);
        liveListAdapter.setData(chatRoomList);


        liveListAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                bundle.clear();
                ChatroomInfo chatroomInfo = chatRoomList.get(position);

                bundle.putString("liveId", chatroomInfo.getLiveId());
                startActivity(RecommendFragment.this, null, LiveShowActivity.class);
            }
        });
    }

    @Override
    protected void isHiddenListener(boolean hidden) {

    }
}
