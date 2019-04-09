package com.lairui.livetest1.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.wanou.framelibrary.utils.CountDownUtils;
import com.wanou.framelibrary.utils.UiTools;

public class LiveCreateRoomShareTipsPop extends PopupWindow {
    private int popupHeight;
    private int popupWidth;
    private View contentView;
    private TextView textView;

    public LiveCreateRoomShareTipsPop(Activity context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = layoutInflater.inflate(R.layout.pop_create_room_share_purple, null);
        setContentView(contentView);
        textView = contentView.findViewById(R.id.tv_pop_share_tips);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);

        ColorDrawable dw = new ColorDrawable(0x00ffffff);
        this.setBackgroundDrawable(dw);
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupHeight = contentView.getMeasuredHeight();
        popupWidth = contentView.getMeasuredWidth();
        setOutsideTouchable(true);
        setFocusable(false);
    }

    private void setTips(String tips) {
        textView.setText(tips);
    }

    public void showPopTips(String shareTips, View parent) {
        CountDownUtils.cancelTimer();
        if (isShowing()) {
            dismiss();
        }
        setTips(shareTips);
        int[] location = new int[]{0, 0};
        if (parent != null) {
            parent.getLocationOnScreen(location);
        }
        parent.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int parentWidth = parent.getMeasuredWidth();
        showAtLocation(parent, Gravity.NO_GRAVITY, (location[0]) + parentWidth / 2 - popupWidth / 2, location[1] - popupHeight + UiTools.dip2px(15));

        CountDownUtils.getTimer(1.5, null, "");
        CountDownUtils.setTimeFinishListener(new CountDownUtils.CountTimeFinishListener() {
            @Override
            public void onTimeFinishListener() {
                if (isShowing()) {
                    dismiss();
                }
            }
        });
    }
}
