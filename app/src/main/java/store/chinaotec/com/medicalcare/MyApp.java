package store.chinaotec.com.medicalcare;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.iflytek.cloud.SpeechUtility;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.lzy.okgo.OkGo;
import com.orhanobut.logger.LogBuilder;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.MyLogStyle;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.Constant;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.MyImageLoader;
import store.chinaotec.com.medicalcare.utill.CrashHandler;
import store.chinaotec.com.medicalcare.utill.GlideImageLoader;
import store.chinaotec.com.medicalcare.utill.Utils;


public class MyApp extends Application {

    private static MyApp instance;
    private static Handler mHandler;
    private static Gson mGson;
    private Display display;
    private TelephonyManager tm;

    //返回主线程的Handler
    public static Handler getHandler() {
        return mHandler;
    }

    public static MyApp getContext() {
        return instance;
    }

    public static Gson getGson() {
        return mGson;
    }

    public String exportPath = "";

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化讯飞语音识别对象
        SpeechUtility.createUtility(MyApp.this, "appid=" + getString(R.string.itef_app_id));
//        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=" + getString(R.string.itef_app_id));
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);
        MyImageLoader.initImageLoader(this, Constant.cachePath);
        mHandler = new Handler();
        tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        init();
        Utils.init(this);//初始化工具类
    }


    private void init() {
        //初始化OKGO网络连接对象
        OkGo.init(this);
        //生成全局上下文Application
        if (instance == null) {
            instance = this;
        }
        //生成全局Gson解析对象
        if (mGson == null) {
            mGson = new Gson();
        }
        if (display == null) {
            WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            display = windowManager.getDefaultDisplay();
        }
        // 初始化网络请求
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager).connectTimeout(60000L, TimeUnit.MILLISECONDS).readTimeout(60000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
        //初始化日志工具类
//        Logger.uprootAll();
//        Logger.initialize(new LogBuilder().logPrintStyle(new MyLogStyle()).showMethodLink(true).showThreadInfo(false).methodOffset(0).tagPrefix("hxk").build());
        //初始化图片选择器
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setCrop(true);
        imagePicker.setSelectLimit(1);
        imagePicker.setFocusWidth(600);
        imagePicker.setFocusHeight(600);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);
        imagePicker.setOutPutX(200);
        imagePicker.setOutPutY(200);
        //全局异常抓取
        CrashHandler.getInstance().init(this);
    }

    public String getCachePath() {
        File cacheDir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
//            cacheDir = getExternalCacheDir();
            cacheDir = Environment.getExternalStorageDirectory();
        else cacheDir = Environment.getDataDirectory();
        if (!cacheDir.exists()) cacheDir.mkdirs();
        return cacheDir.getAbsolutePath();
    }

    //获取当前版本号
    public String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo("store.chinaotec.com.medicalcare", 0);
            versionName = packageInfo.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    @SuppressLint("MissingPermission")
    public String getImei(){
        return tm.getDeviceId();
    }
}
