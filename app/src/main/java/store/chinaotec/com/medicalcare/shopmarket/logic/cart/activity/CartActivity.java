package store.chinaotec.com.medicalcare.shopmarket.logic.cart.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import org.json.JSONObject;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.base.activity.BaseAoActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.base.activity.MainTabActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.base.app.AppManager;
import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.Constant;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.http.AorunApi;
import store.chinaotec.com.medicalcare.shopmarket.common.request.requestUrl.RequestUrl;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.JsonUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.VerifyUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.view.MyChronometer;
import store.chinaotec.com.medicalcare.shopmarket.logic.cart.adapter.CartAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.cart.model.Cart;
import store.chinaotec.com.medicalcare.shopmarket.logic.cart.model.CartItem;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.activity.IntroduceActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.activity.ShopMarketSearchActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.activity.ShopMarketSeckillAndGroupBuyActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.activity.AllOrderActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.activity.OrderInfoActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.OrderCreat;
import store.chinaotec.com.medicalcare.shopmarket.logic.pay.activity.PayShopSkuActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.shops.activity.ShopDetailsActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.activity.SkuInfoActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.activity.SkuListDiscountActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.activity.SkuListSearchActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.activity.SkuListTypeActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.activity.SkuTypeListSearchActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.user.activity.MyFavoritesActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.user.activity.SkuMyHistoryListActivity;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * 购物车
 */
public class CartActivity extends BaseAoActivity implements SwipeMenuCreator,
        SwipeMenuListView.OnMenuItemClickListener, OnItemClickListener, MyChronometer.OnChronometerTickListener {
    private final int page_size = 20;
    /**
     * @Description 连续点击有效时间
     */
    private final long outTime = 2000;
    public String mShoppingCartSid;
    /**
     * 取消全选商品的skuCodes
     */
    public String longSkuCodesNo;
    /**
     * 全选商品的skuCodes
     */
    public String longSkuCodesOk;
    /**
     * 存储VisitKey文件的共享首选项
     */
    private SharedPreferences fileNameAli;
    private TextView mTvTitle;
    private Button mBtnGoShopping;
    private ImageView mBtnSelectAll;
    private boolean isChangeNum = false;
    private CartAdapter mAdapter;
    private SwipeMenuListView mLv;
    private boolean flagClear = false;
    private boolean mFalghttp = true;
    private boolean mFalgLoadMore = true;
    private ArrayList<CartItem> cartItemDataList;
    // 商品的价格
    private TextView mTvPrice;
    // 商品总件数
    private TextView tv_pnum_total;
    /*** 购物车无商品时显示的布局*/
    private LinearLayout ll_logined;
    /*** 购物车有商品时显示的布局*/
    private RelativeLayout rl_cart;
    // ll_unlogined
    private int page_index = 1;
    /*** 全部选中和取消全选，选中/取消单个 0 单个 1 全选 2 取消全选*/
    private int CheckType;
    private long num;
    private JSONObject jo = null;
    private Cart cart;
    private String pnumTotal;
    private int mPosition;
    /**
     * 是否全选
     */
    private boolean isCheckAll = false;
    private ImageView mBtnBack;
    private String TAG = "cartActivity";
    private String visitKey;
    private String sId;
    /*** 得到服务器返回的 不含运费 的 总价格*/
    private String totprice;
    private int delPosition;
    private Button btn_go_logining;
    private TextView tv_login_text;
    private String storeCode;
    /*** 满68免运费*/
    private TextView tv_cart_free_freight;

    @Override
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        // 将Activity添加到堆栈区。我是这样理解的。
        AppManager.getInstance().addActivity(this);
    }

    @Override
    protected void onClickEvent(View paramView) {
        switch (paramView.getId()) {
            case R.id.btn_go_logining:
//                startActivity(new Intent(this, LoginActivity.class));TODO
                break;
            case R.id.btn_go_shopping:
                ((RadioButton) MainTabActivity.main_radio.getChildAt(0)).setChecked(true);
                break;
            case R.id.btn_cart_goto_pay:
                if (!"".equals(sId)) {
                    checkSubmitData();
                } else {
//                    startActivity(new Intent(this, LoginActivity.class));TODO
                }
                break;
            case R.id.btn_check:
                CheckType = 0;
                mPosition = Integer.valueOf(paramView.getTag() + "");
                skuSelect(mPosition);
                break;
            case R.id.tv_name:
                CheckType = 0;
                mPosition = Integer.valueOf(paramView.getTag() + "");
                Intent intent = new Intent(this, SkuInfoActivity.class);
                intent.putExtra(SourceConstant.SKU_CODE,
                        String.valueOf(cartItemDataList.get(mPosition).skuCode) + "");
                startActivityForResult(intent, Constant.REQUEST_CODE_SKU_INFO);
                break;
            case R.id.img:
                CheckType = 0;
                mPosition = Integer.valueOf(paramView.getTag() + "");
                Intent intent1 = new Intent(this, SkuInfoActivity.class);
                intent1.putExtra(SourceConstant.SKU_CODE, String.valueOf(cartItemDataList.get(mPosition).skuCode) + "");
                startActivityForResult(intent1, Constant.REQUEST_CODE_SKU_INFO);
                break;
            case R.id.btn_select_all:
                if (!isCheckAll) {
                    mBtnSelectAll.setImageResource(R.drawable.btn_select_down);
                    skuSelectAll();
                } else {
                    mBtnSelectAll.setImageResource(R.drawable.btn_select_up);
                    skuUnselectAll();
                }
                break;
            case R.id.btn_add:
                mPosition = Integer.valueOf(paramView.getTag() + "");
//                if (cartItemDataList.get(mPosition).quantity < cartItemDataList.get(mPosition).availableQty) {
//                    if (!isChangeNum) {
//                        isChangeNum = true;
                cartItemDataList.get(mPosition).quantity++;
                ((MyChronometer) paramView).start();
//                    }
//                }else {
//                    cartItemDataList.get(mPosition).quantity = cartItemDataList.get(mPosition).availableQty;
//                }
                // mAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_minus:
                mPosition = Integer.valueOf(paramView.getTag() + "");
                if (cartItemDataList.get(mPosition).quantity > 1) {
                    if (!isChangeNum) {
                        isChangeNum = true;
                        cartItemDataList.get(mPosition).quantity--;
                        ((MyChronometer) paramView).start();
                    }
                } else {
                    cartItemDataList.get(mPosition).quantity = 1;
                }
                break;
        }
    }

    private void checkSubmitData() {
        AorunApi.getCheckGotoOrder(sId, mDataCallback);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_shop_market_cart);
    }

    @Override
    protected void initView() {
        LogUtil.e(TAG, "CartActivity.initView()=====================");
        mTvTitle = (TextView) findViewById(R.id.title_textview);
        mBtnGoShopping = (Button) findViewById(R.id.btn_go_shopping);
        mLv = (SwipeMenuListView) findViewById(R.id.lv_cart);
        mTvPrice = (TextView) findViewById(R.id.tv_price_total);
        ll_logined = (LinearLayout) findViewById(R.id.ll_logined);
        rl_cart = (RelativeLayout) findViewById(R.id.rl_cart);
        tv_pnum_total = (TextView) findViewById(R.id.tv_pnum_total);
        tv_cart_free_freight = (TextView) findViewById(R.id.tv_cart_free_freight);

        tv_login_text = (TextView) findViewById(R.id.tv_login_text);
        mBtnSelectAll = (ImageView) findViewById(R.id.btn_select_all);

        mBtnBack = (ImageView) findViewById(R.id.title_btn_left);
        btn_go_logining = (Button) findViewById(R.id.btn_go_logining);
        fileNameAli = this.getSharedPreferences(SourceConstant.fileNameAli, 0);
        sId = SpUtill.getString(this, ResourseSum.Medica_SP, "saveSid");
        visitKey = getSharedPreferences("cart", Context.MODE_PRIVATE).getString("visitkey", "");
        LogUtil.e(TAG, "cart---visitKey====" + visitKey);
        storeCode = fileNameAli.getString(SourceConstant.STORE_CODE, "");
    }

    @Override
    protected void initListener() {
        LogUtil.e(TAG, "CartActivity.initListener()=====================");
        mBtnGoShopping.setOnClickListener(this);
        btn_go_logining.setOnClickListener(this);
        findViewById(R.id.btn_cart_goto_pay).setOnClickListener(this);// 去结算
        mLv.setOnItemClickListener(this);
        // set creator
        mLv.setMenuCreator(this);
        // step 2. listener item click event
        mLv.setOnMenuItemClickListener(this);
//		mLv.setOnScrollListener(new ListenerLoadMore() {
//			@Override
//			protected void nextPage() {
//				cartList();
//			}
//		});
        mBtnSelectAll.setOnClickListener(this);

    }

    @Override
    protected void processLogic() {
        LogUtil.e(TAG, "CartActivity.processLogic()=====================");
        mTvTitle.setText(R.string.cart_title);
        mBtnBack.setVisibility(View.INVISIBLE);
        initCartList();
        cartList();
    }

    private void initCartList() {
        cartItemDataList = new ArrayList<CartItem>();
        mAdapter = new CartAdapter(this, cartItemDataList);
        mLv.setAdapter(mAdapter);
        if (isCheckAll) {
            mBtnSelectAll.setImageResource(R.drawable.btn_select_down);
        } else {
            mBtnSelectAll.setImageResource(R.drawable.btn_select_up);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.e(TAG, "CartActivity.onResume()=====================");
        sId  = SpUtill.getString(this, ResourseSum.Medica_SP, "saveSid");
        storeCode = fileNameAli.getString(SourceConstant.STORE_CODE, "");
        LogUtil.e("切换到商城时--购物车--=====storeCode===", "storeCode===" + storeCode);
        if (!VerifyUtil.isEmpyty(sId)) {
            btn_go_logining.setVisibility(View.GONE);
            tv_login_text.setVisibility(View.GONE);
        }

        SkuListDiscountActivity.ffinsh = true;
        SkuTypeListSearchActivity.ffinsh = true;
        SkuListSearchActivity.ffinsh = true;
        SkuListTypeActivity.ffinsh = true;
        ShopMarketSearchActivity.ffinsh = true;
        SkuMyHistoryListActivity.ffinsh = true;
        MyFavoritesActivity.ffinsh = true;
        IntroduceActivity.ffinsh = true;
        OrderInfoActivity.ffinsh = true;
        AllOrderActivity.ffinsh = true;
        ShopMarketSeckillAndGroupBuyActivity.ffinsh = true;
        ShopDetailsActivity.ffinsh = true;
        getViewVistisby();
        mFalgLoadMore = true;
        page_index = 1;
        flagClear = true;
//		mFalghttp = true;
        num = 0;
        cartList();
        mAdapter.notifyDataSetChanged();
        if (cartItemDataList.size() == 0) {
            rl_cart.setVisibility(View.GONE);
            mLv.setVisibility(View.GONE);
            findViewById(R.id.linearLayout1).setVisibility(View.GONE);
//			findViewById(R.id.linearLayout2).setVisibility(View.GONE);
            if (VerifyUtil.isEmpyty(sId)) {
                btn_go_logining.setVisibility(View.VISIBLE);
                tv_login_text.setVisibility(View.VISIBLE);
            } else {
                btn_go_logining.setVisibility(View.GONE);
                tv_login_text.setVisibility(View.GONE);
            }
            ll_logined.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 购物车列表条目
     */
    private void cartList() {
        if (mFalghttp) {
            if (mFalgLoadMore) {
//				mFalghttp = false;
                LogUtil.e(TAG, "请求了服务器");
                RequestVo reqVo = null;

                if ("".equals(sId)) {
                    LogUtil.e(TAG, "现在是未登录状态去服务器获取数据    visitKey===" + visitKey);
                    mFalghttp = false;
                    AorunApi.getStoreCartList(visitKey, "", page_index, page_size, "", mDataCallback);

                } else {
                    LogUtil.e(TAG, "是登录状态去服务器获取数据    sId===" + sId);
                    mFalghttp = false;
                    AorunApi.getStoreCartList("", sId, page_index, page_size, "", mDataCallback);
                }
            }
        }
    }

    /**
     * 选中商品
     */
    private void skuSelect(int position) {
        if ("".equals(sId)) {
            AorunApi.getCartSkuSelect(visitKey, "", cartItemDataList.get(position).skuCode, cartItemDataList
                    .get(position).isSelected.equals("y") ? "n" : "y", "", mDataCallback);
        } else {
            AorunApi.getCartSkuSelect("", sId, cartItemDataList.get(position).skuCode, cartItemDataList
                    .get(position).isSelected.equals("y") ? "n" : "y", "", mDataCallback);
        }
    }

    /**
     * 商品全选
     */
    private void skuSelectAll() {
        LogUtil.e("cartItemDataList.size()=====", cartItemDataList.size() + "");
        if (cartItemDataList.size() < 1) {
            return;
        }
        if ("".equals(sId)) {
            AorunApi.getCartSkuSelectAll(visitKey, "", "y", mDataCallback);
        } else {
            AorunApi.getCartSkuSelectAll("", sId, "y", mDataCallback);
        }
        isCheckAll = true;
    }

    /**
     * 取消商品全选
     */
    private void skuUnselectAll() {

        if ("".equals(sId)) {
            AorunApi.getCartSkuSelectAll(visitKey, "", "n", mDataCallback);
        } else {
            AorunApi.getCartSkuSelectAll("", sId, "n", mDataCallback);
        }
        isCheckAll = false;
    }

    /**
     * 修改行的数量
     */
    private void skuChangeNum(int position, int quantity) {

        if (sId == null || "".equals(sId)) {
            AorunApi.getCartSkuChangeNum(visitKey, "", cartItemDataList.get(position).skuCode, quantity + "", "", mDataCallback);
        } else {
            AorunApi.getCartSkuChangeNum("", sId, cartItemDataList.get(position).skuCode, quantity + "", "", mDataCallback);
        }
    }

    /**
     * 删除商品
     */
    private void skuDel() {

        if ("".equals(sId)) {
            AorunApi.getCartDel(visitKey, "", cartItemDataList.get(delPosition).skuCode, "", mDataCallback);
        } else {
            AorunApi.getCartDel("", sId, cartItemDataList.get(delPosition).skuCode, "", mDataCallback);
        }
    }

    private void skuPay() {

        if (sId == null || "".equals(sId)) {
//            startActivityForResult(new Intent(this, LoginActivity.class), Constant.REQUEST_CODE_LOGIN);TODO
            return;
        } else {
            String priceStr = mTvPrice.getText().toString().trim();
            String a[] = priceStr.split(":");
            double price = Double.parseDouble(a[1]);
            if (price > 0) {
                Intent intent = new Intent(this, PayShopSkuActivity.class);
                intent.setAction(OrderCreat.GOTO_CART_PAYMENT);
                startActivityForResult(intent, Constant.REQUEST_CODE_CART_PAY);
            } else {
                ToastUtil.MyToast(this, "请选择要购买的商品");
                return;
            }
        }
    }

    @Override
    protected void processData(String data, RequestVo reqVo) {
        mFalghttp = true;
        switch (reqVo.requestUrl) {
            case RequestUrl.CART_SKU_CHECK_ALL:
                try {
                    JSONObject jo = new JSONObject(data);
                    totprice = jo.getString("priceTotal");
                    pnumTotal = jo.getString("pnumTotal");
                    mTvPrice.setText("￥:" + totprice);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (isCheckAll) {
                    setAllSelectY();
                } else {
                    setAllSelectN();
                }
                mAdapter.notifyDataSetChanged();

                break;
            case RequestUrl.CART_SKU_CHECK:

                try {
                    JSONObject jo = new JSONObject(data);
                    totprice = jo.getString("priceTotal");
                    pnumTotal = jo.getString("pnumTotal");
                    mTvPrice.setText("￥:" + totprice);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                switch (CheckType) {
                    case 0:
                        cartItemDataList.get(mPosition).isSelected = reqVo.params.get("isSelected").toString();
                        mAdapter.notifyDataSetChanged();
                        break;
//                    case 1:
//                        LogUtil.e(TAG, "进了全选=========");
//                        setAllSelectY();
//                        break;
//                    case 2:
//                        LogUtil.e(TAG, "进了取消全选=========");
//                        setAllSelectN();
//                        break;
                }

                int cartSelectNum = getCartSelectNum();
                // 如果选中数量和总数量一样，代表全选了
                if (cartSelectNum == cartItemDataList.size()) {
                    LogUtil.e(TAG, "全选了cartSelectNum：" + cartSelectNum + "购物车的数量：" + cartItemDataList.size());
                    mBtnSelectAll.setImageResource(R.drawable.btn_select_down);
                    setAllSelectY();
                    isCheckAll = true;
                } else if (cartSelectNum == 0) {
                    LogUtil.e(TAG, "取消全选了cartSelectNum：" + cartSelectNum + "购物车的数量：" + cartItemDataList.size());
                    mBtnSelectAll.setImageResource(R.drawable.btn_select_up);
                    isCheckAll = false;
                    setAllSelectN();
                } else {
                    mBtnSelectAll.setImageResource(R.drawable.btn_select_up);
                    isCheckAll = false;
                }
                break;
            case RequestUrl.CART_SKU_CHANGE_NUM:
                isChangeNum = false;
                cartItemDataList.get(mPosition).quantity = Integer.valueOf(reqVo.params.get("quantity"));
                mAdapter.notifyDataSetChanged();
                try {
                    jo = new JSONObject(data);
                    mTvPrice.setText("￥:" + jo.getString("priceTotal"));
                    mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case RequestUrl.CART_COUNT:
                if (!VerifyUtil.isEmpyty(data)) {
                    String pnumTotal = data;
                    MainTabActivity.setCartNumberText(pnumTotal);
                }
                break;
            case RequestUrl.CART_LIST:
                cart = JsonUtil.jsonToEntity(data, Cart.class);

                if (null != cart && !"".equals(cart)) {
                    cartItemDataList.clear();
                    ArrayList<CartItem> list = cart.getItems();
                    if (list.size() != 0) {
                        cartItemDataList.addAll(list);
                        mAdapter.notifyDataSetChanged();

                        mTvPrice.setText("￥:" + String.valueOf(cart.getShoppingSummaryDto().getPriceTotal()));

                        if (cart.pnumTotal > 0) {
                            num += cart.pnumTotal;
                        }
                        mShoppingCartSid = cart.getShoppingCartSid();
                    }
                    getViewVistisby();
                }
                break;

            case RequestUrl.CART_DEl:

                cartItemDataList.remove(delPosition);
                mAdapter.notifyDataSetChanged();

                if (cartItemDataList.size() == 0) {
                    ll_logined.setVisibility(View.VISIBLE);
                    findViewById(R.id.linearLayout1).setVisibility(View.GONE);
//				findViewById(R.id.linearLayout2).setVisibility(View.GONE);
                } else {
                    LogUtil.e(TAG, "=====删除后刷新购物车列表=====");
                    flagClear = true;
                    cartList();
                }
                break;

            case RequestUrl.ORDER_CHECK_GOTO_ORDER:
                skuPay();
                break;
        }
    }

    @Override
    public void create(SwipeMenu menu) {
        LogUtil.e(TAG, "CartActivity.create()==========menu===========");
        // create "delete" item
        SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
        // set item background
        deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
        // set item width
        deleteItem.setWidth(dp2px(90));
        // set a icon
        // deleteItem.setIcon(R.drawable.address_del);
        deleteItem.setTitle("删除");
        deleteItem.setTitleSize(18);
        deleteItem.setTitleColor(Color.WHITE);
        // add to menu
        menu.addMenuItem(deleteItem);
    }

    @Override
    public void onMenuItemClick(int position, SwipeMenu arg1, int index) {
        delPosition = position;
        skuDel();
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {

            return;
        }

        switch (requestCode) {
            case Constant.REQUEST_CODE_LOGIN:
                page_index = 1;
                // flagList = true;
                cartItemDataList.clear();
                mAdapter.notifyDataSetChanged();
                cartList();
                break;
            case Constant.REQUEST_CODE_CART_PAY:
                setResult(RESULT_OK);
                // finish();
                break;
        }
    }

    @Override
    public void onChronometerTick(MyChronometer chronometer) {
        int postion = Integer.valueOf(chronometer.getTag().toString());
        int quantity = cartItemDataList.get(postion).quantity;
        skuChangeNum(postion, quantity);
    }

    @Override
    protected void onStart() {
        LogUtil.e(TAG, "CartActivity.onStart()==============");
        super.onStart();
    }

    @Override
    protected void onPause() {
        LogUtil.e(TAG, "CartActivity.onPause()===============");
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    /**
     * 判断状态
     */
    public void getViewVistisby() {
        LogUtil.e(TAG, "进判断之前rl_cart的显示状态" + rl_cart.getVisibility());
        // 购物车有商品时,显示商品列表
        if (cartItemDataList.size() != 0) {
            // if (mLv.getVisibility() == View.GONE) {
            ll_logined.setVisibility(View.GONE);
            rl_cart.setVisibility(View.VISIBLE);
            mLv.setVisibility(View.VISIBLE);
            findViewById(R.id.linearLayout1).setVisibility(View.VISIBLE);
//			findViewById(R.id.linearLayout2).setVisibility(View.VISIBLE);
            mAdapter = new CartAdapter(this, cartItemDataList);
            mLv.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            LogUtil.e(TAG, "==========rl_cart的显示状态" + rl_cart.getVisibility());
            // }
            // 购物车商品时,显示商品列表
        } else {
            rl_cart.setVisibility(View.GONE);
            mLv.setVisibility(View.GONE);
            ll_logined.setVisibility(View.VISIBLE);
        }
        int cartSelectNum = getCartSelectNum();
        // 如果选中数量和总数量一样，代表全选了
        if (cartSelectNum == cartItemDataList.size()) {
            LogUtil.e(TAG, "全选了cartSelectNum：" + cartSelectNum + "购物车的数量：" + cartItemDataList.size());
            mBtnSelectAll.setImageResource(R.drawable.btn_select_down);
            isCheckAll = true;
        } else {
            mBtnSelectAll.setImageResource(R.drawable.btn_select_up);
            isCheckAll = false;
        }
    }

    public int getCartSelectNum() {
        int num = 0;
        for (int i = 0; i < cartItemDataList.size(); i++) {
            if ("y".equals(cartItemDataList.get(i).isSelected)) {
                num++;
            }
        }
        return num;
    }

    /**
     * 取消全选
     */
    public void setAllSelectN() {
        for (int i = 0; i < cartItemDataList.size(); i++) {
            cartItemDataList.get(i).isSelected = "n";
        }
    }

    /**
     * 全选
     */
    public void setAllSelectY() {
        for (int i = 0; i < cartItemDataList.size(); i++) {
            cartItemDataList.get(i).isSelected = "y";
        }
    }

}
