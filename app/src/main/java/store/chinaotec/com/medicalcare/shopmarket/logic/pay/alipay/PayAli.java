package store.chinaotec.com.medicalcare.shopmarket.logic.pay.alipay;


public class PayAli {
    private AlipayModel alipay;

    public PayAli(AlipayModel alipay) {
        this.alipay = alipay;
    }

    /**
     * create the order info. 创建订单信息
     */
    public String getOrderInfo() {

        String orderInfo = null;
        // 参数编码， 固定值
        orderInfo = "_input_charset=" + "\"" + alipay._input_charset + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + alipay.body + "\"";

        orderInfo += "&it_b_pay=" + "\"" + alipay.it_b_pay + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + alipay.notify_url + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + alipay.out_trade_no + "\"";

        // 合作者身份ID
        orderInfo += "&partner=" + "\"" + alipay.partner + "\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=" + "\"" + alipay.payment_type + "\"";

        orderInfo += "&return_url=\"http://m.alipay.com\"";
        // 卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + alipay.seller_id + "\"";

        // 接口名称， 固定值
        orderInfo += "&service=" + "\"" + alipay.service + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + alipay.subject + "\"";

        // 商品金额
        // double totalFee = Float.valueOf(alipay.total_fee);
        // totalFee = totalFee * 0.01;
        // BigDecimal bg = new BigDecimal(totalFee);
        // totalFee = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        orderInfo += "&total_fee=" + "\"" + alipay.total_fee + "\"";

        orderInfo += "&sign=" + "\"" + alipay.sign + "\"";

        orderInfo += "&sign_type=" + "\"" + "RSA" + "\"";

        return orderInfo;
    }

}
