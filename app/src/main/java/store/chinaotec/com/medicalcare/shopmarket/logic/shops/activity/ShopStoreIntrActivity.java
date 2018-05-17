package store.chinaotec.com.medicalcare.shopmarket.logic.shops.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.base.activity.BaseAoActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.http.AorunApi;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.DialUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.MyImageLoader;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.model.StoreHome;
import store.chinaotec.com.medicalcare.shopmarket.logic.shops.entity.Shops;
import store.chinaotec.com.medicalcare.shopmarket.logic.user.model.User;
import store.chinaotec.com.medicalcare.utill.UserKeeper;


/**
 * 店铺介绍
 * Created by seven on 2016/8/11 0011.
 */
public class ShopStoreIntrActivity extends BaseAoActivity {
    private TextView mTvTitle;
    private User user;
    private String sid;
    private SharedPreferences fileNameAli;
    private String storeCode;
    private ImageView storeIcon;
    private TextView storeName;
    private TextView storeType;
    private TextView storeSales;
    private TextView storeSkuNum;
    private StoreHome storeHomeList;
    private TextView storeIntro;
    private TextView storePhone;
    private TextView storeArea;
    private TextView storeOpenTime;
    private ImageView storeIconphone;
    private Shops storeInfo;
    private RelativeLayout rl_shop_market_store_area;

    @Override
    protected void onClickEvent(View paramView) {
        switch (paramView.getId()) {
            case R.id.iv_shop_market_store_phone:
                checkIsOpenCall();
                break;
            case R.id.rl_shop_market_store_area:
////                intent.putExtra("Address_Map",storeInfo.getArea());
//                intent.putExtra("Address_Map","新江湾城地铁站");
//                startActivity(intent);
                break;
        }
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_shop_market_store_intro);
    }

    @Override
    protected void initView() {
        mTvTitle = (TextView) findViewById(R.id.title_textview);
        storeIcon = (ImageView) findViewById(R.id.iv_store_shop_icon);
        storeName = (TextView) findViewById(R.id.tv_store_shop_name);
        storeType = (TextView) findViewById(R.id.tv_store_shop_type);
        storeSales = (TextView) findViewById(R.id.tv_store_shop_sales);
        storeSkuNum = (TextView) findViewById(R.id.tv_store_shop_sku_num);

        storeIntro = (TextView) findViewById(R.id.tv_shop_market_store_intro);
        storePhone = (TextView) findViewById(R.id.tv_shop_market_store_phone);
        storeArea = (TextView) findViewById(R.id.tv_shop_market_store_area);
        storeOpenTime = (TextView) findViewById(R.id.tv_shop_market_store_opened_time);
        storeIconphone = (ImageView) findViewById(R.id.iv_shop_market_store_phone);
        rl_shop_market_store_area = (RelativeLayout) findViewById(R.id.rl_shop_market_store_area);
        user = UserKeeper.readUser(this);
        sid = user.sid;
        fileNameAli = this.getSharedPreferences(SourceConstant.fileNameAli, 0);
        storeCode = fileNameAli.getString(SourceConstant.STORE_CODE, "");

    }

    @Override
    protected void initListener() {
        storeIconphone.setOnClickListener(this);
        rl_shop_market_store_area.setOnClickListener(this);
    }

    @Override
    protected void processLogic() {
        mTvTitle.setText("店铺简介");
        getData();
    }

    private void getData() {
        AorunApi.getStoreIntro(storeCode, mDataCallback);
    }

    @Override
    protected void processData(String data, RequestVo requestVo) {
        //门店首页的请求返回的数据
        parserData(data);
    }

    private void parserData(String data) {
        storeHomeList = JSON.parseObject(data, StoreHome.class);
        storeInfo = storeHomeList.storeInfoDto;
        setStoreInfo(storeInfo);
    }

    private void setStoreInfo(Shops storeInfo) {
        storeName.setText(storeInfo.getName());
        storeType.setText(storeInfo.getStoreCategoryName());
        storeSales.setText("销量 " + storeInfo.getActualOrderNumber() + "");
        storeSkuNum.setText("共" + storeInfo.getSkuNum() + "件商品");

        storeIntro.setText(storeInfo.getDescription());
        storePhone.setText(storeInfo.getTelephone());
        storeArea.setText(storeInfo.getArea());
        storeOpenTime.setText(storeInfo.getOpenTime());

        String imgUrl = storeInfo.getStoreImage();
        if ("".equals(imgUrl) || null == imgUrl) {
            storeIcon.setBackgroundResource(R.drawable.error_type);
        } else {
            MyImageLoader.displayImage(imgUrl, storeIcon);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            SharedPreferences.Editor editor = fileNameAli.edit();
            editor.putString(SourceConstant.STORE_CODE, "");
            editor.commit();
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
        if (!"".equals(storeInfo.getTelephone()) && null != storeInfo.getTelephone()) {
            startActivity(iu.getTel(storeInfo.getTelephone()));
        }
    }
}
