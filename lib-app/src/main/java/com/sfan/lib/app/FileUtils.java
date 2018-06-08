package com.sfan.lib.app;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by zhazhiyong on 2018/6/1.
 * 文件
 */

public final class FileUtils {

    private FileUtils() {
    }

    /**
     * 是否有SD卡
     *
     * @return true有，false没有
     */
    public static boolean haveSDCard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // 有SD卡
            return true;
        } else {
            return false;
        }
    }

    /**
     * 从Assets文件夹中复制文件
     *
     * @param context 上下文
     * @param oldPath Assets中文件或目录
     * @param newPath 外部文件或目录
     * @throws Exception 异常
     */
    public static void copyFilesFromAssets(Context context, String oldPath, String newPath) throws Exception {
        // 获取assets目录下的所有文件及目录名
        String fileNames[] = context.getAssets().list(oldPath);
        if (fileNames.length > 0) {// 如果是目录，则递归
            for (String filePath : fileNames) {
                copyFilesFromAssets(context, oldPath + File.separator + filePath, newPath + File.separator + filePath);
            }
        } else {// 文件
            // 开始复制文件
            File file = createNewFile(newPath);
            InputStream is = context.getAssets().open(oldPath);
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {//循环从输入流读取 buffer字节
                fos.write(buffer, 0, len);//将读取的输入流写入到输出流
            }
            fos.flush();//刷新缓冲区
            fos.close();
            is.close();
        }
    }

    /**
     * 从Assets文件夹中读取文件内容
     *
     * @param context  上下文
     * @param fileName Assets中文件或目录
     * @return 文件内容
     * @throws Exception 异常
     */
    public static String readAssetsFileToString(Context context, String fileName) throws Exception {
        StringBuffer buffer = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName), "UTF-8"));
        String str;
        while ((str = br.readLine()) != null) {
            buffer.append(str);
        }
        br.close();
        return buffer.toString().trim();
    }

    /**
     * 创建文件及目录
     *
     * @param filePath 文件或目录全路径名称
     * @return 文件
     */
    public static File createNewFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {//判断是否存在
            if (file.isDirectory()) {//目录
                // 创建目录
                file.mkdirs();
            } else {// 文件
                // 目录
                String folderPath = file.getParent();
                if (!TextUtils.isEmpty(folderPath)) {
                    File folder = new File(folderPath);
                    if (!folder.exists()) {
                        // 创建目录
                        folder.mkdirs();
                    }
                }
                // 创建文件
                file.createNewFile();
            }
        }
        return file;
    }

    /**
     * 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换
     */
    public static String getImageAbsolutePath(Context context, Uri imageUri) {
        if (context == null || imageUri == null)
            return null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
            if ("com.android.externalstorage.documents".equals(imageUri.getAuthority())) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if ("com.android.providers.downloads.documents".equals(imageUri.getAuthority())) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if ("com.android.providers.media.documents".equals(imageUri.getAuthority())) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split("\\:");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(imageUri.getScheme())) {// MediaStore
            // (and general)
            if ("com.google.android.apps.photos.content".equals(imageUri.getAuthority()))// Return
                // the remote address
                return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        } else if ("file".equalsIgnoreCase(imageUri.getScheme())) {// File
            return imageUri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

}
