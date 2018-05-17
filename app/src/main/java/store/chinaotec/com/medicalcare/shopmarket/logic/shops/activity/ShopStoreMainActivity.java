package store.chinaotec.com.medicalcare.shopmarket.logic.shops.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.base.activity.BaseAoActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.http.AorunApi;
import store.chinaotec.com.medicalcare.shopmarket.common.request.requestUrl.RequestUrl;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.DialUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.MyImageLoader;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.view.MyScrollGridView;
import store.chinaotec.com.medicalcare.shopmarket.common.view.MyScrollListView;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.activity.ShopMarketSeckillAndGroupBuyActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.adapter.FunctionStoreListAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.adapter.StoreSkuListAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.model.FunctionalBlock;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.model.StoreHome;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.model.StoreSku;
import store.chinaotec.com.medicalcare.shopmarket.logic.shops.entity.Shops;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.activity.SkuListDiscountActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.user.model.User;
import store.chinaotec.com.medicalcare.utill.UserKeeper;

/**
 * Created by wjc on 2016/8/18 0018.
 */
public class ShopStoreMainActivity extends BaseAoActivity {
    private static String sid;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SourceConstant.ONE:
                    startActivity(new Intent(ShopStoreMainActivity.this, ShopMarketSeckillAndGroupBuyActivity.class));
                    break;
                case SourceConstant.TWO:
                    startActivity(new Intent(ShopStoreMainActivity.this, SkuListDiscountActivity.class));
                    break;
                default:
                    super.handleMessage(msg);// 这里最好对不需要或者不关心的消息抛给父类，避免丢失消息
                    break;
            }
        }
    };
    private TextView tvHomeTitle;
    private ImageView btnBack;
    private ImageView btnSearch;
    private MyScrollListView lv_store_discount;
    private MyScrollGridView gv_home_function;
    private User user;
    private SharedPreferences fileNameAli;
    private String storeCode;
    private int page_index = 1;
    private StoreHome storeHomeList;
    private ArrayList<StoreSku> storeSkuList;
    private StoreSkuListAdapter storeSkusAdapter;
    private ImageView storeIcon;
    private TextView storeName;
    private TextView storeType;
    private TextView storeSales;
    private TextView storeSkuNum;
    private ImageView native_map;
    private Shops storeInfo;
    private MyBroadcastReceiver mReceiver;
    private String location;
    private Float longitude;
    private Float latitude;

    @Override
    protected void onClickEvent(View paramView) {
        switch (paramView.getId()) {
            case R.id.title_btn_left:
                SharedPreferences.Editor editor = fileNameAli.edit();
                editor.putString(SourceConstant.STORE_CODE, "");
                editor.commit();
                this.finish();
                break;
            case R.id.title_btn_right:
                checkIsOpenCall();
                break;
            case R.id.native_map:
                checkIsOpenLoaction();
                break;
        }
    }

    private void checkIsOpenCall() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {

                    ToastUtil.showMessageOKCancel("您需要获取打电话的权限\n设置方法:权限管理->电话->允许", this,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent();
                                    intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                    intent.addCategory("android.intent.category.DEFAULT");
                                    intent.setData(Uri.parse("package:" + "org.aorun.ym"));
                                    startActivity(intent);
                                }
                            });
                } else {
                    openCallService();
                }
            } else {
                openCallService();
            }
        } else {
            openCallService();
        }
    }

    private void openCallService() {
        DialUtil iu = new DialUtil(this);
        String tel = storeInfo.getTelephone();
        if (!"".equals(tel) && tel != null) {
            startActivity(iu.getTel(tel));
        }
    }

    private void checkIsOpenLoaction() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {

                    ToastUtil.showMessageOKCancel("您需要获取定位的权限\n设置方法:权限管理->定位->允许", this,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent();
                                    intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                    intent.addCategory("android.intent.category.DEFAULT");
                                    intent.setData(Uri.parse("package:" + "org.aorun.ym"));
                                    startActivity(intent);
                                }
                            });
                } else {
                    openLocationService();
                }
            } else {
                openLocationService();
            }
        } else {
            openLocationService();
        }
    }

    private void openLocationService() {
        Intent intent = new Intent(this, AmapActivity.class);
        intent.putExtra(SourceConstant.STORE_LOCATION_LONGITUDE, String.valueOf(longitude));
        intent.putExtra(SourceConstant.STORE_LOCATION_LATITUDE, String.valueOf(latitude));
        startActivity(intent);
    }

    @Override
    protected void initLayout() {
        mReceiver = new MyBroadcastReceiver();
        setContentView(R.layout.activity_shop_market_store_main);
        IntentFilter filter = new IntentFilter();
        filter.addAction(SourceConstant.START_SHOP_STORE_TUAN_MIAO_TWO);
        filter.addAction(SourceConstant.START_SHOP_STORE_CXZQ_TWO);
        this.registerReceiver(mReceiver, filter);
    }

    @Override
    protected void initView() {
        tvHomeTitle = (TextView) findViewById(R.id.title_textview);
        btnBack = (ImageView) findViewById(R.id.title_btn_left);
        btnSearch = (ImageView) findViewById(R.id.title_btn_right);
        storeIcon = (ImageView) findViewById(R.id.iv_store_main_icon);
        storeName = (TextView) findViewById(R.id.tv_store_main_name);
        storeType = (TextView) findViewById(R.id.tv_store_main_type);
        storeSales = (TextView) findViewById(R.id.tv_store_main_sales);
        storeSkuNum = (TextView) findViewById(R.id.tv_store_main_sku_num);
        gv_home_function = (MyScrollGridView) findViewById(R.id.gv_home_function);
        lv_store_discount = (MyScrollListView) findViewById(R.id.lv_store_discount);
        native_map = (ImageView) findViewById(R.id.native_map);
        user = UserKeeper.readUser(this);
        sid = user.sid;
        fileNameAli = this.getSharedPreferences(SourceConstant.fileNameAli, 0);
        storeCode = fileNameAli.getString(SourceConstant.STORE_CODE, "");
    }

    @Override
    protected void initListener() {
        btnBack.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        native_map.setOnClickListener(this);
    }

    @Override
    protected void processLogic() {
        tvHomeTitle.setText("店铺首页");
        btnSearch.setBackgroundResource(R.drawable.icon_store_home_tel);

        getStoreHomeList();
    }

    /**
     * 获取门店首页的数据
     */

    private void getStoreHomeList() {
        AorunApi.enterStoreHome(sid, storeCode, page_index, mDataCallback);
    }

    @Override
    protected void processData(String data, RequestVo reqVo) {
        switch (reqVo.requestUrl) {
            case RequestUrl.STORE_ENTER_STORE_HOME:
                //门店首页的请求返回的数据
                callbackStoreHomeList(data);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SourceConstant.YU_MEN_TO_SHOP_STORE_TUAN_MIAO == SourceConstant.THREE) {
            SourceConstant.YU_MEN_TO_SHOP_STORE_TUAN_MIAO = SourceConstant.ZERO;
            this.sendBroadcast(new Intent(SourceConstant.START_SHOP_STORE_TUAN_MIAO_TWO));
        }
        if (SourceConstant.YU_MEN_TO_SHOP_STORE_CXZQ == SourceConstant.FIVE) {
//            SourceConstant.YU_MEN_TO_SHOP_STORE_CXZQ = SourceConstant.ZERO;
            this.sendBroadcast(new Intent(SourceConstant.START_SHOP_STORE_CXZQ_TWO));
        }
    }

    private void callbackStoreHomeList(String obj) {
        storeHomeList = JSON.parseObject(obj, StoreHome.class);
        storeInfo = storeHomeList.storeInfoDto;
        longitude = storeInfo.getLongitude();
        latitude = storeInfo.getLatitude();
        if (null != storeInfo && !"".equals(storeInfo)) {
            setStoreInfo(storeInfo);
        }

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        ArrayList<FunctionalBlock> bankDtos = storeHomeList.block;
        if (bankDtos.size() > 0) {
            FunctionStoreListAdapter functionAdapter = new FunctionStoreListAdapter(this, bankDtos, dm.widthPixels);
            gv_home_function.setAdapter(functionAdapter);
            functionAdapter.notifyDataSetChanged();
        }

        storeSkuList = storeHomeList.sku;
        if (storeSkuList.size() > 0) {
            storeSkusAdapter = new StoreSkuListAdapter(this, storeSkuList);
            lv_store_discount.setAdapter(storeSkusAdapter);
//            storeSkusAdapter.notifyDataSetChanged();
        }
    }

    private void setStoreInfo(Shops storeInfo) {
        storeName.setText(storeInfo.getName());
        storeType.setText(storeInfo.getStoreCategoryName());
        storeSales.setText("销量 " + storeInfo.getActualOrderNumber() + "");
        storeSkuNum.setText("共" + storeInfo.getSkuNum() + " 件商品");

        String imgUrl = storeInfo.getStoreImage();
        if ("".equals(imgUrl) || null == imgUrl) {
            storeIcon.setBackgroundResource(R.drawable.error_img);
        } else {
            MyImageLoader.displayImage(imgUrl, storeIcon);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            AppManager.getInstance().AppExit(MainActivity.this);
            SharedPreferences.Editor editor = fileNameAli.edit();
            editor.putString(SourceConstant.STORE_CODE, "");
            editor.commit();
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(SourceConstant.START_SHOP_STORE_TUAN_MIAO_TWO)) {
                Message message = new Message();
                message.what = SourceConstant.ONE;
                mHandler.sendMessage(message);
            }

            if (action.equals(SourceConstant.START_SHOP_STORE_CXZQ_TWO)) {
                Message message = new Message();
                message.what = SourceConstant.TWO;
                mHandler.sendMessage(message);
            }
        }
    }
}
