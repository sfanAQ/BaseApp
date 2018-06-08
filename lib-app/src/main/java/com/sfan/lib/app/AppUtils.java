package com.sfan.lib.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by zhazhiyong on 2018/6/1.
 * 应用工具
 */

public final class AppUtils {

    private AppUtils() {
    }

    /**
     * 获取应用信息 versionCode
     *
     * @return 应用信息
     */
    public static PackageInfo getPackageInfo(Context context, int flags) {
        PackageInfo info = null;
        try {
            PackageManager manager = context.getPackageManager();
            info = manager.getPackageInfo(context.getPackageName(), flags);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 获取应用版本号 versionName是给用户看的，可以写1.1 , 1.2等等版本
     *
     * @return 当前应用的版本号
     */
    public static String getVersionName(Context context, int flags) {
        PackageInfo info = getPackageInfo(context, flags);
        return info == null ? null : info.versionName;
    }

    /**
     * 获取应用开发版本号 versionCode
     *
     * @return 当前应用的版本号
     */
    public static int getVersionCode(Context context, int flags) {
        PackageInfo info = getPackageInfo(context, flags);
        return info == null ? 0 : info.versionCode;
    }

}
