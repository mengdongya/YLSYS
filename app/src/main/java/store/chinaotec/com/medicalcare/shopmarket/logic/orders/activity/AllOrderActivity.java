package store.chinaotec.com.medicalcare.shopmarket.logic.orders.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.shopmarket.common.base.activity.BaseAoActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.Constant;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.http.AorunApi;
import store.chinaotec.com.medicalcare.shopmarket.common.request.requestUrl.RequestUrl;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.JsonUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.Md5Utils;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.VerifyUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.view.XListView;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.adapter.EvaluateAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.adapter.OrderAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.ApplyRebateDto;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.EvaluateOrderUserRes;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.OrderNumber;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.OrderTypeNum;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.OrderUser;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.OrderUserRes;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.SkuByOrderLine;
import store.chinaotec.com.medicalcare.shopmarket.logic.pay.activity.PaySelectActivity;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * 全部订单
 */
public class AllOrderActivity extends BaseAoActivity implements AdapterView.OnItemClickListener, XListView.IXListViewListener {
    public static boolean ffinsh = true;
    private final int page_size = 10;
    HashMap<String, String> myOrdersTypeMap = new HashMap<String, String>();
    HashMap<String, String> myOrderTypeMap = new HashMap<String, String>();
    int[] arrMenuTv = {R.id.tv_menu_name, R.id.tv_menu_name01, R.id.tv_menu_name02, R.id.tv_menu_name03,
            R.id.tv_menu_name04};
    int[] arrNumberTv = {R.id.tv_menu_number, R.id.tv_menu_number01, R.id.tv_menu_number02, R.id.tv_menu_number03,
            R.id.tv_menu_number04};
    /****
     * 订单类型orderType
     * 全部订单(allOrder)，待付款(waitPay)，待发货(waitDeliver)，待收货
     */
    String[] orderType = {"allOrder", "waitPay", "waitDeliver", "waitReceipt"};
    /**
     * 订单列表适配器
     */
    private OrderAdapter mAdapter;
    /**
     * 用户登录标识
     */
    private String sid;
    /**
     * 订单列表的listview
     */
    private XListView lv_order_content;
    private String[] arries;
    /**
     * 商品数量
     */
    private TextView tv_total;
    /**
     * 全部订单的menu
     */
    private TextView tv_menu_name;
    /**
     * 待付款订单的menu
     */
    private TextView tv_menu_name01;
    /**
     * 待发货订单的menu
     */
    private TextView tv_menu_name02;
    /**
     * 待收货订单订单的menu
     */
    private TextView tv_menu_name03;
    /**
     * 带评价商品的menu
     */
    private TextView tv_menu_name04;
    private int mOrderDel = -1;
    /**
     * 订单数量
     */
    private int total;
    /**
     * 全部订单的布局
     */
    private LinearLayout ll_order_all;
    /**
     * 待付款订单的布局
     */
    private LinearLayout ll_order_01;
    /**
     * 待发货订单的布局
     */
    private LinearLayout ll_order_02;
    /**
     * 待收货订单的布局
     */
    private LinearLayout ll_order_03;
    /**
     * 带评价商品的布局
     */
    private LinearLayout ll_order_04;
    private OrderUserRes mOrderUserRes = null;
    /**
     * 订单集合
     */
    private ArrayList<OrderUser> orderList;
    /**
     * 订单-带评价-商品集合
     */
    private ArrayList<SkuByOrderLine> evaluatrSkulist;
    /**
     * 商品列表的adapter
     */
    private EvaluateAdapter mEvaluateAdapter;
    /**
     * 当前点击的订单编码
     */
    private String orderCode;
    /**
     * 显示订单的布局
     */
    private LinearLayout ll_order;
    /**
     * 没有订单的时候显示布局
     */
    private LinearLayout ll_no_order;
    private int page_index = 1;
    private boolean mFlagLoadMore = true;
    private boolean mFlagHttp = true;
    private String TAG = "AllOrderActivity";
    private OrderNumber orderNumber;
    private JsonElement json;
    private StringBuffer move_falg;
    private int initialPosition;
    private int position;
    /**
     * 下面的蓝色滚动条
     */
    private View view_bow;
    private TextView tv_menu_number;
    private TextView tv_menu_number01;
    private TextView tv_menu_number02;
    private TextView tv_menu_number03;
    private TextView tv_menu_number04;
    private OrderTypeNum orderTypeNums;
    private boolean flagClear = false;
    private ArrayList<OrderUser> mDataList;
    private boolean isFirstComeIn = true;
    private String orderStatus;
    private Handler handler;
    private boolean isRefresh = true;
    private EvaluateOrderUserRes evaluateOrderUser;

    @Override
    protected void onClickEvent(View paramView) {
        switch (paramView.getId()) {
            case R.id.title_btn_left:
                finish();
                break;
            case R.id.ll_order_all:
                initOrderData();
                position = 0;
                isFirstComeIn = true;
                flagClear = true;
                orderStatus = orderType[0];
                page_index = 1;
                startTranslateAnimation(position);
                setTextViewForColor(R.id.tv_menu_name, R.id.tv_menu_number);
                myOrdersTypeMap.put("order_type", "全部订单");
                getData(orderStatus);
                break;
            case R.id.ll_order_01:
                initOrderData();
                position = 1;
                isFirstComeIn = true;
                flagClear = true;
                orderStatus = orderType[1];
                page_index = 1;
                startTranslateAnimation(position);
                setTextViewForColor(R.id.tv_menu_name01, R.id.tv_menu_number01);
                myOrdersTypeMap.put("order_type", "代付款订单");
                getData(orderStatus);
//                getDataPay();
                break;
            case R.id.ll_order_02:
                initOrderData();
                position = 2;
                orderStatus = orderType[2];
                isFirstComeIn = true;
                flagClear = true;
                page_index = 1;
                startTranslateAnimation(position);
                setTextViewForColor(R.id.tv_menu_name02, R.id.tv_menu_number02);
                myOrdersTypeMap.put("order_type", "代发货订单");
                getData(orderStatus);
//                getDataSend();
                break;
            case R.id.ll_order_03:
                initOrderData();
                position = 3;
                isFirstComeIn = true;
                flagClear = true;
                orderStatus = orderType[3];
                page_index = 1;
                startTranslateAnimation(position);
                setTextViewForColor(R.id.tv_menu_name03, R.id.tv_menu_number03);
                myOrdersTypeMap.put("order_type", "代收货订单");
                getData(orderStatus);
//                getDataReceive();
                break;
            case R.id.ll_order_04:
                initEvaluteData();
                position = 4;
                isFirstComeIn = true;
                flagClear = true;
                page_index = 1;
                startTranslateAnimation(position);
                setTextViewForColor(R.id.tv_menu_name04, R.id.tv_menu_number04);
                myOrdersTypeMap.put("order_type", "待评价");
                getDataEvaluate();
                break;
            case R.id.tv_bt1:
                // 拿到订单的position
                int position0 = Integer.valueOf(paramView.getTag() + "");
                int orderStatus = -1;
                String ItemorderCode = "";
                // 根据position 拿到订单状态
                if (orderList != null) {
                    orderStatus = Integer.parseInt(orderList.get(position0).getOrderStatus());
                    ItemorderCode = orderList.get(position0).getOrderCode();
                }
                if (orderStatus != -1 && !VerifyUtil.isEmpyty(ItemorderCode)) {
                    switch (orderStatus) {
                        // 1代表待付款（按钮1的作用的取消订单）
                        case 1:
                            unOrderdialog(ItemorderCode);
                            myOrderTypeMap.put("orderinfo_btn_type", "取消订单");
                            SourceConstant.ISGetDate = true;
                            break;
                        // 2代表审核中
                        case 2:
                            break;
                        // 3代表待发货
                        case 3:
                            break;
                        // 4 审核失败，请联系客服 暂不能删除
                        case 4:
//					ToastUtil.MyToast(this, "已申请退款，请您耐心等待");
                            break;
                        // 5，7待收货
                        case 5:
                            checkOrderExpress(ItemorderCode);
                            break;
                        case 7:
                            break;
                        // 9 会员取消订单 订单已取消 能删除
                        // 10 系统取消订单 订单已取消 能删除
                        case 9:
                        case 10:
                        case 12:

                            break;
                        // 6 交易完成 能评价 能删除
                        case 6:
                        case 8:
                            gotoEvaluate();
                            myOrderTypeMap.put("orderinfo_btn_type", "去评价");
//                            MobclickAgent.onEvent(context, UMENG_BTN_MY_ORDERS_ITEM_TYPE, myOrderTypeMap);
                            SourceConstant.ISGetDate = true;
                            break;
                        default:
                            break;
                    }
                }
                break;
            case R.id.tv_bt2:
                // 拿到订单的position
                int position1 = Integer.valueOf(paramView.getTag() + "");
                int orderStatus1 = -1;
                String price = "";
                String ItemorderCode1 = "";
                // 根据position 拿到订单状态
                if (orderList != null) {
                    orderStatus1 = Integer.parseInt(orderList.get(position1).getOrderStatus());
                    ItemorderCode1 = orderList.get(position1).getOrderCode();
                    price = orderList.get(position1).getPaymentTotalActual() + "";
                    LogUtil.e(TAG, "price:" + price + "ItemorderCode1:" + ItemorderCode1);
                }
                if (orderStatus1 != -1 && !VerifyUtil.isEmpyty(ItemorderCode1)) {
                    switch (orderStatus1) {
                        // 1代表待付款
                        case 1:
                            gotoPay(price, ItemorderCode1);

                            SourceConstant.ISGetDate = true;
                            break;
                        // 2代表审核中
                        case 2:
                            myOrderTypeMap.put("orderinfo_btn_type", "审核中");
//                            MobclickAgent.onEvent(context, UMENG_BTN_MY_ORDERS_ITEM_TYPE, myOrderTypeMap);

                            break;
                        // 3代表待发货
                        case 3:
                            remindDelivery(ItemorderCode1);
                            myOrderTypeMap.put("orderinfo_btn_type", "提醒发货");
//                            MobclickAgent.onEvent(context, UMENG_BTN_MY_ORDERS_ITEM_TYPE, myOrderTypeMap);

                            break;
                        // 4 审核失败，请联系客服 暂不能删除
                        case 4:
                            ApplyRebateDialog(ItemorderCode1);
//					ToastUtil.MyToast(this, "已申请退款，请您耐心等待");
                            myOrderTypeMap.put("orderinfo_btn_type", "审核失败");
                            break;
                        // 5，7待收货
                        case 5:
                        case 7:
                            ProductDialog(orderStatus1 + "", ItemorderCode1);
                            myOrderTypeMap.put("orderinfo_btn_type", "确认收货");
                            SourceConstant.ISGetDate = true;
                            break;
                        // 6 交易完成 能评价 能删除
                        case 6:
                        case 8:
                            delOrderDialog(orderStatus1 + "", ItemorderCode1);
                            myOrderTypeMap.put("orderinfo_btn_type", "删除订单");
                            SourceConstant.ISGetDate = true;
                            break;
                        // 9 会员取消订单 订单已取消 能删除
                        // 10 系统取消订单 订单已取消 能删除
                        case 9:
                        case 10:
                        case 11:
                            // 12 已退款 订单已取消 能删除
                        case 12:
                            delOrderDialog(orderStatus1 + "", ItemorderCode1);
                            myOrderTypeMap.put("orderinfo_btn_type", "删除订单");
                            SourceConstant.ISGetDate = true;
                            break;
                        default:
                            break;
                    }
                }
                break;

            default:
                break;
        }

    }

    private void initEvaluteData() {
        evaluatrSkulist = new ArrayList<SkuByOrderLine>();
        mEvaluateAdapter = new EvaluateAdapter(this, evaluatrSkulist);
        lv_order_content.setAdapter(mEvaluateAdapter);
    }

    /**
     * 查看物流信息
     *
     * @param itemorderCode
     */
    private void checkOrderExpress(String itemorderCode) {
        Intent intent = new Intent(this, OrderExpressInfoActivity.class);
        intent.putExtra("itemorderCode", itemorderCode);
        startActivity(intent);
    }

    /**
     * 查询所有订单列表
     */
    private void getData(String orderStatus) {
        AorunApi.getOrderAll(sid, page_index, orderStatus, mDataCallback);
    }

    /**
     * 查询待评价列表
     */
    private void getDataEvaluate() {
        AorunApi.getOrderEvaluateList(sid, page_index, mDataCallback);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_shop_market_order_list);
        ((TextView) findViewById(R.id.title_textview)).setText("订单列表");
        SourceConstant.IS_ORDER_OK = false;

    }

    @Override
    protected void initView() {

        sid = SpUtill.getString(this, ResourseSum.Medica_SP, "saveSid");
        arries = SourceConstant.list_Menu_Name;
        lv_order_content = (XListView) findViewById(R.id.lv_order_content);
        ll_order_all = (LinearLayout) findViewById(R.id.ll_order_all);
        ll_order_01 = (LinearLayout) findViewById(R.id.ll_order_01);
        ll_order_02 = (LinearLayout) findViewById(R.id.ll_order_02);
        ll_order_03 = (LinearLayout) findViewById(R.id.ll_order_03);
        ll_order_04 = (LinearLayout) findViewById(R.id.ll_order_04);
        ll_no_order = (LinearLayout) findViewById(R.id.ll_no_order);
        ll_order = (LinearLayout) findViewById(R.id.ll_order);
        tv_menu_name = (TextView) findViewById(R.id.tv_menu_name);
        tv_menu_name01 = (TextView) findViewById(R.id.tv_menu_name01);
        tv_menu_name02 = (TextView) findViewById(R.id.tv_menu_name02);
        tv_menu_name03 = (TextView) findViewById(R.id.tv_menu_name03);
        tv_menu_name04 = (TextView) findViewById(R.id.tv_menu_name04);
        tv_menu_number = (TextView) findViewById(R.id.tv_menu_number);
        tv_menu_number01 = (TextView) findViewById(R.id.tv_menu_number01);
        tv_menu_number02 = (TextView) findViewById(R.id.tv_menu_number02);
        tv_menu_number03 = (TextView) findViewById(R.id.tv_menu_number03);
        tv_menu_number04 = (TextView) findViewById(R.id.tv_menu_number04);
        view_bow = (View) findViewById(R.id.view_bow);
    }

    @Override
    protected void initListener() {
        ImageView btnBack = (ImageView) findViewById(R.id.title_btn_left);
        btnBack.setOnClickListener(this);
        ll_order_all.setOnClickListener(this);
        ll_order_01.setOnClickListener(this);
        ll_order_02.setOnClickListener(this);
        ll_order_03.setOnClickListener(this);
        ll_order_04.setOnClickListener(this);
        lv_order_content.setOnItemClickListener(this);
        lv_order_content.setPullLoadEnable(true);
        lv_order_content.setXListViewListener(this);
        handler = new Handler();
    }

    @Override
    protected void processLogic() {
        isFirstComeIn = true;
        // 根据跳转判断首次显示哪一页
        Intent intent = getIntent();
        int indx = intent.getIntExtra(SourceConstant.ALLORDERS_FIRST, 0);
        initData();
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) view_bow.getLayoutParams(); // 取控件textView当前的布局参数
        linearParams.width = SourceConstant.screenWidth / 5;
        linearParams.leftMargin = indx * linearParams.width;
        linearParams.rightMargin = (5 - indx) * linearParams.width;
        initialPosition = indx * linearParams.width;
        getmData(indx);
    }

    public void getmData(int indx) {
        switch (indx) {
            case 0:
                position = 0;
                orderStatus = orderType[0];
                getData(orderStatus);
                setTextViewForColor(R.id.tv_menu_name, R.id.tv_menu_number);
                break;
            case 1:
                position = 1;
                orderStatus = orderType[1];
                getData(orderStatus);
                setTextViewForColor(R.id.tv_menu_name01, R.id.tv_menu_number01);
                break;
            case 2:
                position = 2;
                orderStatus = orderType[2];
                getData(orderStatus);
                setTextViewForColor(R.id.tv_menu_name02, R.id.tv_menu_number02);
                break;
            case 3:
                position = 3;
                LogUtil.e(TAG, "position:" + position);
                orderStatus = orderType[3];
                getData(orderStatus);
                setTextViewForColor(R.id.tv_menu_name03, R.id.tv_menu_number03);
                break;
            case 4:
                position = 4;
                initEvaluteData();
                getDataEvaluate();
                setTextViewForColor(R.id.tv_menu_name04, R.id.tv_menu_number04);
                break;
        }
    }

    private void initData() {
        if ("".equals(sid)) {
            this.finish();
//            startActivity(new Intent(this, LoginActivity.class));TODO
        }
        initOrderData();
    }

    private void initOrderData() {
        mDataList = new ArrayList<OrderUser>();
        mAdapter = new OrderAdapter(this, mDataList);
        lv_order_content.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                flagClear = true;
                page_index = 1;
                if (position != 4) {
                    getData(orderStatus);
                } else {
                    getDataEvaluate();
                }

                load();
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isRefresh) {
                    if (position != 4) {
                        getData(orderStatus);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        getDataEvaluate();
                        mEvaluateAdapter.notifyDataSetChanged();
                    }
                    load();
                } else {
                    lv_order_content.stopLoadMore();
                    ToastUtil.MyToast(getApplicationContext(), "没有更多数据了");
                }
            }
        }, 1000);
    }

    private void load() {
        lv_order_content.stopRefresh();
        lv_order_content.stopLoadMore();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        lv_order_content.setRefreshTime(str);
    }

    @Override
    protected void processData(String data, RequestVo reqVo) {
        switch (reqVo.requestUrl) {
            case RequestUrl.ORDER_LIST:
                if (flagClear) {
                    flagClear = false;
                    LogUtil.e(TAG, "清空了集合");
                    mDataList.clear();
                    mAdapter.notifyDataSetChanged();
                }
                callbackOrderList(data);

                if (orderList.size() != 0) {
                    isRefresh = true;
                    isFirstComeIn = false;
                    mDataList.addAll(orderList);
                    mAdapter.notifyDataSetChanged();
                    LogUtil.e(TAG, "当前第几页: " + page_index);
                    page_index++;

                    ll_no_order.setVisibility(View.GONE);
                    ll_order.setVisibility(View.VISIBLE);

                } else {
                    if (isFirstComeIn) {
                        ll_no_order.setVisibility(View.VISIBLE);
                        ll_order.setVisibility(View.GONE);
                    } else {
                        isRefresh = false;
                        isFirstComeIn = false;
                    }
                }
                break;
            case RequestUrl.ORDER_EVALUATE_LIST:
                if (flagClear) {
                    flagClear = false;
                    LogUtil.e(TAG, "清空了集合");
                    evaluatrSkulist.clear();
                    mEvaluateAdapter.notifyDataSetChanged();
                }
                if (null != data) {
                    callbackEvaluateList(data);
                }
                ArrayList<SkuByOrderLine> sku = evaluateOrderUser.getOrderLineDtoList();
                if (sku.size() != 0) {
                    isRefresh = true;
                    isFirstComeIn = false;
                    evaluatrSkulist.addAll(sku);
                    mEvaluateAdapter.notifyDataSetChanged();
                    page_index++;
                    ll_no_order.setVisibility(View.GONE);
                    ll_order.setVisibility(View.VISIBLE);
                    LogUtil.e(TAG, " evaluatrSkulist不是空的   去设置数据了============");

                } else {
                    if (isFirstComeIn) {
                        ll_no_order.setVisibility(View.VISIBLE);
                        ll_order.setVisibility(View.GONE);
                    } else {
                        isRefresh = false;
                        isFirstComeIn = false;
                    }
                }
                break;
            case RequestUrl.ORDER_CANCEL:
                if (SourceConstant.ISGetDate) {
                    // 如果点击的是全部订单里面的就查询全部订单，否则至查询代付款的订单信息
                    isFirstComeIn = true;
                    flagClear = true;
                    page_index = 1;
                    if (position == 0) {
                        LogUtil.e(TAG, "===================请求服务器查询全部订单列表 ");
                        orderStatus = orderType[0];
                        getData(orderStatus);
                    } else if (position == 1) {
                        LogUtil.e(TAG, "===================请求服务器查询待付款订单列表 ");
                        orderStatus = orderType[1];
                        getData(orderStatus);
//                        getDataPay();
//                        getData(sid);
                    }
                    SourceConstant.ISGetDate = !SourceConstant.ISGetDate;
                }
                // 刷新界面
                mAdapter.notifyDataSetChanged();
                break;
            case RequestUrl.ORDER_CONFIRM_RECEIPT:
                if (SourceConstant.ISGetDate) {
                    // 如果点击的是全部订单里面的就查询全部订单，否则至查询代付款的订单信息
                    isFirstComeIn = true;
                    flagClear = true;
                    page_index = 1;
                    if (position == 0) {
                        orderStatus = orderType[0];
                        getData(orderStatus);
                    } else if (position == 3) {
                        orderStatus = orderType[3];
                        getData(orderStatus);
//                        getData(sid);
                    }
                    SourceConstant.ISGetDate = !SourceConstant.ISGetDate;
                }
                ToastUtil.MyToast(this, "收货成功");
                // 刷新界面
                mAdapter.notifyDataSetChanged();
                break;
            case RequestUrl.ORDER_DELETE:
                ToastUtil.MyToast(this, "订单删除成功");
                if (SourceConstant.ISGetDate) {
                    // 如果点击的是全部订单里面的就查询全部订单，否则至查询代付款的订单信息
                    isFirstComeIn = true;
                    flagClear = true;
                    page_index = 1;
                    if (position == 0) {
                        getData(orderStatus);
                        orderStatus = orderType[0];
//                        getData(sid);
                    }
                    SourceConstant.ISGetDate = !SourceConstant.ISGetDate;
                }

                // 刷新界面
                mAdapter.notifyDataSetChanged();
                break;
            // 提醒卖家发货发货
            case RequestUrl.ORDER_REMINDDELIVERY:
                LogUtil.e(TAG, "收到了服务器请求 提醒卖家发货=======");
                ToastUtil.MyToast(AllOrderActivity.this, "已提醒发货");
                // 刷新界面
                mAdapter.notifyDataSetChanged();
                break;
            case RequestUrl.ORDER_APPLY_REFUND:
                ToastUtil.MyToast(AllOrderActivity.this, "已提交申请退款,请耐心等待");
                break;
            case RequestUrl.ORDER_QUANTITY:
                LogUtil.e(TAG, "查询dao l 数量");
                callbackOrderBumber(data);
                break;
            default:
                break;
        }
    }

    /**
     * 解析订单数量
     */
    private void callbackOrderBumber(Object data) {
        LogUtil.e(TAG, "解析 数量");
        orderNumber = JsonUtil.jsonToEntity(data.toString(), OrderNumber.class);
        LogUtil.e(TAG, "orderNumber:" + orderNumber.toString());
        setcartNumber(orderNumber);
    }

    /**
     * 设置各种订单的数量
     */
    private void setcartNumber(OrderNumber orderNumber) {
        Integer toBeEvaluatedNo = 0;
        Integer total = 0;
        Integer toBeShippedNo = 0;
        Integer toBeReceivedNo = 0;
        Integer toBePaidNo = 0;
        // 全部
        tv_menu_number.setText(Html.fromHtml(String.format(this.getResources().getString(R.string.order_tv_order_menu),
                total + "")));
        // 代发货
        tv_menu_number02.setText(Html.fromHtml(String.format(this.getResources()
                .getString(R.string.order_tv_order_menu), toBeShippedNo + "")));
        // 待付款
        tv_menu_number01.setText(Html.fromHtml(String.format(this.getResources()
                .getString(R.string.order_tv_order_menu), toBePaidNo + "")));
        // 待收货
        tv_menu_number03.setText(Html.fromHtml(String.format(this.getResources()
                .getString(R.string.order_tv_order_menu), toBeReceivedNo + "")));
        // 待评价
        tv_menu_number04.setText(Html.fromHtml(String.format(this.getResources()
                .getString(R.string.order_tv_order_menu), toBeEvaluatedNo + "")));
    }

    /**
     * 删除列表上的数据
     */
    private void delorder() {
        if (SourceConstant.POSTION_ORDER != -1) {
            orderList.remove(SourceConstant.POSTION_ORDER);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void setOrderNumber(OrderTypeNum orderTypeNums) {
        // 全部
        tv_menu_number.setText(Html.fromHtml(String.format(this.getResources().getString(R.string.order_tv_order_menu),
                orderTypeNums.getAllOrderNum())));
        // 代发货
        tv_menu_number02.setText(Html.fromHtml(String.format(this.getResources()
                .getString(R.string.order_tv_order_menu), orderTypeNums.getWaitDeliverNum())));
        // 待付款
        tv_menu_number01.setText(Html.fromHtml(String.format(this.getResources()
                .getString(R.string.order_tv_order_menu), orderTypeNums.getWaitPayNum())));
        // 待收货
        tv_menu_number03.setText(Html.fromHtml(String.format(this.getResources()
                .getString(R.string.order_tv_order_menu), orderTypeNums.getWaitReceiptNum())));
        // 待评价
        tv_menu_number04.setText(Html.fromHtml(String.format(this.getResources()
                .getString(R.string.order_tv_order_menu), orderTypeNums.getWaitEvaluateNum())));
    }

    /**
     * 解析待评价列表
     */
    private void callbackEvaluateList(Object data) {
        evaluateOrderUser = JsonUtil.jsonToEntity(data.toString(), EvaluateOrderUserRes.class);

        setOrderNumber(evaluateOrderUser.getOrderTypeNumDto());
    }

    private void callbackOrderList(Object obj) {
        try {
            JsonParser jsonParser = new JsonParser();
            json = jsonParser.parse(obj.toString());

            JsonObject orderTypeNum = json.getAsJsonObject().get("orderTypeNumDto").getAsJsonObject();
            orderTypeNums = new OrderTypeNum();
            orderTypeNums.setAllOrderNum(orderTypeNum.get("allOrderNum").getAsInt());
            orderTypeNums.setWaitPayNum(orderTypeNum.get("waitPayNum").getAsInt());
            orderTypeNums.setWaitDeliverNum(orderTypeNum.get("waitDeliverNum").getAsInt());
            orderTypeNums.setWaitReceiptNum(orderTypeNum.get("waitReceiptNum").getAsInt());
            orderTypeNums.setWaitEvaluateNum(orderTypeNum.get("waitEvaluateNum").getAsInt());
            setOrderNumber(orderTypeNums);

            JsonArray jsonSizeList = json.getAsJsonObject().get("orderList").getAsJsonArray();
            orderList = new ArrayList<OrderUser>();
            for (int i = 0; i < jsonSizeList.size(); i++) {// 遍历JSONArray
                JsonObject oj = jsonSizeList.get(i).getAsJsonObject();
                OrderUser order = new OrderUser();
                order.setOrderCode(oj.get("orderCode").getAsString());
                order.setOrderStatus(oj.get("orderStatus").getAsString());
                order.setPaymentTotalActual(oj.get("totalPrice").getAsDouble());
                LogUtil.e(TAG, "解析到的价格============" + order.getPaymentTotalActual());
                order.setStoreName(oj.get("storeName").getAsString());
                order.setStoreCode(oj.get("storeCode").getAsString());

                // ---------------------------------------

                JsonArray OrderLineList = oj.get("skuByOrderLines").getAsJsonArray();
                ArrayList<SkuByOrderLine> orderlines = new ArrayList<SkuByOrderLine>();
                for (int j = 0; j < OrderLineList.size(); j++) {

                    JsonObject oj1 = OrderLineList.get(j).getAsJsonObject();
                    SkuByOrderLine orderline = new SkuByOrderLine();
                    orderline.setIsComment(oj1.get("isComment").getAsString());
//                    orderline.setCreateOrderTime(oj1.get("createOrderTime").getAsString());
                    orderline.setSkuSize(oj1.get("skuSize").getAsString());
                    orderline.setLineTotalPrice(oj1.get("lineTotalPrice").getAsString());
                    orderline.setSkuPrice(oj1.get("skuPrice").getAsDouble());
                    orderline.setSkuColor(oj1.get("skuColor").getAsString());
                    orderline.setOrderCode(oj1.get("orderCode").getAsString());
                    orderline.setSkuName(oj1.get("skuName").getAsString());
                    orderline.setQuantity(oj1.get("quantity").getAsInt());
//                    orderline.setOrderLineId(oj1.get("orderLineId").getAsString());
                    orderline.setSkuImg(oj1.get("skuImg").getAsString());
                    orderline.setSkuCode(oj1.get("skuCode").getAsString());
                    orderlines.add(orderline);
                }

                JsonArray canHandleOperate = oj.get("canHandleOperate").getAsJsonArray();
                ArrayList<String> string1 = new ArrayList<String>();
                for (int k = 0; k < canHandleOperate.size(); k++) {
                    String asString1 = canHandleOperate.get(k).getAsString();
                    string1.add(asString1);
                }
                order.setCanHandleOperate(string1);
                order.setSkuByOrderLines(orderlines);
                orderList.add(order);
            }
//            total = json.getAsJsonObject().get("total").getAsInt();
            mOrderUserRes = new OrderUserRes(total, orderList);

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
        OrderUser order = (OrderUser) adapter.getItemAtPosition(position);
        orderCode = order.getOrderCode();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            finish();
        }

    }

    /**
     * 设置文字颜色
     */
    private void setTextViewForColor(int menuId, int numberId) {

        for (int i = 0; i < arrMenuTv.length; i++) {
            TextView view = (TextView) findViewById(arrMenuTv[i]);
            if (arrMenuTv[i] != menuId) {
                view.setTextColor(this.getResources().getColor(R.color.black));
            } else {
                view.setTextColor(this.getResources().getColor(R.color.text_color_orange));
            }
        }
        for (int i = 0; i < arrNumberTv.length; i++) {
            TextView view = (TextView) findViewById(arrNumberTv[i]);
            if (arrNumberTv[i] != numberId) {
                view.setTextColor(this.getResources().getColor(R.color.black));
            } else {
                view.setTextColor(this.getResources().getColor(R.color.text_color_orange));
            }
        }

    }

    /**
     * 跳转评价
     */
    private void gotoEvaluate() {
        position = 4;
        initEvaluteData();
        isFirstComeIn = true;
        flagClear = true;
        page_index = 1;
        setTextViewForColor(R.id.tv_menu_name04, R.id.tv_menu_number04);
        startTranslateAnimation(position);
        getDataEvaluate();
    }

    /**
     * 去付款
     */
    private void gotoPay(String price, String OrderCoded) {
        Intent intent = new Intent(AllOrderActivity.this, PaySelectActivity.class);
        SourceConstant.IS_APP_PAY = SourceConstant.ONE;
        intent.putExtra(SourceConstant.TOTAL_ACTUAL, price);
        intent.putExtra(SourceConstant.ORDER_CODE, OrderCoded);
        AllOrderActivity.this.setResult(RESULT_OK, intent);
        startActivityForResult(intent, Constant.REQUEST_CODE_PAY_SELECT);
    }

    /**
     * 向服务器请求取消订单
     */
    private void cancelOrder(String OrderCode) {
        AorunApi.getCancelOrder(sid, OrderCode, "cancelOrder", mDataCallback);
    }

    /**
     * 向服务器请求删除订单
     */
    private void delorders(String orderStatus, String OrderCode) {
        AorunApi.getOrderDel(sid, OrderCode, "deleteOrder", mDataCallback);
    }

    /**
     * 提醒卖家发货
     */
    private void remindDelivery(String ItemorderCode1) {

        LogUtil.e(TAG, "向服务器请求 提醒卖家发货=======");
        AorunApi.getRemindDelivery(sid, ItemorderCode1, "remindDelivery", mDataCallback);
    }

    /**
     * 向服务器请求确认收货
     */
    private void confirmReceipt(String orderStatus, String orderCode) {
        AorunApi.getOrderConfirmReceipt(sid, orderCode, "confirmReceipt", mDataCallback);
    }

    /**
     * 申请退款
     */
    protected void applyRebate(String orderCode2) {
        ApplyRebateDto applyRebateDto = new ApplyRebateDto(sid, orderCode2, "1", "");
        String data = JsonUtil.entityToJson(applyRebateDto);
        String mac = Md5Utils.getMac(data);
        AorunApi.applyRefund(data, mac, mDataCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 取消订单弹出的dialog
     */
    protected void unOrderdialog(final String OrderCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定取消订单？");
        builder.setTitle("取消订单");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancelOrder(OrderCode);
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * 申请退款弹出的dialog
     */
    protected void ApplyRebateDialog(final String orderCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("您确定申请退款?");
        builder.setTitle("申请退款");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 向服务器发送确定申请退款的操作
                applyRebate(orderCode);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * 确认收货弹出的dialog
     */
    protected void ProductDialog(final String orderStatus1, final String ItemorderCode1) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("您确定收货?");
        builder.setTitle("确定收货");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 向服务器发送确认订单的操作
                confirmReceipt(orderStatus1, ItemorderCode1);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * 删除订单弹出的dialog
     */
    protected void delOrderDialog(final String orderStatus1, final String ItemorderCode1) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("您已经确定删除订单");
        builder.setTitle("删除订单");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 向服务器发送删除订单的操作
                delorders(orderStatus1, ItemorderCode1);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sid = SpUtill.getString(this, ResourseSum.Medica_SP, "saveSid");
        isFirstComeIn = true;
        flagClear = true;
        page_index = 1;
        if (!VerifyUtil.isEmpyty(sid)) {
            // 查询所有订单数量
//            getData(sid);
        }
        if (OrderEvaluateActivity.isEvaluate) {
            getDataEvaluate();
            LogUtil.e(TAG, "刷新评价列表");
            OrderEvaluateActivity.isEvaluate = false;
        }

        if (SourceConstant.IS_PAY_SUCCESS == SourceConstant.THREE) {
            orderStatus = orderType[1];
            getData(orderStatus);
            SourceConstant.IS_PAY_SUCCESS = SourceConstant.ONE;
            SourceConstant.IS_APP_PAY = 0;
            LogUtil.e(TAG, "刷新代付款列表");
        }

        if (!ffinsh) {
            this.finish();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * 动画移动选中状态
     */
    private void startTranslateAnimation(int id) {
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) view_bow.getLayoutParams(); // 取控件textView当前的布局参数
        int bow_width = SourceConstant.screenWidth / 5;
        linearParams.leftMargin = 0;
        linearParams.rightMargin = 0;
        Animation translateIn = null;
        // 平移动画
        translateIn = new TranslateAnimation(initialPosition, bow_width * id, 0, 0);
        translateIn.setDuration(300);
        translateIn.setFillAfter(true);
        view_bow.startAnimation(translateIn);
        // 存储上一次的位置
        initialPosition = bow_width * id;
        // LogUtil.e(TAG, "移动前的======初始位置：" + initialPosition);
    }
}

