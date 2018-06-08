package com.sfan.lib.app;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by zhazhiyong on 2018/6/1.
 * Gson解析json
 */
public class GsonUtils {

    private GsonUtils() {
    }

    /**
     * 解析json字符串
     *
     * @param json
     * @param classT
     * @return
     */
    public static <T> T parseJSON(String json, Class<T> classT) {
        T info = null;
        try {
            Gson gson = new Gson();
            info = gson.fromJson(json, classT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 解析json数组
     * <p>
     * Type所在的包：java.lang.reflect
     * TypeToken所在的包：com.google.gson.reflect.TypeToken
     * Type type = new TypeToken<ArrayList<TypeInfo>>(){}.getType();
     * List<TypeInfo> types = GsonUtils.parseJSONArray(jsonArr, type);
     *
     * @param jsonArr
     * @param type
     * @return
     */
    public static <T> T parseJSONArray(String jsonArr, Type type) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonArr, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }


    public static String convertJSON(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

}
