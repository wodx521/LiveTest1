package com.lairui.livetest1.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;

import com.lairui.livetest1.R;
import com.lairui.livetest1.ui.adapter.ShareAdapter;
import com.wanou.framelibrary.utils.UiTools;

import java.util.ArrayList;
import java.util.List;

public class ShareDialog {
    private static List<Dialog> dialogList = new ArrayList<>();

    public static void getDialog(Activity activity) {
        AlertDialog dialog = new AlertDialog.Builder(activity).create();
        dialogList.add(dialog);
        View view = View.inflate(UiTools.context, R.layout.share, null);
        RecyclerView rvShare = view.findViewById(R.id.rvShare);
        ShareAdapter shareAdapter = new ShareAdapter(activity);
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
