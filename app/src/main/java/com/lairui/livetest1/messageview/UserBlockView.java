package com.lairui.livetest1.messageview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.message.ChatroomUserBlock;
import com.lairui.livetest1.utils.DataInterface;

import io.rong.imlib.model.MessageContent;

/**
 * Created by duanliuyi on 2018/6/20.
 */

public class UserBlockView extends BaseMsgView {

    private TextView tvInfo;

    public UserBlockView(Context context) {
        super(context);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.msg_system_view, this);
        tvInfo = (TextView) view.findViewById(R.id.tv_system_info);
    }

    @Override
    public void setContent(MessageContent msgContent, String senderUserId) {
        if (msgContent instanceof ChatroomUserBlock) {
            String id = ((ChatroomUserBlock) msgContent).getId();
            String name = "";
            if (DataInterface.getUserInfo(id) != null) {
                name = DataInterface.getUserInfo(id).getName();
            } else {
                name = id;
            }
            tvInfo.setText("系统通知  " + name + "被踢出聊天室");
        }

    }
}
