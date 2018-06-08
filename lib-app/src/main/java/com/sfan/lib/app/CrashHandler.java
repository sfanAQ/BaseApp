package com.sfan.lib.app;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhazhiyong on 2018/6/1.
 * 程序崩溃处理
 */

public class CrashHandler implements UncaughtExceptionHandler {

    /**
     * CrashHandler实例
     */
    private static CrashHandler instance;

    /**
     * 程序的Context对象
     */
    private Context mContext;

    /**
     * Map表：用来存储设备信息和异常信息
     */
    private Map<String, String> infos = new HashMap<>();

    /**
     * 用于格式化日期，作为日志文件名的一部分
     */
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    /**
     * 获取CrashHandler实例，单例模式
     */
    public static CrashHandler getInstance() {
        if (instance == null) {
            synchronized (CrashHandler.class) {
                instance = new CrashHandler();
            }
        }
        return instance;
    }

    /**
     * CrashHandler构造器：无实现， 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 初始化：设置UncaughtException为程序“未补捉异常”的默认处理器
     */
    public void init(Context context) {
        mContext = context;
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(CrashHandler.this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        // 自定义错误处理,收集错误信息 发送错误报告等操作
        if (ex != null) {
            // 收集设备参数信息
            collectDeviceInfo(mContext);
            // 保存日志文件
            saveCatchInfo2File(ex);
        }
    }

    /**
     * 收集设备参数信息
     *
     * @param context 上下文
     */
    private void collectDeviceInfo(Context context) {
        // 获取包信息
        String versionName = AppUtils.getVersionName(context, PackageManager.GET_ACTIVITIES);
        versionName = versionName == null ? "null" : versionName;
        String versionCode = AppUtils.getVersionCode(context, PackageManager.GET_ACTIVITIES) + "";
        infos.put("versionName", versionName);
        infos.put("versionCode", versionCode);
        // 获取Build的所有信息
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex 错误日志
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCatchInfo2File(Throwable ex) {
        // 拼接字符串
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }
        // 拼接异常信息
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            // 将崩溃信息写入log文件
            String time = formatter.format(new Date());
            long timestamp = System.currentTimeMillis();
            String fileName = "crash-" + time + "-" + timestamp + ".log";
            String path;
            boolean haveSDCard = FileUtils.haveSDCard();
            if (haveSDCard) {
                path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myapp/file/crash";
            } else {
                path = "/myapp/file/crash";
            }
            File dir = new File(path);
            if (!dir.exists()) {
                // 创建
                dir.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(path + File.separator + fileName);
            fos.write(sb.toString().getBytes());
//            // 发送给开发人员
//            sendCrashLog2PM(path + File.separator + fileName);
            fos.close();
            return fileName;
        } catch (Exception e) {
        }
        return null;
    }

//    /**
//     * 将捕获的导致崩溃的错误信息发送给开发人员 目前只将log日志保存在sdcard 和输出到LogCat中，并未发送给后台。
//     */
//    private void sendCrashLog2PM(String fileName) {
//        String content = ""; // 文件内容字符串
//        InputStream input = null;
//        BufferedReader reader = null;
//
//        // 打开文件
//        File file = new File(fileName);
//        // 如果path是传递过来的参数，可以做一个非目录的判断
//        if (file.isDirectory()) {
//        } else {
//            try {
//                input = new FileInputStream(file);
//                if (input != null) {
//                    InputStreamReader inputReader = new InputStreamReader(input);
//                    reader = new BufferedReader(inputReader);
//                    String line;
//                    // 分行读取
//                    while ((line = reader.readLine()) != null) {
//                        content += line + "\n";
//                    }
//                    input.close();
//                }
//            } catch (java.io.FileNotFoundException e) {
//            } catch (IOException e) {
//            } finally { // 关闭流
//                try {
//                    reader.close();
//                    input.close();
//                } catch (IOException e) {
//                }
//            }
//        }
//    }

}
