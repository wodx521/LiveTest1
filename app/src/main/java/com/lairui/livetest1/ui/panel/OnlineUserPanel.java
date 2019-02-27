package com.lairui.livetest1.ui.panel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.ui.adapter.MemberAdapter;
import com.lairui.livetest1.utils.ChatroomKit;
import com.lairui.livetest1.utils.DataInterface;

/**
 * Created by duanliuyi on 2018/5/18.
 */

public class OnlineUserPanel extends LinearLayout {

    private ListView lvUser;
    private MemberAdapter adapter;

    public OnlineUserPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(final Context context) {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_online_user, this);
        lvUser = findViewById(R.id.lv_online_user);
        adapter = new MemberAdapter(context, DataInterface.getUserList(ChatroomKit.getCurrentRoomId()), false);
        lvUser.setAdapter(adapter);

    }
}
