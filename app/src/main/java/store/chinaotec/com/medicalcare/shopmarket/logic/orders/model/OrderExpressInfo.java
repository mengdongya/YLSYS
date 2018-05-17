package store.chinaotec.com.medicalcare.shopmarket.logic.orders.model;

/**
 * 物流信息请求对象
 */
public class OrderExpressInfo {
    private String sid;
    private String orderCode;

    public OrderExpressInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

    public OrderExpressInfo(String sid, String orderCode) {
        super();
        this.sid = sid;
        this.orderCode = orderCode;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

}
