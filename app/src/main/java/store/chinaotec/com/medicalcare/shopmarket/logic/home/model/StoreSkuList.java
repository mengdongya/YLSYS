package store.chinaotec.com.medicalcare.shopmarket.logic.home.model;

import java.util.ArrayList;

/**
 * 门店商品列表
 */
public class StoreSkuList {

    public ArrayList<String> imgUrls = new ArrayList<String>();
    public String imgPath;
    public String currentPrice;//价格
    public String skuNo;
    public String inventory;//库存
    public String skuCode;
    public String name;

    public ArrayList<String> getImgUrl() {
        return imgUrls;
    }

    public void setImgUrl(ArrayList<String> imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getSkuNo() {
        return skuNo;
    }

    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "StoreSkuList [imgUrls=" + imgUrls + ", currentPrice=" + currentPrice + ", skuNo=" + skuNo
                + ", inventory=" + inventory + ", skuCode=" + skuCode + ", name=" + name + "]";
    }
}
