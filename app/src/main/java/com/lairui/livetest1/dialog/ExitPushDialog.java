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

public class ExitPushDialog {
    private static List<Dialog> dialogList = new ArrayList<>();

    public static void getDialog(Activity activity) {
        AlertDialog dialog = new AlertDialog.Builder(activity).create();
        dialogList.add(dialog);
        View view = View.inflate(UiTools.context, R.layout.exit_confirm, null);
        TextView tvCancel = view.findViewById(R.id.tvCancel);
        TextView tvConfirm = view.findViewById(R.id.tvConfirm);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        dialog.setView(view);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        int deviceWidth = UiTools.getDeviceWidth(activity);
        attributes.width = deviceWidth * 2 / 3;
        dialog.getWindow().setAttributes(attributes);
    }
}
