package store.chinaotec.com.medicalcare.shopmarket.logic.home.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.base.activity.BaseActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.base.activity.MainTabActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.request.requestUrl.RequestUrl;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.httputil.DataCallback;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;


/**
 * 此类描述的是: 活动介绍页
 *
 * @author: wjc
 * @version:1.0
 * @date:2016年3月15日 下午5:53:19
 */
@SuppressLint("SetJavaScriptEnabled")
public class IntroduceActivity extends BaseActivity implements DataCallback<RequestVo> {

    public static boolean ffinsh = true;
    private WebView wv_event_introduce;
    private String eventUrl;
    private ProgressBar progressBar;
    private SharedPreferences fileNameAli;
    private String sid;
    private String visitKey;
    private TextView mTvTitle;
    private ImageView mBtnBack;
    private String title;

    @Override
    protected void onClickEvent(View paramView) {
        switch (paramView.getId()) {
            case R.id.title_btn_left:
                finish();
                break;
        }
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_shop_market_event_introduce);
    }

    @Override
    protected void initView() {
        wv_event_introduce = (WebView) findViewById(R.id.wv_event_introduce);
        fileNameAli = this.getSharedPreferences(SourceConstant.fileNameAli, 0);
        mTvTitle = (TextView) findViewById(R.id.title_textview);
        mBtnBack = (ImageView) findViewById(R.id.title_btn_left);

        visitKey = fileNameAli.getString(SourceConstant.VisitKey, "");

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        // 设置允许使用
        wv_event_introduce.getSettings().setJavaScriptEnabled(true);
        wv_event_introduce.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wv_event_introduce.getSettings().setAppCacheEnabled(false);
        // 适应屏幕
        wv_event_introduce.getSettings().setUseWideViewPort(true);
        wv_event_introduce.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        wv_event_introduce.getSettings().setDomStorageEnabled(true);
        wv_event_introduce.getSettings().setSupportZoom(false);// 支持缩放
        wv_event_introduce.getSettings().setBuiltInZoomControls(false);// 设置显示缩放按钮
        wv_event_introduce.getSettings().setAllowFileAccess(true); // 允许访问文件
        wv_event_introduce.getSettings().setLoadWithOverviewMode(true);
        wv_event_introduce.setWebViewClient(new MyWebViewClient());
        wv_event_introduce.setWebChromeClient(new MyWebChromeClient());
    }

    @Override
    protected void initListener() {
        mBtnBack.setOnClickListener(this);
    }

    @Override
    protected void processLogic() {

        Intent intent = getIntent();
        eventUrl = intent.getStringExtra(SourceConstant.EVENT_URL);
        title = intent.getStringExtra("title_shop");
        mTvTitle.setText(title);
        wv_event_introduce.loadUrl(eventUrl);
        Adaptive();
    }

    // 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && wv_event_introduce.canGoBack()) {
            wv_event_introduce.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * webview 适配
     */
    @SuppressWarnings("deprecation")
    public void Adaptive() {
        LinearLayout.LayoutParams mWebViewLP = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT);
        wv_event_introduce.setLayoutParams(mWebViewLP);
        wv_event_introduce.setInitialScale(25);
        WebSettings settings = wv_event_introduce.getSettings();
//		// 适应屏幕
        settings.setUseWideViewPort(true);
//		settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
//		settings.setDomStorageEnabled(true);
//		settings.setSupportZoom(false);// 支持缩放
//		settings.setBuiltInZoomControls(false);// 设置显示缩放按钮
//		settings.setAllowFileAccess(true); // 允许访问文件
//		settings.setLoadWithOverviewMode(true);
    }

    @Override
    public void processData(RequestVo reqVo, boolean status) {
        if (!status) {
            ToastUtil.MyToast(this, R.string.net_error);
            return;
        }

//        Result result = mResFormat.verfiyResponseCode(reqVo.resultStr);

//        if (result.responseCode != 0) {
//            ToastUtil.MyToast(this, result.msg);
//            return;
//        }
        switch (reqVo.requestUrl) {
            case RequestUrl.CART_ADD:
                ToastUtil.MyToast(this, "已成功加入购物车!");
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        if (!ffinsh) {
            this.finish();
        }
        super.onResume();
//		MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
//		MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        LogUtil.e("", "进了onDestroy()====");
        super.onDestroy();
    }

    // 监听 所有点击的链接，如果拦截到我们需要的，就跳转到相对应的页面。
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String urlString = new String(url.toString());
            if (url != null && url.contains("?method=toSkuDetail&skuCode=")) {
                LogUtil.e("", "url====" + url.toString());
                String split[] = urlString.split("&");
                LogUtil.e("", "url--split====" + split[1]);
                String skuCode[] = split[1].split("=");
                if (!"".equals(skuCode[1])) {
                    LogUtil.e("分割出来的商品code--", "skuCode==" + skuCode[1]);
                    //TODO
                }
                return true;
            } else if (url != null && url.contains("?method=addCart&skuCode=")) {
                String split[] = urlString.split("&");
                String skuCode[] = split[1].split("=");
                if (!"".equals(skuCode[1])) {
                    LogUtil.e("分割出来的商品code--", "skuCode==" + skuCode[1]);
//						addCart(skuCode[1]);
                }
                return true;
            } else if (url != null && url.contains("?method=list")) {
                IntroduceActivity.this.finish();
                ((RadioButton) MainTabActivity.main_radio.getChildAt(2))
                        .setChecked(true);
                return false;
            } else if (url != null && url.contains("?method=backApp")) {
                if (SourceConstant.IS_BACK_CURRENT_APP != SourceConstant.FOUR) {
                    IntroduceActivity.this.finish();
                } else {
                    IntroduceActivity.this.finish();
                    ((RadioButton) MainTabActivity.main_radio.getChildAt(0))
                            .setChecked(true);
                }
                return false;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progressBar.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            progressBar.setVisibility(View.GONE);
        }
    }

    class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
        }
    }
}
