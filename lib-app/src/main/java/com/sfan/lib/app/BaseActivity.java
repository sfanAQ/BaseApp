package com.sfan.lib.app;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by zhazhiyong on 2018/6/1.
 * Activity
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int getContentViewLayoutResID();

    protected abstract void init(Bundle savedInstanceState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewLayoutResID());
        init(savedInstanceState);
    }

    /**
     * 设置通知标题栏颜色
     *
     * @param color 颜色
     */
    public void setStatusBarColor(int color) {
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(color);
    }

    /**
     * 修改状态栏StatusBar（SystemUI）图标和字体是否深色
     *
     * @param isFontColorDark 是否深色
     */
    public void setStatusBarLightMode(boolean isFontColorDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {// Android系统版本>=6.0
            if (isFontColorDark) {
                // 沉浸式
                // window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                // 状态栏字体及图标颜色设置为深色
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                // 默认
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
    }

    /**
     * 沉浸模式View适应状态栏高度
     *
     * @param views View控件
     */
    public void setStatusBarLayout(View... views) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            int statusBarHeight = tintManager.getConfig().getStatusBarHeight();
            for (int i = 0; i < views.length; i++) {
                views[i].setPadding(0, statusBarHeight, 0, 0);
            }
        }
    }

}
