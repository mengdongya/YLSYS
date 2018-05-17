package store.chinaotec.com.medicalcare.shopmarket.logic.orders.model;

/**
 * 请求服务时订单状态
 */
public class OrderOfInfo {
    /**
     * 用户登录标识
     */
    private String sid;
    /**
     * 订单编号
     */
    private String orderCode;
    /**
     * 订单状态
     */
    private String orderStatus;
    /**
     * 第几页
     */
    private String pageIndex;
    /**
     * 分页数据大小
     */
    private String pageSize;

    public OrderOfInfo(String sid, String orderCode, String orderStatus,
                       String pageIndex, String pageSize) {
        super();
        this.sid = sid;
        this.orderCode = orderCode;
        this.orderStatus = orderStatus;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

}
