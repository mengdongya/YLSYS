package store.chinaotec.com.medicalcare.shopmarket.logic.orders.model;


import android.os.Parcel;

import java.io.Serializable;

/**
 * 此类描述的是:订单-商品详情
 *
 * @author: wyk
 * @version:1.0
 * @date:2015年7月24日 下午1:48:19
 */

public class SkuByOrderLine implements Serializable {
    /**
     * 商品尺码
     */
    private String skuSize;
    /**
     * 商品单价
     */
    private double skuPrice;
    /**
     * 商品颜色
     */
    private String skuColor;
    /**
     * 订单编码
     */
    private String orderCode;
    /**
     * 商品名称
     */
    private String skuName;
    /**
     * 商品数量
     */
    private Integer quantity;
    /**
     * 订单行ID
     */
    private String orderLineId;
    /**
     * 商品图片
     */
    private String skuImg;
    /**
     * 商品编码
     */
    private String skuCode;
    /**
     * 商品是否评价过 y: 已评价 n:为评价
     */
    private String isComment;
    /**
     * 创建订单的时间
     */
    private String createOrderTime;
    private String lineTotalPrice;

    public SkuByOrderLine() {
        // TODO Auto-generated constructor stub
    }

    private SkuByOrderLine(Parcel in) {
        // mData = in.readInt();
    }

    public String getCreateOrderTime() {
        return createOrderTime;
    }

    public void setCreateOrderTime(String createOrderTime) {
        this.createOrderTime = createOrderTime;
    }

    public String getLineTotalPrice() {
        return lineTotalPrice;
    }

    public void setLineTotalPrice(String lineTotalPrice) {
        this.lineTotalPrice = lineTotalPrice;
    }

    public String getIsComment() {
        return isComment;
    }

    public void setIsComment(String isComment) {
        this.isComment = isComment;
    }

    public String getSkuSize() {
        return skuSize;
    }

    public void setSkuSize(String skuSize) {
        this.skuSize = skuSize;
    }

    public double getSkuPrice() {
        return skuPrice;
    }

    public void setSkuPrice(double skuPrice) {
        this.skuPrice = skuPrice;
    }

    public String getSkuColor() {
        return skuColor;
    }

    public void setSkuColor(String skuColor) {
        this.skuColor = skuColor;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(String orderLineId) {
        this.orderLineId = orderLineId;
    }

    public String getSkuImg() {
        return skuImg;
    }

    public void setSkuImg(String skuImg) {
        this.skuImg = skuImg;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

}
