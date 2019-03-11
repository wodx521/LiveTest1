package com.lairui.livetest1.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.ui.activity.LoginActivity;
import com.lairui.livetest1.utils.ChatroomKit;
import com.wanou.framelibrary.manager.ActivityManage;
import com.wanou.framelibrary.utils.SpUtils;
import com.wanou.framelibrary.utils.UiTools;

import java.util.ArrayList;
import java.util.List;

public class ExitNoticeDialog {
    private static List<Dialog> dialogList = new ArrayList<>();

    public static void getDialog(Activity activity, String notice, String notice1) {
        int deviceWidth = UiTools.getDeviceWidth(activity);

        PopupWindow popupWindow = new PopupWindow(deviceWidth * 2 / 3, ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = UiTools.parseLayout(R.layout.exit_confirm, null);
        TextView textView = view.findViewById(R.id.textView);
        TextView textView1 = view.findViewById(R.id.textView1);
        TextView tvCancel = view.findViewById(R.id.tvCancel);
        TextView tvConfirm = view.findViewById(R.id.tvConfirm);
        textView.setText(notice);
        textView1.setText(notice1);
        popupWindow.setContentView(view);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(UiTools.getDrawable(R.drawable.shape_choose_list));
        popupWindow.showAtLocation(textView, Gravity.CENTER,0,0);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                ActivityManage.getInstance().finishAll();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatroomKit.logout();
                SpUtils.put("token", "");
                ActivityManage.getInstance().finishAll();
                Intent intent = new Intent(activity, LoginActivity.class);
                activity.startActivity(intent);
            }
        });
    }
}
