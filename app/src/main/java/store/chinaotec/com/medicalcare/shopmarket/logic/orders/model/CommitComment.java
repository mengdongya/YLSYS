package store.chinaotec.com.medicalcare.shopmarket.logic.orders.model;

/**
 * 此类描述的是:向服务器提交评价商品的实体类
 *
 * @author: wyk
 * @version:1.0
 * @date:2015年7月25日 上午11:28:38
 */
public class CommitComment {
    private String sid;
    private String skuCode;
    private String orderCode;
    private String content;
    private String sourceCode;
    private String skuColor;
    private String skuSize;
    private String orderLineId;
    private String score;
    private String isAnonymous;

    public CommitComment() {
        // TODO Auto-generated constructor stub
    }

    public CommitComment(String sid, String skuCode, String orderCode,
                         String content, String sourceCode, String skuColor, String skuSize,
                         String orderLineId, String score, String isAnonymous
    ) {
        super();
        this.sid = sid;
        this.skuCode = skuCode;
        this.orderCode = orderCode;
        this.content = content;
        this.sourceCode = sourceCode;
        this.skuColor = skuColor;
        this.skuSize = skuSize;
        this.orderLineId = orderLineId;
        this.score = score;
        this.isAnonymous = isAnonymous;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getSkuColor() {
        return skuColor;
    }

    public void setSkuColor(String skuColor) {
        this.skuColor = skuColor;
    }

    public String getSkuSize() {
        return skuSize;
    }

    public void setSkuSize(String skuSize) {
        this.skuSize = skuSize;
    }

    public String getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(String orderLineId) {
        this.orderLineId = orderLineId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(String isAnonymous) {
        this.isAnonymous = isAnonymous;
    }
}
