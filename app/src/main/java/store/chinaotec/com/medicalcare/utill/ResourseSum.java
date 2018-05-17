package store.chinaotec.com.medicalcare.utill;

import android.content.Context;

import store.chinaotec.com.medicalcare.MyApp;

public class ResourseSum {
    public static String NEWS_PUSH_SWITCH = "news_push";
    public static String DOCTOR_REPLY_REMINDER = "doctor_reply_reminder";
    public static String HEALTH_PLAN_PUSH = "health_plan_push";
    public static String PRODUCT_NEWS_PUSH = "product_news_push";

    //保存信息的SP文件名字
    public static String Medica_SP = "save_login";
    //SP文件获取数据时,没有数据时默认返回的值
    public static int default_value = 500;
    //保存最新版本app文件的路径
    public static String APP_PATH = MyApp.getContext().getDir("update", Context.MODE_PRIVATE).getAbsolutePath();

    public static int LOGIN_RESPONSE = 1;
    public static int ADD_PATIENT = 2;
    public static int ADD_HEALTH_PERSON = 3;
    public static int LOGIN_OUT_RESPONSE = 4;
    public static int TURN_TO_UPDATE = 0;
    public static int TURN_TO_ADD_CONTACT = 5;
    public static final int DATAREQUEST = 6;
    public static int TURN_TO_ADD_INFO = 7;
    public static int TURN_TO_DISEASE = 8;
    public static String REFRESH_HEALTH_FRAGMENT = "refresh_fragment";
    public static String REFRESH_DISEASE_FRAGMENT = "refresh_disease_fragment";
}
