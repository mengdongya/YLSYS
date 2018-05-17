package store.chinaotec.com.medicalcare.javabean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hxk on 2017/9/25 0025 15:53
 * 紧急求救医生javabean
 */

public class HelpDoctorBeean {
    /**
     * msg : 请求数据成功!
     * responseCode : 0
     * data : {"memberCalloutList":[{"calloutName":"美美","memberCalloutId":5,"telephone":"333"},{"calloutName":"妹妹11","memberCalloutId":4,"telephone":"34567"},{"calloutName":"妹妹","memberCalloutId":3,"telephone":"444"},{"calloutName":"妈妈","memberCalloutId":2,"telephone":"111"},{"calloutName":"爸爸","memberCalloutId":1,"telephone":"222"}]}
     */

    private String msg;
    private int responseCode;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
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
            private String memberCalloutId;
            private String telephone;

            public String getCalloutName() {
                return calloutName;
            }

            public void setCalloutName(String calloutName) {
                this.calloutName = calloutName;
            }

            public String getMemberCalloutId() {
                return memberCalloutId;
            }

            public void setMemberCalloutId(String memberCalloutId) {
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
