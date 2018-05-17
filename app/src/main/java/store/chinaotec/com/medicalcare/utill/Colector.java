package store.chinaotec.com.medicalcare.utill;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxk on 2017/7/18 0018 09:14
 * activity管理器
 */

public class Colector {
    private static List<Activity> list = new ArrayList();

    public static void addActivity(Activity activity) {
        list.add(activity);
    }

    public static void removeActivity(Activity activity) {
        list.remove(activity);
    }

    public static void clearActivity() {
        for (int i = 0; i < list.size(); i++) {
            Activity activity1 = list.get(i);
            activity1.finish();
        }
    }
}
