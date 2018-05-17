package store.chinaotec.com.medicalcare.javabean;

import java.util.List;

/**
 * Created by wjc on 2017/9/18 0018.
 */
public class HospitalDetailResponse {
    private String msg;
    private String responseCode;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{
        private HospitalDetail hospitalDetail;
        private List<DoctorList> doctor;

        public HospitalDetail getHospitalDetail() {
            return hospitalDetail;
        }

        public void setHospitalDetail(HospitalDetail hospitalDetail) {
            this.hospitalDetail = hospitalDetail;
        }

        public List<DoctorList> getDoctor() {
            return doctor;
        }

        public void setDoctor(List<DoctorList> doctor) {
            this.doctor = doctor;
        }

        public static class HospitalDetail {
            private String ridingPaths;
            private String summary;
            private String starLevel;
            private String headImage;
            private String name;
            private String guidePaths;
            private String code;
            private String isCollect;
            private String landscapeImages;
            private String longitude;
            private String latitude;

            public String getRidingPaths() {
                return ridingPaths;
            }

            public void setRidingPaths(String ridingPaths) {
                this.ridingPaths = ridingPaths;
            }

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            public String getStarLevel() {
                return starLevel;
            }

            public void setStarLevel(String starLevel) {
                this.starLevel = starLevel;
            }

            public String getHeadImage() {
                return headImage;
            }

            public void setHeadImage(String headImage) {
                this.headImage = headImage;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getGuidePaths() {
                return guidePaths;
            }

            public void setGuidePaths(String guidePaths) {
                this.guidePaths = guidePaths;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getIsCollect() {
                return isCollect;
            }

            public void setIsCollect(String isCollect) {
                this.isCollect = isCollect;
            }

            public String getLandscapeImages() {
                return landscapeImages;
            }

            public void setLandscapeImages(String landscapeImages) {
                this.landscapeImages = landscapeImages;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {

                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }
        }

        public static class DoctorList{
            private int id;
            private String code;
            private String name;
            private String cityCode;
            private String doctorLabel;
            private String hospitalCode;
            private int hospitalOfficeId;
            private int status;
            private int appointment;
            private String starLevel;
            private String headImage;
            private String summary;
            private int contractFee;
            private int telephoneFee;
            private int imageTextSeekFee;
            private int videoFee;
            private long createTime;
            private long updateTime;

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

            public String getStarLevel() {
                return starLevel;
            }

            public void setStarLevel(String starLevel) {
                this.starLevel = starLevel;
            }

            public String getHeadImage() {
                return headImage;
            }

            public void setHeadImage(String headImage) {
                this.headImage = headImage;
            }

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            public int getContractFee() {
                return contractFee;
            }

            public void setContractFee(int contractFee) {
                this.contractFee = contractFee;
            }

            public int getTelephoneFee() {
                return telephoneFee;
            }

            public void setTelephoneFee(int telephoneFee) {
                this.telephoneFee = telephoneFee;
            }

            public int getImageTextSeekFee() {
                return imageTextSeekFee;
            }

            public void setImageTextSeekFee(int imageTextSeekFee) {
                this.imageTextSeekFee = imageTextSeekFee;
            }

            public int getVideoFee() {
                return videoFee;
            }

            public void setVideoFee(int videoFee) {
                this.videoFee = videoFee;
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
        }
    }

}
