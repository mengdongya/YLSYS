package store.chinaotec.com.medicalcare.shopmarket.logic.pay.model;

import java.util.ArrayList;

/**
 * Created by seven on 2017/1/9 0009.
 */
public class FreightInfo {
    private String totalPrice;
    private String totalFreight;
    private ArrayList<ChangeAddressListDtoList> changeAddressListDtoList;

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalFreight() {
        return totalFreight;
    }

    public void setTotalFreight(String totalFreight) {
        this.totalFreight = totalFreight;
    }

    public ArrayList<ChangeAddressListDtoList> getChangeAddressListDtoList() {
        return changeAddressListDtoList;
    }

    public void setChangeAddressListDtoList(ArrayList<ChangeAddressListDtoList> changeAddressListDtoList) {
        this.changeAddressListDtoList = changeAddressListDtoList;
    }
}
