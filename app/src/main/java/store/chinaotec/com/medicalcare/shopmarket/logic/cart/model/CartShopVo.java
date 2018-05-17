package store.chinaotec.com.medicalcare.shopmarket.logic.cart.model;

import java.util.ArrayList;

public class CartShopVo {

    public boolean parentbox;// listview 外部全选的标记
    private ArrayList<CartItem> items;
    private String storeCode;
    private String storeName;

    public ArrayList<CartItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<CartItem> items) {
        this.items = items;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

}
