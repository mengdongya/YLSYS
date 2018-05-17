package store.chinaotec.com.medicalcare.shopmarket.logic.orders.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 此类描述的是: 订单条目的详情
 *
 * @author: wjc
 * @version:1.0
 * @date:2016年1月20日 下午2:50:32
 */
public class AppDetailList implements Serializable {
    private ArrayList<String> addressStr;
    private ArrayList<String> orderStr;
    private ArrayList<String> priceStr;

    public AppDetailList() {
        super();
        // TODO Auto-generated constructor stub
    }

    public AppDetailList(ArrayList<String> addressStr, ArrayList<String> orderStr, ArrayList<String> priceStr) {
        super();
        this.addressStr = addressStr;
        this.orderStr = orderStr;
        this.priceStr = priceStr;
    }

    public ArrayList<String> getAddressStr() {
        return addressStr;
    }

    public void setAddressStr(ArrayList<String> addressStr) {
        this.addressStr = addressStr;
    }

    public ArrayList<String> getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(ArrayList<String> orderStr) {
        this.orderStr = orderStr;
    }

    public ArrayList<String> getPriceStr() {
        return priceStr;
    }

    public void setPriceStr(ArrayList<String> priceStr) {
        this.priceStr = priceStr;
    }

    @Override
    public String toString() {
        return "AppDetailList [addressStr=" + addressStr + ", orderStr=" + orderStr + ", priceStr=" + priceStr + "]";
    }

}
