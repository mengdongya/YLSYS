package store.chinaotec.com.medicalcare.shopmarket.logic.orders.model;

/**
 * 此类描述的是:取消订单，确认收货等操作想服务器请求的参数对象
 *
 * @author: wyk
 * @version:1.0
 * @date:2015年7月25日 下午2:57:49
 */
public class CancelOrder {
    private String sid;
    private String orderStatus;
    private String orderCode;

    public CancelOrder() {
        // TODO Auto-generated constructor stub
    }

    public CancelOrder(String sid, String orderStatus, String orderCode) {
        super();
        this.sid = sid;
        this.orderStatus = orderStatus;
        this.orderCode = orderCode;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Override
    public String toString() {
        return "CancelOrder [sid=" + sid + ", orderStatus=" + orderStatus
                + ", orderCode=" + orderCode + "]";
    }

}
