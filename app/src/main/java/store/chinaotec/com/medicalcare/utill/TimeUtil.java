package store.chinaotec.com.medicalcare.utill;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhuhao on 2016/7/25.
 */
public class TimeUtil {
    private static SimpleDateFormat sf = null;

    public static String getDateToString(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sf.format(d);
    }

    public static String getDate(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(d);
    }

    public static String getYMD(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy/MM/dd");
        return sf.format(d);
    }

    public static String getMD(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("MM-dd");
        return sf.format(d);
    }

    public static String getyMdHm(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sf.format(d);
    }

    public static String getyMHm(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy/MM/HH:mm");
        return sf.format(d);
    }

    public static String getMdHm(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("MM-dd HH:mm");
        return sf.format(d);
    }

    public static String getHm(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("HH:mm");
        return sf.format(d);
    }

    public static Date getDateFromString(String string) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(string);
        } catch (ParseException e) {
            return null;
        }
    }


    public static long getTimeFromString(String string) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            return sdf.parse(string).getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    /**
     * 返回当前系统时间(格式以HH:mm:ss形式)
     */
    public static String getDateForHHmm() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(new Date());
    }
}
