package com.wanou.framelibrary.utils;

import android.text.TextUtils;

public class ParseIntUtils {
    public static Integer init(Integer value) {
        if (value == null) {
            value = 0;
        }
        return value;
    }

    public static Long init(Long value) {
        if (value == null) {
            value = 0L;
        }
        return value;
    }

    public static Double init(Double value) {
        if (value == null) {
            value = 0.0D;
        }
        return value;
    }

    public static Float init(Float value) {
        if (value == null) {
            value = 0.0F;
        }
        return value;
    }

    public static float getFloat(String content) {
        return getFloat(content, 0.0F);
    }

    public static float getFloat(String content, float defaultValue) {
        if (!TextUtils.isEmpty(content)) {
            try {
                return Float.parseFloat(content);
            } catch (Exception var3) {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    public static double getDouble(String content) {
        return getDouble(content, 0.0D);
    }

    public static double getDouble(String content, double defaultValue) {
        if (!TextUtils.isEmpty(content)) {
            try {
                return Double.parseDouble(content);
            } catch (Exception var4) {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    public static long getLong(String content) {
        return getLong(content, 0L);
    }

    public static long getLong(String content, long defaultValue) {
        if (!TextUtils.isEmpty(content)) {
            try {
                return Long.parseLong(content);
            } catch (Exception var4) {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    public static int getInt(String content) {
        return getInt(content, 0);
    }

    public static int getInt(String content, int defaultValue) {
        if (!TextUtils.isEmpty(content)) {
            try {
                return Integer.parseInt(content);
            } catch (Exception var3) {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }
}
