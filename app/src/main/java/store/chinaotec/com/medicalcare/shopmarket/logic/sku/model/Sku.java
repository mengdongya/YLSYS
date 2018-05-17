package store.chinaotec.com.medicalcare.shopmarket.logic.sku.model;

/**
 * 商品列表--item
 */
public class Sku {
    /**
     * 商品唯一编号
     */
    public String skuCode;
    /**
     * 产品编号
     */
    public String productCode;
    /**
     * 商品名称
     */
    public String name;// string 是
    /**
     * URL连接 是 商品图片
     */
    public String imgPath;//
    /**
     * 商品单价(现价)
     */
    public String priceNew;// Int 否
    //	/** 商品单价(原价) */
    public String priceOld;// Int 是
    /**
     * 商品颜色
     */
    public String color;//
    /**
     * 商品尺寸
     */
    public String size;//
    /**
     * 品牌编号
     */
    public String hasGift;//
    /**
     * 折扣
     */
    public String discount;//
    /**
     * 评论个数
     */
    public int commentCount;// 评价个数
    /**
     * 是否是会员商品
     */
    public String isMemberSku;// y 是会员商品 n 不是会员商品
    /**
     * VIP会员商品价格
     */
    public String priceVip;

    //会员售价
    public String aladingPrice;
    //是否可用
    public String usableSku;
    //Y自营；n旗舰店
    public String selfSell;
    public String currentPrice;
    public String skuImg;
    public String skuName;
    public String storeType;
    public String storeCode;
    public String skuPrice;

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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getPriceNew() {
        return priceNew;
    }

    public void setPriceNew(String priceNew) {
        this.priceNew = priceNew;
    }

    public String getPriceOld() {
        return priceOld;
    }

    public void setPriceOld(String priceOld) {
        this.priceOld = priceOld;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getHasGift() {
        return hasGift;
    }

    public void setHasGift(String hasGift) {
        this.hasGift = hasGift;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getIsMemberSku() {
        return isMemberSku;
    }

    public void setIsMemberSku(String isMemberSku) {
        this.isMemberSku = isMemberSku;
    }

    public String getPriceVip() {
        return priceVip;
    }

    public void setPriceVip(String priceVip) {
        this.priceVip = priceVip;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    @Override
    public String toString() {
        return "Sku [skuCode=" + skuCode + ", productCode=" + productCode
                + ", name=" + name + ", imgPath=" + imgPath + ", priceNew="
                + priceNew + ", color=" + color
                + ", size=" + size + ", hasGift=" + hasGift + ", discount="
                + discount + ", commentCount=" + commentCount + ", "
                + "isMemberSku=" + isMemberSku + "priceVip=" + priceVip + "]";
    }

}
