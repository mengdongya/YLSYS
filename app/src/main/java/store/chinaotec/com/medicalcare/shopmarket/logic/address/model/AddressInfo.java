package store.chinaotec.com.medicalcare.shopmarket.logic.address.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 地址详情
 */
public class AddressInfo implements Parcelable {
    public static final Creator<AddressInfo> CREATOR = new Creator<AddressInfo>() {
        public AddressInfo createFromParcel(Parcel source) {
            AddressInfo addressInfo = new AddressInfo();
            addressInfo.addressId = source.readString();
            addressInfo.name = source.readString();
            addressInfo.phone = source.readString();

            addressInfo.provinceId = source.readString();
            addressInfo.cityId = source.readString();
            addressInfo.districtId = source.readString();

            addressInfo.provinceName = source.readString();
            addressInfo.cityName = source.readString();
            addressInfo.districtName = source.readString();

            addressInfo.addressInfo = source.readString();
            addressInfo.isDefault = source.readString();
            return addressInfo;
        }

        public AddressInfo[] newArray(int size) {
            return new AddressInfo[size];
        }
    };
    // 地址id
    public String addressId;
    // 联系人姓名
    public String name;
    // 联系人电话
    public String phone;
    // 省 ID
    public String provinceId;
    // 省 名字
    public String provinceName;
    // 市 Id
    public String cityId;
    // 市 名字
    public String cityName;
    // 区 Id
    public String districtId;
    // 区 名字
    public String districtName;
    // 地址(详细地址,无省市区)
    public String addressInfo;
    //是否默认
    public String isDefault;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(addressId);
        parcel.writeString(name);
        parcel.writeString(phone);

        parcel.writeString(provinceId);
        parcel.writeString(cityId);
        parcel.writeString(districtId);

        parcel.writeString(provinceName);
        parcel.writeString(cityName);
        parcel.writeString(districtName);

        parcel.writeString(addressInfo);
        parcel.writeString(isDefault);
    }

    @Override
    public String toString() {
        return "address_id = " + addressId + " , name = " + name + " , phone = " + phone + " , province_id = " + provinceId
                + " , province_name = " + provinceName + " , city_id = " + cityId + " , city_name = " + cityName + " , district_id = " + districtId +
                " , district_name = " + districtName + " , is_drfault = " + isDefault;

    }

}
