package store.chinaotec.com.medicalcare.shopmarket.logic.orders.model;

/***
 * 此类描述的是:申请退款的请求对象
 *
 * @author: wjc
 * @version:1.0
 * @date:2015年11月17日 上午11:57:47
 */
public class ApplyRebateDto {
    private String sid;
    private String orderCode;
    private String sourceCode;
    /**
     * ”退款原因
     */
    private String refundReason;

    public ApplyRebateDto(String sid, String orderCode, String sourceCode, String refundReason) {
        super();
        this.sid = sid;
        this.orderCode = orderCode;
        this.sourceCode = sourceCode;
        this.refundReason = refundReason;
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

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    @Override
    public String toString() {
        return "ApplyRebate [sid=" + sid + ", orderCode=" + orderCode + ", sourceCode=" + sourceCode
                + ", refundReason=" + refundReason + "]";
    }
}
