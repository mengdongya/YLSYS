package store.chinaotec.com.medicalcare.javabean;

import java.util.List;

/**
 * Created by wjc on 2018/3/2 0002.
 */

public class DiseaseSearchBean {
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
        private List<MemberSickDeals> memberSickDeals;

        public List<MemberSickDeals> getMemberSickDeals() {
            return memberSickDeals;
        }

        public void setMemberSickDeals(List<MemberSickDeals> memberSickDeals) {
            this.memberSickDeals = memberSickDeals;
        }

        public static class MemberSickDeals{
            private int id;
            private int medicalTypeId;
            private String sickDealTypeName;
            private String name;
            private String image;
            private int medicalType;
            private long createTime;
            private long updateTime;

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

            public String getSickDealTypeName() {
                return sickDealTypeName;
            }

            public void setSickDealTypeName(String sickDealTypeName) {
                this.sickDealTypeName = sickDealTypeName;
            }

            public int getMedicalType() {
                return medicalType;
            }

            public void setMedicalType(int medicalType) {
                this.medicalType = medicalType;
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

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

        }
    }
}
