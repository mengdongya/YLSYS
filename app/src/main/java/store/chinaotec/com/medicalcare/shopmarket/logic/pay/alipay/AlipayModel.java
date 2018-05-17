package store.chinaotec.com.medicalcare.shopmarket.logic.pay.alipay;

public class AlipayModel {
    public String body;
    public String subject;
    public String sign_type;
    public String notify_url;//回调地址
    public String out_trade_no;//商户订单号
    public String sign;
    public String _input_charset;
    public String it_b_pay;
    public String appenv;
    public String total_fee;//总金额
    public String service;
    public String paymethod;
    public String seller_id;//卖家
    public String app_id;//商户号
    public String partner;
    public String payment_type;
}
