package store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomUtils {
    public static String cacheImgFolder = Environment
            .getExternalStorageDirectory() + "/Fushionbaby/image";
    public final static File SD_CARD_TEMP_DIR = new File(cacheImgFolder,
            "/TEMP_IMG.jpg");
    public static String cacheImg = Environment.getExternalStorageDirectory()
            + "/aladingshop";

    public static void initFolder() {
        createFolder(cacheImgFolder);
    }

    public static void createFolder(String f) {
        File folder = new File(f);
        if (!folder.exists()) {
            try {
                folder.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * 递归删除文件和文件夹
     *
     * @param file 要删除的根目录
     */
    public static void clearCache(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                clearCache(f);
            }
            file.delete();
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param fontScale（DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param fontScale（DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }

    /**
     * 提示用户去登录
     */
    public static void loginDilog(String message, String title, final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setPositiveButton("登录", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
//				context.startActivity(new Intent(context,LoginActivity.class));
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * 判断是否是合法手机号
     *
     * @param mobiles
     * @return 是否合法
     */
    public static boolean isMobileNO(String mobiles) {

        // Pattern p = Pattern.compile("^13[0-9]{9}|15[0-9]{9}|14[0-9]{9}|18[0-9]{9}|16[0-9]{9}|17[0-9]{9}|19[0-9]{9}|11[0-9]{9}|12[0-9]{9}$");
        Pattern p = Pattern.compile("^1\\d{10}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 判断是否是合法身份证号
     *
     * @param mobiles
     * @return 是否合法
     */
    public static boolean isIdCard(String idCard) {

//		Pattern p1 = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X|x)$");
        Pattern p1 = Pattern.compile("^(\\d{14}|\\d{17})(\\d|[xX])$");
        Matcher m1 = p1.matcher(idCard);
        return m1.matches();
    }

    /**
     * @param @param  imagev
     * @param @param  w
     * @param @param  ev
     * @param @return 设定文件
     * @return boolean 返回类型
     * @throws
     * @Title: checkInteraptorTouch
     * @Description: (判断点击是否在popupwindow外)
     */
    private boolean checkInteraptorTouch(Button imagev, PopupWindow w,
                                         MotionEvent ev) {
        if (w == null || ev == null || !w.isShowing()) {
            return false;
        }
        View v = w.getContentView();
        if (v == null) {
            return false;
        }

        int[] xy = new int[2];
        v.getLocationOnScreen(xy);

        int tx = (int) ev.getX();
        int ty = (int) ev.getY();
        // 触摸点在对话框外围
        if (xy[0] < tx && xy[1] < ty && tx > xy[0] + v.getWidth()
                && ty > xy[1] + v.getHeight()) {
            return false;
        } else {
            imagev.getLocationOnScreen(xy);

            int imageW = xy[0] + imagev.getWidth();
            int imageH = xy[1] + imagev.getHeight();
            if (ev.getRawX() >= xy[0] && ev.getRawX() <= imageW
                    && ev.getRawY() >= xy[1] && ev.getRawY() <= imageH)
                return true;
            return false;
        }
    }
}
