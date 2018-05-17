package store.chinaotec.com.medicalcare.shopmarket.logic.user.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.activity.LoginActivity;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.shopmarket.common.base.activity.BaseAoActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.base.app.AppManager;
import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.Constant;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.http.AorunApi;
import store.chinaotec.com.medicalcare.shopmarket.common.referencecontrol.weight.BGABadgeRadioButton;
import store.chinaotec.com.medicalcare.shopmarket.common.request.requestUrl.RequestUrl;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.DialUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.JsonUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.StringUtils;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.VerifyUtil;
import store.chinaotec.com.medicalcare.shopmarket.logic.address.activity.ListAddressActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.activity.AllOrderActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.OrderNumber;
import store.chinaotec.com.medicalcare.utill.SpUtill;
import store.chinaotec.com.medicalcare.view.GlideCircleTransform;

/**
 * 个人中心
 */
public class UserActivity extends BaseAoActivity {

    private RelativeLayout mAddress;
    private RelativeLayout my_foot;
    private Button btn_main_login;
    private DialUtil intentUtil;
    private String sId;
    /**
     * 待付款的按钮
     */
    private BGABadgeRadioButton btn_unpay;
    /**
     * 待发货的按钮
     */
    private BGABadgeRadioButton btn_unsend_car;
    /**
     * 待收货的按钮
     */
    private BGABadgeRadioButton btn_unreceiver;
    /**
     * 待评价的按钮
     */
    private BGABadgeRadioButton btn_unevaluate;
    private Intent mIntent;
    private String TAG = "UserActivity";
    private FrameLayout fl_bg_user_center;
    private RelativeLayout open_store_shop;
    private RelativeLayout customer_complaint;
    private RelativeLayout user_my_collect;
    private RelativeLayout mBtnOrders;
    private LinearLayout person_login;
    private LinearLayout person_enter;
    private ImageView person_head_enter;
    private TextView person_name_enter;
    private OrderNumber orderNumber;
    private TextView tv_login_username;

    @Override
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        // 将Activity添加到堆栈区。我是这样理解的。
        AppManager.getInstance().addActivity(this);
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    protected void onClickEvent(View paramView) {
        if (SourceConstant.IS_NET_STATE) {
            if ("".equals(sId)) {
                startActivityForResult(new Intent(this, LoginActivity.class), SourceConstant.IS_XXX);
                return;
            }
            switch (paramView.getId()) {
                case R.id.person_login:
                    startActivityForResult(new Intent(this, LoginActivity.class), SourceConstant.IS_XXX);
                    break;
                case R.id.person_enter:
//                    startActivity(new Intent(this, DataActivity.class));
                    break;
                case R.id.rl_user_address:
                    // 地址管理
                    startActivity(new Intent(this, ListAddressActivity.class));
                    break;
                case R.id.rl_user_my_collect:
                    // 我的收藏
                    startActivity(new Intent(this, MyFavoritesActivity.class));
                    break;
//                case R.id.rl_user_apply_open_shop:
//                    startActivity(new Intent(this, ApplyOpenShopActivity.class));
//                    break;
                case R.id.btn_main_login:
                    // 用户登录
                    startActivity(new Intent(this, LoginActivity.class));
                    break;
//                case R.id.rl_customer_complaint:
//                    startActivity(new Intent(this, SuggestionFeedbackActivity.class));
//                    break;
                case R.id.rl_all_my_orders:
                    // 我的订单
                    mIntent = new Intent(this, AllOrderActivity.class);
                    mIntent.putExtra(SourceConstant.ALLORDERS_FIRST, 0);
                    startActivity(mIntent);
                    break;
                case R.id.btn_unpay:
                    // 代付款
                    mIntent = new Intent(this, AllOrderActivity.class);
                    mIntent.putExtra(SourceConstant.ALLORDERS_FIRST, SourceConstant.ONE);
                    startActivity(mIntent);
                    break;
                case R.id.btn_unsend:
                    // 待发货
                    mIntent = new Intent(this, AllOrderActivity.class);
                    mIntent.putExtra(SourceConstant.ALLORDERS_FIRST, SourceConstant.TWO);
                    startActivity(mIntent);
                    break;
                case R.id.btn_unreceiver:
                    // 待收货
                    mIntent = new Intent(this, AllOrderActivity.class);
                    mIntent.putExtra(SourceConstant.ALLORDERS_FIRST, SourceConstant.THREE);
                    startActivity(mIntent);
                    break;
                case R.id.btn_unevaluate:
                    // 待评价
                    mIntent = new Intent(this, AllOrderActivity.class);
                    mIntent.putExtra(SourceConstant.ALLORDERS_FIRST, SourceConstant.FOUR);
                    startActivity(mIntent);
                    break;
                //我的足迹
                case R.id.rl_user_my_foot:
                    startActivity(new Intent(this, SkuMyHistoryListActivity.class));
                    break;
            }
        } else {
            ToastUtil.MyToast(this, R.string.no_net_error);
        }
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_shop_market_user);
    }

    @Override
    protected void initView() {

        btn_main_login = (Button) findViewById(R.id.btn_main_login);
        tv_login_username = (TextView) findViewById(R.id.tv_user_login_username);
        fl_bg_user_center = (FrameLayout) findViewById(R.id.fl_bg_user_center);

        person_login = (LinearLayout) findViewById(R.id.person_login);
        person_enter = (LinearLayout) findViewById(R.id.person_enter);
        person_head_enter = (ImageView) findViewById(R.id.person_head_enter);
        person_name_enter = (TextView) findViewById(R.id.person_name_enter);

        mBtnOrders = (RelativeLayout) findViewById(R.id.rl_all_my_orders);
        mAddress = (RelativeLayout) findViewById(R.id.rl_user_address);
        user_my_collect = (RelativeLayout) findViewById(R.id.rl_user_my_collect);
        my_foot = (RelativeLayout) findViewById(R.id.rl_user_my_foot);
        customer_complaint = (RelativeLayout) findViewById(R.id.rl_customer_complaint);
        open_store_shop = (RelativeLayout) findViewById(R.id.rl_user_apply_open_shop);
        btn_unpay = (BGABadgeRadioButton) findViewById(R.id.btn_unpay);
        btn_unsend_car = (BGABadgeRadioButton) findViewById(R.id.btn_unsend);
        btn_unreceiver = (BGABadgeRadioButton) findViewById(R.id.btn_unreceiver);
        btn_unevaluate = (BGABadgeRadioButton) findViewById(R.id.btn_unevaluate);

        sId = SpUtill.getString(this, ResourseSum.Medica_SP, "saveSid");
        load();
    }

    @Override
    protected void initListener() {
        mBtnOrders.setOnClickListener(this);
        mAddress.setOnClickListener(this);
        my_foot.setOnClickListener(this);
        user_my_collect.setOnClickListener(this);
        btn_unpay.setOnClickListener(this);
        btn_unsend_car.setOnClickListener(this);
        btn_unreceiver.setOnClickListener(this);
        btn_unevaluate.setOnClickListener(this);
        btn_main_login.setOnClickListener(this);
        person_login.setOnClickListener(this);
        person_enter.setOnClickListener(this);
        open_store_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this, ApplyOpenShopActivity.class));
            }
        });
        customer_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SourceConstant.TURN_TO_COMPLAINT_BY_TYPE = SourceConstant.SIX;
                startActivity(new Intent(UserActivity.this, SuggestionFeedbackActivity.class));
            }
        });
    }

    @Override
    protected void processLogic() {
        intentUtil = new DialUtil(this);
        if (!VerifyUtil.isEmpyty(sId)) {
            getData(sId);
        }
    }

    /**
     * 查询所有订单数量
     */
    private void getData(String sid) {
        if (!VerifyUtil.isEmpyty(sid)) {
            AorunApi.getEachOrderQuantity(sid, mDataCallback);
        }
    }

    /**
     * 设置徽章数量
     */
    private void setcartNumber(OrderNumber orderNumber) {
        int toBeShippedNo = orderNumber.getWaitDeliverNum();
        int toBePaidNo = orderNumber.getWaitPayNum();
        int toBeReceivedNo = orderNumber.getWaitReceiptNum();
        int toBeEvaluatedNo = orderNumber.getWaitEvaluateNum();
        if (toBeShippedNo > 0) {
            btn_unsend_car.showTextBadge(String.valueOf(toBeShippedNo));//toBeShippedNo + "");
        } else {
            btn_unsend_car.hiddenBadge();
        }
        if (toBePaidNo > 0) {
            btn_unpay.showTextBadge(toBePaidNo + "");
        } else {
            btn_unpay.hiddenBadge();
        }
        if (toBeReceivedNo > 0) {
            btn_unreceiver.showTextBadge(toBeReceivedNo + "");
        } else {
            btn_unreceiver.hiddenBadge();
        }
        if (toBeEvaluatedNo > 0) {
            btn_unevaluate.showTextBadge(toBeEvaluatedNo + "");
        } else {
            btn_unevaluate.hiddenBadge();
        }
    }

    public void load() {
        if (StringUtils.isEmpty(sId)) {
            person_enter.setVisibility(View.GONE);
            person_login.setVisibility(View.VISIBLE);

            btn_unpay.hiddenBadge();
            btn_unsend_car.hiddenBadge();
            btn_unreceiver.hiddenBadge();
            btn_unevaluate.hiddenBadge();
            return;
        } else {
            person_enter.setVisibility(View.VISIBLE);
            person_login.setVisibility(View.GONE);
            String logo = SpUtill.getString(this, ResourseSum.Medica_SP, "logo");
            if (!StringUtils.isEmpty(logo)){
                Glide.with(UserActivity.this).load(logo).placeholder(R.drawable.person_head).
                        transform(new GlideCircleTransform(this)).into(person_head_enter);
            }

        }
//        if (StringUtils.isEmpty(user.nickName)) {
//            person_name_enter.setText("昵称");
//        } else {
//            person_name_enter.setText(user.nickName);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sId = SpUtill.getString(this, ResourseSum.Medica_SP, "saveSid");
        LogUtil.e("", "sid========" + sId);
        if (!"".equals(sId)) {
            getData(sId);
        }
        load();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        sId = SpUtill.getString(this, ResourseSum.Medica_SP, "saveSid");
        if (!"".equals(sId)) {
            getData(sId);
        }
        load();
        super.onRestart();
    }

    @Override
    protected void processData(String data, RequestVo reqVo) {
        switch (reqVo.requestUrl) {
            case RequestUrl.ORDER_QUANTITY:
                LogUtil.e(TAG, "查询 数量");
                callbackOrderBumber(data);
                break;
        }
    }

    /**
     * 解析订单数量
     */
    private void callbackOrderBumber(Object data) {
        LogUtil.e(TAG, "解析 数量");
        orderNumber = JsonUtil.jsonToEntity(data.toString(), OrderNumber.class);
        LogUtil.e(TAG, "orderNumber:" + JsonUtil.entityToJson(orderNumber));
        setcartNumber(orderNumber);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        sId = SpUtill.getString(this, ResourseSum.Medica_SP, "saveSid");
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            // 相册选择照片
            case Constant.REQUEST_CODE_CHOOSE_BIG_PICTURE:
                if (data != null) {
                    startActivityForResult(intentUtil.getPhotoShear(data.getData(),
                            1, 1, 200, 200), Constant.REQUEST_CODE_PHOTO_SHEAR);
                }
                break;
            case Constant.REQUEST_CODE_CHOOSE_BIG_CAMERA:
                // Uri uri = Uri.fromFile(MyUtils.SD_CARD_TEMP_DIR);
                // startActivityForResult(
                // intentUtil.getPhotoShear(uri, 1, 1, 200, 200),
                // Constant.REQUEST_CODE_PHOTO_SHEAR);
                break;
            // 剪切照片
            case Constant.REQUEST_CODE_PHOTO_SHEAR:
                break;
            // 修改姓名
            // case Constant.REQUEST_CODE_CHANGE_NAME:
            // if (data != null) {
            // mTvName.setText(data.getAction());
            // }
            // break;
            // 修改密码
            // case Constant.REQUEST_CODE_CHANGE_PWD:
            // break;
            case Constant.REQUEST_CODE_ORDER_LIST:
                finish();
                break;
        }
    }

}

