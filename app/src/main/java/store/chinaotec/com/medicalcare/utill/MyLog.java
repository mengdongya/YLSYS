package store.chinaotec.com.medicalcare.utill;


import com.orhanobut.logger.Logger;

/**
 * Created by hxk on 2017/8/17 0017 11:20
 */

public class MyLog {
    public static boolean debug = true;

    public static void d(String message, Object... args) {
        if (debug) {
            Logger.d(message, args);
        }
    }

    public static void d(String tag, String message, Object... args) {
        if (debug) {
            Logger.t(tag).d(message, args);
        }
    }

    public static void i(String message, Object... args) {
        if (debug) {
            Logger.i(message, args);
        }
    }

    public static void i(String tag, String message, Object... args) {
        if (debug) {
            Logger.t(tag).i(message, args);
        }
    }

    public static void w(String message, Object... args) {
        if (debug) {
            Logger.w(message, args);
        }
    }

    public static void w(String tag, String message, Object... args) {
        if (debug) {
            Logger.t(tag).w(message, args);
        }
    }

    public static void json(String json) {
        if (debug) {
            Logger.json(json);
        }
    }

    public static void xml(String xml) {
        if (debug) {
            Logger.xml(xml);
        }
    }

    public static void object(Object object) {
        if (debug) {
            Logger.object(object);
        }
    }
}
