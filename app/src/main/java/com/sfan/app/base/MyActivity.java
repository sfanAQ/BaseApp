package com.sfan.app.base;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.sfan.lib.app.BaseActivity;
import com.sfan.lib.app.HandleCallback;
import com.sfan.lib.app.MyBroadcastReceiver;
import com.sfan.lib.app.MyHandler;
import com.sfan.lib.app.ReceiveCallback;

/**
 * Created by zzy on 2018/6/1.
 * Activity
 */

public abstract class MyActivity extends BaseActivity implements HandleCallback, ReceiveCallback {

    protected Handler mHandler;

    protected BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new MyHandler(this);
        mReceiver = new MyBroadcastReceiver(this);
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
        mReceiver = null;
        super.onDestroy();
    }

    // 初始化窗口属性，让状态栏和导航栏透明
    protected void setStatusBarTranslucent(int color, boolean isFontColorDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {// Android系统版本>=4.4
            Window window = getWindow();
            // 透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            // 透明虚拟键盘
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        // 通知标题栏所需颜色
        setStatusBarColor(color);
        // 是否深色
        setStatusBarLightMode(isFontColorDark);
    }

    /**
     * 注册广播
     *
     * @param actions 注册
     */
    protected void registerReceiver(String... actions) {
        if (actions == null) return;
        IntentFilter filter = new IntentFilter();
        for (String action : actions) {
            filter.addAction(action);
        }
        registerReceiver(mReceiver, filter);
    }

    /**
     * 注销广播
     */
    protected void unregisterReceiver() {
        unregisterReceiver(mReceiver);
    }

}
