/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package store.chinaotec.com.medicalcare.utill;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class FileUtil {
    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "pic.jpg");
        return file;
    }

    public static File getSaveFile() {
        File file = new File(FileUtils.getSdPath() + "/" + "temp.jpg");
        return file;
    }

    /**
     * 支持7.0
     *
     * @param context
     * @param file
     * @return
     */
    public static Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), "store.chinaotec.com.medicalcare.provider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    public static byte[] getBitmap(String localPath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap tBitmap = BitmapFactory.decodeFile(localPath);

        final int width = tBitmap.getWidth();
        final int height = tBitmap.getHeight();
        options.inSampleSize = 1;
        if (height > 1280 || width > 720) {
            final int heightRatio = Math.round((float) height / (float) 720);
            final int widthRatio = Math.round((float) width / (float) 1280);
            options.inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        options.inJustDecodeBounds = false;
        tBitmap.recycle();
        tBitmap = BitmapFactory.decodeFile(localPath, options);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        tBitmap.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        tBitmap.recycle();
        return baos.toByteArray();
    }
}
