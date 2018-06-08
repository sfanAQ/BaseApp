package com.sfan.lib.app;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by zhazhiyong on 2018/6/4.
 * Glide 加载图片工具
 */

public final class MyGlide {

    private MyGlide() {
    }

    // 清除图片缓存
    public static void clearCache(Context context) {
        Glide.get(context).clearMemory();
        Glide.get(context).clearDiskCache();
    }

    public static void loadImage(Context context, ImageView imageView, String url) {
        Glide.with(context).load(url).into(imageView);
    }

}
