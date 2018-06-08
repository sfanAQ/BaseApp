package com.sfan.lib.app;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zhazhiyong on 2018/6/1.
 * SP存储
 */

public class SharedPreferencesUtils {

    private SharedPreferences sharedPreferences;

    public SharedPreferencesUtils(Context context, String spsName) {
        sharedPreferences = context.getSharedPreferences(spsName, Context.MODE_PRIVATE);
    }

    public String readString(String key, String defValue) {
        String rStr = sharedPreferences.getString(key, defValue);
        return rStr;
    }

    public void writeString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public boolean readBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    public void writeBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public int readInteger(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    public void writeInteger(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public long readLong(String key) {
        return sharedPreferences.getLong(key, 0L);
    }

    public void writeLong(String key, long value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

}
