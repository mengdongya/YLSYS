package store.chinaotec.com.medicalcare.shopmarket.logic.pay.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.base.activity.BaseAoActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.Constant;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.http.AorunApi;
import store.chinaotec.com.medicalcare.shopmarket.common.request.requestUrl.RequestUrl;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.JsonUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.view.MyScrollListView;
import store.chinaotec.com.medicalcare.shopmarket.logic.address.activity.OrderListAddressActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.address.model.AddressInfo;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.OrderCreat;
import store.chinaotec.com.medicalcare.shopmarket.logic.pay.adapter.PayShopSkuAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.pay.model.FreightInfo;
import store.chinaotec.com.medicalcare.shopmarket.logic.pay.model.GotoOrderShops;
import store.chinaotec.com.medicalcare.shopmarket.logic.pay.model.PayShopSku;
import store.chinaotec.com.medicalcare.shopmarket.logic.pay.model.ShopContent;
import store.chinaotec.com.medicalcare.shopmarket.logic.pay.service.ServicePay;
import store.chinaotec.com.medicalcare.shopmarket.logic.pay.service.impl.ServicePayImpl;
import store.chinaotec.com.medicalcare.shopmarket.logic.user.model.User;
import store.chinaotec.com.medicalcare.utill.UserKeeper;

/**
 * 此类描述的是:确认订单页
 *
 * @author: wjc
 * @version:1
 * @date:2016年8月8日
 */
public class PayShopSkuActivity extends BaseAoActivity {

    /**
     * 配送日期 1:周一到周日(任意时间) 2:周一到周五(工作日) 3:周六周日(休息日)
     */
    public static String APP_SEND_DATE = "1";
    private View view_night;
    private ImageView btnBack;
    private Button btnPay;
    private TextView mTvName;
    private TextView mTvPhone;
    private TextView mTvAddress;
    private LinearLayout mllAddress;
    private TextView mTvPriceTotalActual;
    private PopupWindow popupWindow;
    /**
     * 选择显示的配送时间
     */
    private TextView tv_check_send_date;
    private SharedPreferences fileNameAli;
    private String sId;
    private PayShopSku payShopSku;
    private String mProvinceId;
    private ServicePay mServicePay = null;

    private OrderCreat mOrderCreat = new OrderCreat();
    private ArrayList<GotoOrderShops> orderShops;
    private MyScrollListView lv_sku_pay;
    private RelativeLayout rl_send_date;
    private TextView tv_payorder_total_num;
    private String TAG = "PayShopSkuActivity";
    private String shopItemMemo;
    private ArrayList<ShopContent> shopContentList;
    private String entityShopContent;
    private int mPosition;
    private String totalActual;
    private String storeOrderActual;
    private String cardnoPrice;
    private PayShopSkuAdapter payShopSkuAdapter;
    private String freightPrice;
    private String orderItemActual;
    private User user;
    private int contion = 0;
    private TextView tv_price_freight_total;
    private String registerId;
    private TelephonyManager telephonyManager;

    @Override
    protected void onClickEvent(View paramView) {
        switch (paramView.getId()) {
            case R.id.title_btn_left:
                finish();
                break;
            case R.id.rl_send_date:
                view_night.setVisibility(View.VISIBLE);
                sendDateDilog();
                break;
            case R.id.rl_pay_address:
                Intent intent = new Intent(this, OrderListAddressActivity.class);
                intent.putExtra("addressId", mOrderCreat.address_id);
                startActivityForResult(intent, Constant.REQUEST_CODE_ADDRESS_LIST);
                break;
            case R.id.btn_submit_order:
                getShopContent();
                LogUtil.e(TAG, "ONCLICK点击了几次了 :  " + (contion++));
                submitCreatOrder();
                break;
            case R.id.ed_input_shop_goto_order_memo:
//                mPosition = Integer.valueOf(paramView.getTag() + "");
//                shopItemMemo = ((EditText) findViewById(R.id.ed_input_shop_goto_order_memo)).getText().toString().trim();
//                for (int i = 0; i < orderShops.size(); i++) {
//				      String memoItem = orderShops.get(memoPosition).getMemo();
//                    orderShops.get(mPosition).setMemo(shopItemMemo);
//                }
                break;
        }
    }

    private void getShopContent() {

        if (payShopSkuAdapter != null) {
            payShopSkuAdapter.notifyDataSetChanged();
            for (int i = 0; i < orderShops.size(); i++) {

                ShopContent shopContent = new ShopContent(orderShops.get(i).getStoreCode(),
                        orderShops.get(i).getMemo());
                shopContentList.add(shopContent);
            }
            entityShopContent = JsonUtil.entityToJson(shopContentList);
            LogUtil.e(TAG, "entityShopContent==" + entityShopContent);
        }
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_shop_market_pay);
    }

    @Override
    protected void initView() {
        btnBack = (ImageView) findViewById(R.id.title_btn_left);
        btnPay = (Button) findViewById(R.id.btn_submit_order);
//		btnPay.setTag(0);
        tv_payorder_total_num = (TextView) findViewById(R.id.tv_order_num_pay);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvPhone = (TextView) findViewById(R.id.tv_phone);
        mTvAddress = (TextView) findViewById(R.id.tv_address);
        mllAddress = (LinearLayout) findViewById(R.id.ll_address_details);
        mTvPriceTotalActual = (TextView) findViewById(R.id.tv_price_totalActual);
        tv_check_send_date = (TextView) findViewById(R.id.tv_check_send_date);
        rl_send_date = (RelativeLayout) findViewById(R.id.rl_send_date);
        tv_price_freight_total = (TextView) findViewById(R.id.tv_price_freight_total);
        fileNameAli = this.getSharedPreferences(SourceConstant.fileNameAli, 0);
        registerId = getSharedPreferences("jpush", Context.MODE_PRIVATE).getString("regid", "");
        user = UserKeeper.readUser(this);
        sId = user.sid;
        view_night = (View) findViewById(R.id.view_night_send_date);
        lv_sku_pay = (MyScrollListView) findViewById(R.id.lv_sku_pay);
    }

    @Override
    protected void initListener() {
        btnBack.setOnClickListener(this);
        rl_send_date.setOnClickListener(this);
        // 选择地址
        findViewById(R.id.rl_pay_address).setOnClickListener(this);
        btnPay.setOnClickListener(this);
    }

    @Override
    protected void processLogic() {
        ((TextView) findViewById(R.id.title_textview)).setText("确认订单");
        mServicePay = new ServicePayImpl(this, mDataCallback);
        shopContentList = new ArrayList<ShopContent>();
        telephonyManager = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        getData();
    }

    /**
     * 购物车结算请求
     */
    private void getData() {
        AorunApi.getPayCart(sId, mDataCallback);
    }

    /***
     * 提交订单
     */
    private void submitCreatOrder() {
        if ("".equals(mTvName.getText().toString().trim())) {
            ToastUtil.MyToast(this, "请选择送货地址");
            return;
        }

        contion = contion + 1;
        LogUtil.e(TAG, "点击了几次了 :  " + (contion));
        btnPay.setEnabled(false);

        mOrderCreat.sid = sId;
        mOrderCreat.send_date = APP_SEND_DATE;
        mOrderCreat.macAddr = telephonyManager.getDeviceId();
        mOrderCreat.pushSignAddr = registerId;
        mOrderCreat.shopContent = entityShopContent;
        mServicePay.creatOrder(mOrderCreat);
    }

    @Override
    protected void onResume() {
        super.onResume();
        contion = 0;
        LogUtil.e(TAG, "进了onResume方法了");
        btnPay.setEnabled(true);
    }

    @Override
    protected void processData(String data, RequestVo reqVo) {
        switch (reqVo.requestUrl) {
            case RequestUrl.PAY_CART:
                parserGotoShopOrder(data);
                break;

            case RequestUrl.ORDER_CREAT:
                ToastUtil.MyToast(this, "订单提交成功!");
                finish();
                LogUtil.e(TAG, "订单提交成功之后返回的支付序列号 orderCodes ==" + data);
                Intent mIntent = new Intent(this, PaySelectActivity.class);
                mIntent.putExtra(SourceConstant.ORDER_CODE, data);
                mIntent.putExtra(SourceConstant.TOTAL_ACTUAL, totalActual);
                startActivity(mIntent);
                break;
            case RequestUrl.FREIGHT_INFO:
                topullAdress(data);
                break;
        }
    }

    /**
     * j解析服务器返回的 计算运费的(地址)参数
     */
    private void topullAdress(Object data) {

        FreightInfo freightInfo = JsonUtil.jsonToEntity(data.toString(), FreightInfo.class);
        totalActual = freightInfo.getTotalPrice();
        mTvPriceTotalActual.setText("总合计：￥" + freightInfo.getTotalPrice());
        tv_price_freight_total.setVisibility(View.VISIBLE);
        tv_price_freight_total.setText("（含运费：￥" + freightInfo.getTotalFreight() + "）");
        if (orderShops.size() > 0) {
            for (int j = 0; j < orderShops.size(); j++) {
//                orderShops.get(j).getGotoOrderStoreSummaryDto().setSkuTotal(orderItemActual);
                orderShops.get(j).getGotoOrderStoreSummaryDto().setFreight(freightInfo.getChangeAddressListDtoList().get(j).getFreight());
                payShopSkuAdapter.notifyDataSetChanged();
            }
        }

    }

    /**
     * 此方法描述的是： 解析确认订单返回的json串
     */
    private void parserGotoShopOrder(Object data) {
        payShopSku = JsonUtil.jsonToEntity(data.toString(), PayShopSku.class);
        totalActual = payShopSku.getGotoOrderSummaryDto().getTotalPirce();
        mTvPriceTotalActual.setText("总合计：￥" + totalActual);
        tv_price_freight_total.setVisibility(View.GONE);
        tv_payorder_total_num.setText(payShopSku.getGotoOrderSummaryDto().getQuantityTotal() + "");
        mOrderCreat.payoff_id = payShopSku.getGotoOrderSummaryDto().getPayOffId();
        LogUtil.d(TAG, "PayOffId====" + payShopSku.getGotoOrderSummaryDto().getPayOffId());
        PayShopSku.GotoOrderAddress gotoOrderAddress = payShopSku.getGotoOrderAddressDto();

        if ("0".equals(gotoOrderAddress.getDefaultAddressId())) {
            mOrderCreat.address_id = "-1";
            mTvName.setVisibility(View.GONE);
            mTvPhone.setVisibility(View.GONE);
            mTvAddress.setText("点击此处选择地址");
            mProvinceId = "";
        } else {
            mTvName.setText(gotoOrderAddress.getContactor());
            mTvPhone.setText(gotoOrderAddress.getMobile());
            mTvAddress.setText(gotoOrderAddress.getProvinceName() + " "
                    + gotoOrderAddress.getCityName() + " "
                    + gotoOrderAddress.getDistrictName() + " "
                    + gotoOrderAddress.getAddress());
            mProvinceId = gotoOrderAddress.getProvince();
            mOrderCreat.address_id = gotoOrderAddress.getDefaultAddressId();
            getFreight(mOrderCreat.address_id);
        }

        orderShops = payShopSku.getGotoOrderStoreDtoList();
        payShopSkuAdapter = new PayShopSkuAdapter(this, orderShops);
        lv_sku_pay.setAdapter(payShopSkuAdapter);

    }

    private void getFreight(String addressId) {
        AorunApi.getFreight(sId, addressId, mOrderCreat.payoff_id, mDataCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 处理地址
        if (resultCode == RESULT_CANCELED && data != null
                && data.getExtras() != null) {
            if (data.getExtras().containsKey("addressId")) {
                if (data.getExtras().getString("addressId").equals("-1")) {
                    mTvAddress.setText("点击此处选择地址");
                    mTvName.setText("");
                    mTvPhone.setText("");
                    mProvinceId = "-1";
                }
            }
        }

        if (RESULT_OK != resultCode) {
            return;
        } else {

            switch (requestCode) {
                case Constant.REQUEST_CODE_ADDRESS_LIST:

                    AddressInfo addressInfo = data.getParcelableExtra("address");
                    mOrderCreat.address_id = addressInfo.addressId;
                    mTvName.setText(addressInfo.name);
                    mTvPhone.setText(addressInfo.phone);
                    mTvAddress.setText(addressInfo.provinceName + " "
                            + addressInfo.cityName + " " + addressInfo.districtName
                            + " " + addressInfo.addressInfo);
                    mTvName.setVisibility(View.VISIBLE);
                    mTvPhone.setVisibility(View.VISIBLE);
                    mProvinceId = addressInfo.provinceId;
                    getFreight(mOrderCreat.address_id);
//				getOrderFreight(mProvinceId);
                    LogUtil.e(TAG, "省ID:" + addressInfo.provinceId);

                    break;
                case Constant.REQUEST_CODE_CANUSE_COUPON:
                    totalActual = data.getStringExtra("total_Actual");
                    cardnoPrice = data.getStringExtra("cardno_Price");
                    storeOrderActual = data.getStringExtra("store_OrderActual");
                    upDataPrice();
                    break;
            }
        }
    }

    private void upDataPrice() {
        mTvPriceTotalActual.setText(totalActual);
//		if (!"".equals(cardnoPrice)) {
//			orderShops.get(mPosition).setUsableCouponCount("-"+cardnoPrice+"元");
//		}
        orderShops.get(mPosition).getGotoOrderStoreSummaryDto().setSkuTotal(storeOrderActual);
        payShopSkuAdapter.notifyDataSetChanged();
        LogUtil.e(TAG, "执行了刷新操作....");
    }

    private void sendDateDilog() {
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(R.layout.popwindow_shop_pay_send_data, null);
        popupWindow = new PopupWindow(view,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        // 设置可以获得焦点
        popupWindow.setTouchable(true);
        // 设置弹窗内可点击
        popupWindow.setFocusable(true);
        // 设置弹窗背景
        Drawable win_bg = this.getResources().getDrawable(R.color.transparent);
        popupWindow.setBackgroundDrawable(win_bg);
        // 设置弹窗外可点击
        popupWindow.setOutsideTouchable(false);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                view_night.setVisibility(View.GONE);
            }
        });
        view.findViewById(R.id.tv_send_date_1).setOnClickListener(
                new OnClickListener() {
                    private String text1;

                    @Override
                    public void onClick(View v) {
                        text1 = getText(R.string.creat_order_check_send_data_1).toString();
                        tv_check_send_date.setText(text1);
                        APP_SEND_DATE = "1";
                        popupWindow.dismiss();
                    }
                });
        view.findViewById(R.id.tv_send_date_2).setOnClickListener(
                new OnClickListener() {
                    private String text2;

                    @Override
                    public void onClick(View v) {
                        text2 = getText(R.string.creat_order_check_send_data_2).toString();
                        tv_check_send_date.setText(text2);
                        APP_SEND_DATE = "2";
                        popupWindow.dismiss();
                    }
                });
        view.findViewById(R.id.tv_send_date_3).setOnClickListener(
                new OnClickListener() {
                    public String text3;

                    @Override
                    public void onClick(View v) {
                        text3 = getText(R.string.creat_order_check_send_data_3).toString();
                        tv_check_send_date.setText(text3);
                        APP_SEND_DATE = "3";
                        popupWindow.dismiss();
                    }
                });

    }

}
