package store.chinaotec.com.medicalcare.utill;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.IOException;
import store.chinaotec.com.medicalcare.MyApp;

/**
 * Created by hxk on 2017/9/30 0030 16:08
 * 自主诊疗沟通页面解析xml汇总
 */

public class VoiceAnyUtill {
    /**
     * @param sharedPreferences SP文件对象
     * @param s                 调用AddCase创建病历接口返回的XML字符串
     *                          解析创建病例号后返回的xml字符串
     */
    public static void anysConnectInter(SharedPreferences sharedPreferences, String s) {

    }

    /**
     * @param width   调用ocr文字识别时所传图片的 宽
     * @param height  调用ocr文字识别时所传图片的 高
     * @param uri     调用ocr文字识别时所传图片的 uri
     *                获取ocr文字识别所传图片的宽  高值
     */
    public static void getOCRphotoSize(int width, int height, Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(MyApp.getContext().getContentResolver(), uri);
            //图片宽
            width = bitmap.getWidth();
            //图片高
            height = bitmap.getHeight();
            MyLog.d("拍照或者相册中选取的照片宽高..width" + width + "..height.." + height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
