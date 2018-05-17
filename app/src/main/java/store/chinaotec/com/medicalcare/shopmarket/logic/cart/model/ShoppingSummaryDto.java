package store.chinaotec.com.medicalcare.shopmarket.logic.cart.model;

/**
 * Created by seven on 2016/8/7 0007.
 */
public class ShoppingSummaryDto {

    private String priceTotal;
    private String loginStatus;
    private int pnumTotal;

    public String getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(String priceTotal) {
        this.priceTotal = priceTotal;
    }

    public int getPnumTotal() {
        return pnumTotal;
    }

    public void setPnumTotal(int pnumTotal) {
        this.pnumTotal = pnumTotal;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }
}
