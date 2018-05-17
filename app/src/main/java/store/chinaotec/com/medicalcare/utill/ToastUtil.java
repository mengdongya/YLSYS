package store.chinaotec.com.medicalcare.utill;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {


    /**
     * 更加有效的控制弹出不重复
     */
    private static Toast toast;

    public static void showToast(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
