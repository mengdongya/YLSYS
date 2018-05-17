package store.chinaotec.com.medicalcare.shopmarket.common.base.service;


import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;

import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;

/**
 * 此类描述的是:实时监听网络状态，接收网络状态的广播
 *
 * @author: wyk
 * @version:1.0
 * @date:2015年8月20日 下午5:24:26
 */
public class ListenNetStateService extends Service {
    private ConnectivityManager connectivityManager;
    private NetworkInfo info;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                Log.d("mark", "网络状态已经改变");
                connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                info = connectivityManager.getActiveNetworkInfo();
                if (info != null && info.isAvailable()) {
                    String name = info.getTypeName();
                    int type = info.getType();
                    Log.d("mark", "当前网络名称：" + name + "当前网络ID：" + type);
                    // 网络状态ID 0：mobile(移动网络) 1：WIFI
                    if (type == 0) {
                        boolean mobileNet = isMobileConnected(context);
                        Log.d("mark", "当前mobile网络是否可用：" + mobileNet);
                        if (mobileNet) {
                            // 发送有网络的广播
                            context.sendBroadcast(new Intent(
                                    SourceConstant.Net_State_Cast_OK));
                            SourceConstant.IS_NET_STATE = true;
                        } else {
                            SourceConstant.IS_NET_STATE = false;
                            // 发送无网络的广播
                            context.sendBroadcast(new Intent(
                                    SourceConstant.Net_State_Cast_NO));
                        }
                    } else if (type == 1) {
                        boolean wifiNet = isWifiConnected(context);
                        Log.d("mark", "当前WIFI网络是否可用：" + wifiNet);
                        if (wifiNet) {
                            // 发送有网络的广播
                            context.sendBroadcast(new Intent(
                                    SourceConstant.Net_State_Cast_OK));
                            SourceConstant.IS_NET_STATE = true;
                        } else {
                            SourceConstant.IS_NET_STATE = false;
                            // 发送无网络的广播
                            context.sendBroadcast(new Intent(
                                    SourceConstant.Net_State_Cast_NO));
                        }
                    }

                } else {
                    SourceConstant.IS_NET_STATE = false;
                    // 发送无网络的广播
                    context.sendBroadcast(new Intent(
                            SourceConstant.Net_State_Cast_NO));
                }
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, mFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 判断MOBILE网络是否可用
     */
    public boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断WIFI网络是否可用
     */
    public boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
