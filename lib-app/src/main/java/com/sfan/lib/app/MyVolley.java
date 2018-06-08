package com.sfan.lib.app;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by zhazhiyong on 2018/6/4.
 * Volley 网络Http请求工具
 */

public final class MyVolley {

    private MyVolley() {
    }

    private static RequestQueue mRequestQueue = null;

    // 通过单例模式获取实例
    private static RequestQueue getInstance(Context context) {
        if (mRequestQueue == null) {
            synchronized (MyVolley.class) {
                mRequestQueue = Volley.newRequestQueue(context);
            }
        }
        return mRequestQueue;
    }

    /**
     * GET请求
     *
     * @param context  上下文
     * @param url      请求地址
     * @param listener 回调接口
     */
    public static void get(Context context, String url, final VolleyListener listener) {
        StringRequest myReq = new StringRequest(Request.Method.GET, url, listener, listener) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String parsed;
                try {
                    // 解决Volley下载中文乱码问题
                    parsed = new String(response.data, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    parsed = new String(response.data);
                }
                return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        //设置30s延迟，只请求一次
        myReq.setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 1, 1.0f));
        RequestQueue requestQueue = getInstance(context);
        requestQueue.add(myReq);
    }

    /**
     * POST请求
     *
     * @param context  上下文
     * @param url      请求地址
     * @param params   请求体
     * @param listener 接口回调
     */
    public static void post(Context context, String url, final Map<String, String> params, final VolleyListener listener) {
        StringRequest myReq = new StringRequest(Request.Method.POST, url, listener, listener) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String parsed;
                try {
                    // 解决Volley下载中文乱码问题
                    parsed = new String(response.data, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    parsed = new String(response.data);
                }
                return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 1, 1.0f));
        RequestQueue requestQueue = getInstance(context);
        requestQueue.add(myReq);
    }

}
