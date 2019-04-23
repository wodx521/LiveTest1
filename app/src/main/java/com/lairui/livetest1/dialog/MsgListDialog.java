package com.lairui.livetest1.dialog;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.ui.adapter.ListInfoAdapter;
import com.wanou.framelibrary.utils.UiTools;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class MsgListDialog {

    private static RecyclerView rvList;
    private static ConstraintLayout clEmpty;

    public static void getDialog(Activity activity, List<String> tabTitle, List<String> trade,
                                 List<String> friend, List<String> noAttentionList) {
        AlertDialog dialog = new AlertDialog.Builder(activity).create();
        View view = UiTools.parseLayout(R.layout.layout_msg_list);
        ImageView ivCloseList = view.findViewById(R.id.ivCloseList);
        TextView tvIgnore = view.findViewById(R.id.tvIgnore);
        RadioButton rbTrade = view.findViewById(R.id.rbTrade);
        RadioButton rbFriend = view.findViewById(R.id.rbFriend);
        RadioButton rbNoAttention = view.findViewById(R.id.rbNoAttention);

        rvList = view.findViewById(R.id.rvList);
        clEmpty = view.findViewById(R.id.clEmpty);

        ListInfoAdapter listInfoAdapter = new ListInfoAdapter(activity);
        rvList.setAdapter(listInfoAdapter);

        ivCloseList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // TODO: 2019/4/18 消息的弹窗设置
        tvIgnore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIgnoreClickListener != null) {
                    mIgnoreClickListener.onIgnoreClickListener();
                }
            }
        });
        rbTrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listInfoAdapter.setData(trade);
                if (trade != null && trade.size() > 0) {
                    rvList.setVisibility(View.VISIBLE);
                    clEmpty.setVisibility(View.GONE);
                } else {
                    rvList.setVisibility(View.GONE);
                    clEmpty.setVisibility(View.VISIBLE);
                }
            }
        });
        rbFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listInfoAdapter.setData(friend);
                if (friend != null && friend.size() > 0) {
                    rvList.setVisibility(View.VISIBLE);
                    clEmpty.setVisibility(View.GONE);
                } else {
                    rvList.setVisibility(View.GONE);
                    clEmpty.setVisibility(View.VISIBLE);
                }
            }
        });
        rbNoAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listInfoAdapter.setData(noAttentionList);
                if (noAttentionList != null && noAttentionList.size() > 0) {
                    rvList.setVisibility(View.VISIBLE);
                    clEmpty.setVisibility(View.GONE);
                } else {
                    rvList.setVisibility(View.GONE);
                    clEmpty.setVisibility(View.VISIBLE);
                }
            }
        });

        rbTrade.performClick();
        dialog.setView(view);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setWindowAnimations(R.style.anim_menu_bottombar);
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        // dialog显示时背景的昏暗程度
        attributes.dimAmount = 0f;
        attributes.width = UiTools.getDeviceWidth(activity);
        attributes.height = UiTools.getDeviceWidth(activity) / 3;
        dialog.getWindow().setAttributes(attributes);
    }

    public interface IgnoreClickListener {
        void onIgnoreClickListener();
    }

    static IgnoreClickListener mIgnoreClickListener;

    public static void setmIgnoreClickListener(IgnoreClickListener mIgnoreClickListener) {
        MsgListDialog.mIgnoreClickListener = mIgnoreClickListener;
    }
}
