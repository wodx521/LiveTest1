package com.lairui.livetest1.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lairui.livetest1.R;
import com.lairui.livetest1.entity.bean.ShareBean;
import com.lairui.livetest1.ui.adapter.ShareAdapter;
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;
import com.wanou.framelibrary.utils.UiTools;

import java.util.ArrayList;
import java.util.List;

import io.rong.imlib.filetransfer.QiniuRequest;

public class ShareDialog {
    static OnItemClickListener mOnItemClickListener;
    private static List<Dialog> dialogList = new ArrayList<>();

    public static void getDialog(Activity activity, List<ShareBean> shareBeanList) {
        AlertDialog dialog = new AlertDialog.Builder(activity).create();
        dialogList.add(dialog);
        View view = View.inflate(UiTools.context, R.layout.share, null);
        RecyclerView rvShare = view.findViewById(R.id.rvShare);
        ShareAdapter shareAdapter = new ShareAdapter(activity);
        rvShare.setAdapter(shareAdapter);
        shareAdapter.setShareBeanList(shareBeanList);
        shareAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.itemClickListener(position);
                }
            }
        });

        dialog.setView(view);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.anim_menu_bottombar);
        WindowManager.LayoutParams attributes = window.getAttributes();
        int deviceWidth = UiTools.getDeviceWidth(activity);

        attributes.width = deviceWidth;
        window.setAttributes(attributes);
    }

    public static void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void itemClickListener(int position);
    }
}
