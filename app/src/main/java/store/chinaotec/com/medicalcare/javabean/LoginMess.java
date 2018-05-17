package store.chinaotec.com.medicalcare.javabean;

/**
 * Created by hxk on 2017/7/17 0017 16:04
 * 用户登录,后台返回的数据解析
 */

public class LoginMess {
    /**
     * msg : 请求数据成功!
     * responseCode : 0
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
        /**
         * memberId : 8916
         * sid : 90po6bg3I3AC12G88z1Zr12NuS37U255
         * loginName : 17316334998
         * nickName : abc
         * imgPath :
         * epoints : 0
         * telephone : 17316334998
         * memberType : 1     1表示的是病人 2医生
         */

        private String memberId;
        private String sid;
        private String loginName;
        private String nickName;
        private String imgPath;
        private int epoints;
        private String telephone;
        private int memberType;

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getImgPath() {
            return imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public int getEpoints() {
            return epoints;
        }

        public void setEpoints(int epoints) {
            this.epoints = epoints;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public int getMemberType() {
            return memberType;
        }

        public void setMemberType(int memberType) {
            this.memberType = memberType;
        }
    }
}
