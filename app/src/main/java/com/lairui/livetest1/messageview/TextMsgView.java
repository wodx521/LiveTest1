package com.lairui.livetest1.messageview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.ui.panel.EmojiManager;
import com.lairui.livetest1.utils.DataInterface;

import io.rong.imlib.model.MessageContent;
import io.rong.message.TextMessage;

public class TextMsgView extends BaseMsgView {

    private TextView username;
    private TextView msgText;

    public TextMsgView(Context context) {
        super(context);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.msg_text_view, this);
        username = (TextView) view.findViewById(R.id.username);
        msgText = (TextView) view.findViewById(R.id.msg_text);
    }

    @Override
    public void setContent(MessageContent msgContent, String senderUserId) {
        TextMessage msg = (TextMessage) msgContent;
        String name = "";
        if (DataInterface.getUserInfo(senderUserId) != null) {
            name = DataInterface.getUserInfo(senderUserId).getName();
        } else {
            name = senderUserId;
        }
        username.setText(name + ": ");
        msgText.setText(EmojiManager.parse(msg.getContent(), msgText.getTextSize()));
        msgText.setText(msg.getContent());

    }
}
