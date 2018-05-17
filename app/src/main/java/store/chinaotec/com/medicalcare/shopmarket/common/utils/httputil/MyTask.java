package store.chinaotec.com.medicalcare.shopmarket.common.utils.httputil;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;


public class MyTask implements Runnable {
    private Context context;
    private RequestVo reqVo;
    private Handler handler;

    public MyTask(Context context, RequestVo reqVo, Handler handler) {
        this.context = context;
        this.reqVo = reqVo;
        this.handler = handler;
    }

    @Override
    public void run() {
        Message msg = new Message();
        if (reqVo != null) {
//            if (NetUtil.hasNetwork(context)) {
//                // 真正和后台服务器交互的方法
//                msg.obj = NetUtil.service(reqVo);
//                msg.what = NetUtil.SUCCESS;
//            } else {
//                msg.obj = reqVo;
//                msg.what = NetUtil.NET_FAILED;
//            }
            handler.sendMessage(msg);
        }
    }

}
