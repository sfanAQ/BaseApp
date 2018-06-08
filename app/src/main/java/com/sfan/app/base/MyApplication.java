package com.sfan.app.base;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.sfan.lib.app.BaseApp;
import com.sfan.lib.app.MyLog;
import com.sfan.lib.app.MyToast;

/**
 * Created by zzy on 2018/6/4.
 * Application
 */

public class MyApplication extends BaseApp {

    private static final boolean DEBUG = true;// debug开关

    @Override
    public void onCreate() {
        super.onCreate();
        MyLog.setDebug(DEBUG);
        MyToast.setDebug(DEBUG);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // MultiDex
        MultiDex.install(this);
    }

}
