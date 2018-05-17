package store.chinaotec.com.medicalcare.utill;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.File;

import okhttp3.Call;
import okhttp3.Response;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.javabean.UpdateBean;

import static store.chinaotec.com.medicalcare.shopmarket.logic.home.activity.MainActivity.sid;

/**
 * Created by HYY on 2018/1/25.
 */

public class UpdateVersionUtil {
    //下载器
    private static DownloadManager downloadManager;
    //下载的ID
    private static long downloadId;
    private static Activity sActivity;
    private static String appUploadUrl = "";//下载地址
    private static int version = 0;//安装之后的版本
    private static String downloadfileName;


    /**
     * 更新app版本
     *
     * @param activity
     */
    public static void checkUpdate(Activity activity) {
        try {
            sActivity = activity;
            //初始化sp对象
            SharedPreferences sharedPreferences = sActivity.getSharedPreferences(ResourseSum.Medica_SP, sActivity.MODE_PRIVATE);
            //获取sid
            sid = sharedPreferences.getString("sid", "");
            //获取mac地址
            String macIp = BaseUtill.getMacAddres(MyApp.getContext());
            int versionNum = MyCommonUtil.getVersionCode(sActivity);
            downloadfileName = uploadFileName(versionNum);
            OkGo.post(MyUrl.AUTO_UPDATE).params("sourceCode", 1)//
                    .params("versionNum", versionNum)//
                    .params("sid", sid)//
                    .params("macAddr", macIp)//
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            Gson gson = MyApp.getGson();
                            final UpdateBean updateBean = gson.fromJson(s, UpdateBean.class);
                            if (updateBean.getResponseCode() == 0) {
                                //提示下载
                                //判断是否下载过
                                version = updateBean.getData().getVersion();
                                String content = updateBean.getData().getContent();
                                String name = updateBean.getData().getName();
                                // 需要更新
                                appUploadUrl = updateBean.getData().getUpdateUrl();
                                downloadfileName = uploadFileName(version);
                                Popup(content, name);
                            } else {
                                //不需要下载
                                //判断APK是否存在如果存在就删掉
                                File downloadFile = getDownloadFile();
                                if (downloadFile.exists()) {
                                    FileUtils.deleteFile(downloadFile);
                                }
                            }
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 提示更新
     *
     * @param message 提示内容
     * @param title   标题
     */
    private static void Popup(String message, final String title) {
        new AlertDialog.Builder(sActivity).setTitle("版本更新").setMessage(message).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downloadAPK(title);
            }
        }).setNegativeButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    //下载apk
    private static void downloadAPK(final String title) {
        if (getDownloadFile().exists()) {
            installAPKV2();
        } else {
            //执行下载
            if (!MyCommonUtil.isEmpty(appUploadUrl)) {
                //创建下载任务
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(appUploadUrl));
//            //设置在什么网络情况下进行下载
//            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
                //移动网络情况下是否允许漫游
                request.setAllowedOverRoaming(false);
                request.setTitle(title);
                //request.setDescription("Apk Downloading");
                request.setVisibleInDownloadsUi(true);
                //在通知栏显示下载进度
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                } else {
                    //在通知栏中显示，默认就是显示的
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                }
                //设置下载的路径
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, downloadfileName);
                //获取DownloadManager
                downloadManager = (DownloadManager) sActivity.getSystemService(Context.DOWNLOAD_SERVICE);
                //将下载请求加入下载队列，加入下载队列后会给该任务返回一个long型的id，通过该id可以取消任务，重启任务、获取下载的文件等等
                downloadId = downloadManager.enqueue(request);

                //注册广播接收者，监听下载状态
                sActivity.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            }
        }

    }


    //广播监听下载的各个状态
    private static BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkStatus();
        }
    };

    //检查下载状态
    private static void checkStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        //通过下载的id查找
        query.setFilterById(downloadId);
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                //下载暂停
                case DownloadManager.STATUS_PAUSED:
                    break;
                //下载延迟
                case DownloadManager.STATUS_PENDING:
                    break;
                //正在下载
                case DownloadManager.STATUS_RUNNING:
                    break;
                //下载完成
                case DownloadManager.STATUS_SUCCESSFUL:
                    //下载完成安装APK
                    installAPKV2();
                    break;
                //下载失败
                case DownloadManager.STATUS_FAILED:
                    ToastUtil.showToast(sActivity, "下载失败");
                    break;
            }
        }
        c.close();
    }


    /**
     * 获取下载文件路径
     *
     * @return
     */
    private static File getDownloadFile() {
        File apkFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), downloadfileName);
        return apkFile;
    }

    /**
     * 7.0兼容
     */
    private static void installAPKV2() {
        File apkFile = getDownloadFile();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 24) {
            Uri apkUri = FileProvider.getUriForFile(sActivity, "store.chinaotec.com.medicalcare.provider", apkFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        sActivity.startActivity(intent);
    }


    /**
     * 设置下载apk名字
     *
     * @param versionNum
     * @return
     */
    private static String uploadFileName(int versionNum) {
        return "medical" + "_" + versionNum + ".apk";
    }


}
