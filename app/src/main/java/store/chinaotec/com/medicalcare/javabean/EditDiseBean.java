package store.chinaotec.com.medicalcare.javabean;

/**
 * Created by Administrator on 2017/12/5 0005.
 * 修改用户的疾病种类返回的数据
 */

public class EditDiseBean {
    /**
     * msg : 请求数据成功!
     * responseCode : 0
     * data : {"id":161,"chronicId":56,"memberId":13432,"patientId":228,"createTime":1512463523000,"updateTime":1512466050304}
     */

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

    public static class DataBean {
        /**
         * id : 161
         * chronicId : 56
         * memberId : 13432
         * patientId : 228
         * createTime : 1512463523000
         * updateTime : 1512466050304
         */

        private int id;
        private String chronicId;
        private int memberId;
        private String patientId;
        private long createTime;
        private long updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getChronicId() {
            return chronicId;
        }

        public void setChronicId(String chronicId) {
            this.chronicId = chronicId;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public String getPatientId() {
            return patientId;
        }

        public void setPatientId(String patientId) {
            this.patientId = patientId;
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
