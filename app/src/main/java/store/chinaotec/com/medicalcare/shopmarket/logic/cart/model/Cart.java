package store.chinaotec.com.medicalcare.shopmarket.logic.cart.model;

import java.util.ArrayList;

public class Cart {
    public String priceTotal;

    public long pnumTotal;

    private String shoppingCartSid;
    private int totalQuantity;
    private String loginStatus;
    private String totalPrice;
    private ArrayList<CartItem> items;
    private ShoppingSummaryDto shoppingSummaryDto;

    public Cart(String shoppingCartSid, int totalQuantity, String loginStatus, String totalPrice,
                ArrayList<CartItem> items) {
        super();
        this.shoppingCartSid = shoppingCartSid;
        this.totalQuantity = totalQuantity;
        this.loginStatus = loginStatus;
        this.totalPrice = totalPrice;
        this.items = items;
    }

    public String getShoppingCartSid() {
        return shoppingCartSid;
    }

    public void setShoppingCartSid(String shoppingCartSid) {
        this.shoppingCartSid = shoppingCartSid;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ArrayList<CartItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<CartItem> items) {
        this.items = items;
    }

    public ShoppingSummaryDto getShoppingSummaryDto() {
        return shoppingSummaryDto;
    }

    public void setShoppingSummaryDto(ShoppingSummaryDto shoppingSummaryDto) {
        this.shoppingSummaryDto = shoppingSummaryDto;
    }
}
