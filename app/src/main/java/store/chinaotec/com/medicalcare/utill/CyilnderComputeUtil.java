package store.chinaotec.com.medicalcare.utill;

/**
 * Created by HYY on 2017/12/27.
 */

public class CyilnderComputeUtil {

    private static Integer h = SizeUtils.dp2px(140);

    public static Integer getComputeNumber(Integer maxInteger, Integer stepNumber) {
        if (h < maxInteger) {
            float a = h;
            float b = maxInteger;
            float hs = a / b;
            float c = hs * stepNumber;
            if (c == 0) {
                return 0;
            }
            return Math.round(c);
        }
        return stepNumber;
    }
}
