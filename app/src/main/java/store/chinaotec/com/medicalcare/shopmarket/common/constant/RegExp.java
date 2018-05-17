package store.chinaotec.com.medicalcare.shopmarket.common.constant;


/**
 * @author Lizl
 * @ClassName: RegExp
 * @Description: TODO 正则表达式
 * @date 2014-06-09
 */
public class RegExp {
    /**
     * 用户名
     */
    public static final String USERNAME = "";
    /**
     * 密码
     */
    public static final String PASSWORD = "";
    /**
     * 手机号码
     */
    public static final String PHONE_NUM = "^[1]\\d{10}";
    /**
     * 短信验证码
     */
    public static final String SMS_CODE = "\\d{6}";
    /**
     * 图片连接
     */
    public static final String IMAGE_URL = "\\.(jpg)$";
    /**
     * 身份证
     */
    public static final String IDCARD = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X|x)$";

}
