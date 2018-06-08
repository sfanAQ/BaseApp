package com.sfan.lib.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.lang.ref.WeakReference;

/**
 * Created by zhazhiyong on 2018/6/1.
 * BroadcastReceiver 广播弱引用 垃圾回收器System.gc()被回收
 */

public class MyBroadcastReceiver extends BroadcastReceiver {

    private WeakReference<ReceiveCallback> mReceiver;

    public MyBroadcastReceiver(ReceiveCallback receiver) {
        mReceiver = new WeakReference<>(receiver);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ReceiveCallback receiver = mReceiver.get();
        if (receiver != null) {
            receiver.receiveCallback(context, intent);
        }
    }

}
