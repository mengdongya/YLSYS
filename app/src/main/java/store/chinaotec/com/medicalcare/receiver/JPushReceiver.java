package store.chinaotec.com.medicalcare.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import store.chinaotec.com.medicalcare.activity.RemindListActivity;
import store.chinaotec.com.medicalcare.activity.ValidateListActivity;

/**
 * Created by seven on 2018/1/10 0010.
 */
public class JPushReceiver extends BroadcastReceiver {
    private static int type = 0;
    String message;

    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d("notice", "onReceive - " + intent.getAction());

        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        System.out.println("收到了自定义消息。消息内容是： extras " + extras);
        try {
            JSONObject jsonObject = new JSONObject(extras);
            String typeStr = jsonObject.getString("type");
            type = Integer.valueOf(typeStr);
            System.out.println("type 获取正常 ！" + type);
        } catch (Exception e) {
            System.out.println("type 获取出现异常 ！");
            e.printStackTrace();
        }

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            context.getSharedPreferences("jpush", Context.MODE_PRIVATE).edit().putString("regid", regId).commit();
//            noticeUtil.setMessage(message, context, type);
            Log.d("jpush", "[MyReceiver] 接收Registration Id : " + regId);

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            System.out.println("收到了自定义消息。消息内容是：" + message + "type: " + type);

            // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
                .getAction())) {
            System.out.println("收到了通知" + "type: " + type);
            // 在这里可以做些统计，或者做些其他工作

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
                .getAction())) {

            System.out.println("用户点击打开了通知");

            // 在这里可以自己写代码去定义用户点击后的行为
            Intent i = null;
            if (type == 101) {
                i = new Intent(context, ValidateListActivity.class);
            }else if(type == 102){
                i = new Intent(context, RemindListActivity.class);
            }
            context.startActivity(i);
        } else {
            Log.d("notice", "Unhandled intent - " + intent.getAction());
        }
    }
}
