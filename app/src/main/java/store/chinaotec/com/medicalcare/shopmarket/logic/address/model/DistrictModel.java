package store.chinaotec.com.medicalcare.shopmarket.logic.address.model;

import java.io.Serializable;

public class DistrictModel implements Serializable {
    private String name;
    private String code;

    public DistrictModel() {
        super();
    }

    public DistrictModel(String name, String zipcode) {
        super();
        this.name = name;
        this.code = zipcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipcode() {
        return code;
    }

    public void setZipcode(String zipcode) {
        this.code = zipcode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return name;
    }


}
