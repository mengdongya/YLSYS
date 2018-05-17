package store.chinaotec.com.medicalcare.shopmarket.logic.home.model;

/**
 * Created by seven on 2016/8/20 0020.
 */
public class ShopGroupBuyAndSeckill {

    private String skuCode;
    private String storeCode;
    private String skuImgPath;
    private String skuName;
    private String skuTogetherPrice;
    private String skuPrice;
    private int actualOrderNumber;
    private int skuTotalNumber;
    private int limitNum;

    private String saleRate;
    private String showNumber;
    /* 距离秒杀结束的秒数  */
    private String distanceEndSecond;


    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getSkuImgPath() {
        return skuImgPath;
    }

    public void setSkuImgPath(String skuImgPath) {
        this.skuImgPath = skuImgPath;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuTogetherPrice() {
        return skuTogetherPrice;
    }

    public void setSkuTogetherPrice(String skuTogetherPrice) {
        this.skuTogetherPrice = skuTogetherPrice;
    }

    public String getSkuPrice() {
        return skuPrice;
    }

    public void setSkuPrice(String skuPrice) {
        this.skuPrice = skuPrice;
    }

    public String getSaleRate() {
        return saleRate;
    }

    public void setSaleRate(String saleRate) {
        this.saleRate = saleRate;
    }

    public String getShowNumber() {
        return showNumber;
    }

    public void setShowNumber(String showNumber) {
        this.showNumber = showNumber;
    }

    public String getDistanceEndSecond() {
        return distanceEndSecond;
    }

    public void setDistanceEndSecond(String distanceEndSecond) {
        this.distanceEndSecond = distanceEndSecond;
    }

    public int getActualOrderNumber() {
        return actualOrderNumber;
    }

    public void setActualOrderNumber(int actualOrderNumber) {
        this.actualOrderNumber = actualOrderNumber;
    }

    public int getSkuTotalNumber() {
        return skuTotalNumber;
    }

    public void setSkuTotalNumber(int skuTotalNumber) {
        this.skuTotalNumber = skuTotalNumber;
    }

    public int getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(int limitNum) {
        this.limitNum = limitNum;
    }
}
