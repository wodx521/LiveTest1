package com.lairui.livetest1.dialog;

import android.app.Activity;
import android.content.DialogInterface;
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

                        mSendClickListener.onSendClickListener(msgContent, switchView.isChecked());
                    } else {
                        UiTools.showToast(R.string.isMsgNull);
                    }
                }
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mSendClickListener != null) {
                    mSendClickListener.onDismissChange();
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
        attributes.dimAmount = 0f;
        attributes.width = UiTools.getDeviceWidth(activity);
        attributes.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(attributes);
    }

    public interface SendClickListener {
        void onSendClickListener(String content, boolean isNormalMsg);

        void onDismissChange();
    }

    private static SendClickListener mSendClickListener;



    public static void setmSendClickListener(SendClickListener mSendClickListener) {
        MsgDialog.mSendClickListener = mSendClickListener;
    }

}
