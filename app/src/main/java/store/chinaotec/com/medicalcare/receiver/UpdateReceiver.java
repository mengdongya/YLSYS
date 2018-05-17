package store.chinaotec.com.medicalcare.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UpdateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
            //现有程序被其他版本替换安装后删除安装包
            String packageName = intent.getDataString();
//            String path = ResourseSum.APP_PATH;
//            File files = new File(path);
//            File[] fileAry = files.listFiles();
//            for (int i = 0; i < fileAry.length; i++) {
//                File file = fileAry[i];
//                if (file.isFile()) {
//                    file.delete();
//                }
//            }
        }
    }
}
