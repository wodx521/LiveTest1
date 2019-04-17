package com.lairui.livetest1.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.wanou.framelibrary.utils.UiTools;

import java.util.ArrayList;
import java.util.List;

public class ExitLiveDialog {
    private static List<Dialog> dialogList = new ArrayList<>();
    private static OnConfirmClickListener mOnConfirmClickListener;

    public static void getDialog(Activity activity, String title, String content) {
        AlertDialog dialog = new AlertDialog.Builder(activity).create();
        dialogList.add(dialog);
        View view = View.inflate(UiTools.context, R.layout.exit_confirm, null);
        TextView textView = view.findViewById(R.id.textView);
        TextView textView1 = view.findViewById(R.id.textView1);
        TextView tvCancel = view.findViewById(R.id.tvCancel);
        TextView tvConfirm = view.findViewById(R.id.tvConfirm);
        if (UiTools.noEmpty(title)) {
            textView.setText(title);
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
        if (UiTools.noEmpty(content)) {
            textView1.setText(content);
            textView1.setVisibility(View.VISIBLE);
        } else {
            textView1.setVisibility(View.GONE);
        }

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnConfirmClickListener != null) {
                    dialog.dismiss();
                    mOnConfirmClickListener.confirmClickListener();
                }
            }
        });
        dialog.setView(view);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        int deviceWidth = UiTools.getDeviceWidth(activity);
        attributes.width = deviceWidth * 3 / 4;
        dialog.getWindow().setAttributes(attributes);
    }

    public static void setOnConfirmClickListener(OnConfirmClickListener onConfirmClickListener) {
        mOnConfirmClickListener = onConfirmClickListener;
    }

    public interface OnConfirmClickListener {
        void confirmClickListener();
    }
}
