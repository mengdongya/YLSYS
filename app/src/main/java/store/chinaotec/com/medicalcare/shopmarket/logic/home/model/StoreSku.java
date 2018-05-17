package store.chinaotec.com.medicalcare.shopmarket.logic.home.model;

import java.util.ArrayList;

/**
 * 门店的商品和头上的banner
 */
public class StoreSku {
    public String labelCode;
    public ArrayList<StoreSkuList> storeSkuList = new ArrayList<StoreSkuList>();
    public String labelName;

    public String getLabelCode() {
        return labelCode;
    }

    public void setLabelCode(String labelCode) {
        this.labelCode = labelCode;
    }

    public ArrayList<StoreSkuList> getStoreSkuList() {
        return storeSkuList;
    }

    public void setStoreSkuList(ArrayList<StoreSkuList> storeSkuList) {
        this.storeSkuList = storeSkuList;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    @Override
    public String toString() {
        return "StoreSku [labelCode=" + labelCode + ", storeSkuList=" + storeSkuList + ", labelName=" + labelName + "]";
    }

}
