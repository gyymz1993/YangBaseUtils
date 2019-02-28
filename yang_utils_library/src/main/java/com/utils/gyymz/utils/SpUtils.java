package com.utils.gyymz.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

/**
 * @author: gyymz1993
 * 创建时间：2017/3/31 13:19
 **/
public class SpUtils {
    public static SharedPreferences sharedPreferences;
    public static SpUtils spUtils;

    private SpUtils() {

    }

    public static SpUtils getInstance() {
        if (spUtils == null) {
            spUtils = new SpUtils();
        }
        return spUtils;
    }

    /**
     * @param key
     * @param object
     */
    public static void putObject(String key, Object object) {
        sharedPreferences.edit().putString(key, new Gson().toJson(object)).commit();
    }

    /**
     * @param key
     * @param clas Class
     * @return T
     */
    public static <T> T getObject(String key, Class<T> clas) {
        String configJson = sharedPreferences.getString(key, null);
        if (!TextUtils.isEmpty(configJson)) {
            return new Gson().fromJson(configJson, clas);
        } else {
            return null;
        }
    }

    /**
     * @param key
     * @param value ֵ
     */
    public static void putInt(String key, int value) {
        sharedPreferences.edit().putInt(key, value).commit();
    }

    /**
     * @param key
     * @param defaultValue
     * @return ֵ
     */
    public static int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    /**
     * 存储long型数据
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putLong(Context context, String key, long value) {
        sharedPreferences.edit().putLong(key, value).commit();//去定义一个常量
    }

    public static Long getLong(Context context, String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    public static Long getLong(Context context, String key) {
        return getLong(context, key, 1l);
    }

    public void init(Context context) {
        sharedPreferences = context
                .getSharedPreferences("config", 0);
    }

    /**
     * 得到配置文件
     *
     * @return
     */
    public SharedPreferences getSp() {
        return sharedPreferences;
    }

    /**
     * 保存string值到sp文件
     *
     * @param key
     * @param value
     */
    public void saveString(String key, String value) {
        sharedPreferences.edit().putString(key, value).commit();
    }

    public void removeKey(String key) {
        sharedPreferences.edit().remove(key).commit();
    }

    /**
     * 保存boolean键值到sp文件
     *
     * @param key
     * @param value
     */
    public void saveBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    /**
     * 得到boolean值
     *
     * @param key
     * @param defValue
     */
    public boolean getBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    /**
     * 取字符串，默认“”
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    /* get and put long value */
    public void saveLong(String key, long value) {
        sharedPreferences.edit().putLong(key, value).commit();

    }

    public long getLong(String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    /* get and put int value */
    public void setIntValue(String key, int value) {
        sharedPreferences.edit().putInt(key, value).commit();
    }

    public int getIntValue(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

}
