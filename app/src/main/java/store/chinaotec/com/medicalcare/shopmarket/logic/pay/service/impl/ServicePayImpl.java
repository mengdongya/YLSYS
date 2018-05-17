package store.chinaotec.com.medicalcare.shopmarket.logic.pay.service.impl;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import store.chinaotec.com.medicalcare.shopmarket.common.base.service.BaseServices;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.Constant;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.http.AorunApi;
import store.chinaotec.com.medicalcare.shopmarket.common.http.DataCallBack;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.OrderCreat;
import store.chinaotec.com.medicalcare.shopmarket.logic.pay.alipay.Result;
import store.chinaotec.com.medicalcare.shopmarket.logic.pay.service.ServicePay;


/**
 * Created by seven on 2016/8/5 0005.
 */
public class ServicePayImpl extends BaseServices implements ServicePay {
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;

    private Context context;
    private final Handler mHandlerAlipay = new Handler() {
        private String TAG = "ServicePayImpl";

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SDK_PAY_FLAG:
                    Result resultObj = new Result((String) msg.obj);
                    String resultStatus = resultObj.resultStatus;
                    LogUtil.e(TAG, "=======================支付宝返回码：" + resultStatus);
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        SourceConstant.IS_PAY_OK = 0;
                        Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
                        SourceConstant.IS_PAY_SUCCESS = SourceConstant.THREE;

                        LogUtil.e(TAG, "支付成功了。。。。。。。。。。。。。。。。");
                        ((Activity) context).finish();

                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”
                        // 代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(context, "支付结果确认中", Toast.LENGTH_SHORT)
                                    .show();

                        }

                        if (TextUtils.equals(resultStatus, "6001")) {
                            SourceConstant.IS_PAY_OK = 1;
                            Toast.makeText(context, "取消支付", Toast.LENGTH_SHORT).show();
                        }
                    }

                    ((Activity) context).setResult(((Activity) context).RESULT_OK);
                    break;
                case SDK_CHECK_FLAG: {
                    Toast.makeText(context, "检查结果为：" + msg.obj, Toast.LENGTH_SHORT)
                            .show();
                    break;
                }


            }
        }

    };
    private DataCallBack dataCallback;
    private String TAG = "ServicePayImpl";

    public ServicePayImpl(Context context, DataCallBack dataCallback) {
        this.context = context;
        this.dataCallback = dataCallback;
    }

    @Override
    public void creatOrder(Object entity) {
        AorunApi.getCreatOrder((OrderCreat) entity, dataCallback);
    }

    @Override
    public void getPayInfoAli(String orderCode, String sId) {
        AorunApi.getAliPayInfo(orderCode, Constant.ALI_APPID, sId, dataCallback);
    }

    @Override
    public void payAli(final String payInfo) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask((Activity) context);
                // 调用支付接口
                String result = alipay.pay(payInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandlerAlipay.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


}

