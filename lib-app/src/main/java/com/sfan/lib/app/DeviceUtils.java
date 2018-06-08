package com.sfan.lib.app;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

/**
 * Created by zhazhiyong on 2018/6/1.
 * 移动设备
 */

public final class DeviceUtils {

    private DeviceUtils() {
    }

    /**
     * 获取Android设备唯一标识码
     *
     * @param context 上下文
     * @return Android设备唯一标识码
     */
    @SuppressLint("HardwareIds")
    public static String getUdid(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 网络是否连接
     *
     * @param context 上下文
     * @return true网络连接，false网络断开
     */
    @SuppressLint("MissingPermission")
    public static boolean isNetworkReachable(Context context) {
        ConnectivityManager connect = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connect != null) {
            NetworkInfo networkInfo = connect.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                // 网络连接
                return true;
            }
        }
        return false;
    }

    /**
     * 获取屏幕宽 px
     *
     * @param context 上下文
     * @return 屏幕宽 px
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;// 屏幕宽（像素，如：480px）
    }

    /**
     * 获取屏幕高 px
     *
     * @param context 上下文
     * @return 屏幕高 px
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;// 屏幕高（像素，如：800px）
    }

    /**
     * 获取当前应用包名
     *
     * @return 包名
     */
    public static String getAppPackageName(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager != null) {
                ComponentName componentInfo = activityManager.getRunningTasks(1).get(0).topActivity;
                return componentInfo.getPackageName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得当前activity的名字
     *
     * @return 当前activity的名字
     */
    public static String getRunningActivityName(Context context) {
        String contextActivity = null;
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager != null) {
                String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();// 完整类名
                contextActivity = runningActivity.substring(runningActivity.lastIndexOf(".") + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contextActivity;
    }

}
