package store.chinaotec.com.medicalcare.shopmarket.common.utils.httputil;

import android.os.Handler;
import android.os.Message;

import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;


public class MyHandler extends Handler {
    private DataCallback<RequestVo> callBack;
    private CustomProgressDialog progressDialog;

    public MyHandler(DataCallback<RequestVo> callBack, CustomProgressDialog progressDialog) {
        this.callBack = callBack;
        this.progressDialog = progressDialog;
    }

    public void handleMessage(Message msg) {
        boolean status = false;
        RequestVo reqVo = (RequestVo) msg.obj;
        String strResult = reqVo.resultStr;

        //发起请求成功
//        if (msg.what == NetUtil.SUCCESS) {
//            status = true;
//        }
        //请求超时or请求内容为空
        if (strResult == null || strResult.equals("")) {
            status = false;
        }

        // if (msg.what == NetUtil.NET_FAILED) {
        // state = false;
        // }

        callBack.processData(reqVo, status);
        closeProgressDialog();
    }

    /**
     * 关闭提示框
     */
    protected void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
