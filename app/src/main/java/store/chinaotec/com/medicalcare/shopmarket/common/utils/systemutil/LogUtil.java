package store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil;

import android.util.Log;

import store.chinaotec.com.medicalcare.BuildConfig;

/**
 * @author :hulanlan
 * @ClassName: LogUtil
 * @Description: TODO(统一LOG管理)
 * @date 2015-5-12 下午5:06:24
 */
public class LogUtil {
    /**
     * 调试开关，无论是自动还是手动都能很好的控制调试状态 true 为显示log日志，false为不显示（项目上线前必须改成false）
     */
    public static final boolean DEBUG = BuildConfig.DEBUG && true;

    public static void v(String tag, String msg) {
        if (DEBUG)
            Log.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (DEBUG)
            Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (DEBUG)
            Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (DEBUG)
            Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (DEBUG)
            Log.e(tag, msg);
    }

    public static void w(String tag, String msg, Throwable ex) {
        if (DEBUG)
            Log.w(tag, msg, ex);
    }

    public static void e(String tag, String msg, Throwable ex) {
        if (DEBUG)
            Log.e(tag, msg, ex);
    }
}
