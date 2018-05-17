package store.chinaotec.com.medicalcare.shopmarket.logic.orders.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.base.activity.BaseAoActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.http.AorunApi;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.MyImageLoader;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.CommitComment;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.SkuByOrderLine;
import store.chinaotec.com.medicalcare.shopmarket.logic.user.model.User;
import store.chinaotec.com.medicalcare.utill.UserKeeper;


/***
 * 此类描述的是:评价商品
 *
 * @author: wyk
 * @version:1.0
 * @date:2015年7月24日 下午5:17:14
 */
public class OrderEvaluateActivity extends BaseAoActivity implements OnRatingBarChangeListener {
    public static boolean isEvaluate = false;
    /**
     * 订单-商品详情
     */
    public SkuByOrderLine skuByOrder;
    private ImageView mBtnBack;
    private Bundle mBundle;
    private EditText mEdContent;
    /**
     * 待评价商品的图片
     */
    private ImageView iv_order_evaluate_img;
    /**
     * 商品名称
     */
    private TextView tv_order_evaluate_name;
    /**
     * 商品颜色
     */
    private TextView tv_order_evaluate_color;
    /**
     * 商品尺码
     */
    private TextView tv_order_evaluate_size;
    /**
     * 商品数量
     */
    private TextView tv_order_evaluate_number;
    /**
     * 商品价格
     */
    private TextView tv_order_evaluate_price;
    /**
     * 评价内容
     */
    private EditText ed_evaluate_feedback;
    /**
     * 是否匿名评论
     */
    private ImageView iv_evaluate_anonymity;
    /**
     * 发表评论
     */
    private Button btn_submit_evaluate;
    /**
     * 是否匿名评价
     */
    private boolean isAnonymity = false;
    /**
     * 星星评分
     */
    private RatingBar app_evaluate_star;
    private SharedPreferences fileNameAli;
    /**
     * 用户登录标识
     */
    private String sid;
    /**
     * 评论等级（1好评，2中评，3差评）
     */
    private String commentLevel = "1";
    /**
     * 发表评价的请求参数的对象
     */
    private CommitComment mComment;
    private String TAG = "orderEvalute";
    private int numStars = 5;
    private TextView tv_star_content;
    private User user;

    @Override
    protected void onClickEvent(View paramView) {
        switch (paramView.getId()) {
            case R.id.title_btn_left:
                finish();
                break;
            case R.id.title_btn_right:
                submit();
                break;
            case R.id.iv_evaluate_anonymity:
                if (isAnonymity) {
                    iv_evaluate_anonymity.setImageResource(R.drawable.btn_select_down);
                } else {
                    iv_evaluate_anonymity.setImageResource(R.drawable.btn_select_up);
                }
                isAnonymity = !isAnonymity;
                break;
            case R.id.btn_submit_evaluate:
                submit();
                break;
            default:
                break;
        }
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_shop_market_order_sku_feedback);
        ((TextView) findViewById(R.id.title_textview)).setText("发表评价");
    }

    @Override
    protected void initView() {
        mBtnBack = (ImageView) findViewById(R.id.title_btn_left);
        if (SourceConstant.skuByOrder != null) {
            skuByOrder = SourceConstant.skuByOrder;
        }
        fileNameAli = this.getSharedPreferences(SourceConstant.fileNameAli, 0);
        user = UserKeeper.readUser(this);
        sid = user.sid;
        iv_order_evaluate_img = (ImageView) findViewById(R.id.iv_order_evaluate_img);
        tv_order_evaluate_name = (TextView) findViewById(R.id.tv_order_evaluate_name);
        tv_order_evaluate_color = (TextView) findViewById(R.id.tv_order_evaluate_color);
        tv_order_evaluate_size = (TextView) findViewById(R.id.tv_order_evaluate_size);
        tv_order_evaluate_number = (TextView) findViewById(R.id.tv_order_evaluate_number);
        tv_order_evaluate_price = (TextView) findViewById(R.id.tv_order_evaluate_price);
        ed_evaluate_feedback = (EditText) findViewById(R.id.ed_evaluate_feedback);
        iv_evaluate_anonymity = (ImageView) findViewById(R.id.iv_evaluate_anonymity);
        btn_submit_evaluate = (Button) findViewById(R.id.btn_submit_evaluate);
        tv_star_content = (TextView) findViewById(R.id.tv_star_content);
        if (isAnonymity) {
            iv_evaluate_anonymity.setImageResource(R.drawable.btn_select_down);
        } else {
            iv_evaluate_anonymity.setImageResource(R.drawable.btn_select_up);
        }
        app_evaluate_star = (RatingBar) findViewById(R.id.app_evaluate_star);
    }

    @Override
    protected void initListener() {
        mBtnBack.setOnClickListener(this);
        iv_evaluate_anonymity.setOnClickListener(this);
        btn_submit_evaluate.setOnClickListener(this);
        app_evaluate_star.setOnRatingBarChangeListener(this);
    }

    @Override
    protected void processLogic() {
        mBundle = getIntent().getExtras();
        String imgPath = skuByOrder.getSkuImg() + "";
        if (!"".equals(imgPath)) {
            MyImageLoader.loadImage(skuByOrder.getSkuImg(), iv_order_evaluate_img);
        } else {
            iv_order_evaluate_img.setImageResource(R.drawable.error_img);
        }
        String skuName = skuByOrder.getSkuName();
        String skuColor = skuByOrder.getSkuColor();
        String skuSize = skuByOrder.getSkuSize();
        String skuNumber = skuByOrder.getQuantity() + "";
        String skuPrice = skuByOrder.getSkuPrice() + "";
        tv_order_evaluate_name.setText(skuName);
        if (!"".equals(skuByOrder.getSkuColor())) {
            tv_order_evaluate_color.setText(Html.fromHtml(String.format(this
                            .getResources().getString(R.string.order_tv_order_max),
                    skuColor)));
        } else {
            tv_order_evaluate_color.setVisibility(View.GONE);
        }
        if (!"".equals(skuByOrder.getSkuSize())) {
            tv_order_evaluate_size.setText(Html.fromHtml(String.format(this
                            .getResources().getString(R.string.order_tv_order_max),
                    skuSize)));
        } else {
            tv_order_evaluate_size.setVisibility(View.GONE);
        }

        tv_order_evaluate_number.setText(Html.fromHtml(String.format(this
                        .getResources().getString(R.string.evaluate_title_number),
                skuNumber)));
        tv_order_evaluate_price.setText(Html.fromHtml(String.format(this
                        .getResources().getString(R.string.evaluate_title_price),
                skuPrice)));
    }

    private void submit() {
        String content = ed_evaluate_feedback.getText().toString().trim() == null ? ""
                : ed_evaluate_feedback.getText().toString().trim();
        if (content.equals("")) {
            content = "";
        }
        String isAnonymous = isAnonymity == true ? "y" : "n";

        mComment = new CommitComment(sid, skuByOrder.getSkuCode(),
                skuByOrder.getOrderCode(), content, SourceConstant.ANDROID_CODE, skuByOrder.getSkuColor(),
                skuByOrder.getSkuSize(), skuByOrder.getOrderLineId(), numStars + "", isAnonymous);
        AorunApi.getSkuEvaluateAdd(mComment, mDataCallback);
    }

    @Override
    protected void processData(String data, RequestVo requestVo) {
        ToastUtil.MyToast(this, "评价成功");
        isEvaluate = true;
        finish();
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating,
                                boolean fromUser) {
        LogUtil.e(TAG, "--------------------------------------" + rating);
        numStars = (int) rating;
        switch (numStars) {
            case 0:
                commentLevel = "差评，非常不满意！";
                break;
            case 1:
                commentLevel = "差评，不太满意！";
                break;
            case 2:
                commentLevel = "中差评，基本满意！";
                break;
            case 3:
                commentLevel = "中评，基本满意！";
                break;
            case 4:
                commentLevel = "好评，很满意！";
                break;
            case 5:
                commentLevel = "好评，非常满意！";
                break;

            default:
                break;
        }
        tv_star_content.setText(commentLevel);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
