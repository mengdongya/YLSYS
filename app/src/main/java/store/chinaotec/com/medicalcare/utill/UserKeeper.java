package store.chinaotec.com.medicalcare.utill;

import android.content.Context;
import android.content.SharedPreferences;

import java.math.BigDecimal;

import store.chinaotec.com.medicalcare.shopmarket.logic.user.model.User;

/**
 * Created by HZ on 2015/10/9.
 */
public class UserKeeper {
    private static final String USER = "user";
    private static final String KEY_USEID = "memberId";
    private static final String KEY_LOGID = "sid";
    private static final String KEY_USERNAME = "loginName";
    private static final String KEY_NICKNAME = "nickName";
    private static final String KEY_SCORE = "epoints";
    private static final String KEY_HEADICON = "imgPath";
    private static final String KEY_TOTLEMONEY = "walletMoney";
    private static final String KEY_USEMONEY = "availableMoney";
    private static final String KEY_LEVEL = "gradeName";
    private static final String KEY_PHONE = "telephone";

    public static void writeUser(Context tContext, User user) {
        if (tContext == null || user == null) {
            return;
        }

        SharedPreferences pref = tContext.getSharedPreferences(USER, Context.MODE_APPEND);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(KEY_USEID, user.memberId);
        editor.putString(KEY_LOGID, user.sid);
        editor.putString(KEY_USERNAME, user.loginName);
        editor.putString(KEY_NICKNAME, user.nickName);
        editor.putInt(KEY_SCORE, user.epoints);
        editor.putString(KEY_HEADICON, user.imgPath);
        editor.putString(KEY_TOTLEMONEY, user.walletMoney.toString());
        editor.putString(KEY_USEMONEY, user.availableMoney.toString());
        editor.putString(KEY_LEVEL, user.gradeName);
        editor.putString(KEY_PHONE, user.telephone);
        editor.commit();
    }

    public static User readUser(Context tContext) {
        if (tContext == null) {
            return null;
        }

        User user = new User();
        SharedPreferences pref = tContext.getSharedPreferences(USER, Context.MODE_APPEND);
        user.memberId = (pref.getLong(KEY_USEID, 0));
        user.sid = (pref.getString(KEY_LOGID, ""));
        user.loginName = (pref.getString(KEY_USERNAME, ""));
        user.nickName = (pref.getString(KEY_NICKNAME, ""));
        user.epoints = (pref.getInt(KEY_SCORE, 0));
        user.imgPath = (pref.getString(KEY_HEADICON, ""));
        user.walletMoney = new BigDecimal(pref.getString(KEY_TOTLEMONEY, "0"));
        user.availableMoney = new BigDecimal(pref.getString(KEY_USEMONEY, "0"));
        user.gradeName = (pref.getString(KEY_LEVEL, ""));
        user.telephone = (pref.getString(KEY_PHONE, ""));
        return user;
    }

    public static void clear(Context tContext) {
        if (tContext == null) {
            return;
        }

        SharedPreferences pref = tContext.getSharedPreferences(USER, Context.MODE_APPEND);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear().commit();
    }
}
