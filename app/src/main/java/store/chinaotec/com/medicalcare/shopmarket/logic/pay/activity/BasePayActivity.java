package store.chinaotec.com.medicalcare.shopmarket.logic.pay.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import store.chinaotec.com.medicalcare.shopmarket.common.base.activity.BaseAoActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.request.requestUrl.RequestUrl;

public abstract class BasePayActivity extends BaseAoActivity implements Callback, Runnable {
    public static final String LOG_TAG = "PayDemo";
    public static final int PLUGIN_VALID = 0;
    public static final int PLUGIN_NOT_INSTALLED = -1;
    public static final int PLUGIN_NEED_UPGRADE = 2;
    /*****************************************************************
     * mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
     *****************************************************************/
    public static final String mMode = "00";
    private static String TN_URL_01 = "";
    private Context mContext = null;
    private int mGoodsIdx = 0;
    private Handler mHandler = null;
    private ProgressDialog mLoadingDialog = null;
    protected final View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e(LOG_TAG, " " + v.getTag());
            mGoodsIdx = (Integer) v.getTag();
            // TN_URL_01 = url.concat(code);

            mLoadingDialog = ProgressDialog.show(mContext, // context
                    "", // title
                    "正在努力的获取tn中,请稍候...", // message
                    true); // 进度是否是不确定的，这只和创建进度条有关

            /*************************************************
             * 步骤1：从网络开始,获取交易流水号即TN
             ************************************************/
            new Thread(BasePayActivity.this).start();
        }
    };
    // 正式的
    private String params = "so_code=";

    protected void submitUM(String code, String sId) {

        TN_URL_01 = RequestUrl.PAY_HOST.concat(RequestUrl.YL_GET_TN).concat(params).concat(code).concat("&sId=")
                .concat(sId);

        mLoadingDialog = ProgressDialog.show(mContext, // context
                "", // title
                "正在努力的获取tn中,请稍候...", // message
                true); // 进度是否是不确定的，这只和创建进度条有关

        /*************************************************
         * 步骤1：从网络开始,获取交易流水号即TN
         ************************************************/
        new Thread(BasePayActivity.this).start();
    }

    public abstract void doStartUnionPayPlugin(Activity activity, String tn, String mode);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mHandler = new Handler(this);

        // setContentView(R.layout.demo_activity_main);
        //
        // Button btn0 = (Button) findViewById(R.id.btn0);
        // btn0.setTag(0);
        // btn0.setOnClickListener(mClickListener);
        //
        // TextView tv = (TextView) findViewById(R.id.guide);
        // tv.setTextSize(16);
        // updateTextView(tv);
    }

    public abstract void updateTextView(TextView tv);

    @Override
    public boolean handleMessage(Message msg) {
        Log.e(LOG_TAG, " " + "" + msg.obj);
        if (mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }

        String tn = "";
        if (msg.obj == null || ((String) msg.obj).length() == 0) {
            payErrory((String) msg.obj);
            // AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // builder.setTitle("错误提示");
            // builder.setMessage("网络连接失败,请重试!");
            // builder.setNegativeButton("确定", new
            // DialogInterface.OnClickListener() {
            // @Override
            // public void onClick(DialogInterface dialog, int which) {
            // dialog.dismiss();
            // }
            // });
            // builder.create().show();
        } else {
            tn = (String) msg.obj;
            /*************************************************
             * 步骤2：通过银联工具类启动支付插件
             ************************************************/

            JSONObject jo;
            try {
                jo = new JSONObject(tn);
                int code = jo.getInt("responseCode");
                if (code != 0) {
                    String msgStr = jo.getString("msg");
                    payErrory(msgStr);
                    return false;
                } else {
                    tn = jo.getString("data");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("doStartUnionPayPlugin ==>> " + "tn = " + tn + " , mode = " + mMode);
            doStartUnionPayPlugin(this, tn, mMode);
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*************************************************
         * 步骤3：处理银联手机支付控件返回的支付结果
         ************************************************/

        // requestCode = 10 , resultCode = -1;

        // if (data == null) {
        // return;
        // }

        // String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
		 */
        // String str = data.getExtras().getString("pay_result");
        // if (str.equalsIgnoreCase("success")) {
        // msg = "支付成功！";
        // payErrory(str);
        // return;
        // }
        // if (str.equalsIgnoreCase("fail")) {
        // msg = "支付失败！";
        // payErrory(str);
        // return;
        // }
        // if (str.equalsIgnoreCase("cancel")) {
        // msg = "用户取消了支付";
        // payErrory(str);
        // return;
        // }

    }

    @Override
    public void run() {
        String tn = null;
        InputStream is;
        try {

            String url = TN_URL_01;

            URL myURL = new URL(url);
            URLConnection ucon = myURL.openConnection();
            ucon.setConnectTimeout(120000);
            is = ucon.getInputStream();
            int i = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((i = is.read()) != -1) {
                baos.write(i);
            }

            tn = baos.toString();
            is.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Message msg = mHandler.obtainMessage();
        msg.obj = tn;
        mHandler.sendMessage(msg);
    }

    int startpay(Activity act, String tn, int serverIdentifier) {
        return 0;
    }

    public abstract void payErrory(String str);
}
