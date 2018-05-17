package store.chinaotec.com.medicalcare.utill;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 常用工具
 *
 * @author Melon
 */
public class MyCommonUtil {
    public static String PACKAGENAME = "com.evmtv.microvideocloud";
    public static String apkName = "MicroVideoColud.apk";
    public static String filePath = FileUtils.getSdPath() + "/ylsys/";
    /**
     * 版本代号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }
    /**
     * 空判断
     */
    public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0 || "null".equals(str)) return true;
        else return false;
    }

    /**
     * 创建文件夹
     */
    public static boolean createFile(Context context) {
        File destDir = new File(filePath);
        if (!destDir.exists()) {
            destDir.mkdirs();
            ToastUtil.showToast(context, filePath);

        }
        if (FileUtils.createOrExistsDir(filePath)) {
            if (!FileUtils.isFile(filePath + apkName)) {
                MyCommonUtil.setCopyFile(context);
            }
            return true;
        }
        return false;
    }

    /**
     * 拷贝数据
     *
     * @param context
     */
    public static void setCopyFile(Context context) {
        InputStream istream = null;
        OutputStream ostream = null;
        try {
            AssetManager am = context.getAssets();
            istream = am.open(apkName);
            ostream = new FileOutputStream(filePath + apkName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = istream.read(buffer)) > 0) {
                ostream.write(buffer, 0, length);
            }
            istream.close();
            ostream.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (istream != null) istream.close();
                if (ostream != null) ostream.close();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }
}
