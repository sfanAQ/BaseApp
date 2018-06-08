package com.sfan.lib.app;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Cache;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;

/**
 * Created by zhazhiyong on 2018/6/4.
 * Picasso 加载图片工具
 */

public final class MyPicasso {

    private MyPicasso() {
    }

    // 清除某个图片缓存
    public static void clearCache(Context context, String url) {
        Uri uri = Uri.parse(url);
        Picasso.with(context).invalidate(uri);
    }

    // 清除图片缓存
    public static void clearCache(Context context) {
        try {
            Field f = Picasso.class.getDeclaredField("cache");
            f.setAccessible(true);
            Picasso picasso = Picasso.with(context);
            Cache cache = (Cache) f.get(picasso);
            cache.clear();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void loadImage(Context context, ImageView imageView, String url) {
        Picasso.with(context).load(url).into(imageView);
    }

}
