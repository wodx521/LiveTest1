package com.lairui.livetest1.dialog;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.wanou.framelibrary.utils.UiTools;

import java.util.List;

public class MsgListDialog {
    public static void getDialog(Activity activity, List<String> tabTitle, List<String> trade,
                                 List<String> friend, List<String> noAttentionList) {
        AlertDialog dialog = new AlertDialog.Builder(activity).create();
        View view = View.inflate(UiTools.context, R.layout.layout_msg_list, null);
        ImageView ivCloseList = view.findViewById(R.id.ivCloseList);
        TabLayout tlListTab = view.findViewById(R.id.tlListTab);
        TextView tvIgnore = view.findViewById(R.id.tvIgnore);
        RecyclerView rvList = view.findViewById(R.id.rvList);
        ConstraintLayout clEmpty = view.findViewById(R.id.clEmpty);
        ivCloseList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        for (String tabContent : tabTitle) {
            tlListTab.addTab(tlListTab.newTab().setText(tabContent));
        }
// TODO: 2019/4/18 消息的弹窗设置

        dialog.setView(view);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setWindowAnimations(R.style.anim_menu_bottombar);
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        attributes.width = UiTools.getDeviceWidth(activity);
        attributes.height = UiTools.getDeviceHeight(activity) / 2;
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
        MsgListDialog.mSwitchChangeListener = mSwitchChangeListener;
    }

    public static void setmSendClickListener(SendClickListener mSendClickListener) {
        MsgListDialog.mSendClickListener = mSendClickListener;
    }

}
