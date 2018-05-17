package store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

//import com.lzl.util.image.MyUtils;

public class DialUtil {
    private Context context;

    public DialUtil(Context context) {
        this.context = context;
    }

    /**
     * 拨打电话
     */
    public Intent getTel(String phoneNum) {
        Intent intent = null;
        // 如果输入不为空创建打电话的Intent
        if (phoneNum != null && phoneNum.trim().length() != 0) {
            try {
                // 直接拨打电话
                // String action = Intent.ACTION_CALL;
                // 进入拨号界面
                String action = Intent.ACTION_DIAL;
                Uri uri = Uri.parse("tel:" + phoneNum);
                intent = new Intent(action, uri);
            } catch (ActivityNotFoundException e) {
                // 如果是pad 没有拨打电话界面或功能, 则抛出此异常
                e.printStackTrace();
            }
        }
        return intent;
    }

    /**
     * 发送短信
     */
    public Intent getSMS(String phoneNum) {
        Intent intent = null;
        if (phoneNum != null && phoneNum.trim().length() != 0) {
            try {
                // 设置 电话号码
                Uri smsToUri = Uri.parse("smsto:" + phoneNum);
                intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
                // 设置预编辑短信内容
                intent.putExtra("sms_body", "");
            } catch (ActivityNotFoundException e) {
                // TODO: handle exception
                e.printStackTrace();
                // Toast.makeText(UserInfoDailyActivity.this, "", duration)
            }
        }
        return intent;
    }

    /**
     * 打开相机
     *
     * @param uriSave 图片保存路径
     */
    public Intent getCamera(Uri uriSave) {

        // 调用系统的拍照功能
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 指定调用相机拍照后照片的储存路径
//		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(MyUtils.SD_CARD_TEMP_DIR));
        return intent;
    }

    /**
     * 打开相册
     */
    public Intent getAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        return intent;
    }

    /**
     * 剪切照片
     *
     * @param uriPhone 被剪切图片路径
     * @param uriSave  剪切后保存路径
     * @param aspectX  裁剪框比例--宽
     * @param aspectY  裁剪框比例--高
     * @param outputX  输出图片大小--宽
     * @param outputY  输出图片大小--高
     * @return
     */
    public Intent getPhotoShear(Uri uriPhone, int aspectX, int aspectY, int outputX, int outputY) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uriPhone, "image/*");

        // 可裁剪
        intent.putExtra("crop", "true");
        // 使用比例
        intent.putExtra("scale", true);
        // 裁剪框比例
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        // 输出图片大小
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        // 是否返回数据
        intent.putExtra("return-data", true);
        // 剪切后保存路径
        // String path = MyUtils.cacheImgFolder;
//		MyUtils.initFolder();
        // final File SD_CARD_TEMP_DIR = new File(path, "/TEMP_IMG.jpg");

//		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(MyUtils.SD_CARD_TEMP_DIR));
//
        // 返回数据格式
        // intent.putExtra("outputFormat",
        // Bitmap.CompressFormat.JPEG.toString());

        // 是否启用人脸识别
        // intent.putExtra("noFaceDetection", false);

        return intent;
    }

    public Bitmap decodeUriAsBitmap(Uri uri) {
        try {
            return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 图片转化字符串 添加图片回收 及流的关闭
     *
     * @param bitmap
     */
    public String getBitmapToBase64(Bitmap bitmap, int dstWidth, int dstHeight) {

        // ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        // return baos.toByteArray();

        Bitmap bmpCompressed = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmpCompressed.compress(CompressFormat.JPEG, 100, bos);
        byte[] data;
        String jpgStr;
        data = bos.toByteArray();
        jpgStr = Base64.encodeToString(data, Base64.DEFAULT);
        if (jpgStr != null && !jpgStr.equals("")) {
            // 关闭bos流
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.gc();
            }
            return jpgStr;
        } else {
            return "";
        }
    }

}
