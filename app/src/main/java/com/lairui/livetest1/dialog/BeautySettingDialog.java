package com.lairui.livetest1.dialog;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lairui.livetest1.R;
import com.lairui.livetest1.ui.adapter.BaseToolAdapter;
import com.lairui.livetest1.ui.adapter.BeautySettingAdapter;
import com.lairui.livetest1.ui.adapter.FeaturesAdapter;
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;
import com.wanou.framelibrary.utils.UiTools;

import java.util.List;

public class BeautySettingDialog {

    public static void getDialog(Activity activity, List<String> beautyTypeList, int beautyProgress,
                                 int whitenProgress) {
        AlertDialog dialog = new AlertDialog.Builder(activity).create();
        View view = View.inflate(UiTools.context, R.layout.layout_beauty_setting, null);
        SeekBar sbBeauty = view.findViewById(R.id.sbBeauty);
        TextView tvBeautyPercent = view.findViewById(R.id.tvBeautyPercent);
        SeekBar sbWhitening = view.findViewById(R.id.sbWhitening);
        TextView tvWhiteningPercent = view.findViewById(R.id.tvWhiteningPercent);
        RecyclerView rvBeautyType = view.findViewById(R.id.rvBeautyType);
        RadioGroup rgStyle = view.findViewById(R.id.rgStyle);
        RadioButton rbSmooth = view.findViewById(R.id.rbSmooth);
        RadioButton rbNatural = view.findViewById(R.id.rbNatural);
        RadioButton rbHazy = view.findViewById(R.id.rbHazy);
        SeekBar sbRuddy = view.findViewById(R.id.sbRuddy);
        TextView tvRuddyPercent = view.findViewById(R.id.tvRuddyPercent);



        BeautySettingAdapter beautySettingAdapter = new BeautySettingAdapter(activity);
        rvBeautyType.setAdapter(beautySettingAdapter);
        beautySettingAdapter.setBeautyTypeList(beautyTypeList);
        sbBeauty.setProgress(beautyProgress);
        sbWhitening.setProgress(whitenProgress);
        beautySettingAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                if (mItemClickListener != null) {
                    mItemClickListener.beautyItemClickListener(position);
                }
            }
        });

        sbBeauty.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mSeekBarChangeListener != null) {
                    mSeekBarChangeListener.whitenSeekBarChange(progress);
                    String defaultPercent = UiTools.getString(R.string.defaultPercent);
                    tvBeautyPercent.setText(defaultPercent.replace("100", "" + progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbWhitening.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mSeekBarChangeListener != null) {
                    String defaultPercent = UiTools.getString(R.string.defaultPercent);
                    mSeekBarChangeListener.whitenSeekBarChange(progress);
                    tvWhiteningPercent.setText(defaultPercent.replace("100", "" + progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        dialog.setView(view);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.shape_black_trans_bg_round5);
        dialog.show();
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setWindowAnimations(R.style.anim_menu_bottombar);
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        int deviceWidth = UiTools.getDeviceWidth(activity);
        int deviceHeight = UiTools.getDeviceHeight(activity);

        attributes.width = deviceWidth * 9 / 10;
        attributes.height = deviceHeight / 2;
        dialog.getWindow().setAttributes(attributes);
    }

    public interface ItemClickListener {
        void beautyItemClickListener(int position);
    }

    public interface SeekBarChangeListener {
        void beautySeekBarChange(int progress);

        void whitenSeekBarChange(int progress);
    }

    static ItemClickListener mItemClickListener;
    static SeekBarChangeListener mSeekBarChangeListener;

    public static void setmItemClickListener(ItemClickListener mItemClickListener) {
        BeautySettingDialog.mItemClickListener = mItemClickListener;
    }

    public static void setmSeekBarChangeListener(SeekBarChangeListener mSeekBarChangeListener) {
        BeautySettingDialog.mSeekBarChangeListener = mSeekBarChangeListener;
    }
}
