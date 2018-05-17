package store.chinaotec.com.medicalcare.javabean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hxk on 2017/7/12 0012 09:45
 * 一键呼叫联系人数据解析对象
 */

public class OneKeyPersBean implements Serializable{
    /**
     * msg : 请求数据成功!
     * responseCode : 0
     */

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

    public static class DataBean implements Serializable{
        private List<MemberCalloutListBean> memberCalloutList;

        public List<MemberCalloutListBean> getMemberCalloutList() {
            return memberCalloutList;
        }

        public void setMemberCalloutList(List<MemberCalloutListBean> memberCalloutList) {
            this.memberCalloutList = memberCalloutList;
        }

        public static class MemberCalloutListBean implements Serializable{
            /**
             * calloutName : 美美
             * memberCalloutId : 5
             * telephone : 333
             */

            private String calloutName;
            private int memberCalloutId;
            private String telephone;

            public String getCalloutName() {
                return calloutName;
            }

            public void setCalloutName(String calloutName) {
                this.calloutName = calloutName;
            }

            public int getMemberCalloutId() {
                return memberCalloutId;
            }

            public void setMemberCalloutId(int memberCalloutId) {
                this.memberCalloutId = memberCalloutId;
            }

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }
        }
    }
}
