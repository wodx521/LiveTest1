package com.lairui.livetest1.entity.livebean;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.TypedValue;

import com.lairui.livetest1.R;
import com.wanou.framelibrary.utils.UiTools;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

import static com.tencent.rtmp.TXLiveConstants.BEAUTY_STYLE_SMOOTH;

@Entity
public class BeautyEffectBean {
    @Id
    public long mainId;
    private int style;
    private int beautyLevel;
    private int whiteningLevel;
    private int ruddyLevel;
    private int filterType;

    public BeautyEffectBean() {
        style = BEAUTY_STYLE_SMOOTH;
        beautyLevel = 5;
        whiteningLevel = 3;
        ruddyLevel = 2;
        filterType = 0;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public int getBeautyLevel() {
        return beautyLevel;
    }

    public void setBeautyLevel(int beautyLevel) {
        this.beautyLevel = beautyLevel;
    }

    public int getWhiteningLevel() {
        return whiteningLevel;
    }

    public void setWhiteningLevel(int whiteningLevel) {
        this.whiteningLevel = whiteningLevel;
    }

    public int getRuddyLevel() {
        return ruddyLevel;
    }

    public void setRuddyLevel(int ruddyLevel) {
        this.ruddyLevel = ruddyLevel;
    }

    public int getFilterType() {
        return filterType;
    }

    public void setFilterType(int filterType) {
        this.filterType = filterType;
    }

    private Bitmap decodeResource(Resources resources, int id) {
        TypedValue value = new TypedValue();
        resources.openRawResource(id, value);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inTargetDensity = value.density;
        return BitmapFactory.decodeResource(resources, id, opts);
    }

    public Bitmap getFilterBitmapByIndex(int index) {
        Bitmap bmp = null;
        switch (index) {
            case 1:
                bmp = decodeResource(UiTools.resources, R.drawable.filter_biaozhun);
                break;
            case 2:
                bmp = decodeResource(UiTools.resources, R.drawable.filter_yinghong);
                break;
            case 3:
                bmp = decodeResource(UiTools.resources, R.drawable.filter_yunshang);
                break;
            case 4:
                bmp = decodeResource(UiTools.resources, R.drawable.filter_chunzhen);
                break;
            case 5:
                bmp = decodeResource(UiTools.resources, R.drawable.filter_bailan);
                break;
            case 6:
                bmp = decodeResource(UiTools.resources, R.drawable.filter_yuanqi);
                break;
            case 7:
                bmp = decodeResource(UiTools.resources, R.drawable.filter_chaotuo);
                break;
            case 8:
                bmp = decodeResource(UiTools.resources, R.drawable.filter_xiangfen);
                break;
            case 9:
                bmp = decodeResource(UiTools.resources, R.drawable.filter_white);
                break;
            case 10:
                bmp = decodeResource(UiTools.resources, R.drawable.filter_langman);
                break;
            case 11:
                bmp = decodeResource(UiTools.resources, R.drawable.filter_qingxin);
                break;
            case 12:
                bmp = decodeResource(UiTools.resources, R.drawable.filter_weimei);
                break;
            case 13:
                bmp = decodeResource(UiTools.resources, R.drawable.filter_fennen);
                break;
            case 14:
                bmp = decodeResource(UiTools.resources, R.drawable.filter_huaijiu);
                break;
            case 15:
                bmp = decodeResource(UiTools.resources, R.drawable.filter_landiao);
                break;
            case 16:
                bmp = decodeResource(UiTools.resources, R.drawable.filter_qingliang);
                break;
            case 17:
                bmp = decodeResource(UiTools.resources, R.drawable.filter_rixi);
                break;
            default:
                bmp = null;
                break;
        }
        return bmp;
    }
}