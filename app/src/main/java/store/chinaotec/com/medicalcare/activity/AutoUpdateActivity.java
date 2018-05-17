package store.chinaotec.com.medicalcare.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.io.File;

import okhttp3.Call;
import okhttp3.Response;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.UpdateBean;
import store.chinaotec.com.medicalcare.utill.BaseUtill;
import store.chinaotec.com.medicalcare.utill.ResourseSum;

public class AutoUpdateActivity extends BaseActivity {

    private int versionCode;
    private String sid;
    private String macIp;
    private RationaleListener mRationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            new AlertDialog.Builder(AutoUpdateActivity.this)
                    .setTitle("友好提醒！")
                    .setMessage("没有访问权限将不能为您提供更好的服务，请允许获取权限")
                    .setPositiveButton("好", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.resume();// 用户同意继续申请。
                        }
                    })
                    .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.cancel(); // 用户拒绝申请。
                        }
                    }).show();
        }
    };

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode) {
            if (requestCode == 100) {

            } else if (requestCode == 101) {
                autoUptate();
            }
        }

        @Override
        public void onFailed(int requestCode) {
            if (requestCode == 100) {
            } else if (requestCode == 101) {
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_update);
        initBaseData();

    }

    private void autoUptate() {
        OkGo.post(MyUrl.AUTO_UPDATE).params("sourceCode", 1)
                .params("versionNum", versionCode).params("sid", sid)
                .params("macAddr", macIp).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Gson gson = MyApp.getGson();
                final UpdateBean updateBean = gson.fromJson(s, UpdateBean.class);
                if (updateBean.getResponseCode() == 0) {
                    //有新的版本更新弹窗提示
                    AlertDialog update = new AlertDialog.Builder(AutoUpdateActivity.this).create();
                    update.setTitle(updateBean.getData().getContent());
                    update.setCancelable(false);
                    //点击"确定"下载最新版本程序安装
                    update.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //下载路径
                            final String updateUrl = updateBean.getData().getUpdateUrl();
                            //更新时间  做为文件名
                            final String updateTime = updateBean.getData().getUpdateTime();
                            //下载最新版本的app文件的保存路径
                            final String path = ResourseSum.APP_PATH;
                            //开始下载最新版本的app文件
                            beginDownloadApp(updateUrl, updateTime, path);
                        }
                    });
                    //点击"取消"不下载安装最新的程序
                    update.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(MyApp.getContext(), HomeActivity.class));
                        }
                    });
                    update.show();
                } else if (updateBean.getResponseCode() == 203) {  //当前是最新版本
//                    BaseUtill.toastUtil(updateBean.getMsg());
                    startActivity(new Intent(MyApp.getContext(), HomeActivity.class));
                }
            }
        });
//        startActivity(new Intent(MyApp.getContext(), HomeActivity.class));
    }

    private void beginDownloadApp(final String updateUrl, final String updateTime, final String path) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkGo.get(updateUrl).execute(new FileCallback(path, updateTime) {
                    @Override
                    public void onSuccess(File file, Call call, Response response) {
                        String absolutePath = file.getAbsolutePath();
                        BaseUtill.addPersion(absolutePath);
                        BaseUtill.install(AutoUpdateActivity.this, file);
                        startActivity(new Intent(MyApp.getContext(), HomeActivity.class));
                    }

                    @Override
                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        AlertDialog alertDialog1 = new AlertDialog.Builder(AutoUpdateActivity.this).create();
                        alertDialog1.setTitle("开始下载....");
                        alertDialog1.setCancelable(false);
                        alertDialog1.show();
                        View inflate = LayoutInflater.from(AutoUpdateActivity.this).inflate(R.layout.update_dialog, null);
                        ProgressBar updateProgres = (ProgressBar) inflate.findViewById(R.id.update_progres);
                        updateProgres.setProgress((int) (Math.round(progress * 10000) * 1.0f / 100));
                        alertDialog1.setContentView(inflate);
                        //下载完后下载提示弹窗关闭消失
                        if (currentSize == totalSize) {
                            alertDialog1.dismiss();
                        }
                    }
                });
            }
        }).start();
    }

    private void initBaseData() {
        //初始化sp对象
        SharedPreferences sharedPreferences = getSharedPreferences(ResourseSum.Medica_SP, MODE_PRIVATE);
        //获取当前安装的应用信息
        PackageInfo nowPackageinfo = BaseUtill.getNowPackageinfo(this);
        if (nowPackageinfo != null) {
            //获取当前应用的版本号
            versionCode = nowPackageinfo.versionCode;
        }
        //获取sid
        sid = sharedPreferences.getString("sid", "");
        //获取mac地址
        macIp = BaseUtill.getMacAddres(MyApp.getContext());

        checkLocalPermission();
    }

    private void checkLocalPermission() {
        AndPermission.with(this)
                .requestCode(101)
                .permission(android.Manifest.permission.ACCESS_COARSE_LOCATION)
                .rationale(mRationaleListener)
                .send();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // 只需要调用这一句，剩下的AndPermission自动完成。
        AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults, listener);
    }
}
