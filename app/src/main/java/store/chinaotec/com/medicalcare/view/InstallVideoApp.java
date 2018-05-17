package store.chinaotec.com.medicalcare.view;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import store.chinaotec.com.medicalcare.utill.AppUtils;
import store.chinaotec.com.medicalcare.utill.AutoInstall;
import store.chinaotec.com.medicalcare.utill.MyCommonUtil;
import store.chinaotec.com.medicalcare.utill.ToastUtil;

import static store.chinaotec.com.medicalcare.utill.MyCommonUtil.createFile;

/**
 * Created by HYY on 2017/12/5.
 * 安装微视云
 */

public class InstallVideoApp {

    public static void InstallLaunchApp(final LoadingAlertDialog mDialog, final Activity mActivity) {
        mDialog.show("加载中请稍后...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Message message = new Message();
                Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message message) {
                        super.handleMessage(message);
                        if (!AppUtils.isInstallApp(MyCommonUtil.PACKAGENAME)) {
                            if (createFile(mActivity)) {
                                ToastUtil.showToast(mActivity, "请按照指示安装通信插件!!");
                                AutoInstall.setUrl(MyCommonUtil.filePath + "/" + MyCommonUtil.apkName);
                                AppUtils.installApp(MyCommonUtil.filePath + "/" + MyCommonUtil.apkName, "store.chinaotec.com.medicalcare.provider");
                            } else {
                                //友情提示
                                ToastUtil.showToast(mActivity, "生成文件失败,请检查是否允许存储权限!!");
                            }
                        } else {
                            //打开APP
                            AppUtils.launchApp(mActivity, MyCommonUtil.PACKAGENAME, 1000);
                        }
                        mDialog.dismiss();
                    }
                };
                handler.sendMessage(message);
                Looper.loop();
            }
        }).start();
    }

}
