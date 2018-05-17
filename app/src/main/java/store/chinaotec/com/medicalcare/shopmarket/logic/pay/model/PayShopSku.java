package store.chinaotec.com.medicalcare.shopmarket.logic.pay.model;

import java.util.ArrayList;

public class PayShopSku {

    private GotoOrderAddress gotoOrderAddressDto;
    private ArrayList<GotoOrderShops> gotoOrderStoreDtoList;
    private GotoOrderSummaryDto gotoOrderSummaryDto;

    public GotoOrderAddress getGotoOrderAddressDto() {
        return gotoOrderAddressDto;
    }

    public void setGotoOrderAddressDto(GotoOrderAddress gotoOrderAddressDto) {
        this.gotoOrderAddressDto = gotoOrderAddressDto;
    }

    public ArrayList<GotoOrderShops> getGotoOrderStoreDtoList() {
        return gotoOrderStoreDtoList;
    }

    public void setGotoOrderStoreDtoList(ArrayList<GotoOrderShops> gotoOrderStoreDtoList) {
        this.gotoOrderStoreDtoList = gotoOrderStoreDtoList;
    }

    public GotoOrderSummaryDto getGotoOrderSummaryDto() {
        return gotoOrderSummaryDto;
    }

    public void setGotoOrderSummaryDto(GotoOrderSummaryDto gotoOrderSummaryDto) {
        this.gotoOrderSummaryDto = gotoOrderSummaryDto;
    }

    public class GotoOrderSummaryDto {
        private String payOffId;
        private String totalPirce;
        private int quantityTotal;

        public String getPayOffId() {
            return payOffId;
        }

        public void setPayOffId(String payOffId) {
            this.payOffId = payOffId;
        }

        public int getQuantityTotal() {
            return quantityTotal;
        }

        public void setQuantityTotal(int quantityTotal) {
            this.quantityTotal = quantityTotal;
        }

        public String getTotalPirce() {
            return totalPirce;
        }

        public void setTotalPirce(String totalPirce) {
            this.totalPirce = totalPirce;
        }
    }

    public class GotoOrderAddress {
        private String defaultAddressId;
        private String contactor;
        private String province;
        private String city;
        private String district;
        private String provinceName;
        private String cityName;
        private String districtName;
        private String address;
        private String mobile;

        public String getDefaultAddressId() {
            return defaultAddressId;
        }

        public void setDefaultAddressId(String defaultAddressId) {
            this.defaultAddressId = defaultAddressId;
        }

        public String getContactor() {
            return contactor;
        }

        public void setContactor(String contactor) {
            this.contactor = contactor;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getDistrictName() {
            return districtName;
        }

        public void setDistrictName(String districtName) {
            this.districtName = districtName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
