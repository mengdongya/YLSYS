package store.chinaotec.com.medicalcare.utill;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hxk on 2017/10/26 0026 16:46
 */

public class SpUtill {
    /**
     * @param context 上下文对象
     * @param spName  sp文件名字
     * @param key     存入数据键名
     * @param value   存入的字符串数据
     *                存字符串数据到sp
     */

    public static void putString(Context context, String spName, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(spName, context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).apply();
    }

    /**
     * @param context 上下文对象
     * @param spName  sp文件名字
     * @param key     存入数据键名
     * @return 存入的字符串
     * 从sp获取字符串数据
     */

    public static String getString(Context context, String spName, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(spName, context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key, "");
        return value;
    }

    /**
     * @param context 上下文对象
     * @param spName  sp文件名字
     * @param key     存入数据键名
     * @param value   存入的整型数据
     *                存整型数据到sp
     */
    public static void putInt(Context context, String spName, String key, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(spName, context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(key, value).apply();
    }

    /**
     * @param context 上下文对象
     * @param spName  sp文件名字
     * @param key     存入数据键名
     * @return 存入的整型数据
     * 从sp获取整型数据
     */
    public static int getInt(Context context, String spName, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(spName, context.MODE_PRIVATE);
        int value = sharedPreferences.getInt(key, ResourseSum.default_value);
        return value;
    }

    /**
     * @param context 上下文对象
     * @param spName  sp文件名字
     * @param key     存入数据键名
     * @param value   存入的布尔数据
     *                存存布尔据到sp
     */
    public static void putBoolen(Context context, String spName, String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(spName, context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    /**
     * @param context 上下文对象
     * @param spName  sp文件名字
     * @param key     存入数据键名
     * @return 存入的布尔数据
     * 从sp获取布尔数据
     */
    public static boolean getBoolen(Context context, String spName, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(spName, context.MODE_PRIVATE);
        boolean value = sharedPreferences.getBoolean(key, false);
        return value;
    }
}
