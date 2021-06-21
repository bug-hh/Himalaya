package com.bughh.himalaya.utils;

import android.util.Log;

public class LogUtil {
    public static String sTAG = "LogUtil";

    // 控制是否要输出 log
    public static boolean sIsRelase = false;

    /**
     * 如果是要发布了，可以在 application 里面把这里 release 一下，这样就没有 log 输出了
     */
    public static void init(String baseTag, boolean isRelease) {
        sTAG = baseTag;
        sIsRelase = isRelease;
    }

    public static void d(String TAG, String content) {
        if (!sIsRelase) {
            Log.d("[" + sTAG + "]" + TAG, content);
        }
    }

    public static void v(String TAG, String content) {
        if (!sIsRelase) {
            Log.d("[" + sTAG + "]" + TAG, content);
        }
    }

    public static void i(String TAG, String content) {
        if (!sIsRelase) {
            Log.d("[" + sTAG + "]" + TAG, content);
        }
    }



}
