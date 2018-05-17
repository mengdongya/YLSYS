package store.chinaotec.com.medicalcare.javabean;

import java.util.List;

/**
 * Created by seven on 2018/1/16 0016.
 */
public class MyCollectList {
    private List<CollectionBean> data;
    private String msg;
    private String responseCode;

    public List<CollectionBean> getData() {
        return data;
    }

    public void setData(List<CollectionBean> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public static class CollectionBean{
        private int id;
        private String code;
        private String name;
        private String cityCode;
        private String doctorLabel;
        private String hospitalLevel;
        private int hospitalTypeId;
        private String starLevel;
        private String hospitalLabel;
        private String hospitalName;
        private int status;
        private int appointment;
        private String headImage;
        private String address;
        private long longitude;
        private long latitude;
        private String telephone;
        private String summary;
        private String landscapeImages;
        private String ridingPaths;
        private String guidePaths;
        private long createTime ;
        private long updateTime;
        private int memberId;
        private String hospitalCode;
        private int hospitalOfficeId;
        private String isAttention;
        private String contractFee;
        private String telephoneFee;
        private String imageTextSeekFee;
        private String videoFee;
        private String doctorCode;
        private String signAgreen;
        private String isCollect;

        public String getIsCollect() {
            return isCollect;
        }

        public void setIsCollect(String isCollect) {
            this.isCollect = isCollect;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getDoctorLabel() {
            return doctorLabel;
        }

        public void setDoctorLabel(String doctorLabel) {
            this.doctorLabel = doctorLabel;
        }

        public String getHospitalLevel() {
            return hospitalLevel;
        }

        public void setHospitalLevel(String hospitalLevel) {
            this.hospitalLevel = hospitalLevel;
        }

        public int getHospitalTypeId() {
            return hospitalTypeId;
        }

        public void setHospitalTypeId(int hospitalTypeId) {
            this.hospitalTypeId = hospitalTypeId;
        }

        public String getStarLevel() {
            return starLevel;
        }

        public void setStarLevel(String starLevel) {
            this.starLevel = starLevel;
        }

        public String getHospitalLabel() {
            return hospitalLabel;
        }

        public void setHospitalLabel(String hospitalLabel) {
            this.hospitalLabel = hospitalLabel;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getAppointment() {
            return appointment;
        }

        public void setAppointment(int appointment) {
            this.appointment = appointment;
        }

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public long getLongitude() {
            return longitude;
        }

        public void setLongitude(long longitude) {
            this.longitude = longitude;
        }

        public long getLatitude() {
            return latitude;
        }

        public void setLatitude(long latitude) {
            this.latitude = latitude;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getLandscapeImages() {
            return landscapeImages;
        }

        public void setLandscapeImages(String landscapeImages) {
            this.landscapeImages = landscapeImages;
        }

        public String getHospitalName() {
            return hospitalName;
        }

        public void setHospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
        }

        public String getRidingPaths() {
            return ridingPaths;
        }

        public void setRidingPaths(String ridingPaths) {
            this.ridingPaths = ridingPaths;
        }

        public String getGuidePaths() {
            return guidePaths;
        }

        public void setGuidePaths(String guidePaths) {
            this.guidePaths = guidePaths;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public String getHospitalCode() {
            return hospitalCode;
        }

        public void setHospitalCode(String hospitalCode) {
            this.hospitalCode = hospitalCode;
        }

        public int getHospitalOfficeId() {
            return hospitalOfficeId;
        }

        public void setHospitalOfficeId(int hospitalOfficeId) {
            this.hospitalOfficeId = hospitalOfficeId;
        }

        public String getIsAttention() {
            return isAttention;
        }

        public void setIsAttention(String isAttention) {
            this.isAttention = isAttention;
        }

        public String getContractFee() {
            return contractFee;
        }

        public void setContractFee(String contractFee) {
            this.contractFee = contractFee;
        }

        public String getTelephoneFee() {
            return telephoneFee;
        }

        public void setTelephoneFee(String telephoneFee) {
            this.telephoneFee = telephoneFee;
        }

        public String getImageTextSeekFee() {
            return imageTextSeekFee;
        }

        public void setImageTextSeekFee(String imageTextSeekFee) {
            this.imageTextSeekFee = imageTextSeekFee;
        }

        public String getVideoFee() {
            return videoFee;
        }

        public void setVideoFee(String videoFee) {
            this.videoFee = videoFee;
        }

        public String getDoctorCode() {
            return doctorCode;
        }

        public void setDoctorCode(String doctorCode) {
            this.doctorCode = doctorCode;
        }

        public String getSignAgreen() {
            return signAgreen;
        }

        public void setSignAgreen(String signAgreen) {
            this.signAgreen = signAgreen;
        }
    }
}
