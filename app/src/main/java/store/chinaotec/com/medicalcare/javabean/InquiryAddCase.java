package store.chinaotec.com.medicalcare.javabean;

/**
 * Created by seven on 2018/3/9 0009.
 */

public class InquiryAddCase {
    private String msg;
    private String responseCode;
    private DataBase data;

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

    public DataBase getData() {
        return data;
    }

    public void setData(DataBase data) {
        this.data = data;
    }

    public static class DataBase{
        private String sessionid;
        private String casecode;

        public String getSessionid() {
            return sessionid;
        }

        public void setSessionid(String sessionid) {
            this.sessionid = sessionid;
        }

        public String getCasecode() {
            return casecode;
        }

        public void setCasecode(String casecode) {
            this.casecode = casecode;
        }
    }
}
