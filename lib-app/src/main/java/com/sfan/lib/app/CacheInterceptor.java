package com.sfan.lib.app;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhazhiyong on 2018/6/1.
 * OkHttp缓存设置
 */

public class CacheInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                // 关闭连接，不让它保持连接.不加头部时Connection的值为Keep-Alive
                .addHeader("Connection", "close")
                .build();
        MyLog.d("New Request intercept----" + request.toString());
        Response response = chain.proceed(request);
        if (DeviceUtils.isNetworkReachable(BaseApp.getContext())) {
            // 设置缓存时间，过后失效
            int maxAge = 0;// 这里设置的为0就是说不进行缓存
            response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            // 这里设置的是我们的没有网络的缓存时间
            int maxStale = 60 * 60 * 24; // 一天
            response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
        return response;
    }

}
