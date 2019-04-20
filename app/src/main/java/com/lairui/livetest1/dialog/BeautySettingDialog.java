package com.lairui.livetest1.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
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

import com.google.android.exoplayer.util.extensions.OutputBuffer;
import com.lairui.livetest1.R;
import com.lairui.livetest1.entity.livebean.BeautyEffectBean;
import com.lairui.livetest1.ui.adapter.BaseToolAdapter;
import com.lairui.livetest1.ui.adapter.BeautySettingAdapter;
import com.lairui.livetest1.ui.adapter.FeaturesAdapter;
import com.lairui.livetest1.utils.CustomSeekBarChangeListener;
import com.lairui.livetest1.utils.ObjectBox;
import com.tencent.rtmp.TXLiveConstants;
import com.wanou.framelibrary.base.BaseRecycleViewAdapter;
import com.wanou.framelibrary.utils.SpUtils;
import com.wanou.framelibrary.utils.UiTools;

import java.util.List;

import io.objectbox.Box;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class BeautySettingDialog {
    @SuppressLint("StaticFieldLeak")
    private static SeekBar sbRuddy;
    @SuppressLint("StaticFieldLeak")
    private static SeekBar sbBeauty;
    @SuppressLint("StaticFieldLeak")
    private static SeekBar sbWhitening;
    @SuppressLint("StaticFieldLeak")
    private static RadioButton rbSmooth;
    @SuppressLint("StaticFieldLeak")
    private static RadioButton rbNatural;
    @SuppressLint("StaticFieldLeak")
    private static RadioButton rbHazy;
    @SuppressLint("StaticFieldLeak")
    private static TextView tvBeautyPercent;
    @SuppressLint("StaticFieldLeak")
    private static TextView tvWhiteningPercent;
    @SuppressLint("StaticFieldLeak")
    private static TextView tvRuddyPercent;
    private static Box<BeautyEffectBean> beautyEffectBeanBox;
    private static BeautyEffectBean beautyEffectBean1;

    public static void getDialog(Activity activity, List<String> beautyTypeList) {
        beautyEffectBeanBox = ObjectBox.getBoxStore().boxFor(BeautyEffectBean.class);
        long beautyId = (long) SpUtils.get("beautyId", -1L);
        if (beautyId != -1) {
            beautyEffectBean1 = beautyEffectBeanBox.get(beautyId);
        }
        AlertDialog dialog = new AlertDialog.Builder(activity).create();
        View view = View.inflate(UiTools.context, R.layout.layout_beauty_setting, null);
        sbBeauty = view.findViewById(R.id.sbBeauty);
        sbWhitening = view.findViewById(R.id.sbWhitening);
        sbRuddy = view.findViewById(R.id.sbRuddy);
        RecyclerView rvBeautyType = view.findViewById(R.id.rvBeautyType);
        rbSmooth = view.findViewById(R.id.rbSmooth);
        rbNatural = view.findViewById(R.id.rbNatural);
        rbHazy = view.findViewById(R.id.rbHazy);
        tvBeautyPercent = view.findViewById(R.id.tvBeautyPercent);
        tvWhiteningPercent = view.findViewById(R.id.tvWhiteningPercent);
        tvRuddyPercent = view.findViewById(R.id.tvRuddyPercent);
        setDefault();
        rbSmooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                styleChange();
            }
        });
        rbNatural.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                styleChange();
            }
        });
        rbHazy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                styleChange();
            }
        });

        // 滤镜列表设置
        BeautySettingAdapter beautySettingAdapter = new BeautySettingAdapter(activity);
        rvBeautyType.setAdapter(beautySettingAdapter);
        beautySettingAdapter.setBeautyTypeList(beautyTypeList);
        beautySettingAdapter.setSelect(beautyEffectBean1.getFilterType());
        beautySettingAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                if (mItemClickListener != null) {
                    Bitmap filterBitmap = beautyEffectBean1.getFilterBitmapByIndex(position);
                    mItemClickListener.beautyItemClickListener(position, filterBitmap);
                    beautyEffectBean1.setFilterType(position);
                    beautyEffectBeanBox.put(beautyEffectBean1);
                }
            }
        });

        sbBeauty.setOnSeekBarChangeListener(new CustomSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int ruddyProgress, boolean fromUser) {
                styleChange();
            }
        });

        sbWhitening.setOnSeekBarChangeListener(new CustomSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int ruddyProgress, boolean fromUser) {
                styleChange();
            }
        });

        sbRuddy.setOnSeekBarChangeListener(new CustomSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int ruddyProgress, boolean fromUser) {
                styleChange();
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

    private static void styleChange() {
        if (mItemClickListener != null) {
            int style = getStyle(rbSmooth, rbNatural, rbHazy);
            int beautyProgress = sbBeauty.getProgress();
            int whiteningProgress = sbWhitening.getProgress();
            int ruddyProgress = sbRuddy.getProgress();
            beautyEffectBean1.setStyle(style);
            beautyEffectBean1.setBeautyLevel(beautyProgress);
            beautyEffectBean1.setWhiteningLevel(whiteningProgress);
            beautyEffectBean1.setRuddyLevel(ruddyProgress);
            beautyEffectBeanBox.put(beautyEffectBean1);
            String defaultPercent = UiTools.getString(R.string.defaultPercent);
            tvBeautyPercent.setText(defaultPercent.replace("100", "" + (beautyProgress * 10)));
            tvWhiteningPercent.setText(defaultPercent.replace("100", "" + (whiteningProgress * 10)));
            tvRuddyPercent.setText(defaultPercent.replace("100", "" + (ruddyProgress * 10)));
            mItemClickListener.beautyEffectChangeListener(style, beautyProgress, whiteningProgress, ruddyProgress);
        }
    }

    // 滤镜回调
    public interface ItemClickListener {
        void beautyItemClickListener(int position, Bitmap filterBitmap);

        void dismissListener();

        void beautyEffectChangeListener(int style, int beautyProgress, int whiteningProgress, int ruddyProgress);
    }

    private static ItemClickListener mItemClickListener;

    public static void setmItemClickListener(ItemClickListener mItemClickListener) {
        BeautySettingDialog.mItemClickListener = mItemClickListener;
    }

    private static int getStyle(RadioButton rbSmooth, RadioButton rbNatural, RadioButton rbHazy) {
        if (rbSmooth.isChecked()) {
            return TXLiveConstants.BEAUTY_STYLE_SMOOTH;
        } else if (rbNatural.isChecked()) {
            return TXLiveConstants.BEAUTY_STYLE_NATURE;
        } else if (rbHazy.isChecked()) {
            return TXLiveConstants.BEAUTY_STYLE_HAZY;
        } else {
            return TXLiveConstants.BEAUTY_STYLE_SMOOTH;
        }
    }

    private static void setDefault() {
        int style = beautyEffectBean1.getStyle();
        int beautyLevel = beautyEffectBean1.getBeautyLevel();
        int whiteningLevel = beautyEffectBean1.getWhiteningLevel();
        int ruddyLevel = beautyEffectBean1.getRuddyLevel();
        String defaultPercent = UiTools.getString(R.string.defaultPercent);
        switch (style) {
            case TXLiveConstants.BEAUTY_STYLE_SMOOTH:
                rbSmooth.setChecked(true);
                break;
            case TXLiveConstants.BEAUTY_STYLE_NATURE:
                rbNatural.setChecked(true);
                break;
            case TXLiveConstants.BEAUTY_STYLE_HAZY:
                rbHazy.setChecked(true);
                break;
            default:
                rbSmooth.setChecked(true);
                break;
        }
        tvBeautyPercent.setText(defaultPercent.replace("100", "" + (beautyLevel * 10)));
        tvWhiteningPercent.setText(defaultPercent.replace("100", "" + (whiteningLevel * 10)));
        tvRuddyPercent.setText(defaultPercent.replace("100", "" + (ruddyLevel * 10)));
        sbBeauty.setProgress(beautyLevel);
        sbWhitening.setProgress(whiteningLevel);
        sbRuddy.setProgress(ruddyLevel);
    }
}
