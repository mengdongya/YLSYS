package store.chinaotec.com.medicalcare.shopmarket.logic.address.model;

import java.io.Serializable;
import java.util.List;

public class ProvinceModel implements Serializable {
    private String name;
    private String code;
    private List<CityModel> cityList;

    public ProvinceModel() {
        super();
    }

    public ProvinceModel(String name, String code, List<CityModel> cityList) {
        super();
        this.name = name;
        this.code = code;
        this.cityList = cityList;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityModel> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityModel> cityList) {
        this.cityList = cityList;
    }

    @Override
    public String toString() {
        return "ProvinceModel [name=" + name + ", code=" + code + ", cityList="
                + cityList + "]";
    }


}
