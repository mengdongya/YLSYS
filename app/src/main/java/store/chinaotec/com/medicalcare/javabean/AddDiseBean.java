package store.chinaotec.com.medicalcare.javabean;

/**
 * Created by hxk on 2017/10/18 0018 11:05
 * 添加完慢性病后返回的数据解析对象
 */

public class AddDiseBean {
    /**
     * msg : 请求数据成功!
     * responseCode : 0
     * data : {"id":6,"chronicId":35,"memberId":8931,"patientId":6,"createTime":1508297088727,"updateTime":null}
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
         * id : 6
         * chronicId : 35
         * memberId : 8931
         * patientId : 6
         * createTime : 1508297088727
         * updateTime : null
         */

        private int id;
        private int chronicId;
        private int memberId;
        private int patientId;
        private long createTime;
        private Object updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getChronicId() {
            return chronicId;
        }

        public void setChronicId(int chronicId) {
            this.chronicId = chronicId;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public int getPatientId() {
            return patientId;
        }

        public void setPatientId(int patientId) {
            this.patientId = patientId;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }
    }
}
