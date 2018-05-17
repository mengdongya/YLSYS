package store.chinaotec.com.medicalcare.shopmarket.common.base.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.base.service.ListenNetStateService;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.httputil.CustomProgressDialog;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.httputil.DataCallback;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.httputil.MyHandler;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.httputil.MyTask;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.httputil.ThreadPoolManager;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;


public abstract class BaseActivity extends Activity implements OnClickListener {

    protected Context context;
    // protected ProgressBar progressDialog1;
    protected CustomProgressDialog progressDialog = null;
    // 防暴力点击
    protected long lastClickTime = 0;
    protected int mWindowWidth = 0;
    protected boolean isDestroy;
    LinearLayout asd;
    private ThreadPoolManager threadPoolManager;
    private ListenNetStateService mService;
    private String mActivityName = "";

    public BaseActivity() {
        threadPoolManager = ThreadPoolManager.getInstance();
    }

    /**
     * Android生命周期回调方法-创建
     */
    @Override
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        mService = new ListenNetStateService();
        isDestroy = false;
//		this.startService(new Intent("org.aorun.ym.ListenNetStateService"));
//		MyUtils.initFolder();
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mWindowWidth = dm.widthPixels;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        initLayout();
        initView();
        initListener();
        processLogic();
    }

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
            showProgressDialog();
        }
        MyHandler handler = new MyHandler(callBack, progressDialog);// handle类数据得到后使用
        MyTask taskThread = new MyTask(this, reqVo, handler);// 线程类--获取数据
        this.threadPoolManager.addTask(taskThread);
    }

    protected boolean verifyHttp(RequestVo reqVo, boolean status, int responseCode, String msg) {
        if (!status) {
            ToastUtil.MyToast(this, R.string.net_error);
            return false;
        }

        if (responseCode != 0) {
            ToastUtil.MyToast(this, msg);
            return false;
        }
        return true;
    }

    /**
     * 设置弹出进度条样式
     */
    protected void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new CustomProgressDialog(this);
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

    public void onClick(View paramView) {
        if (!onClickBottmBarEvent(paramView))
            onClickEvent(paramView);
    }

    /**
     * 响应底部tab点击事件
     *
     * @param paramView
     * @return
     */
    private boolean onClickBottmBarEvent(View paramView) {
        boolean isBar = true;
        switch (paramView.getId()) {
            default:
                isBar = false;
//			if (FramworkApplication.isDebugMode()) {
//				Log.e("TAB6", "DEFAULT");
//			}
                break;
        }
        return isBar;
    }

    /**
     * 防暴力点击 上次点击时间, lastClickTime 本次点击时间, time 时间差, timeD 多长时间内点击无效, timelong
     */
    protected boolean isFastDoubleClick(int timelong) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (timeD > timelong) {
            lastClickTime = time;
            return true;
        }
        return false;
    }

    /**
     * 点击屏幕收起键盘
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager manager;
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }


    /**
     * 显示头像
     */
    protected void setImgHead(String imgUrl, final ImageView imgView, final int resDrawable) {

        if (imgUrl == null || imgUrl.equals("")) {
            imgView.setImageResource(resDrawable);
            return;
        }

//		mImageLoader = new AsyncImageLoader();
//		imgView.setTag(imgUrl);
//		mImageLoader.loadDrawable(imgUrl, imgView, new ImageCallback() {
//			public void imageLoaded(Drawable imageDrawable, ImageView imageView, String imageUrl) {
//				try {
//					imageView = (ImageView) imageView.findViewWithTag(imageUrl);
//
//					if (imageDrawable == null) {
//						imageView.setImageResource(resDrawable);
//					} else {
//						Bitmap bitmap = drawableToBitmap(imageDrawable);
//						imageView.setImageBitmap(bitmap);
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        // canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    protected void tipView(int resLayoutTip, int resLayoutMain) {
        LayoutInflater mInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(resLayoutTip, null, false);
        view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        ((LinearLayout) findViewById(resLayoutMain)).addView(view);
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                processLogic();
            }
        });
        System.out.println(view.getId());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestroy = true;
    }

    public ThreadPoolManager getThreadPoolManager() {
        return threadPoolManager;
    }

    public void setThreadPoolManager(ThreadPoolManager threadPoolManager) {
        this.threadPoolManager = threadPoolManager;
    }

    /**
     * 设置监听事件
     *
     * @param paramView
     */
    protected abstract void onClickEvent(View paramView);

    /**
     * 初始化布局文件
     */
    protected abstract void initLayout();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 关联监听
     */
    protected abstract void initListener();

    /**
     * 逻辑处理
     */
    protected abstract void processLogic();
    /**无网络*/

}
