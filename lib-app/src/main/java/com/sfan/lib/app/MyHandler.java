package com.sfan.lib.app;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by zhazhiyong on 2018/6/1.
 * Handler弱引用 垃圾回收器System.gc()被回收
 */

public class MyHandler extends Handler {

    private WeakReference<HandleCallback> mCallback;

    public MyHandler(HandleCallback callback) {
        mCallback = new WeakReference<>(callback);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        HandleCallback callback = mCallback.get();
        if (callback != null) {
            callback.handleCallback(msg);
        }
    }

}
