package store.chinaotec.com.medicalcare.shopmarket.logic.pay.model;

public class ShopContent {
    private String storeCode;
    private String memo;

    public ShopContent(String storeCode, String memo) {
        super();
        this.storeCode = storeCode;
        this.memo = memo;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}
