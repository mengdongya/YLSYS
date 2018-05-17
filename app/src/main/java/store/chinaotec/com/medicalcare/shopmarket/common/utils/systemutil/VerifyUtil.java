package store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil;

import android.content.Context;

import java.util.List;

/**
 * @author Lizl
 * @ClassName: VerifyUtil
 * @Description: TODO 文本验证
 * @Description: 方法isVerify 判断 null & RegExp
 * @Description: 方法isEmpyStr 判断 null
 * @date 2014-06-09
 */
public class VerifyUtil {

    /**
     * @param context     当前activity上下文
     * @param content     判断的内容
     * @param tipIdNull   为null提示文字
     * @param tipIdRegExp 正则不匹配提示文字
     * @return Returns : true =>>"null" or "length is zero";
     * @Title: isEmpyStr
     * @Description: TODO 判断是否为空
     */
    public static boolean isEmpyStr(Context context, String content, int tipIdNull) {
        if (content == null || content.isEmpty()) {
            ToastUtil.MyToast(context, tipIdNull);
            return true;
        }
        return false;
    }

    /**
     * @return Returns : true =>>"null" or "length is 0";
     * @Title: isEmpyStr
     * @Description: TODO 判断是否为空
     */
    public static boolean isEmpyty(String content) {
        if (content == null) {
            return true;
        }
        if (content.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * @param context     当前activity上下文
     * @param content     判断的内容
     * @param strRegExp   正则表达式(如为null则不判断正则)
     * @param tipIdNull   为null提示文字
     * @param tipIdRegExp 正则不匹配提示文字
     * @return Returns : true =>>通过验证;
     * @Title isVerify
     * @Description TODO 判断是否为空
     */
    public static boolean isVerify(Context context, String content, String strRegExp, int tipIdNull, int tipIdRegExp) {
        if (isEmpyStr(context, content, tipIdNull)) {
            return false;
        }

        if (strRegExp != null && !content.matches(strRegExp)) {
            ToastUtil.MyToast(context, tipIdRegExp);
            return false;
        }
        return true;
    }

    public static boolean isEmpty(@SuppressWarnings("rawtypes") List list) {
        if (list == null) {
            return true;
        }

        if (list.isEmpty()) {
            return true;
        }
        return false;
    }
}
