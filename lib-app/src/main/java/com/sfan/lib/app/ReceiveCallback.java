package com.sfan.lib.app;

import android.content.Context;
import android.content.Intent;

/**
 * Created by zhazhiyong on 2018/6/1.
 * BroadcastReceiver 广播回调
 */

public interface ReceiveCallback {

    void receiveCallback(Context context, Intent intent);
}
