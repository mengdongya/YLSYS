package store.chinaotec.com.medicalcare.javabean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/22 0022.
 */

public class HospitalBean {
    private DataBean data;
    private String msg;
    private String responseCode;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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

    public static class DataBean {
        private List<HospitalListBean> hospitalList;
        private List<HospitalLevel> medicalLevelList;
        private List<HospitalLevel> medicalTypeList;
        private List<HospitalLevel> sortList;
        private List<MedicalAD> medicalADV;

        public List<MedicalAD> getMedicalADV() {
            return medicalADV;
        }

        public void setMedicalADV(List<MedicalAD> medicalADV) {
            this.medicalADV = medicalADV;
        }

        public List<HospitalLevel> getMedicalLevelList() {
            return medicalLevelList;
        }

        public void setMedicalLevelList(List<HospitalLevel> medicalLevelList) {
            this.medicalLevelList = medicalLevelList;
        }

        public List<HospitalLevel> getMedicalTypeList() {
            return medicalTypeList;
        }

        public void setMedicalTypeList(List<HospitalLevel> medicalTypeList) {
            this.medicalTypeList = medicalTypeList;
        }

        public List<HospitalListBean> getHospitalList() {
            return hospitalList;
        }

        public void setHospitalList(List<HospitalListBean> hospitalList) {
            this.hospitalList = hospitalList;
        }

        public List<HospitalLevel> getSortList() {
            return sortList;
        }

        public void setSortList(List<HospitalLevel> sortList) {
            this.sortList = sortList;
        }

        public static class HospitalLevel {
            private int id;
            private String typeName;
            private String typeImage;
            private String type;
            private long createTime;
            private long updateTime;
            private String name;
            private String key;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public String getTypeImage() {
                return typeImage;
            }

            public void setTypeImage(String typeImage) {
                this.typeImage = typeImage;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }
        }

        public static class HospitalListBean {
            private int appointment;
            private String code;
            private String headImage;
            private String hospitalLabel;
            private String name;
            private String starLevel;

            public int getAppointment() {
                return appointment;
            }

            public void setAppointment(int appointment) {
                this.appointment = appointment;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getHeadImage() {
                return headImage;
            }

            public void setHeadImage(String headImage) {
                this.headImage = headImage;
            }

            public String getHospitalLabel() {
                return hospitalLabel;
            }

            public void setHospitalLabel(String hospitalLabel) {
                this.hospitalLabel = hospitalLabel;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getStarLevel() {
                return starLevel;
            }

            public void setStarLevel(String starLevel) {
                this.starLevel = starLevel;
            }
        }

        public static class MedicalAD{
            private int id;
            private int medicalTypeId;
            private String name;
            private String img;
            private String url;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getMedicalTypeId() {
                return medicalTypeId;
            }

            public void setMedicalTypeId(int medicalTypeId) {
                this.medicalTypeId = medicalTypeId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
