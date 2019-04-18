package com.lairui.livetest1.dialog;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.wanou.framelibrary.utils.UiTools;

public class MsgDialog {
    public static void getDialog(Activity activity, boolean isChecked) {
        AlertDialog dialog = new AlertDialog.Builder(activity).create();
        View view = View.inflate(UiTools.context, R.layout.layout_send_msg_view, null);
        Switch switchView = view.findViewById(R.id.switchView);
        EditText etMsgContent = view.findViewById(R.id.etMsgContent);
        TextView tvSend = view.findViewById(R.id.tvSend);
        // 设置开关起始值
        switchView.setChecked(isChecked);
        // 发送监听
        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSendClickListener != null) {
                    String msgContent = UiTools.getText(etMsgContent);
                    if (UiTools.noEmpty(msgContent)) {
                        mSendClickListener.onSendClickListener(msgContent);
                    } else {
                        UiTools.showToast(R.string.isMsgNull);
                    }
                }
            }
        });
        // 开关切换监听
        switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mSwitchChangeListener != null) {
                    mSwitchChangeListener.onSwitchChange(isChecked);
                }
            }
        });

        dialog.setView(view);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setWindowAnimations(R.style.anim_menu_bottombar);
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        attributes.width = UiTools.getDeviceWidth(activity);
        attributes.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(attributes);
    }

    interface SendClickListener {
        void onSendClickListener(String content);
    }

    interface SwitchChangeListener {
        void onSwitchChange(boolean isChecked);
    }

    static SendClickListener mSendClickListener;
    static SwitchChangeListener mSwitchChangeListener;

    public static void setmSwitchChangeListener(SwitchChangeListener mSwitchChangeListener) {
        MsgDialog.mSwitchChangeListener = mSwitchChangeListener;
    }

    public static void setmSendClickListener(SendClickListener mSendClickListener) {
        MsgDialog.mSendClickListener = mSendClickListener;
    }

}
