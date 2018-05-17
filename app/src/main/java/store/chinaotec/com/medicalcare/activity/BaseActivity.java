package store.chinaotec.com.medicalcare.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import store.chinaotec.com.medicalcare.utill.Colector;

/**
 * Created by hxk on 2017/7/17 0017 18:28
 * @RuntimePermissions
 */

public class BaseActivity extends AppCompatActivity {
    protected boolean isDestroy;
    public final static int REQUEST_READ_PHONE_STATE = 101;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        BaseActivityPermissionsDispatcher.startOneWithCheck(this);
//        BaseActivityPermissionsDispatcher.startTwoWithCheck(this);
        //设置屏幕的方向为竖屏,禁止自动旋转
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //activity管理器添加当前activity
        Colector.addActivity(this);
        isDestroy = false;
        //Manifest.permission.WRITE_EXTERNAL_STORAGE使得sd卡获得写的权限
        //Manifest.permission.READ_PHONE_STATE获取机型权限
        //Manifest.permission.CAMERA相机
        //Manifest.permission.RECORD_AUDIO允许程序录制音频

        //Manifest.permission.CALL_PHONE
        //Manifest.permission.RECORD_AUDIO

        String[] permission = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO};
//        requestMorePermissions(this, permission, REQUEST_READ_PHONE_STATE);




        checkAndRequestMorePermissions(this, permission, REQUEST_READ_PHONE_STATE);

    }

    /**
     * 检测并请求多个权限
     */
    public static void checkAndRequestMorePermissions(Context context, String[] permissions, int requestCode) {
        List<String> permissionList = checkMorePermissions(context, permissions);
        requestMorePermissions(context, permissionList, requestCode);
    }

    /**
     * 检测权限
     *
     * @return true：已授权； false：未授权；
     */
    public static boolean checkPermission(Context context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) return true;
        else return false;
    }
    /**
     * 检测多个权限
     *
     * @return 未授权的权限
     */
    public static List<String> checkMorePermissions(Context context, String[] permissions) {
        List<String> permissionList = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (!checkPermission(context, permissions[i])) permissionList.add(permissions[i]);
        }
        return permissionList;
    }
    /**
     * 请求多个权限
     */
    public static void requestMorePermissions(Context context, List permissionList, int requestCode) {
        String[] permissions = (String[]) permissionList.toArray(new String[permissionList.size()]);
        requestMorePermissions(context, permissions, requestCode);
    }
    /**
     * 请求多个权限
     */
    public static void requestMorePermissions(Context context, String[] permissions, int requestCode) {
        if (permissions!=null&&permissions.length>0) {
            ActivityCompat.requestPermissions((Activity) context, permissions, requestCode);
        }
    }
    /**
     * @param view 公共返回当前页面逻辑
     */
    public void back(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //activity管理器移除当前activity
        Colector.removeActivity(this);
        isDestroy = true;
    }

//    @NeedsPermission(Manifest.permission.RECORD_AUDIO)
//    void startOne() {
//    }
//
//
//    @NeedsPermission(Manifest.permission.CALL_PHONE)
//    void startTwo() {
//    }
//
//    @OnShowRationale(Manifest.permission.RECORD_AUDIO)
//    void showOne(final PermissionRequest request) {
//        new AlertDialog.Builder(this).setPositiveButton("同意使用", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                request.proceed();
//            }
//        }).setNegativeButton("拒绝使用", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                request.cancel();
//            }
//        }).setCancelable(false).setMessage("权限管理").show();
//    }
//
//    @OnShowRationale(Manifest.permission.CALL_PHONE)
//    void showTwo(final PermissionRequest request) {
//        new AlertDialog.Builder(this).setPositiveButton("同意使用", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                request.proceed();
//            }
//        }).setNegativeButton("拒绝使用", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                request.cancel();
//            }
//        }).setCancelable(false).setMessage("权限管理").show();
//    }
//
//    @OnPermissionDenied(Manifest.permission.RECORD_AUDIO)
//    void showDeniedForCamera() {
//        Toast.makeText(this, "禁止录音", Toast.LENGTH_SHORT).show();
//    }


}
