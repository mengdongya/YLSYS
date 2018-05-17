package store.chinaotec.com.medicalcare.shopmarket.logic.orders.activity;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.base.activity.MainTabActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.Constant;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.http.AorunApi;
import store.chinaotec.com.medicalcare.shopmarket.common.request.requestUrl.RequestUrl;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.JsonUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.Md5Utils;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.view.MyScrollListView;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.adapter.OrderInfoAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.adapter.OrderInfoPriceListAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.adapter.OrderInfoStatusListAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.AppDetailStr;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.ApplyRebateDto;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.CancelOrder;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.OrderOfInfo;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.OrderUser;
import store.chinaotec.com.medicalcare.shopmarket.logic.pay.activity.BasePayActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.activity.SkuInfoActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.user.model.User;
import store.chinaotec.com.medicalcare.utill.UserKeeper;

/**
 * 订单详情
 */
public class OrderInfoActivity extends BasePayActivity implements OnItemClickListener {
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;
    public static boolean ffinsh = true;
    public static boolean wxFlag = false;
    private final int page_size = 10;
    String price;
    HashMap<String, String> orderInfoTypeMap = new HashMap<String, String>();
    /**
     * 订单收件人名称
     */
    private TextView tv_orderinfo_name;
    /**
     * 订单收件人电话
     */
    private TextView tv_orderinfo_phone;
    /**
     * 订单收件人地址
     */
    private TextView tv_orderinfo_address;
    /**
     * 订单状态
     */
    private TextView tv_orderinfo_status;
    /**
     * 订单编号
     */
    private TextView tv_orderinfo_code;
    /**
     * 订单卖家是否已发货
     */
    private TextView tv_orderinfo_trans_status;
    /**
     * 订单金额
     */
    private TextView tv_orderinfo_price;
    /**
     * 订单商品数量
     */
    private TextView tv_orderinfo_num;
    /**
     * 订单运费
     */
    private TextView tv_orderinfo_price_freight;
    /**
     * 订单优惠卷
     */
    private TextView tv_price_orderinfo_invoice;
    /**
     * 订单实付金额
     */
    private TextView tv_price_orderinfo;
    /**
     * 订单下单时间
     */
    private TextView tv_orderinfo_order_time;
    /**
     * 订单成功支付时间
     */
    private TextView tv_orderinfo_pay_time;
    /**
     * 订单发货时间
     */
    private TextView tv_orderinfo_send_time;
    /**
     * 订单合计金额
     */
    private TextView tv_order_total;
    /**
     * （功能）按钮
     */
    private Button btn_action;
    /**
     * 显示订单中的商品
     */
    private MyScrollListView lv_orderinfo_sku;
    private OrderInfoAdapter mAdapter;
    private RelativeLayout mRlAction;
    private OrderUser orderUser;
    /**
     * 支付方式
     */
    private String paymentType = "";
    /**
     * 显示的页数
     */
    private int page_index = 1;
    private boolean mFlagLoadMore = true;
    private boolean mFlagHttp = true;
    /**
     * 红包布局
     */
    private RelativeLayout rl_redAmount_orderinfo;
    /**
     * 红包使用数量
     */
    private TextView tv_redAmount_orderinfo;
    /**
     * 审核不通过原因
     */
    private TextView tv_orderinfo_fail_reason;
    /**
     * 积分
     */
    private TextView tv_orderinfo_integral;
    /**
     * 价格清单
     */
    private MyScrollListView lv_orderinfo_price_list;
    /**
     * 状态清单
     */
    private MyScrollListView lv_orderinfo_status_list;
    private User user;
    private TextView tv_order_info_store_name;
    private SharedPreferences fileNameAli;
    private String sid;
    private String TAG = "OrderInfoActivity";
    private int orderStatus;
    private OrderInfoPriceListAdapter orderInfoPriceListAdapter;
    private OrderInfoStatusListAdapter orderInfoStatusListAdapter;
    private String mOrderCode;

    private boolean payResultDialog() {
        final ProgressDialog dialog = ProgressDialog.show(this, "温馨提示",
                "支付结果确认中,请稍后", true);
        dialog.show();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                dialog.dismiss();
                wxFlag = true;
                setResult(RESULT_OK);
                finish();
            }
        }, 3000);
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!ffinsh) {
            this.finish();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onClickEvent(View paramView) {
        switch (paramView.getId()) {
            case R.id.title_btn_left:
                if (SourceConstant.IS_BACK_CURRENT_APP != SourceConstant.THREE) {
                    finish();
                } else {
                    ((RadioButton) MainTabActivity.main_radio.getChildAt(3)).setChecked(true);
                }
                break;
            case R.id.btn_action:
                switch (orderStatus) {
                    // 1代表待付款
                    case 1:
                        //orderUser = (OrderUser) getIntent().getSerializableExtra("order");
                        if (orderUser != null) {
//					Intent intent = new Intent(this, PaySelectActivity.class);TODO
//					LogUtil.e(TAG, "====TotalActual===="+orderUser.getPaymentTotalActual()+"");
//					intent.putExtra(SourceConstant.TOTAL_ACTUAL,
//							orderUser.getPaymentTotalActual()+"");
//					intent.putExtra(SourceConstant.ORDER_CODE,
//							orderUser.getOrderCode());
//					setResult(RESULT_OK, intent);
//					startActivityForResult(intent, Constant.REQUEST_CODE_PAY_SELECT);

                        }
                        // -------------------------------------------------------------

                        break;
                    //2-审核中
                    case 2:
                        break;
                    // 3代表待发货--操作（提醒发货）
                    case 3:
                        remindDelivery();
                        break;
                    case 4://--审核失败
                        // btn_action.setText("请联系客服");
//				ToastUtil.MyToast(this, "已申请退款，请您耐心等待");
                        applyRebate();
                        break;
                    // 5待收货
                    case 5:
                    case 7://确认收货
                        confirmReceipt();
                        break;
                    case 6:
                    case 8://去评价
                        // 去评价，跳转到评价
                        SourceConstant.skuByOrder = orderUser.getSkuByOrderLines().get(0);
                        Intent kIntent = new Intent(OrderInfoActivity.this,
                                AllOrderActivity.class);
                        kIntent.putExtra(SourceConstant.ALLORDERS_FIRST, 4);
                        startActivity(kIntent);
                        break;
                    case 9:
                    case 10://删除订单
                        delorders();
                        break;
                    // 12 已退款 订单已取消 能删除
                    case 12:
                        // 删除订单
                        delorders();
                        break;
                    default:
                        break;
                }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_shop_market_order_info);
    }

    @Override
    protected void initView() {
//		orderUser = (OrderUser) getIntent().getSerializableExtra("order");
        ((TextView) findViewById(R.id.title_textview))
                .setText(R.string.orderinfo_title);
        tv_orderinfo_name = (TextView) findViewById(R.id.tv_orderinfo_name);
        tv_orderinfo_phone = (TextView) findViewById(R.id.tv_orderinfo_phone);
        tv_orderinfo_address = (TextView) findViewById(R.id.tv_orderinfo_address);
        tv_orderinfo_status = (TextView) findViewById(R.id.tv_orderinfo_status);
        tv_orderinfo_code = (TextView) findViewById(R.id.tv_orderinfo_code);
        tv_orderinfo_trans_status = (TextView) findViewById(R.id.tv_orderinfo_trans_status);
        tv_orderinfo_price = (TextView) findViewById(R.id.tv_orderinfo_price);
        tv_orderinfo_num = (TextView) findViewById(R.id.tv_orderinfo_num);
        tv_orderinfo_price_freight = (TextView) findViewById(R.id.tv_orderinfo_price_freight);
        tv_price_orderinfo_invoice = (TextView) findViewById(R.id.tv_price_orderinfo_invoice);
        tv_price_orderinfo = (TextView) findViewById(R.id.tv_price_orderinfo);
        tv_orderinfo_order_time = (TextView) findViewById(R.id.tv_orderinfo_order_time);
        tv_orderinfo_pay_time = (TextView) findViewById(R.id.tv_orderinfo_pay_time);
        tv_orderinfo_send_time = (TextView) findViewById(R.id.tv_orderinfo_send_time);
        tv_orderinfo_fail_reason = (TextView) findViewById(R.id.tv_orderinfo_fail_reason);
        rl_redAmount_orderinfo = (RelativeLayout) findViewById(R.id.rl_redAmount_orderinfo);
        tv_redAmount_orderinfo = (TextView) findViewById(R.id.tv_redAmount_orderinfo);
        tv_order_total = (TextView) findViewById(R.id.tv_order_total);
        tv_orderinfo_integral = (TextView) findViewById(R.id.tv_orderinfo_integral);
        btn_action = (Button) findViewById(R.id.btn_action);
        lv_orderinfo_sku = (MyScrollListView) findViewById(R.id.lv_orderinfo_sku);
        lv_orderinfo_price_list = (MyScrollListView) findViewById(R.id.lv_orderinfo_price_list);
        lv_orderinfo_status_list = (MyScrollListView) findViewById(R.id.lv_orderinfo_status_list);
        tv_order_info_store_name = (TextView) findViewById(R.id.tv_order_info_store_name);
    }

    @Override
    protected void initListener() {
        ImageView btnBack = (ImageView) findViewById(R.id.title_btn_left);
        btnBack.setOnClickListener(this);
        btn_action.setOnClickListener(this);
    }

    @Override
    protected void processLogic() {
        fileNameAli = this.getSharedPreferences(SourceConstant.fileNameAli, 0);
        user = UserKeeper.readUser(this);
        sid = user.sid;
//		orderUser = (OrderUser) getIntent().getSerializableExtra("order");
        Intent intent = getIntent();
        mOrderCode = intent.getStringExtra("order_code");
        LogUtil.e(TAG, "mOrderCode==" + mOrderCode);

        getOrderInfo(mOrderCode);
    }

    /**
     * 请求订单详情
     */
    private void getOrderInfo(String orderCode) {
        AorunApi.getOrderInfo(sid, orderCode, mDataCallback);
    }

    private void initData() {

        // 给控件控件赋值
//		tv_orderinfo_name.setText("收件人:"+orderUser.getReceiver());
//		tv_orderinfo_phone.setText(orderUser.getReceiverMobile());
//		tv_orderinfo_address.setText("收货地址:"+orderUser.getProvince()
//				+ orderUser.getCity() + orderUser.getDistrict()
//				+ orderUser.getAddress());
        tv_orderinfo_name.setText(orderUser.getAddressStr().get(0) + orderUser.getAddressStr().get(1));
        tv_orderinfo_address.setText(orderUser.getAddressStr().get(2) + orderUser.getAddressStr().get(3));
        orderStatus = Integer.parseInt(orderUser.getOrderStatus());
        String orderStatusName = "";
        String orderStatusNames = "";
        switch (orderStatus) {
            // 1代表待付款
            case 1:
                btn_action.setText(R.string.orderinfo_pay);
                orderStatusName = String.format(this.getResources().getString(
                        R.string.orderinfo_tab_pay));
                orderStatusNames = "您的订单已提交，请在2小时内完成支付订单，超时订单关闭";
                tv_orderinfo_pay_time.setVisibility(View.GONE);
                tv_orderinfo_send_time.setVisibility(View.GONE);
                tv_orderinfo_fail_reason.setVisibility(View.GONE);
                break;
            case 2:
                orderStatusNames = "审核中";
                orderStatusName = "审核中"; // 不能删除
                btn_action.setText("审核中");
                break;
            // 3代表待发货
            case 3:
                btn_action.setText(R.string.orderinfo_remind_send);
                orderStatusNames = "订单支付成功";
                orderStatusName = String.format(this.getResources().getString(
                        R.string.orderinfo_tab_send));
                tv_orderinfo_fail_reason.setVisibility(View.GONE);
                tv_orderinfo_send_time.setVisibility(View.GONE);
                break;
            case 4:
                orderStatusNames = "审核失败"; // 不能删除
                btn_action.setText("申请退款");
                orderStatusName = "订单审核失败";
                tv_orderinfo_send_time.setVisibility(View.GONE);
                tv_orderinfo_fail_reason.setVisibility(View.VISIBLE);
                break;
            // 5待收货
            case 5:
            case 7:
                orderStatusNames = "卖家已发货";
                btn_action.setText(R.string.orderinfo_confirm_receipt);
                orderStatusName = String.format(this.getResources().getString(
                        R.string.orderinfo_tab_receive));
                tv_orderinfo_pay_time.setVisibility(View.VISIBLE);
                tv_orderinfo_send_time.setVisibility(View.VISIBLE);
                tv_orderinfo_fail_reason.setVisibility(View.GONE);
                break;
            case 6:
            case 8:
                orderStatusNames = "订单交易完成";
                orderStatusName = "交易完成";// 评价 //【能删除】
                btn_action.setText("去评价");
                tv_orderinfo_fail_reason.setVisibility(View.GONE);
                break;
            case 9:
            case 10:
            default:
                orderStatusNames = "订单已关闭"; // 【能删除】
                orderStatusName = "订单已关闭"; // 【能删除】
                btn_action.setText("删除订单");
                tv_orderinfo_fail_reason.setVisibility(View.GONE);
                break;
            // 12 已退款 订单已取消 能删除
            case 12:
                orderStatusNames = "审核失败，已退款"; // 【能删除】
                orderStatusName = "订单已关闭"; // 【能删除】
                btn_action.setText("删除订单");
                break;
        }

        if (!"".equals(orderUser.getEpointsMoney()) && orderUser.getEpointsMoney() != null) {
            tv_orderinfo_integral.setText(orderUser.getEpointsMoney() + "");
        } else {
            tv_orderinfo_integral.setText("0.0");
        }
        tv_order_info_store_name.setText(orderUser.getStoreName());
        tv_orderinfo_num.setText(orderUser.getSkuNum() + "");

        tv_price_orderinfo.setText(orderUser.getPaymentTotalActual() + "");
        LogUtil.e(TAG, "TotalActual() + ==" + orderUser.getPaymentTotalActual());
        tv_orderinfo_order_time.setText(Html.fromHtml(String.format(this
                        .getResources().getString(R.string.orderinfo_order_time),
                orderUser.getCreateOrderTime())));
        tv_orderinfo_pay_time.setText(Html.fromHtml(String.format(this
                        .getResources().getString(R.string.orderinfo_pay_time),
                orderUser.getPaymentCompleteTime())));
        tv_orderinfo_send_time.setText(Html.fromHtml(String.format(this
                        .getResources().getString(R.string.orderinfo_send_time),
                orderUser.getDeliveryTime())));
        tv_orderinfo_fail_reason.setText(Html.fromHtml(String.format(this
                        .getResources().getString(R.string.orderinfo_fail_reason),
                orderUser.getFailReason())));
        LogUtil.e(TAG, "tv_order_total============" + orderUser.getTotalPrice());
        tv_order_total.setText(Html.fromHtml(String.format(this.getResources()
                        .getString(R.string.orderinfo_total_price),
                "<font color='#FE0002'>" + orderUser.getTotalPrice()
                        + "</font>")));
        ArrayList<String> orderStr = orderUser.getOrderStr();
        ArrayList<AppDetailStr> orderStrList = new ArrayList<AppDetailStr>();
        for (int j = 0; j < orderStr.size(); j += 2) {
            AppDetailStr appDetailStrr = new AppDetailStr(orderStr.get(j), orderStr.get(j + 1));

            LogUtil.e(TAG, "orderStr[i]===" + orderStr.get(j));
            orderStrList.add(appDetailStrr);
        }
//		LogUtil.e("把字符串转换成json====", "orderStrList======"+JsonUtil.entityToJson(orderStrList));

        orderInfoStatusListAdapter = new OrderInfoStatusListAdapter(this, orderStrList);
        lv_orderinfo_status_list.setAdapter(orderInfoStatusListAdapter);

        mAdapter = new OrderInfoAdapter(this, orderUser.getSkuByOrderLines());
        lv_orderinfo_sku.setAdapter(mAdapter);
        lv_orderinfo_sku.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(OrderInfoActivity.this, SkuInfoActivity.class);
                intent.putExtra(SourceConstant.SKU_CODE, orderUser.getSkuByOrderLines().get(position).getSkuCode());
//				startActivity(intent);
            }
        });

        ArrayList<String> priceStr = orderUser.getPriceStr();
        ArrayList<AppDetailStr> list = new ArrayList<AppDetailStr>();
        for (int i = 0; i < priceStr.size(); i += 2) {
            AppDetailStr appDetailStr = new AppDetailStr(priceStr.get(i), priceStr.get(i + 1));

            LogUtil.e(TAG, "priceStr[i]===" + priceStr.get(i));
            list.add(appDetailStr);
        }
        orderInfoPriceListAdapter = new OrderInfoPriceListAdapter(this, list);
        lv_orderinfo_price_list.setAdapter(orderInfoPriceListAdapter);

    }

    @Override
    protected void processData(String data, RequestVo reqVo) {
        switch (reqVo.requestUrl) {
            case RequestUrl.ORDER_INFO:
                orderUser = JsonUtil.jsonToEntity(data, OrderUser.class);
                if (orderUser != null) {
                    initData();
                }
                break;
            case RequestUrl.ORDER_CONFIRM_RECEIPT:
                ToastUtil.MyToast(this, "快去评价下你的宝贝吧");
                this.finish();
                break;
            case RequestUrl.ORDER_CANCEL:
//			ToastUtil.MyToast(this, "订单取消成功");
                this.finish();
                break;

            case RequestUrl.ORDER_DELETE:
//			ToastUtil.MyToast(this, "订单删除成功");
                this.finish();
                break;
            // 提醒卖家发货发货
            case RequestUrl.ORDER_REMINDDELIVERY:
                ToastUtil.MyToast(this, "已提醒卖家发货");
//			this.finish();
                break;
            case RequestUrl.ORDER_APPLY_REFUND:
                ToastUtil.MyToast(this, "已提交申请退款,请耐心等待");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (RESULT_OK != resultCode) {
            return;
        }
        switch (requestCode) {
            case Constant.REQUEST_CODE_PAY_SELECT:
                break;
            default:
                break;
        }
    }

    @Override
    public void doStartUnionPayPlugin(Activity activity, String tn, String mode) {
//		UPPayAssistEx.startPayByJAR(activity, PayActivity.class, null, null,tn, mode);
    }

    @Override
    public void updateTextView(TextView tv) {
        // TODO Auto-generated method stub

    }

    @Override
    public void payErrory(String str) {

        if (str != null && str.equalsIgnoreCase("success")) {
            ToastUtil.MyToast(this, "支付成功");
            setResult(RESULT_OK);
            finish();
        } else {
            ToastUtil.MyToast(this, "支付失败");
            setResult(RESULT_OK);
            finish();

        }
    }

    /**
     * 向服务器请求取消订单
     */
    private void cancelOrder() {
        AorunApi.getCancelOrder(sid, orderUser.getOrderCode(), "cancelOrder", mDataCallback);
    }

    /**
     * 向服务器请求删除订单
     */
    private void delorders() {
        AorunApi.getOrderDel(sid, orderUser.getOrderCode(), "deleteOrder", mDataCallback);
    }

    /**
     * 提醒卖家发货
     */
    private void remindDelivery() {
        if (mFlagHttp) {
            mFlagHttp = false;
            if (mFlagLoadMore) {
                AorunApi.getRemindDelivery(sid, orderUser.getOrderCode(), "remindDelivery", mDataCallback);
            }
        }
    }

    /**
     * 申请退款
     */
    private void applyRebate() {
        if (mFlagHttp) {
            mFlagHttp = false;
            if (mFlagLoadMore) {
                ApplyRebateDto applyRebateDto = new ApplyRebateDto(sid, orderUser.getOrderCode(), "1", "");
                String data = JsonUtil.entityToJson(applyRebateDto);
                String mac = Md5Utils.getMac(data);
                AorunApi.applyRefund(data, mac, mDataCallback);
            }
        }

    }

    /**
     * 向服务器请求确认收货
     */
    private void confirmReceipt() {
        if (mFlagHttp) {
            mFlagHttp = false;
            if (mFlagLoadMore) {
                OrderOfInfo morder = new OrderOfInfo(sid, "", "", page_index
                        + "", page_size + "");
                CancelOrder mCancelOrder = new CancelOrder(sid, orderUser.getOrderStatus(),
                        orderUser.getOrderCode());
                String toJson = JsonUtil.entityToJson(mCancelOrder);
//                AorunApi.getOrderConfirmReceipt(toJson, mDataCallback);
            }
        }
    }

    /**
     * 取消订单弹出的dialog
     */
    protected void unOrderdialog() {
        Builder builder = new Builder(this);
        builder.setMessage("确定取消订单？");
        builder.setTitle("取消订单");
        builder.setPositiveButton("是", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancelOrder();
            }
        });
        builder.setNegativeButton("否", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * 确认收货之后去评价弹出的dialog
     */
    protected void Productdialog() {
        Builder builder = new Builder(this);
        builder.setMessage("您已经确定收货啦");
        builder.setTitle("商品评价");
        builder.setPositiveButton("去评价", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent mIntent = new Intent(OrderInfoActivity.this,
                        AllOrderActivity.class);
                mIntent.putExtra(SourceConstant.ALLORDERS_FIRST, 4);
                startActivity(mIntent);
            }
        });

        builder.setNegativeButton("返回", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

}
