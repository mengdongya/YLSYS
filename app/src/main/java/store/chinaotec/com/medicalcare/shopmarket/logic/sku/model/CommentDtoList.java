package store.chinaotec.com.medicalcare.shopmarket.logic.sku.model;

/***
 * 此类描述的是:列表条目的返回对象
 *
 * @author: wjc
 * @version:1.0
 * @date:2016年1月14日 上午11:55:46
 */
public class CommentDtoList {

    private String commentContent;
    private String skuColor;
    private String skuSize;
    private String skuCode;
    private String memberName;
    private int memberId;
    private int score;
    private String commentTime;
    private String isAnonymous;
    private String commentLevel;
    private String skuImg;
    private String skuName;

    public CommentDtoList(String commentContent, String skuColor, String skuSize, String skuCode, String memberName,
                          int memberId, int score, String commentTime, String isAnonymous, String commentLevel, String skuImg,
                          String skuName) {
        super();
        this.commentContent = commentContent;
        this.skuColor = skuColor;
        this.skuSize = skuSize;
        this.skuCode = skuCode;
        this.memberName = memberName;
        this.memberId = memberId;
        this.score = score;
        this.commentTime = commentTime;
        this.isAnonymous = isAnonymous;
        this.commentLevel = commentLevel;
        this.skuImg = skuImg;
        this.skuName = skuName;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
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

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(String isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public String getCommentLevel() {
        return commentLevel;
    }

    public void setCommentLevel(String commentLevel) {
        this.commentLevel = commentLevel;
    }

    public String getSkuImg() {
        return skuImg;
    }

    public void setSkuImg(String skuImg) {
        this.skuImg = skuImg;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

}
