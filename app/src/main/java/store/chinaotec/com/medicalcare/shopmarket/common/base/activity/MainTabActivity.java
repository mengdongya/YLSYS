package store.chinaotec.com.medicalcare.shopmarket.common.base.activity;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.base.app.AppManager;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.referencecontrol.weight.BGABadgeRadioButton;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.logic.cart.activity.CartActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.activity.MainActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.shops.activity.ShopListActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.type.activity.TypeActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.user.activity.UserActivity;

public class MainTabActivity extends BaseTabsActivity {
    private static final String TAG = "MainTabActivity";
    public static RadioGroup main_radio;
    public static BGABadgeRadioButton bt2;
    private NetStateCastReceiver mReceiver;
    private LinearLayout ll_no_netntate;
    private ImageView iv_scroll_top;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {// 覆盖handleMessage方法
            switch (msg.what) {// 根据收到的消息的what类型处理
                // 无网络
                case SourceConstant.ONE:
                    ll_no_netntate.setVisibility(View.VISIBLE);
                    break;
                // 有网络
                case SourceConstant.TWO:

                    ll_no_netntate.setVisibility(View.GONE);
                    break;
                // 隐藏回到顶部按钮
                case SourceConstant.THREE:
                    ObjectAnimator gone = ObjectAnimator.ofFloat(iv_scroll_top,
                            "alpha", 1f, 0f);
                    gone.setDuration(100);
                    gone.start();
                    iv_scroll_top.setVisibility(View.GONE);
                    break;
                // 显示回到顶部按钮
                case SourceConstant.FOUR:
                    ObjectAnimator visible = ObjectAnimator.ofFloat(iv_scroll_top,
                            "alpha", 0f, 1f);
                    visible.setDuration(100);
                    visible.start();
                    iv_scroll_top.setVisibility(View.VISIBLE);
                    break;
//			case SourceConstant.FIVE:
//				Intent intent = new Intent(MainTabActivity.this, SkuInfoActivity.class);
//				intent.putExtra(SourceConstant.SKU_CODE,skuCode);
//				startActivity(intent);
//				LogUtil.d("======","=====开启了商品详情");
//				break;
//			case SourceConstant.SIX:
//				startActivity(new Intent(MainTabActivity.this, ShopDetailsActivity.class));
//				break;
                default:
                    super.handleMessage(msg);// 这里最好对不需要或者不关心的消息抛给父类，避免丢失消息
                    break;
            }
        }
    };
    /**
     * 存储VisitKey文件的共享首选项
     */
    private SharedPreferences fileNameAli;
    private String skuCode;

    public static void setCartNumberText(String number) {
//		if (Integer.parseInt(number)!=0) {
//			bt2.showTextBadge(number);
//		}else{
//			bt2.hiddenBadge();
//		}
    }

    @Override
    protected void onClickEvent(View paramView) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_shop_main_tab);
        ll_no_netntate = (LinearLayout) findViewById(R.id.ll_no_netntate);
        iv_scroll_top = (ImageView) findViewById(R.id.iv_scroll_top);
        // 注册接收网络更改的广播
        mReceiver = new NetStateCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(SourceConstant.Net_State_Cast_NO);
        filter.addAction(SourceConstant.Net_State_Cast_OK);
        filter.addAction(SourceConstant.SCROLL_TOP_VISIBILITY_GONE);
        filter.addAction(SourceConstant.SCROLL_TOP_VISIBILITY_VISIBLE);
//		filter.addAction(SourceConstant.START_SHOP_SKU_INFO);
//		filter.addAction(SourceConstant.START_SHOP_STORE);
        this.registerReceiver(mReceiver, filter);
        LogUtil.e(TAG, "网络判断 是否有网络  true：有网络，false：无网络=====" + SourceConstant.IS_NET_STATE);
        if (SourceConstant.IS_NET_STATE) {
            ll_no_netntate.setVisibility(View.GONE);
        } else {
            ll_no_netntate.setVisibility(View.VISIBLE);

        }
        ll_no_netntate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 点击跳转到系统的设置页
                startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            }
        });
        iv_scroll_top.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 发送回到顶部的广播
                MainTabActivity.this.sendBroadcast(new Intent(
                        SourceConstant.SCROLL_TOP_OK));
            }
        });
        AppManager.getInstance().addActivity(this);
        DisplayMetrics dm = new DisplayMetrics();
        // 获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        SourceConstant.screenWidth = dm.widthPixels;
        SourceConstant.screenHeigh = dm.heightPixels;
        // 初始化sharesdk
//		ShareSDK.initSDK(this);

    }

    @Override
    protected void initIntent() {
        super.list = new ArrayList<Intent>();
        super.list.add(new Intent(this, MainActivity.class));
//        super.list.add(new Intent(this, TypeActivity.class));
        super.list.add(new Intent(this, ShopListActivity.class));
        super.list.add(new Intent(this, CartActivity.class));
        super.list.add(new Intent(this, UserActivity.class));
        super.arrayTitle = new String[]{"A_TAB","C_TAB", "D_TAB", "E_TAB"};
    }

    @Override
    protected void initListener() {
        ((BGABadgeRadioButton) findViewById(R.id.radio_button0))
                .setOnCheckedChangeListener(this);
//        ((BGABadgeRadioButton) findViewById(R.id.radio_button1))
//                .setOnCheckedChangeListener(this);
        ((BGABadgeRadioButton) findViewById(R.id.radio_button2))
                .setOnCheckedChangeListener(this);
        ((BGABadgeRadioButton) findViewById(R.id.radio_button3))
                .setOnCheckedChangeListener(this);
        ((BGABadgeRadioButton) findViewById(R.id.radio_button4))
                .setOnCheckedChangeListener(this);

    }

    @Override
    protected void initView() {
        // TODO Auto-generated method stub
        main_radio = (RadioGroup) findViewById(R.id.main_radio);
        bt2 = (BGABadgeRadioButton) findViewById(R.id.radio_button2);
    }

    @Override
    protected void processLogic() {
    }

    @Override
    protected void onResume() {
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
        // Intent mIntent = new Intent("com.yili.ListenNetStateService");
        // this.stopService(mIntent);
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    /**
     * 接收网络更改的广播
     */
    class NetStateCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // 无网络的广播
            if (action.equals(SourceConstant.Net_State_Cast_NO)) {
                Message message = new Message();
                message.what = SourceConstant.ONE;
                mHandler.sendMessage(message);
            }
            // 有网络的广播
            if (action.equals(SourceConstant.Net_State_Cast_OK)) {
                Message message = new Message();
                message.what = SourceConstant.TWO;
                mHandler.sendMessage(message);
            }
            // 隐藏回到顶部按钮的广播
            if (action.equals(SourceConstant.SCROLL_TOP_VISIBILITY_GONE)) {
                Message message = new Message();
                message.what = SourceConstant.THREE;
                mHandler.sendMessage(message);
            }
            // 显示回到顶部按钮的广播
            if (action.equals(SourceConstant.SCROLL_TOP_VISIBILITY_VISIBLE)) {
                Message message = new Message();
                message.what = SourceConstant.FOUR;
                mHandler.sendMessage(message);
            }
//			//跳到商品详情
//			if (action.equals(SourceConstant.START_SHOP_SKU_INFO)){
//				skuCode = intent.getStringExtra(SourceConstant.SKU_CODE);
//				Message message = new Message();
//				message.what = SourceConstant.FIVE;
//				mHandler.sendMessage(message);
//				LogUtil.e(TAG,"======走到了发消息跳转");
//			}
//			//跳到店铺详情
//			if (action.equals(SourceConstant.START_SHOP_STORE)) {
//				Message message = new Message();
//				message.what = SourceConstant.SIX;
//				mHandler.sendMessage(message);
//			}
        }
    }
}
