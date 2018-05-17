package store.chinaotec.com.medicalcare.shopmarket.common.base.service;

import android.content.Context;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.httputil.CustomProgressDialog;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.httputil.DataCallback;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.httputil.MyHandler;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.httputil.MyTask;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.httputil.ThreadPoolManager;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;


public abstract class BaseServices {
    protected CustomProgressDialog progressDialog = null;
    protected ThreadPoolManager threadPoolManager = ThreadPoolManager.getInstance();
    ;

    /**
     * 开户子线程从服务器上获取数据
     * <p/>
     * 从服务器上获取数据，并回调处理
     *
     * @param reqVo
     * @param callBack
     */
    protected void getDataFromServer(RequestVo reqVo, DataCallback<RequestVo> callBack) {
        if (reqVo.hasDialog) {
            showProgressDialog(reqVo.context);
        }
        MyHandler handler = new MyHandler(callBack, progressDialog);// handle类数据得到后使用
        MyTask taskThread = new MyTask(reqVo.context, reqVo, handler);// 线程类--获取数据
        this.threadPoolManager.addTask(taskThread);
    }

    protected boolean verifyHttp(RequestVo reqVo, boolean status, int responseCode, String msg) {
        if (!status) {
            ToastUtil.MyToast(reqVo.context, R.string.net_error);
            return false;
        }

        if (responseCode != 0) {
            ToastUtil.MyToast(reqVo.context, msg);
            return false;
        }
        return true;
    }

    /**
     * 设置弹出进度条样式
     */
    protected void showProgressDialog(Context context) {
        if (progressDialog == null) {
            progressDialog = new CustomProgressDialog(context);
        }
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
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
