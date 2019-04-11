package com.lairui.livetest1.dialog;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.entity.bean.CategoryBean;
import com.lairui.livetest1.ui.adapter.ChooseListAdapter;
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;
import com.wanou.framelibrary.utils.UiTools;

import java.util.List;

public class ChoosePopup {
    private static long time = 0;
    private static OnChooseContentListener mOnChooseContentListener;

    public static void getPopup(Activity activity, List<CategoryBean> coinBeans, TextView textView) {
        int deviceHeight = UiTools.getDeviceHeight(activity);
        final PopupWindow popupWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, deviceHeight / 3);
        View view = UiTools.parseLayout(R.layout.choose_list, null);
        RecyclerView mRvChooseList = view.findViewById(R.id.rvCategory);
        final ChooseListAdapter chooseListAdapter = new ChooseListAdapter(activity);
        mRvChooseList.setAdapter(chooseListAdapter);
        chooseListAdapter.setData(coinBeans);
        popupWindow.setContentView(view);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
        popupWindow.setBackgroundDrawable(UiTools.getDrawable(R.drawable.shape_choose_list));
        popupWindow.showAtLocation(activity.findViewById(R.id.nestedScrollView), Gravity.BOTTOM, 0, 0);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long l = System.currentTimeMillis();
                if ((l - time) > 1000) {
                    time = l;
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    } else {
                        popupWindow.showAtLocation(activity.findViewById(R.id.nestedScrollView), Gravity.BOTTOM, 0, 0);
                    }
                }
            }
        });
        chooseListAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                if (mOnChooseContentListener != null) {
                    chooseListAdapter.setSelect(position);
                    mOnChooseContentListener.onChooseClickListener(position);
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                }
            }
        });
    }

    public static void setOnChooseContentListener(OnChooseContentListener onChooseContentListener) {
        mOnChooseContentListener = onChooseContentListener;
    }

    public interface OnChooseContentListener {
        void onChooseClickListener(int position);
    }
}
