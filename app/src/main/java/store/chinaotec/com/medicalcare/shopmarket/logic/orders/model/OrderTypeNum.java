package store.chinaotec.com.medicalcare.shopmarket.logic.orders.model;

/**
 * Created by wjc on 2016/8/16 0016.
 */
public class OrderTypeNum {
    private int allOrderNum;
    private int waitPayNum;
    private int waitDeliverNum;
    private int waitReceiptNum;
    private int waitEvaluateNum;

    public OrderTypeNum() {
        super();
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
