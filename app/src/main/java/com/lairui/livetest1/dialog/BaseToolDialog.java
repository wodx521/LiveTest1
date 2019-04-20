package com.lairui.livetest1.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.ui.adapter.BaseToolAdapter;
import com.lairui.livetest1.ui.adapter.FeaturesAdapter;
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;
import com.wanou.framelibrary.utils.UiTools;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class BaseToolDialog {

    public static void getDialog(Activity activity, List<String> toolList, List<Integer> toolImage,
                                 List<String> featuresList, List<Integer> featuresImage, boolean showFeatures) {
        AlertDialog dialog = new AlertDialog.Builder(activity).create();
        View view = View.inflate(UiTools.context, R.layout.layout_base_tools, null);
        RecyclerView rvToolList = view.findViewById(R.id.rvToolList);
        TextView textView12 = view.findViewById(R.id.textView12);
        RecyclerView rvFeaturesList = view.findViewById(R.id.rvFeaturesList);

        if (showFeatures) {
            textView12.setVisibility(View.VISIBLE);
            rvFeaturesList.setVisibility(View.VISIBLE);
        } else {
            textView12.setVisibility(View.GONE);
            rvFeaturesList.setVisibility(View.GONE);
        }

        // 基础工具列表
        BaseToolAdapter baseToolAdapter = new BaseToolAdapter(activity);
        rvToolList.setAdapter(baseToolAdapter);
        baseToolAdapter.setData(toolList, toolImage);
        baseToolAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                if (mItemClickListener != null) {
                    dialog.dismiss();
                    mItemClickListener.baseToolItemListener(position);
                }
            }
        });
        // 游戏与功能列表
        FeaturesAdapter featuresAdapter = new FeaturesAdapter(activity);
        rvFeaturesList.setAdapter(featuresAdapter);
        featuresAdapter.setData(featuresList, featuresImage);

        featuresAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                if (mItemClickListener != null) {
                    dialog.dismiss();
                    mItemClickListener.featuresItemListener(position);
                }
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mItemClickListener != null) {
                    mItemClickListener.dismissListener();
                }
            }
        });
        dialog.setView(view);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.shape_white_transparent_round20);
        dialog.show();
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setWindowAnimations(R.style.anim_menu_bottombar);
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        int deviceWidth = UiTools.getDeviceWidth(activity);
        attributes.dimAmount = 0f;
        attributes.width = deviceWidth * 9 / 10;
        attributes.height = WRAP_CONTENT;
        dialog.getWindow().setAttributes(attributes);
    }

    public interface ItemClickListener {
        void baseToolItemListener(int position);

        void featuresItemListener(int position);

        void dismissListener();
    }

   private static ItemClickListener mItemClickListener;

    public static void setItemClickListener(ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
