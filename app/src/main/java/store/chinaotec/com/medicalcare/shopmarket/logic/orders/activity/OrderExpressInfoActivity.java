package store.chinaotec.com.medicalcare.shopmarket.logic.orders.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.base.activity.BaseAoActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.http.AorunApi;
import store.chinaotec.com.medicalcare.shopmarket.common.request.requestUrl.RequestUrl;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.JsonUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.adapter.OrderExpressAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.ExpressResArrayDto;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.ExpressResDto;
import store.chinaotec.com.medicalcare.shopmarket.logic.user.model.User;
import store.chinaotec.com.medicalcare.utill.UserKeeper;

/**
 * 此类描述的是:查看物流信息
 *
 * @author: wjc
 * @version:1.0
 * @date:205年11月11日 上午11:44:35
 */
public class OrderExpressInfoActivity extends BaseAoActivity {
    private TextView mTvTitle;
    private ImageView mbtnBack;
    private SharedPreferences fileNameAli;
    private String sid;
    private String orderCode;
    private ExpressResDto entity;
    /**
     * 物流公司的中文名
     */
    private TextView tv_order_express_name;
    /**
     * 物流单号
     */
    private TextView tv_order_express_number;
    /**
     * 物流状态（0：在途 1：揽件 2：疑难  3:签收  4:退签 5：派件6：退回）
     */
    private TextView tv_order_express_state;
    /**
     * 返回跟踪记录的数组
     */
    private ListView lv_order_express_list;
    private String TAG = "OrderExpressInfoActivity";
    private int state;
    private User user;
    private LinearLayout ll_logistics_info;
    private LinearLayout ll_no_logistics_info;

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
        setContentView(R.layout.activity_shop_market_order_express_info);

    }

    @Override
    protected void initView() {
        mbtnBack = (ImageView) findViewById(R.id.title_btn_left);
        mTvTitle = (TextView) findViewById(R.id.title_textview);
        fileNameAli = this.getSharedPreferences(SourceConstant.fileNameAli, 0);
        user = UserKeeper.readUser(this);
        sid = user.sid;
        ll_logistics_info = (LinearLayout) findViewById(R.id.ll_logistics_info);
        ll_no_logistics_info = (LinearLayout) findViewById(R.id.ll_no_logistics_info);
        tv_order_express_name = (TextView) findViewById(R.id.tv_order_express_name);
        tv_order_express_number = (TextView) findViewById(R.id.tv_order_express_number);
        tv_order_express_state = (TextView) findViewById(R.id.tv_order_express_state);
        lv_order_express_list = (ListView) findViewById(R.id.lv_order_express_list);

    }

    @Override
    protected void initListener() {
        mbtnBack.setOnClickListener(this);
    }

    @Override
    protected void processLogic() {
        mTvTitle.setText("物流信息");
        Intent intent = getIntent();
        orderCode = intent.getStringExtra("itemorderCode");
        getData();
    }

    private void getData() {
        AorunApi.getOrderExpress(sid, orderCode, mDataCallback);
    }

    @Override
    protected void processData(String data, RequestVo requestVo) {
        switch (requestVo.requestUrl) {
            case RequestUrl.ORDER_GET_ORDER_EXPRESS:
                setData(data);
                break;
        }

    }

    private void setData(String obj) {
        entity = JsonUtil.jsonToEntity(obj, ExpressResDto.class);
        LogUtil.e(TAG, "entity====" + entity);

        if (entity != null && !"".equals(entity)) {
            ArrayList<ExpressResArrayDto> dataList = entity.getData();
            ll_logistics_info.setVisibility(View.VISIBLE);
            ll_no_logistics_info.setVisibility(View.GONE);
            //-----设置数据
            tv_order_express_name.setText(Html.fromHtml(String.format(this.getResources()
                    .getString(R.string.order_tv_express_name), entity.getName())));
            tv_order_express_number.setText(Html.fromHtml(String.format(this.getResources()
                    .getString(R.string.order_tv_express_number), entity.getNu())));
            OrderExpressAdapter orderExpressAdapter = new OrderExpressAdapter(this, dataList);
            lv_order_express_list.setAdapter(orderExpressAdapter);
            orderExpressAdapter.notifyDataSetChanged();
        } else {
            ll_logistics_info.setVisibility(View.GONE);
            ll_no_logistics_info.setVisibility(View.VISIBLE);
        }
    }
}
