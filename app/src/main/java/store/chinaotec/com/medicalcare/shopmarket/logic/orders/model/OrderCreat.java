package store.chinaotec.com.medicalcare.shopmarket.logic.orders.model;


public class OrderCreat {
    /**
     * 购物车结算
     */
    public static final String GOTO_CART_PAYMENT = "0";
    /**
     * 立即结算
     */
    public static final String GOTO_IMMEDIATE_PAYMENT = "1";
    /**
     * 积分 结算
     */
    public static final String GOTO_SCORE_PAYMENT = "2";
    /**
     * 来源 android = "1 " , IOS="2"
     */
    public final String source_code = "1";
    /**
     * 立即结算:TYPE_PAY_IMMEDIATE<br>
     * 购物车结算:TYPE_PAY_CART
     */
    public String tpyePay;
    /**
     * sessionID
     */
    public String sid;
    /**
     * 结算序列ID
     */
    public String payoff_id;
    /**
     * 地址ID
     */
    public String address_id = "-1";
//	/** 商品优惠券id "|" 分隔 */
//	public String coupons_ids = "";
    /**
     * 是否选中使用积分y使用n不使用积分<该字段可以为空>
     */
    public String is_point = "n";
//	public String integralPriceUsed = "n";
    /**
     * 是否选中使用红包y使用n不使<该字段可以为空>
     */
    public String isRedUse = "n";
    /**
     * 是否索取发票
     */
    public String isInvoice = "n";
    /**
     * 发票类型1个人2公司
     */
    public String invoiceType = "";
    /**
     * 发票抬头
     */
    public String invoiceTitle = "";
    /**
     * 支付方式
     */
    public String payment_type = "";
    /**
     * 配送时间
     */
    public String send_date = "";
    /**
     * 订单备注
     */
    public String memo = "";
    /**
     * 是否使用代金券 (y/n)
     */
    public String is_cardno = "n";
    /** 是否使用代金券 (y/n) */
//	public String isRedUse = "n";
    /**
     * 优惠卷code
     */
    public String cardno = "";

    /**
     * 商品Id (立即结算 - 使用)
     */
    public String sku_id = "";
    /**
     * 商品数量 (立即结算 - 使用)
     */
    public String quantity = "";

    /**
     * 是否自提
     */
    public String isPickUp = "n";

    /**
     * 设备标识
     */
    public String macAddr;
    /**
     * 极光标识
     */
    public String pushSignAddr;

    /**
     * 回购商品的code
     */
    public String buyBackCodes;

    public String shopContent;

    //--------------------中秋活动---
    /**
     * 活动编号
     */
    public String pmCode;
    /**
     * 商品编号
     */
    public String skuCode;

}
