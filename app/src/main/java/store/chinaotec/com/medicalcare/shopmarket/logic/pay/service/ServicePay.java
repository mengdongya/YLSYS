package store.chinaotec.com.medicalcare.shopmarket.logic.pay.service;

/**
 * Created by wjc on 2016/8/5 0005.
 */
public interface ServicePay {

    /**
     * 创建订单
     */
    public void creatOrder(Object entity);

    /**
     * 支付宝 - 获取支付信息
     */
    public void getPayInfoAli(String orderCode, String sId);

    /**
     * 支付宝
     */
    public void payAli(String orderCode);
}
