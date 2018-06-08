package com.sfan.lib.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by zhazhiyong on 2018/6/4.
 * Universal-Image-Loader 加载图片工具
 */

public final class MyImageLoader {

    private MyImageLoader() {
    }

    // 初始化
    private static void init(Context context) {
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context)
                .writeDebugLogs()
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new FIFOLimitedMemoryCache(16 * 1024 * 1024))
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple());
        ImageLoaderConfiguration configuration = builder.build();
        ImageLoader.getInstance().init(configuration);
    }

    // 清除图片缓存
    public static void clearCache() {
        ImageLoader.getInstance().clearMemoryCache();
        ImageLoader.getInstance().clearDiskCache();
    }

    // ImageLoader异步加载图片
    public static void loadImage(ImageView imageView, String url, int resId, ImageScaleType type, Bitmap.Config config, ImageLoadingListener listener) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .imageScaleType(type)
                .bitmapConfig(config)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(resId)// 加载图片过程中显示
                .showImageForEmptyUri(resId)// 路径空
                .showImageOnFail(resId)// 加载失败图片
                .build();
        ImageLoader.getInstance().displayImage(url, imageView, options, listener);
    }

    // 加载图片监听
    public static void loadImageListener(ImageView imageView, String url, int resId, Bitmap.Config config, ImageLoadingListener listener) {
        loadImage(imageView, url, resId, ImageScaleType.EXACTLY, config, listener);
    }

    // 加载原图
    public static void loadImage(ImageView imageView, String url, int resId) {
        loadImage(imageView, url, resId, ImageScaleType.EXACTLY, Bitmap.Config.ARGB_8888, null);
    }

    // 加载缩略图
    public static void loadThumbnail(ImageView imageView, String url, int resId) {
        loadImage(imageView, url, resId, ImageScaleType.EXACTLY, Bitmap.Config.RGB_565, null);
    }

}
