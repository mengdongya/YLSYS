package store.chinaotec.com.medicalcare.shopmarket.common.base.activity;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;

import okhttp3.Request;
import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.base.service.ListenNetStateService;
import store.chinaotec.com.medicalcare.shopmarket.common.http.DataCallBack;
import store.chinaotec.com.medicalcare.shopmarket.common.http.interf.DialogControl;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.DialogHelp;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;

/**
 * Created by wjc on 2016/10/9 0009.
 */
public abstract class BaseAoActivity extends FragmentActivity implements DialogControl, View.OnClickListener {
    protected int mWindowWidth = 0;
    private boolean _isVisible;
    private ProgressDialog _waitDialog;
    protected DataCallBack mDataCallback = new DataCallBack() {
        @Override
        public void onBefore(Request request, int id) {
            super.onBefore(request, id);
            if (this.getRequestVo().hasDialog)
                LogUtil.e("执行---加载进度", "---加载进度----");
            showWaitDialog("加载中...");
        }

        @Override
        public void onAfter(int id) {
            if (this.getRequestVo().hasDialog)
                hideWaitDialog();
        }

        @Override
        protected void parseData(String s, RequestVo requestVo) {
            processData(s, requestVo);
        }
    };
    private ListenNetStateService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mService = new ListenNetStateService();
//        this.startService(new Intent("org.aorun.ym.ListenNetStateService"));
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mWindowWidth = dm.widthPixels;
        init();
        _isVisible = true;
    }

    private void init() {
        initLayout();
        initView();
        initListener();
        processLogic();
    }

    public void onClick(View paramView) {
        onClickEvent(paramView);
    }

    @Override
    public void hideWaitDialog() {
        if (_isVisible && _waitDialog != null) {
            try {
                _waitDialog.dismiss();
                _waitDialog = null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public ProgressDialog showWaitDialog(String message) {
        if (_isVisible) {
            if (_waitDialog == null) {
                _waitDialog = DialogHelp.getWaitDialog(this, message);
            }
            if (_waitDialog != null) {
                _waitDialog.setMessage(message);
                _waitDialog.show();
                LogUtil.e("执行---加载进度", "---执行了---加载进度----");
            }
            return _waitDialog;
        }
        return null;
    }

    /**
     * 解析网络数据，直接解析data数据就可以了
     */
    protected abstract void processData(String data, RequestVo requestVo);

    /**
     * 获取布局文件
     */
    protected abstract void initLayout();

    /**
     * 设置点击事件
     */
    protected abstract void onClickEvent(View paramView);

    /**
     * 初始化控件点击事件
     */
    protected abstract void initView();

    protected abstract void initListener();

    /**
     * 处理界面逻辑
     */
    protected abstract void processLogic();
}
