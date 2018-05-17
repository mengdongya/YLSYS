package store.chinaotec.com.medicalcare.shopmarket.logic.pay.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.request.requestUrl.RequestUrl;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.logic.pay.alipay.AlipayModel;
import store.chinaotec.com.medicalcare.shopmarket.logic.pay.alipay.PayAli;
import store.chinaotec.com.medicalcare.shopmarket.logic.pay.service.impl.ServicePayImpl;
import store.chinaotec.com.medicalcare.shopmarket.logic.user.model.User;
import store.chinaotec.com.medicalcare.utill.UserKeeper;


/**
 * Created by wjc on 2016/8/25 0025.
 */
public class PaySelectActivity extends BasePayActivity {

    int[] arrMenuTv = {R.id.btn_check_zfb};
    private User user;
    private String sid;
    private ServicePayImpl mServicePay = null;
    private String priceTotal;
    private String orderCode;
    private Button gotoPay;
    private ImageView mBtnCheckZFB;
    private int contion = 0;
    private TextView mActualPayment;
    private String TAG = "PaySelectActivity===";

    /**
     * 给Button设置图片
     */
    private void setTextViewForColor(ImageView btn, int id) {
        btn.setImageResource(R.drawable.btn_select_down);
        for (int i = 0; i < arrMenuTv.length; i++) {
            ImageView mButton = (ImageView) findViewById(arrMenuTv[i]);
            if (arrMenuTv[i] != id) {
                mButton.setImageResource(R.drawable.btn_select_up);
            }
        }
    }

    @Override
    protected void onClickEvent(View paramView) {
        switch (paramView.getId()) {
            case R.id.title_btn_left:
                finish();
                break;
            case R.id.btn_pay_sure:
                LogUtil.e(TAG, "ONCLICK点击了几次了 :  " + (contion++));
                gotoPayOrder();
                break;
            case R.id.btn_check_zfb:
                setTextViewForColor(mBtnCheckZFB, R.id.btn_check_zfb);
                break;
        }
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_shop_market_pay_order_select);
        ((TextView) findViewById(R.id.title_textview)).setText("订单支付");
    }

    @Override
    protected void initView() {
        user = UserKeeper.readUser(this);
        sid = user.sid;
        mBtnCheckZFB = (ImageView) findViewById(R.id.btn_check_zfb);
        mActualPayment = (TextView) findViewById(R.id.tv_order_total);
        gotoPay = (Button) findViewById(R.id.btn_pay_sure);
    }

    @Override
    protected void initListener() {
        ImageView btnBack = (ImageView) findViewById(R.id.title_btn_left);
        btnBack.setOnClickListener(this);
        gotoPay.setOnClickListener(this);
        mBtnCheckZFB.setOnClickListener(this);
    }

    @Override
    protected void processLogic() {
        mServicePay = new ServicePayImpl(this, mDataCallback);
        Intent mIntent = getIntent();
        priceTotal = mIntent.getStringExtra(SourceConstant.TOTAL_ACTUAL);
        LogUtil.e("payselect", "=====收到值了totalPrice:" + priceTotal);
        orderCode = mIntent.getStringExtra(SourceConstant.ORDER_CODE);
        LogUtil.e("payselect====", "=====收到值了orderCode:" + orderCode);
        mActualPayment.setText("￥: " + priceTotal);
    }

    @Override
    protected void onResume() {
        super.onResume();
        contion = 0;
        LogUtil.e(TAG, "进了onResume方法了");
        gotoPay.setEnabled(true);
    }

    /**
     * 订单去支付
     */
    private void gotoPayOrder() {
        contion = contion + 1;
        LogUtil.e(TAG, "点击了几次了 :  " + (contion));
        gotoPay.setEnabled(false);
        mServicePay.getPayInfoAli(orderCode, sid);
    }

    @Override
    protected void processData(String data, RequestVo reqVo) {
        switch (reqVo.requestUrl) {
            case RequestUrl.ALI_PAY:
                AlipayModel alipay = JSON.parseObject(data, AlipayModel.class);
                PayAli payAli = new PayAli(alipay);
                final String payInfo = payAli.getOrderInfo();
                mServicePay.payAli(payInfo);
                break;
        }
    }

    @Override
    public void doStartUnionPayPlugin(Activity activity, String tn, String mode) {
    }

    @Override
    public void updateTextView(TextView tv) {
    }

    @Override
    public void payErrory(String str) {
    }

}
