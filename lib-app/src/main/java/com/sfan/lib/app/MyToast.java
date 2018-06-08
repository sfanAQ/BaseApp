package com.sfan.lib.app;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by zhazhiyong on 2018/6/1.
 * Toast工具类
 */
public final class MyToast {

    private MyToast() {
    }

    private static Toast toast = null;
    private static Handler handler = new Handler(Looper.getMainLooper());

    private static boolean DEBUG = true;// debug开关

    public static boolean isDebug() {
        return DEBUG;
    }

    public static void setDebug(final boolean isDebug) {
        DEBUG = isDebug;
    }

    public static void debug(final String msg) {
        if (DEBUG) {
            // 便于开发人员直观的查看
            showToast(msg, Toast.LENGTH_SHORT);
        }
    }

    /**
     * 根据设置的文本显示
     *
     * @param msg
     */
    public static void showToast(final String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    /**
     * 根据设置的资源文件显示
     *
     * @param resId
     */
    public static void showToast(final int resId) {
        showToast(resId, Toast.LENGTH_SHORT);
    }

    /**
     * 显示一个文本并且设置时长
     *
     * @param msg
     * @param len
     */
    public static void showToast(final CharSequence msg, final int len) {
        if (TextUtils.isEmpty(msg)) return;
        handler.post(new Runnable() {
            @Override
            public void run() {
                synchronized (MyToast.class) { //加上同步是为了每个toast只要有机会显示出来
                    if (toast != null) {
                        toast.setText(msg);
                        toast.setDuration(len);
                    } else {
                        toast = Toast.makeText(BaseApp.getContext(), msg, len);
                    }
                    toast.show();
                }
            }
        });
    }

    /**
     * 资源文件方式显示文本
     *
     * @param resId
     * @param len
     */
    public static void showToast(final int resId, final int len) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                synchronized (MyToast.class) {
                    if (toast != null) {
                        toast.setText(resId);
                        toast.setDuration(len);
                    } else {
                        toast = Toast.makeText(BaseApp.getContext(), resId, len);
                    }
                    toast.show();
                }
            }
        });
    }
}
