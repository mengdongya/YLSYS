package store.chinaotec.com.medicalcare.shopmarket.logic.orders.model;

/**
 * 此类描述的是:各种订单数量
 *
 * @author: wyk
 * @version:1.0
 * @date:2015年10月22日 上午10:26:50
 */
public class OrderNumber {
    /**
     * 全部订单数量
     */
    private int allOrderNum;
    /**
     * 待付款数量
     */
    private int waitPayNum;
    /**
     * 待发货数量
     */
    private int waitDeliverNum;
    /**
     * 待收货数量
     */
    private int waitReceiptNum;
    /**
     * 待评价数量
     */
    private int waitEvaluateNum;

    /**
     * 订单
     */
    public OrderNumber() {
        // TODO Auto-generated constructor stub
    }

    public int getAllOrderNum() {
        return allOrderNum;
    }

    public void setAllOrderNum(int allOrderNum) {
        this.allOrderNum = allOrderNum;
    }

    public int getWaitPayNum() {
        return waitPayNum;
    }

    public void setWaitPayNum(int waitPayNum) {
        this.waitPayNum = waitPayNum;
    }

    public int getWaitDeliverNum() {
        return waitDeliverNum;
    }

    public void setWaitDeliverNum(int waitDeliverNum) {
        this.waitDeliverNum = waitDeliverNum;
    }

    public int getWaitReceiptNum() {
        return waitReceiptNum;
    }

    public void setWaitReceiptNum(int waitReceiptNum) {
        this.waitReceiptNum = waitReceiptNum;
    }

    public int getWaitEvaluateNum() {
        return waitEvaluateNum;
    }

    public void setWaitEvaluateNum(int waitEvaluateNum) {
        this.waitEvaluateNum = waitEvaluateNum;
    }
}
