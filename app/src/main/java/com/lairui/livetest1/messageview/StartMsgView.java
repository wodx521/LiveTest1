package com.lairui.livetest1.messageview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.message.ChatroomStart;
import com.lairui.livetest1.utils.CommonUtils;

import io.rong.imlib.model.MessageContent;

/**
 * Created by duanliuyi on 2018/6/20.
 */

public class StartMsgView extends BaseMsgView {

    private TextView tvInfo;

    public StartMsgView(Context context) {
        super(context);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.msg_system_view, this);
        tvInfo = (TextView) view.findViewById(R.id.tv_system_info);
    }

    @Override
    public void setContent(MessageContent msgContent, String senderUserId) {
        if (msgContent instanceof ChatroomStart) {
            String time = ((ChatroomStart) msgContent).getTime();
            long timeLong = Long.valueOf(time);
            String timeString = CommonUtils.getDateToString(timeLong, "yyyy-MM-dd HH:mm:ss");

            tvInfo.setText("系统通知  " + timeString + "  开始直播");
        }

    }
}
